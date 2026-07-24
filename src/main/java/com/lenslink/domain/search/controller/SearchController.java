package com.lenslink.domain.search.controller;

import com.lenslink.domain.search.dto.ProductResponse;
import com.lenslink.domain.search.dto.SearchHistoryResponse;
import com.lenslink.domain.search.dto.SearchResponse;
import com.lenslink.domain.search.service.SearchHistoryService;
import com.lenslink.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/searches")
public class SearchController {
    private final SearchService searchService;
    private final SearchHistoryService searchHistoryService;

    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SearchResponse analyze(@RequestParam("image")MultipartFile image){
        return searchService.search(image);
    }
}
