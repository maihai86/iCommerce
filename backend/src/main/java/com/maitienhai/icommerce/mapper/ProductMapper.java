package com.maitienhai.icommerce.mapper;

import com.maitienhai.icommerce.domain.Product;
import com.maitienhai.icommerce.dto.response.ProductResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductResponse domainToDto(Product product);

    List<ProductResponse> domainsToDtoes(List<Product> product);

}
