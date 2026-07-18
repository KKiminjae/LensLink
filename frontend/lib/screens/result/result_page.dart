import 'package:flutter/material.dart';

import '../../core/constants/app_colors.dart';
import '../../models/product.dart';
import '../../models/search_response.dart';
import '../../widgets/confidence_indicator.dart';
import '../../widgets/product_card.dart';

enum _ProductFilter { all, used, newProduct }

class ResultPage extends StatefulWidget {
  final SearchResponse result;

  const ResultPage({super.key, required this.result});

  @override
  State<ResultPage> createState() => _ResultPageState();
}

class _ResultPageState extends State<ResultPage> {
  _ProductFilter _filter = _ProductFilter.all;
  int _selectedPlatformIndex = 0;

  final List<_PlatformTab> _platformTabs = const [
    _PlatformTab(name: "후루츠", count: 8, icon: Icons.local_mall),
    _PlatformTab(name: "번개장터", count: 6, icon: Icons.bolt),
    _PlatformTab(name: "무신사", count: 4, icon: Icons.shopping_bag),
    _PlatformTab(name: "EQL", count: 3, icon: Icons.storefront),
  ];

  List<Product> get _allProducts => [
    ...widget.result.usedProducts,
    ...widget.result.newProducts,
  ];

  List<Product> get _products {
    switch (_filter) {
      case _ProductFilter.used:
        return widget.result.usedProducts;
      case _ProductFilter.newProduct:
        return widget.result.newProducts;
      case _ProductFilter.all:
        return _allProducts;
    }
  }

  Product? get _summaryProduct {
    if (_allProducts.isEmpty) return null;
    return _allProducts.first;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(
        centerTitle: true,
        leading: IconButton(
          onPressed: () => Navigator.pop(context),
          icon: const Icon(Icons.arrow_back),
        ),
        title: const Text(
          "검색 결과",
          style: TextStyle(fontSize: 15, fontWeight: FontWeight.w800),
        ),
      ),
      body: SafeArea(
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.fromLTRB(20, 8, 20, 16),
              child: _ResultSummaryCard(
                result: widget.result,
                product: _summaryProduct,
              ),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: _FilterRow(
                selected: _filter,
                totalCount: _allProducts.length,
                usedCount: widget.result.usedProducts.length,
                newCount: widget.result.newProducts.length,
                onChanged: (filter) {
                  setState(() {
                    _filter = filter;
                  });
                },
              ),
            ),
            const SizedBox(height: 14),
            SizedBox(
              height: 40,
              child: ListView.separated(
                padding: const EdgeInsets.symmetric(horizontal: 20),
                scrollDirection: Axis.horizontal,
                itemBuilder: (context, index) {
                  final platform = _platformTabs[index];

                  return _PlatformButton(
                    platform: platform,
                    selected: _selectedPlatformIndex == index,
                    onTap: () {
                      setState(() {
                        _selectedPlatformIndex = index;
                      });
                    },
                  );
                },
                separatorBuilder: (context, index) => const SizedBox(width: 10),
                itemCount: _platformTabs.length,
              ),
            ),
            const SizedBox(height: 4),
            Expanded(
              child: _products.isEmpty
                  ? const _EmptyResult()
                  : ListView.builder(
                      padding: const EdgeInsets.fromLTRB(20, 0, 20, 20),
                      itemCount: _products.length,
                      itemBuilder: (context, index) {
                        return ProductCard(product: _products[index]);
                      },
                    ),
            ),
          ],
        ),
      ),
    );
  }
}

class _ResultSummaryCard extends StatelessWidget {
  final SearchResponse result;
  final Product? product;

