package com.maitienhai.icommerce.service.impl;

import com.maitienhai.icommerce.domain.Product;
import com.maitienhai.icommerce.exception.BusinessException;
import com.maitienhai.icommerce.repository.ProductBrandRepository;
import com.maitienhai.icommerce.repository.ProductCategoryRepository;
import com.maitienhai.icommerce.repository.ProductColorRepository;
import com.maitienhai.icommerce.repository.ProductRepository;
import com.maitienhai.icommerce.service.ProductService;
import com.maitienhai.icommerce.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    private final ProductBrandRepository productBrandRepository;

    private final ProductColorRepository productColorRepository;

    @Override
    public Page<Product> getPaging(Long categoryId, Long brandId, Long colorId, Long priceMin, Long priceMax, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        }

        if (categoryId != null) {
            validateCategory(categoryId);
        }
        if (brandId != null) {
            validateBrand(brandId);
        }
        if (colorId != null) {
            validateColor(colorId);
        }

        return productRepository.findAll(ProductSpecification.getPaging(categoryId, brandId, colorId, priceMin, priceMax), pageable);
    }

    private void validateCategory(Long categoryId) {
        productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException("err.category-not-found"));
    }

    private void validateBrand(Long brandId) {
        productBrandRepository.findById(brandId)
                .orElseThrow(() -> new BusinessException("err.brand-not-found"));
    }

    private void validateColor(Long colorId) {
        productColorRepository.findById(colorId)
                .orElseThrow(() -> new BusinessException("err.color-not-found"));
    }

}
