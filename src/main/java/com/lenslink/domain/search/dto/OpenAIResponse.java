package com.lenslink.domain.search.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OpenAIResponse {
    private List<Output> output;

    @Getter
    public static class Output{
        private List<Content> content;
    }

    @Getter
    public static class Content{
        private String text;
    }
}
