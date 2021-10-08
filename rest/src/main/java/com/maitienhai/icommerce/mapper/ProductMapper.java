package com.maitienhai.icommerce.mapper;

import com.maitienhai.icommerce.domain.Product;
import com.maitienhai.icommerce.domain.ProductBrand;
import com.maitienhai.icommerce.domain.ProductCategory;
import com.maitienhai.icommerce.domain.ProductColor;
import com.maitienhai.icommerce.dto.response.ProductBrandRes;
import com.maitienhai.icommerce.dto.response.ProductCategoryRes;
import com.maitienhai.icommerce.dto.response.ProductColorRes;
import com.maitienhai.icommerce.dto.response.ProductResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductResponse domainToDto(Product product);

    List<ProductResponse> domainsToDtoes(List<Product> product);

    ProductCategoryRes productCategoryToProductCategoryRes(ProductCategory entity);

    ProductBrandRes productBrandToProductBrandRes(ProductBrand entity);

    ProductColorRes productColorResToProductColorRes(ProductColor entity);

}
