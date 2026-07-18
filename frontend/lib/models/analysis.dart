class Analysis {
  final String brand;
  final String productName;
  final String category;
  final String color;

  const Analysis({
    required this.brand,
    required this.productName,
    required this.category,
    required this.color,
  });

  factory Analysis.fromJson(Map<String, dynamic> json) {
    return Analysis(
      brand: json["brand"] ?? "",
      productName: json["productName"] ?? "",
      category: json["category"] ?? "",
      color: json["color"] ?? "",
    );
  }
}