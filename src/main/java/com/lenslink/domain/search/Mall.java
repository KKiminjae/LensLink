package com.lenslink.domain.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Mall {

    MUSINSA(
            "무신사",
            "https://www.musinsa.com/search/goods?keyword="
    ),
    KREAM(
            "크림",
            "https://kream.co.kr/search?keyword="
    ),
    FRUITS_FAMILY(
            "후루츠패밀리",
            "https://fruitsfamily.com/search/"
    );
    private final String displayName;
    private final String searchUrl;

    Mall(String displayName, String searchUrl) {
        this.displayName = displayName;
        this.searchUrl = searchUrl;
    }
}
