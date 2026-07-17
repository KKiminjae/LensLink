package com.lenslink.domain.search.controller;

import com.lenslink.domain.search.dto.ProductResponse;
import com.lenslink.domain.search.dto.SearchResponse;
import com.lenslink.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/searches")
public class SearchController {
    private final SearchService searchService;

    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SearchResponse analyze(@RequestParam("image")MultipartFile image){
        return searchService.search(image);
    }
}
