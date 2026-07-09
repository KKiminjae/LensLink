package com.lenslink.domain.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Mall {

    MUSINSA(
            "무신사",
            "http://"
    ),
    KREAM(
            "크림",
            "http://"
    ),
    FRUITS_FAMILY(
            "후루츠패밀리",
            "http://"
    );
    private final String displayName;
    private final String searchUrl;

    Mall(String displayName, String searchUrl) {
        this.displayName = displayName;
        this.searchUrl = searchUrl;
    }
}
