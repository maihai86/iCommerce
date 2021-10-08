package com.maitienhai.icommerce.mapper;

import com.maitienhai.icommerce.domain.*;
import com.maitienhai.icommerce.dto.response.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartResponse domainToDto(Cart entity);

    List<CartResponse> domainsToDtoes(List<Cart> entities);

    ProductResponse productToProductResponse(Product entity);

}
