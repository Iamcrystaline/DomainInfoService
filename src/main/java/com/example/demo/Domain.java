package com.example.demo;

import lombok.Getter;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Domain {

    private final String domainFullName;
    private final Pattern topLevelDomainPattern = Pattern.compile("(.+)\\.(.+)");
    private final Pattern subdomainAndSecondLevelDomainPattern = Pattern.compile("(.+?)\\.(.+)");
    private final String topLevelDomain;
    private final String secondLevelDomain;
    private final String subdomain;

    public Domain(String domainFullName) {
        if (domainFullName == null) {
            throw new InvalidDomainNameException("Invalid domain name. Domain name can't be null");
        }
        Matcher domainMatcher = topLevelDomainPattern.matcher(domainFullName);
        if (domainMatcher.matches()) {
            this.domainFullName = domainFullName;
            this.topLevelDomain = domainMatcher.group(2);
            String remainingDomainName = domainMatcher.group(1);
            domainMatcher = subdomainAndSecondLevelDomainPattern.matcher(remainingDomainName);
            if (domainMatcher.matches()) {
                this.subdomain = domainMatcher.group(1);
                this.secondLevelDomain = domainMatcher.group(2);
            } else {
                this.subdomain = null;
                this.secondLevelDomain = remainingDomainName;
            }
        } else {
            throw new InvalidDomainNameException("Invalid domain name. Domain name should match xxx.xxx pattern");
        }
    }

    public Optional<String> getSubdomain() {
        return Optional.ofNullable(subdomain);
    }
}
