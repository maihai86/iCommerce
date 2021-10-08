package com.maitienhai.icommerce.controller;

import com.maitienhai.icommerce.domain.Product;
import com.maitienhai.icommerce.dto.response.ProductResponse;
import com.maitienhai.icommerce.dto.common.ApiResponse;
import com.maitienhai.icommerce.mapper.ProductMapper;
import com.maitienhai.icommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ProductMapper productMapper;

    @GetMapping
    public ApiResponse<PageImpl<ProductResponse>> getPaging(@RequestParam(name = "category-id", required = false) Long categoryId,
                                                            @RequestParam(name = "brand-id", required = false) Long brandId,
                                                            @RequestParam(name = "color-id", required = false) Long colorId,
                                                            @RequestParam(name = "price-min", required = false) Long priceMin,
                                                            @RequestParam(name = "price-max", required = false) Long priceMax,
                                                            Pageable pageable) {
        Page<Product> page = productService.getPaging(categoryId, brandId, colorId, priceMin, priceMax, pageable);
        return ApiResponse.success(new PageImpl<>(productMapper.domainsToDtoes(page.getContent()), page.getPageable(), page.getTotalElements()));
    }
}
