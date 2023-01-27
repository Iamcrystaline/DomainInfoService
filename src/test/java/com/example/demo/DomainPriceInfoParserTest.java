package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DomainPriceInfoParserTest {

    private DomainPriceInfoParser underTest = new DomainPriceInfoParser();

    @Test
    @DisplayName("Given output XML - When parseDomainInfo - Then should return prices")
    void parseDomainInfoWithoutErrors() {
        // Given
        String XML = """
                <?xml version="1.0" encoding="UTF-8" ?>
                <ApiResponse>
                  <CommandResponse>
                    <UserGetPricingResult>
                      <ProductType>
                        <ProductCategory>
                          <Product>
                            <Price>
                              <DurationType>YEAR</DurationType>
                              <RegularPriceType>ABSOLUTE</RegularPriceType>
                              <Price>35.99</Price>
                              <Currency>USD</Currency>
                              <PricingType>MULTIPLE</PricingType>
                              <RegularPrice>43.19</RegularPrice>
                              <Duration>1</Duration>
                              <YourPriceType>MULTIPLE</YourPriceType>
                              <YourPrice>35.99</YourPrice>
                              <PromotionPrice>0</PromotionPrice>
                            </Price>
                            <Price>
                              <DurationType>YEAR</DurationType>
                              <RegularPriceType>ABSOLUTE</RegularPriceType>
                              <Price>32.99</Price>
                              <Currency>USD</Currency>
                              <PricingType>MULTIPLE</PricingType>
                              <RegularPrice>79.18</RegularPrice>
                              <Duration>2</Duration>
                              <YourPriceType>MULTIPLE</YourPriceType>
                              <YourPrice>32.99</YourPrice>
                              <PromotionPrice>0</PromotionPrice>
                            </Price>
                            <Price>
                              <DurationType>YEAR</DurationType>
                              <RegularPriceType>ABSOLUTE</RegularPriceType>
                              <Price>32.99</Price>
                              <Currency>USD</Currency>
                              <PricingType>MULTIPLE</PricingType>
                              <RegularPrice>118.77</RegularPrice>
                              <Duration>3</Duration>
                              <YourPriceType>MULTIPLE</YourPriceType>
                              <YourPrice>32.99</YourPrice>
                              <PromotionPrice>0</PromotionPrice>
                            </Price>
                            <Price>
                              <DurationType>YEAR</DurationType>
                              <RegularPriceType>ABSOLUTE</RegularPriceType>
                              <Price>32.99</Price>
                              <Currency>USD</Currency>
                              <PricingType>MULTIPLE</PricingType>
                              <RegularPrice>158.36</RegularPrice>
                              <Duration>4</Duration>
                              <YourPriceType>MULTIPLE</YourPriceType>
                              <YourPrice>32.99</YourPrice>
                              <PromotionPrice>0</PromotionPrice>
                            </Price>
                            <Price>
                              <DurationType>YEAR</DurationType>
                              <RegularPriceType>ABSOLUTE</RegularPriceType>
                              <Price>19.99</Price>
                              <Currency>USD</Currency>
                              <PricingType>MULTIPLE</PricingType>
                              <RegularPrice>119.95</RegularPrice>
                              <Duration>5</Duration>
                              <YourPriceType>MULTIPLE</YourPriceType>
                              <YourPrice>19.99</YourPrice>
                              <PromotionPrice>0</PromotionPrice>
                            </Price>
                          </Product>
                        </ProductCategory>
                      </ProductType>
                    </UserGetPricingResult>
                  </CommandResponse>
                </ApiResponse>""";
        String expected = "[{\"DurationType\":\"YEAR\"," +
                "\"RegularPriceType\":\"ABSOLUTE\"," +
                "\"Price\":35.99," +
                "\"Currency\":\"USD\"," +
                "\"PricingType\":\"MULTIPLE\"," +
                "\"RegularPrice\":43.19," +
                "\"Duration\":1," +
                "\"YourPriceType\":\"MULTIPLE\"," +
                "\"YourPrice\":35.99," +
                "\"PromotionPrice\":0}," +
                "{\"DurationType\":\"YEAR\"," +
                "\"RegularPriceType\":\"ABSOLUTE\"," +
                "\"Price\":32.99," +
                "\"Currency\":\"USD\"," +
                "\"PricingType\":\"MULTIPLE\"," +
                "\"RegularPrice\":79.18," +
                "\"Duration\":2," +
                "\"YourPriceType\":\"MULTIPLE\"," +
                "\"YourPrice\":32.99," +
                "\"PromotionPrice\":0}," +
                "{\"DurationType\":\"YEAR\"," +
                "\"RegularPriceType\":\"ABSOLUTE\"," +
                "\"Price\":32.99," +
                "\"Currency\":\"USD\"," +
                "\"PricingType\":\"MULTIPLE\"," +
                "\"RegularPrice\":118.77," +
                "\"Duration\":3," +
                "\"YourPriceType\":\"MULTIPLE\"," +
                "\"YourPrice\":32.99," +
                "\"PromotionPrice\":0}," +
                "{\"DurationType\":\"YEAR\"," +
                "\"RegularPriceType\":\"ABSOLUTE\"," +
                "\"Price\":32.99," +
                "\"Currency\":\"USD\"," +
                "\"PricingType\":\"MULTIPLE\"," +
                "\"RegularPrice\":158.36," +
                "\"Duration\":4," +
                "\"YourPriceType\":\"MULTIPLE\"," +
                "\"YourPrice\":32.99," +
                "\"PromotionPrice\":0}," +
                "{\"DurationType\":\"YEAR\"," +
                "\"RegularPriceType\":\"ABSOLUTE\"," +
                "\"Price\":19.99," +
                "\"Currency\":\"USD\"," +
                "\"PricingType\":\"MULTIPLE\"," +
                "\"RegularPrice\":119.95," +
                "\"Duration\":5," +
                "\"YourPriceType\":\"MULTIPLE\"," +
                "\"YourPrice\":19.99," +
                "\"PromotionPrice\":0}]";

        // When
        String actual = underTest.parseDomainPriceInfo(XML);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}