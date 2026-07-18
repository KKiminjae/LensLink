class Product {
  final String brand;
  final String productName;
  final int price;
  final String platform;
  final String mall;
  final String productUrl;
  final String imageUrl;
  //final bool soldOut;
  final bool used;

  const Product({
    required this.brand,
    required this.productName,
    required this.price,
    required this.platform,
    required this.mall,
    required this.productUrl,
    required this.imageUrl,
    //required this.soldOut,
    required this.used,
  });

  factory Product.fromJson(Map<String, dynamic> json) {
    return Product(
      brand: json["brand"] ?? "",
      productName: json["productName"] ?? "",
      price: json["price"] ?? 0,
      platform: json["platform"] ?? "",
      mall: json["mall"] ?? "",
      productUrl: json["productUrl"] ?? "",
      imageUrl: json["imageUrl"] ?? "",
      // soldOut: json["soldOut"] ?? false,
      used: json["used"] ?? false,
    );
  }
}
