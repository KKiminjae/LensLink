package com.lenslink.domain.search.repository;

import com.lenslink.domain.search.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository
        extends JpaRepository<SearchHistory,Long> {
}
