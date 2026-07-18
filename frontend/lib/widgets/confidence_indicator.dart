import 'package:flutter/material.dart';

import '../core/constants/app_colors.dart';

class ConfidenceIndicator extends StatelessWidget {
  final int confidence;
  final bool compact;

  const ConfidenceIndicator({
    super.key,
    required this.confidence,
    this.compact = false,
  });

  Color get _color {
    if (confidence >= 80) return const Color(0xFF16A34A);
    if (confidence >= 50) return const Color(0xFFF59E0B);
    return const Color(0xFFEF4444);
  }

  String get _label {
    if (confidence >= 80) return "신뢰도 높음";
    if (confidence >= 50) return "신뢰도 보통";
    return "신뢰도 낮음";
  }

  @override
  Widget build(BuildContext context) {
    if (compact) {
      return Text(
        "$confidence%",
        style: TextStyle(
          color: _color,
          fontSize: 13,
          fontWeight: FontWeight.w700,
        ),
      );
    }

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Row(
          children: [
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
              decoration: BoxDecoration(
                color: _color.withValues(alpha: 0.08),
                borderRadius: BorderRadius.circular(5),
                border: Border.all(color: _color),
              ),
              child: Text(
                _label,
                style: TextStyle(
                  color: _color,
                  fontSize: 11,
                  fontWeight: FontWeight.w700,
                ),
              ),
            ),
            const Spacer(),
            Text(
              "$confidence%",
              style: TextStyle(
                color: _color,
                fontSize: 20,
                fontWeight: FontWeight.w800,
              ),
            ),
          ],
        ),
        const SizedBox(height: 8),
        ClipRRect(
          borderRadius: BorderRadius.circular(8),
          child: LinearProgressIndicator(
            value: confidence / 100,
            minHeight: 6,
            color: _color,
            backgroundColor: AppColors.border,
          ),
        ),
      ],
    );
  }
}
