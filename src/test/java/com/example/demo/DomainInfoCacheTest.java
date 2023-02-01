package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DomainInfoCacheTest {

    @Mock
    private Map<Domain, CacheableDomainInfo> cache;

    private DomainInfoCache underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new DomainInfoCache(cache);
    }

    @Test
    @DisplayName("Given not cached value - When checkCache - Then should return 'Not in cache'")
    void checkCacheWithNotCachedValue() {
        // Given
        Domain domain = new Domain("foo.com");
        when(cache.get(domain)).thenReturn(null);

        // When
        String actual = underTest.checkCache(domain);

        // Then
        assertThat(actual).isEqualTo("Not in cache");
    }

    @Test
    @DisplayName("Given cached expired value - When checkCache - Then should return 'Not in cache'")
    void checkCacheWithCachedExpiredValue() {
        // Given
        Domain domain = new Domain("foo.com");
        String cachedValue = "bar";
        CacheableDomainInfo cacheAnswer = new CacheableDomainInfo(cachedValue, LocalDateTime.now().minusHours(2));
        when(cache.get(domain)).thenReturn(cacheAnswer);

        // When
        String actual = underTest.checkCache(domain);

        // Then
        assertThat(actual).isEqualTo("Not in cache");
    }

    @Test
    @DisplayName("Given cached not expired value - When checkCache - Then should return cached value")
    void checkCacheWithCachedNotExpiredValue() {
        // Given
        Domain domain = new Domain("foo.com");
        String cachedValue = "bar";
        CacheableDomainInfo cacheAnswer = new CacheableDomainInfo(cachedValue, LocalDateTime.now().plusHours(2));
        when(cache.get(domain)).thenReturn(cacheAnswer);

        // When
        String actual = underTest.checkCache(domain);

        // Then
        assertThat(actual).isEqualTo(cachedValue);
    }
}