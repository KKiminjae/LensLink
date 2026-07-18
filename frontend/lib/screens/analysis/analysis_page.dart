import 'dart:io';

import 'package:flutter/material.dart';

import '../../core/constants/app_colors.dart';
import '../../models/product.dart';
import '../../models/search_response.dart';
import '../../services/api_service.dart';
import '../../widgets/confidence_indicator.dart';
import '../result/result_page.dart';

class AnalysisPage extends StatefulWidget {
  final File image;

  const AnalysisPage({super.key, required this.image});

  @override
  State<AnalysisPage> createState() => _AnalysisPageState();
}

class _AnalysisPageState extends State<AnalysisPage> {
  final ApiService _apiService = ApiService();

  bool _loading = true;
  SearchResponse? _result;
  String? _error;

  @override
  void initState() {
    super.initState();
    _analyzeImage();
  }

  Future<void> _analyzeImage() async {
    setState(() {
      _loading = true;
      _result = null;
      _error = null;
    });

    try {
      final result = await _apiService.analyzeImage(widget.image);

      if (!mounted) return;

      setState(() {
        _loading = false;
        _result = result;
      });
    } catch (e) {
      if (!mounted) return;

      setState(() {
        _loading = false;
        _error = e.toString();
      });
    }
  }

  void _goToResult() {
    if (_result == null) return;

    Navigator.pushReplacement(
      context,
      MaterialPageRoute(builder: (_) => ResultPage(result: _result!)),
    );
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
          "분석 결과",
          style: TextStyle(fontSize: 15, fontWeight: FontWeight.w800),
        ),
      ),
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.fromLTRB(20, 8, 20, 24),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              _ImagePreview(image: widget.image),
              const SizedBox(height: 18),
              if (_loading)
                const _LoadingAnalysis()
              else if (_error != null)
                _ErrorAnalysis(error: _error!, onRetry: _analyzeImage)
              else
                _AnalysisResult(result: _result!, onShowResult: _goToResult),
            ],
          ),
        ),
      ),
    );
  }
}

class _ImagePreview extends StatelessWidget {
  final File image;

  const _ImagePreview({required this.image});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: ClipRRect(
        borderRadius: BorderRadius.circular(8),
        child: Image.file(image, width: 160, height: 160, fit: BoxFit.cover),
      ),
    );
  }
}

class _LoadingAnalysis extends StatelessWidget {
  const _LoadingAnalysis();

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Padding(
        padding: EdgeInsets.only(top: 40),
        child: Column(
          children: [
            CircularProgressIndicator(),
            SizedBox(height: 18),
            Text(
              "AI가 이미지를 분석하고 있습니다.",
              style: TextStyle(fontSize: 16, fontWeight: FontWeight.w800),
            ),
            SizedBox(height: 8),
            Text(
              "잠시만 기다려주세요.",
              style: TextStyle(color: AppColors.subText, fontSize: 13),
            ),
          ],
        ),
      ),
    );
  }
}

class _ErrorAnalysis extends StatelessWidget {
  final String error;
  final VoidCallback onRetry;

  const _ErrorAnalysis({required this.error, required this.onRetry});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Padding(
        padding: const EdgeInsets.only(top: 36),
        child: Column(
          children: [
            const Icon(Icons.error_outline, color: Colors.red, size: 52),
            const SizedBox(height: 16),
            const Text(
              "분석에 실패했습니다.",
              style: TextStyle(fontSize: 17, fontWeight: FontWeight.w800),
            ),
            const SizedBox(height: 8),
            Text(
              error,
              textAlign: TextAlign.center,
              style: const TextStyle(color: AppColors.subText, fontSize: 12),
            ),
            const SizedBox(height: 20),
            FilledButton(onPressed: onRetry, child: const Text("다시 시도")),
          ],
        ),
      ),
    );
  }
}

class _AnalysisResult extends StatelessWidget {
  final SearchResponse result;
  final VoidCallback onShowResult;

  const _AnalysisResult({required this.result, required this.onShowResult});

