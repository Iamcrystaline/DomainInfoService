package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DomainInfoServiceTest {

    @Mock
    private DomainInfoRequester domainInfoRequester;

    @Mock
    private DomainPriceInfoRequester domainPriceInfoRequester;

    @Mock
    private DomainInfoCache cache;

    @Mock
    private DomainInfoParser domainInfoParser;

    @Mock
    private DomainPriceInfoParser domainPriceInfoParser;

    private DomainInfoService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new DomainInfoService(
                domainInfoRequester,
                domainPriceInfoRequester,
                cache,
                domainInfoParser,
                domainPriceInfoParser
                );
    }

    @Test
    @DisplayName("Given cached domain name - When getDomainInfo - Then should return from cache")
    void getDomainInfoWithCachedDomain() {
        // Given
        String domainName = "foo";
        String cacheAnswer = "bar";
        when(cache.checkCache(domainName)).thenReturn(cacheAnswer);

        // When
        String actual = underTest.getDomainInfo(domainName);

        // Then
        verify(domainInfoRequester, never()).requestDomainInfoApi(any());
        verify(domainInfoParser, never()).parseDomainInfo(any());
        verify(domainPriceInfoParser, never()).parseDomainPriceInfo(any());
        verify(domainPriceInfoRequester, never()).requestDomainPriceInfoApi();
        assertThat(actual).isEqualTo(cacheAnswer);
    }

    @Test
    @DisplayName("Given not cached existing domain name - When getDomainInfo - Then should request domain info requester and parser")
    void getDomainInfoWithNotCachedExistingDomain() {
        // Given
        String domainName = "foo";
        String parserAnswer = "parsed bar";
        String requesterAnswer = "bar";
        when(cache.checkCache(domainName)).thenReturn("Not in cache");
        when(domainInfoRequester.requestDomainInfoApi(domainName)).thenReturn(requesterAnswer);
        when(domainInfoParser.parseDomainInfo(requesterAnswer)).thenReturn(parserAnswer);

        // When
        String actual = underTest.getDomainInfo(domainName);

        // Then
        verify(domainPriceInfoParser, never()).parseDomainPriceInfo(any());
        verify(domainPriceInfoRequester, never()).requestDomainPriceInfoApi();
        assertThat(actual).isEqualTo(parserAnswer);
    }

    @Test
    @DisplayName("Given not cached not existing domain name - When getDomainInfo - Then should request all services")
    void getDomainInfoWithNotCachedNotExistingDomain() {
        // Given
        String domainName = "foo";
        String domainInfoParserAnswer = "Not found";
        String domainInfoRequesterAnswer = "bar";
        String domainPriceInfoParserAnswer = "parsed buzz";
        String domainPriceInfoRequesterAnswer = "buzz";
        when(cache.checkCache(domainName)).thenReturn("Not in cache");
        when(domainInfoRequester.requestDomainInfoApi(domainName)).thenReturn(domainInfoRequesterAnswer);
        when(domainInfoParser.parseDomainInfo(domainInfoRequesterAnswer)).thenReturn(domainInfoParserAnswer);
        when(domainPriceInfoRequester.requestDomainPriceInfoApi()).thenReturn(domainPriceInfoRequesterAnswer);
        when(domainPriceInfoParser.parseDomainPriceInfo(domainPriceInfoRequesterAnswer)).thenReturn(domainPriceInfoParserAnswer);

        // When
        String actual = underTest.getDomainInfo(domainName);

        // Then
        assertThat(actual).isEqualTo(domainPriceInfoParserAnswer);
    }
}