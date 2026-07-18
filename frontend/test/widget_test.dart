import 'package:flutter_test/flutter_test.dart';

import 'package:frontend/main.dart';

void main() {
  testWidgets('shows LensLink home screen', (WidgetTester tester) async {
    await tester.pumpWidget(const LensLinkApp());

    expect(find.text('LensLink'), findsOneWidget);
    expect(find.text('최근 검색'), findsOneWidget);
  });
}
