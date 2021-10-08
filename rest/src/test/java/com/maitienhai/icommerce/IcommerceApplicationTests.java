package com.maitienhai.icommerce;

import com.maitienhai.icommerce.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class IcommerceApplicationTests {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductBrandRepository productBrandRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductColorRepository productColorRepository;

    @Autowired
    CartRepository cartRepository;

    @Test
    void contextLoads() {
        log.info("productRepository.findAll={}", productRepository.findAll());
        log.info("cartRepository.findAll={}", cartRepository.findAll());
        log.info("productBrandRepository.findAll={}", productBrandRepository.findAll());
        log.info("productCategoryRepository.findAll={}", productCategoryRepository.findAll());
        log.info("productColorRepository.findAll={}", productColorRepository.findAll());

        Assertions.assertTrue(true);
    }

}
