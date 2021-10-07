package com.maitienhai.icommerce.service;

import com.maitienhai.icommerce.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<Product> getPaging(Long categoryId, Long brandId, Long colorId, Long priceMin, Long priceMax, Pageable pageable);

}
