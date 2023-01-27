package com.example.demo;

import java.time.LocalDateTime;

public record CacheableDomainInfo (String cachedData, LocalDateTime cachedExpirationTime){

}
