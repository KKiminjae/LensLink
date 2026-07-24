package com.lenslink.domain.search.service;

import com.lenslink.domain.search.dto.SearchHistoryResponse;
import com.lenslink.domain.search.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;

    public List<SearchHistoryResponse> getRecentSearches(){
        return searchHistoryRepository.findTop3ByOrderByCreatedAtDesc()
                .stream()
                .map(SearchHistoryResponse::from)
                .toList();
    }

    public Page<SearchHistoryResponse> getAllSearchHistory(Pageable pageable){
        return searchHistoryRepository
                .findAllByOrderByCreatedAtDesc(pageable)
                .map(SearchHistoryResponse::from);
    }
}
