import 'dart:io';

import 'package:dio/dio.dart';

import '../models/search_response.dart';

class ApiService {
  final Dio _dio = Dio(
    BaseOptions(
      baseUrl: "http://localhost:8080",
      connectTimeout: const Duration(seconds: 10),
      receiveTimeout: const Duration(seconds: 60),
    ),
  );

  Future<SearchResponse> analyzeImage(File image) async {
    final formData = FormData.fromMap({
      "image": await MultipartFile.fromFile(
        image.path,
        filename: image.path.split("/").last,
      ),
    });

    final response = await _dio.post(
      "/api/searches/analyze",
      data: formData,
    );

    return SearchResponse.fromJson(response.data);
  }
}