package com.lenslink.domain.search.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class NaverShoppingResponse {

    private int total;
    private List<Item> items;

    @Getter
    public static class Item{

        private String title;

        private String link;

        private String image;

        private String lprice;

        private String mallName;

        private String brand;
    }
}
