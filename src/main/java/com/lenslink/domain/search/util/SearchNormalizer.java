package com.lenslink.domain.search.util;

public final class SearchNormalizer {
    private SearchNormalizer(){}

    public static String normalize(String text){
        if (text == null) {
            return "";
        }
        return text
                .trim()
                .toLowerCase()
                .replaceAll("[®™©]", "")
                .replaceAll("[^\\p{L}\\p{N}\\s]", "")
                .replaceAll("\\s+", " ");
    }

    public static boolean isUnknown(String value){
        return value == null
                || value.isBlank()
                || value.equalsIgnoreCase("Unknown");
    }
}
