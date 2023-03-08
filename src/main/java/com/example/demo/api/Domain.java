package com.example.demo.api;

import lombok.Getter;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Domain {

    private final String domainFullName;
    private final Pattern domainPattern = Pattern.compile("(.+)\\.(.+)");
    private final String topLevelDomain;
    private final String secondLevelDomain;

    public Domain(String domainFullName) {
        if (domainFullName == null || domainFullName.isEmpty()) {
            throw new InvalidDomainNameException("Invalid domain name. Domain name can't be empty");
        }
        Matcher domainMatcher = domainPattern.matcher(domainFullName);
        if (!domainMatcher.matches()) {
            throw new InvalidDomainNameException("Invalid domain name. Domain name should match xxx.xxx pattern");
        }
        this.domainFullName = domainFullName;
        this.topLevelDomain = domainMatcher.group(2);
        this.secondLevelDomain = domainMatcher.group(1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Domain)) return false;
        Domain domain = (Domain) o;
        return Objects.equals(getDomainFullName(), domain.getDomainFullName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDomainFullName());
    }
}
