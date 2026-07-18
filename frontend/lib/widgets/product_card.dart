import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';

import '../core/constants/app_colors.dart';
import '../models/product.dart';

class ProductCard extends StatelessWidget {
  final Product product;

  const ProductCard({super.key, required this.product});

  String get _formattedPrice {
    final rawPrice = product.price.toString();
    final digits = rawPrice.replaceAll(RegExp(r'[^0-9]'), '');

    if (digits.isEmpty) {
      return rawPrice.isEmpty ? "가격 정보 없음" : rawPrice;
    }

    final buffer = StringBuffer();

    for (int i = 0; i < digits.length; i++) {
      final remaining = digits.length - i;

      buffer.write(digits[i]);

      if (remaining > 1 && remaining % 3 == 1) {
        buffer.write(',');
      }
    }

    return "$buffer원";
  }

  Future<void> _openLink() async {
    if (product.productUrl.isEmpty) return;

    final uri = Uri.parse(product.productUrl);

    if (await canLaunchUrl(uri)) {
      await launchUrl(uri, mode: LaunchMode.externalApplication);
    }
  }

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: _openLink,
      child: Container(
        padding: const EdgeInsets.symmetric(vertical: 12),
        decoration: const BoxDecoration(
          border: Border(bottom: BorderSide(color: AppColors.border)),
        ),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _ProductImage(imageUrl: product.imageUrl),
            const SizedBox(width: 12),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    product.productName,
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                    style: const TextStyle(
                      color: AppColors.text,
                      fontSize: 13,
                      height: 1.25,
                      fontWeight: FontWeight.w800,
                    ),
                  ),
                  const SizedBox(height: 6),
                  Text(
                    _formattedPrice,
                    style: const TextStyle(
                      color: AppColors.text,
                      fontSize: 17,
                      fontWeight: FontWeight.w900,
                    ),
                  ),
                  const SizedBox(height: 5),
                  Text(
                    product.mall.isEmpty ? "몰 정보 없음" : product.mall,
                    style: const TextStyle(
                      color: AppColors.subText,
                      fontSize: 11,
                    ),
                  ),
                ],
              ),
            ),
            const SizedBox(width: 8),
            IconButton(
              onPressed: _openLink,
              icon: const Icon(Icons.open_in_new),
              color: AppColors.subText,
              iconSize: 18,
              visualDensity: VisualDensity.compact,
            ),
          ],
        ),
      ),
    );
  }
}

class _ProductImage extends StatelessWidget {
  final String imageUrl;

  const _ProductImage({required this.imageUrl});

  @override
  Widget build(BuildContext context) {
    if (imageUrl.isEmpty) {
      return Container(
        width: 70,
        height: 70,
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(7),
          border: Border.all(color: AppColors.border),
        ),
        child: const Icon(
          Icons.image_not_supported_outlined,
          color: AppColors.subText,
        ),
      );
    }

    return ClipRRect(
      borderRadius: BorderRadius.circular(7),
      child: Image.network(imageUrl, width: 70, height: 70, fit: BoxFit.cover),
    );
  }
}
