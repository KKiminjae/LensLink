package com.lenslink.domain.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Mall {

    MUSINSA(
            "무신사"
    ),
    KREAM(
            "크림"
    ),
    FRUITS_FAMILY(
            "후루츠패밀리"
    );
    private final String displayName;

    Mall(String displayName) {
        this.displayName = displayName;
    }
}
