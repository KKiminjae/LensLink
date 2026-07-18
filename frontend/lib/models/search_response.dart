import 'analysis.dart';
import 'product.dart';

class SearchResponse {
  final Analysis analysis;
  final List<Product> newProducts;
  final List<Product> usedProducts;

  const SearchResponse({
    required this.analysis,
    required this.newProducts,
    required this.usedProducts,
  });

  factory SearchResponse.fromJson(Map<String, dynamic> json) {
    return SearchResponse(
      analysis: Analysis.fromJson(json["analysis"]),
      newProducts: (json["newProducts"] as List)
          .map((e) => Product.fromJson(e))
          .toList(),
      usedProducts: (json["usedProducts"] as List)
          .map((e) => Product.fromJson(e))
          .toList(),
    );
  }
}