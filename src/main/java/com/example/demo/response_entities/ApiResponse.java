package com.example.demo.response_entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@JacksonXmlRootElement
@JsonIgnoreProperties
@NoArgsConstructor
@Getter
public class ApiResponse {

    @JacksonXmlProperty(isAttribute = true)
    private String Status;
    @JacksonXmlProperty(isAttribute = true)
    private String xmlns;
    @JacksonXmlProperty
    private CommandResponse CommandResponse;
    @JacksonXmlProperty
    @JacksonXmlElementWrapper
    private List<Object> Errors;
    @JacksonXmlProperty
    @JacksonXmlElementWrapper
    private List<Object> Warnings;
    @JacksonXmlProperty
    private String RequestedCommand;
    @JacksonXmlProperty
    private String Server;
    @JacksonXmlProperty
    private String GMTTimeDifference;
    @JacksonXmlProperty
    private String ExecutionTime;

    @NoArgsConstructor
    @Getter
    public static class CommandResponse {

        @JacksonXmlProperty(isAttribute = true)
        private String Type;
        @JacksonXmlProperty
        private UserGetPricingResult UserGetPricingResult;

        @NoArgsConstructor
        @Getter
        public static class UserGetPricingResult {

            @JacksonXmlProperty
            private ProductType ProductType;

            @NoArgsConstructor
            @Getter
            public static class ProductType {

                @JacksonXmlProperty
                private ProductCategory ProductCategory;
                @JacksonXmlProperty(isAttribute = true)
                private String Name;

                @NoArgsConstructor
                @Getter
                public static class ProductCategory {

                    @JacksonXmlProperty
                    private Product Product;
                    @JacksonXmlProperty(isAttribute = true)
                    private String Name;

                    @NoArgsConstructor
                    @Getter
                    public static class Product {

                        @JacksonXmlElementWrapper(useWrapping = false)
                        @JacksonXmlProperty
                        private List<Price> Price;
                        @JacksonXmlProperty(isAttribute = true)
                        private String Name;

                        @NoArgsConstructor
                        @Getter
                        public static class Price {

                            @JacksonXmlProperty(isAttribute = true)
                            private String DurationType;
                            @JacksonXmlProperty(isAttribute = true)
                            private String RegularPriceType;
                            @JacksonXmlProperty(isAttribute = true)
                            private Double Price;
                            @JacksonXmlProperty(isAttribute = true)
                            private String Currency;
                            @JacksonXmlProperty(isAttribute = true)
                            private String PricingType;
                            @JacksonXmlProperty(isAttribute = true)
                            private Double RegularPrice;
                            @JacksonXmlProperty(isAttribute = true)
                            private Integer Duration;
                            @JacksonXmlProperty(isAttribute = true)
                            private String YourPriceType;
                            @JacksonXmlProperty(isAttribute = true)
                            private Double YourPrice;
                            @JacksonXmlProperty(isAttribute = true)
                            private Double PromotionPrice;
                        }
                    }
                }
            }
        }
    }
}
