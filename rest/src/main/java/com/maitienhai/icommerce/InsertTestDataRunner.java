package com.maitienhai.icommerce;

import com.maitienhai.icommerce.domain.Product;
import com.maitienhai.icommerce.domain.ProductBrand;
import com.maitienhai.icommerce.domain.ProductCategory;
import com.maitienhai.icommerce.domain.ProductColor;
import com.maitienhai.icommerce.repository.ProductBrandRepository;
import com.maitienhai.icommerce.repository.ProductCategoryRepository;
import com.maitienhai.icommerce.repository.ProductColorRepository;
import com.maitienhai.icommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;

@Component
@Profile("test")
@RequiredArgsConstructor
@Slf4j
public class InsertTestDataRunner implements CommandLineRunner {

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    private final ProductBrandRepository productBrandRepository;

    private final ProductColorRepository productColorRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("INIT pre-data");
        if (productRepository.count() == 0) {
            ProductCategory category = ProductCategory.builder().name("T-shirt").build();
            ProductBrand brand = ProductBrand.builder().name("Adidas").build();
            ProductColor color = ProductColor.builder().name("Red").build();

            Product product = Product.builder().name("Ultraboost")
                    .brand(brand)
                    .category(category)
                    .color(color)
                    .price(BigDecimal.valueOf(5000000))
                    .build();
            category.setProducts(Collections.singletonList(product));
            brand.setProducts(Collections.singletonList(product));
            color.setProducts(Collections.singletonList(product));
            productRepository.save(product);

            productCategoryRepository.save(ProductCategory.builder().name("Sneaker").build());

            productBrandRepository.save(ProductBrand.builder().name("Nike").build());

            productColorRepository.save(ProductColor.builder().name("Blue").build());
        } else {
            log.info("Initialized already, skip");
        }
    }
}
