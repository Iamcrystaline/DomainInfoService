package com.example.demo;

import com.example.demo.api.Domain;
import com.example.demo.api.InvalidDomainNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DomainTest {

    @Test
    @DisplayName("Given null domain name - When new Domain - Then should throw exception")
    public void newDomainWithNullName() {
        // Given
        String domainName = null;

        // When
        // Then
        assertThatThrownBy(() -> new Domain(domainName))
                .usingRecursiveComparison()
                .isEqualTo(new InvalidDomainNameException("Invalid domain name. Domain name can't be null"));
    }

    @Test
    @DisplayName("Given empty domain name - When new Domain - Then should throw exception")
    public void newDomainWithEmptyName() {
        // Given
        String domainName = "";

        // When
        // Then
        assertThatThrownBy(() -> new Domain(domainName))
                .usingRecursiveComparison()
                .isEqualTo(new InvalidDomainNameException("Invalid domain name. Domain name can't be null"));
    }

    @Test
    @DisplayName("Given only second-level domain name - When new Domain - Then should throw exception")
    public void newDomainWithSecondLevelDomainName() {
        // Given
        String domainName = "abrafoo";

        // When
        // Then
        assertThatThrownBy(() -> new Domain(domainName))
                .usingRecursiveComparison()
                .isEqualTo(new InvalidDomainNameException("Invalid domain name. Domain name should match xxx.xxx pattern"));
    }

    @Test
    @DisplayName("Given only top-level domain name - When new Domain - Then should throw exception")
    public void newDomainWithTopLevelDomainName() {
        // Given
        String domainName = "com";

        // When
        // Then
        assertThatThrownBy(() -> new Domain(domainName))
                .usingRecursiveComparison()
                .isEqualTo(new InvalidDomainNameException("Invalid domain name. Domain name should match xxx.xxx pattern"));
    }

    @Test
    @DisplayName("Given 2 level domain name - When new Domain - Then should successfully create Domain")
    public void newDomainWithTwoLevelDomainName() {
        // Given
        String domainName = "abrafoo.com";

        // When
        Domain actual = new Domain(domainName);

        // Then
        assertThat(actual.getDomainFullName()).isEqualTo(domainName);
        assertThat(actual.getTopLevelDomain()).isEqualTo("com");
        assertThat(actual.getSecondLevelDomain()).isEqualTo("abrafoo");
        assertThat(actual.getSubdomain()).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("Given 3 level domain name - When new Domain - Then should successfully create Domain")
    public void newDomainWithThreeLevelDomainName() {
        // Given
        String domainName = "www.abrafoo.com";

        // When
        Domain actual = new Domain(domainName);

        // Then
        assertThat(actual.getDomainFullName()).isEqualTo(domainName);
        assertThat(actual.getTopLevelDomain()).isEqualTo("com");
        assertThat(actual.getSecondLevelDomain()).isEqualTo("abrafoo");
        assertThat(actual.getSubdomain().get()).isEqualTo("www");
    }

    @Test
    @DisplayName("Given 4 level domain name - When new Domain - Then should successfully create Domain")
    public void newDomainWithFourLevelDomainName() {
        // Given
        String domainName = "www.sandbox.abrafoo.com";

        // When
        Domain actual = new Domain(domainName);

        // Then
        assertThat(actual.getDomainFullName()).isEqualTo(domainName);
        assertThat(actual.getTopLevelDomain()).isEqualTo("com");
        assertThat(actual.getSecondLevelDomain()).isEqualTo("sandbox.abrafoo");
        assertThat(actual.getSubdomain().get()).isEqualTo("www");
    }
}