  const _ResultSummaryCard({required this.result, required this.product});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: AppColors.border),
      ),
      child: Row(
        children: [
          _SummaryThumbnail(imageUrl: product?.imageUrl ?? ""),
          const SizedBox(width: 12),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  result.analysis.productName,
                  maxLines: 2,
                  overflow: TextOverflow.ellipsis,
                  style: const TextStyle(
                    color: AppColors.text,
                    fontSize: 13,
                    fontWeight: FontWeight.w900,
                  ),
                ),
                const SizedBox(height: 6),
                const Row(
                  children: [
                    Text(
                      "신뢰도 ",
                      style: TextStyle(
                        color: AppColors.text,
                        fontSize: 12,
                        fontWeight: FontWeight.w600,
                      ),
                    ),
                    ConfidenceIndicator(confidence: 92, compact: true),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class _SummaryThumbnail extends StatelessWidget {
  final String imageUrl;

  const _SummaryThumbnail({required this.imageUrl});

  @override
  Widget build(BuildContext context) {
    if (imageUrl.isEmpty) {
      return Container(
        width: 54,
        height: 54,
        decoration: BoxDecoration(
          color: AppColors.background,
          borderRadius: BorderRadius.circular(7),
          border: Border.all(color: AppColors.border),
        ),
        child: const Icon(Icons.image_outlined, color: AppColors.subText),
      );
    }

    return ClipRRect(
      borderRadius: BorderRadius.circular(7),
      child: Image.network(imageUrl, width: 54, height: 54, fit: BoxFit.cover),
    );
  }
}

class _FilterRow extends StatelessWidget {
  final _ProductFilter selected;
  final int totalCount;
  final int usedCount;
  final int newCount;
  final ValueChanged<_ProductFilter> onChanged;

  const _FilterRow({
    required this.selected,
    required this.totalCount,
    required this.usedCount,
    required this.newCount,
    required this.onChanged,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        _FilterButton(
          label: "전체 ($totalCount)",
          selected: selected == _ProductFilter.all,
          onTap: () => onChanged(_ProductFilter.all),
        ),
        const SizedBox(width: 8),
        _FilterButton(
          label: "중고 ($usedCount)",
          selected: selected == _ProductFilter.used,
          onTap: () => onChanged(_ProductFilter.used),
        ),
        const SizedBox(width: 8),
        _FilterButton(
          label: "새상품 ($newCount)",
          selected: selected == _ProductFilter.newProduct,
          onTap: () => onChanged(_ProductFilter.newProduct),
        ),
      ],
    );
  }
}

class _FilterButton extends StatelessWidget {
  final String label;
  final bool selected;
  final VoidCallback onTap;

  const _FilterButton({
    required this.label,
    required this.selected,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: GestureDetector(
        onTap: onTap,
        child: Container(
          height: 34,
          decoration: BoxDecoration(
            color: Colors.white,
            border: Border(
              bottom: BorderSide(
                color: selected ? AppColors.primary : Colors.transparent,
                width: 2,
              ),
            ),
          ),
          child: Center(
            child: Text(
              label,
              style: TextStyle(
                color: selected ? AppColors.text : AppColors.subText,
                fontSize: 11,
                fontWeight: selected ? FontWeight.w800 : FontWeight.w600,
              ),
            ),
          ),
        ),
      ),
    );
  }
}

class _PlatformButton extends StatelessWidget {
  final _PlatformTab platform;
  final bool selected;
  final VoidCallback onTap;

  const _PlatformButton({
    required this.platform,
    required this.selected,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        padding: const EdgeInsets.symmetric(horizontal: 10),
        decoration: BoxDecoration(
          color: selected
              ? AppColors.primary.withValues(alpha: 0.08)
              : Colors.white,
          border: Border(
            bottom: BorderSide(
              color: selected ? AppColors.primary : Colors.transparent,
              width: 2,
            ),
          ),
        ),
        child: Row(
          children: [
            CircleAvatar(
              radius: 10,
              backgroundColor: selected ? AppColors.primary : AppColors.text,
              child: Icon(platform.icon, color: Colors.white, size: 11),
            ),
            const SizedBox(width: 6),
            Text(
              "${platform.name} ${platform.count}",
              style: TextStyle(
                color: selected ? AppColors.primary : AppColors.text,
                fontSize: 11,
                fontWeight: FontWeight.w800,
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _EmptyResult extends StatelessWidget {
  const _EmptyResult();

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(Icons.search_off, size: 56, color: Colors.grey),
          SizedBox(height: 16),
          Text(
            "검색 결과가 없습니다.",
            style: TextStyle(fontSize: 16, fontWeight: FontWeight.w800),
          ),
        ],
      ),
    );
  }
}

class _PlatformTab {
  final String name;
  final int count;
  final IconData icon;

  const _PlatformTab({
    required this.name,
    required this.count,
    required this.icon,
  });
}
