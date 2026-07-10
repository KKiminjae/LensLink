package com.lenslink.domain.search.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MusinsaResponse {

    private Data data;

    @Getter
    @NoArgsConstructor
    public static class Data{
        private List<Goods> list;
    }

    @Getter
    @NoArgsConstructor
    public static class Goods{
        private String goodsName;
        private String goodsLinkUrl;
        private String thumbnail;
        private String brandName;
    }
}
