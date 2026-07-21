package com.lenslink.domain.search.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchNormalizerTest {
    @Test
    void 특수문자변환(){
        String result = SearchNormalizer.normalize(" Nike® Air  ");

        assertEquals("nike air", result);
    }

    @Test
    void 공백문자변환(){
        String result = SearchNormalizer.normalize("Nike     Air");
        assertEquals("nike air", result);
    }

    @Test
    void null값변환(){
        assertEquals("",SearchNormalizer.normalize(null));
    }

    @Test
    void isUnknow(){
        assertTrue(SearchNormalizer.isUnknown("Unknown"));
        assertTrue(SearchNormalizer.isUnknown("unknown"));
        assertTrue(SearchNormalizer.isUnknown(""));
        assertTrue(SearchNormalizer.isUnknown(" "));
        assertTrue(SearchNormalizer.isUnknown(null));
    }

}