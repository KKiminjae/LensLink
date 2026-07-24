package com.lenslink.domain.search.repository;

import com.lenslink.domain.search.entity.SearchHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository
        extends JpaRepository<SearchHistory,Long> {
    List<SearchHistory> findTop3ByOrderByCreatedAtDesc();

    Page<SearchHistory> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
