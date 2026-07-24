package com.lenslink.domain.search.controller;

import com.lenslink.domain.search.dto.SearchHistoryResponse;
import com.lenslink.domain.search.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/searches/history")
@RequiredArgsConstructor
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;

    @GetMapping("/recent")
    public List<SearchHistoryResponse> getRecentSearches(){
        return searchHistoryService.getRecentSearches();
    }

    @GetMapping
    public Page<SearchHistoryResponse> getAllSearchHistory(
            @PageableDefault(
                    size = 30,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC)
            Pageable pageable){
        return searchHistoryService.getAllSearchHistory(pageable);
    }
}
