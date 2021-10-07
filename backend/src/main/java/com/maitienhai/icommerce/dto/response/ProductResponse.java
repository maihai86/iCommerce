package com.maitienhai.icommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ProductResponse implements Serializable {

    private Long id;

    private String name;

    private ProductCategoryRes category;

    private ProductBrandRes brand;

    private ProductColorRes color;

}
