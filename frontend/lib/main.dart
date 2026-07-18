import 'package:flutter/material.dart';

import 'core/theme/app_theme.dart';
import 'screens/home/home_page.dart';

void main() {
  runApp(const LensLinkApp());
}

class LensLinkApp extends StatelessWidget {
  const LensLinkApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'LensLink',
      debugShowCheckedModeBanner: false,
      theme: AppTheme.lightTheme,
      home: const HomePage(),
    );
  }
}