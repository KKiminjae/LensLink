package com.lenslink.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
public class OpenAIRequest {
    private String model;

    private List<Input> input;

    @Getter
    @Builder
    public static class Input{
        private String role;

        private List<Content> content;
    }

    @Getter
    public abstract static class Content{
        protected final String type;

        protected Content(String type) {
            this.type = type;
        }
    }
    @Getter
    public static class InputImage extends Content{
        private final String image_url;
        public InputImage(String imageUrl) {
            super("input_image");
            this.image_url = imageUrl;
        }
    }
    @Getter
    public static class InputText extends Content{
        private final String text;

        public InputText(String text) {
            super("input_text");
            this.text = text;
        }
    }


}

