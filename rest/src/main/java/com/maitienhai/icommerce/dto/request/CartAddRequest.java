package com.maitienhai.icommerce.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CartAddRequest {

    @NotNull(message = "{err.product-not-null}")
    private Long productId;

    @NotNull(message = "{err.quantity-not-null}")
    @Positive(message = "{err.quantity-larger-than-zero}")
    private Integer quantity;

}