  List<Product> get _similarProducts =>
      [...result.newProducts, ...result.usedProducts].take(3).toList();

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          result.analysis.productName,
          style: const TextStyle(
            color: AppColors.text,
            fontSize: 18,
            fontWeight: FontWeight.w900,
          ),
        ),
        const SizedBox(height: 10),
        const ConfidenceIndicator(confidence: 92),
        const SizedBox(height: 20),
        _InfoRow(label: "브랜드", value: result.analysis.brand),
        const SizedBox(height: 12),
        _InfoRow(label: "카테고리", value: result.analysis.category),
        const SizedBox(height: 24),
        Row(
          children: [
            const Text(
              "유사 제품 (신뢰도 순)",
              style: TextStyle(fontSize: 15, fontWeight: FontWeight.w900),
            ),
            const Spacer(),
            TextButton(
              onPressed: onShowResult,
              style: TextButton.styleFrom(
                padding: EdgeInsets.zero,
                minimumSize: const Size(46, 32),
                tapTargetSize: MaterialTapTargetSize.shrinkWrap,
              ),
              child: const Text(
                "더보기 >",
                style: TextStyle(color: AppColors.text, fontSize: 12),
              ),
            ),
          ],
        ),
        const SizedBox(height: 8),
        if (_similarProducts.isEmpty)
          const _EmptySimilarProducts()
        else
          ..._similarProducts.indexed.map(
            (entry) => _SimilarProductTile(
              product: entry.$2,
              confidence: 92 - (entry.$1 * 14),
            ),
          ),
        const SizedBox(height: 18),
        SizedBox(
          width: double.infinity,
          height: 50,
          child: FilledButton(
            onPressed: onShowResult,
            style: FilledButton.styleFrom(
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(7),
              ),
            ),
            child: const Text(
              "검색 결과 보기",
              style: TextStyle(fontSize: 15, fontWeight: FontWeight.w800),
            ),
          ),
        ),
      ],
    );
  }
}

class _InfoRow extends StatelessWidget {
  final String label;
  final String value;

  const _InfoRow({required this.label, required this.value});

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        SizedBox(
          width: 64,
          child: Text(
            label,
            style: const TextStyle(
              color: AppColors.subText,
              fontSize: 12,
              fontWeight: FontWeight.w700,
            ),
          ),
        ),
        Expanded(
          child: Text(
            value.isEmpty ? "-" : value,
            style: const TextStyle(
              color: AppColors.text,
              fontSize: 12,
              fontWeight: FontWeight.w700,
            ),
          ),
        ),
      ],
    );
  }
}

class _SimilarProductTile extends StatelessWidget {
  final Product product;
  final int confidence;

  const _SimilarProductTile({required this.product, required this.confidence});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 12),
      child: Row(
        children: [
          _ProductThumbnail(imageUrl: product.imageUrl),
          const SizedBox(width: 12),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  product.productName,
                  maxLines: 1,
                  overflow: TextOverflow.ellipsis,
                  style: const TextStyle(
                    fontSize: 12,
                    fontWeight: FontWeight.w800,
                  ),
                ),
                const SizedBox(height: 4),
                Text(
                  "$confidence%",
                  style: const TextStyle(
                    color: AppColors.subText,
                    fontSize: 11,
                  ),
                ),
              ],
            ),
          ),
          ConfidenceIndicator(confidence: confidence, compact: true),
        ],
      ),
    );
  }
}

class _ProductThumbnail extends StatelessWidget {
  final String imageUrl;

  const _ProductThumbnail({required this.imageUrl});

  @override
  Widget build(BuildContext context) {
    if (imageUrl.isEmpty) {
      return Container(
        width: 40,
        height: 40,
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(7),
          border: Border.all(color: AppColors.border),
        ),
        child: const Icon(
          Icons.image_not_supported_outlined,
          size: 20,
          color: AppColors.subText,
        ),
      );
    }

    return ClipRRect(
      borderRadius: BorderRadius.circular(7),
      child: Image.network(imageUrl, width: 40, height: 40, fit: BoxFit.cover),
    );
  }
}

class _EmptySimilarProducts extends StatelessWidget {
  const _EmptySimilarProducts();

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.symmetric(vertical: 20),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: AppColors.border),
      ),
      child: const Center(
        child: Text(
          "유사 제품이 없습니다.",
          style: TextStyle(color: AppColors.subText, fontSize: 12),
        ),
      ),
    );
  }
}
