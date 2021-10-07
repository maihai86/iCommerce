package com.maitienhai.icommerce;

import com.maitienhai.icommerce.domain.Product;
import com.maitienhai.icommerce.domain.ProductBrand;
import com.maitienhai.icommerce.domain.ProductCategory;
import com.maitienhai.icommerce.domain.ProductColor;
import com.maitienhai.icommerce.exception.BusinessException;
import com.maitienhai.icommerce.repository.ProductBrandRepository;
import com.maitienhai.icommerce.repository.ProductCategoryRepository;
import com.maitienhai.icommerce.repository.ProductColorRepository;
import com.maitienhai.icommerce.repository.ProductRepository;
import com.maitienhai.icommerce.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@SpringBootTest(classes = ICommerceApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class ProductTests {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductBrandRepository productBrandRepository;

    @Autowired
    ProductColorRepository productColorRepository;

    @BeforeAll
    @Transactional
    public void setUp() {
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
    }

    @AfterAll
    @Transactional
    public void cleanup() {
        productCategoryRepository.deleteAll();
        productBrandRepository.deleteAll();
        productColorRepository.deleteAll();
    }

    @Test
    public void getPaging_cateNotFound_test() throws Exception {
        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
            productService.getPaging(0L, 0L, 0L, null, null, null);
        });

        Assertions.assertEquals(exception.getErrorCode(), "err.category-not-found");
    }

    @Test
    public void getPaging_brandNotFound_test() throws Exception {
        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
            List<ProductCategory> categories = productCategoryRepository.findAll();
            productService.getPaging(categories.get(0).getId(), 0L, 0L, null, null, null);
        });

        Assertions.assertEquals(exception.getErrorCode(), "err.brand-not-found");
    }

    @Test
    public void getPaging_colorNotFound_test() throws Exception {
        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
            List<ProductCategory> categories = productCategoryRepository.findAll();
            List<ProductBrand> brands = productBrandRepository.findAll();
            productService.getPaging(categories.get(0).getId(), brands.get(0).getId(), 0L, null, null, null);
        });

        Assertions.assertEquals(exception.getErrorCode(), "err.color-not-found");
    }

    @Test
    public void getPaging_notFound_test1() throws Exception {
        List<ProductCategory> categories = productCategoryRepository.findAll();
        List<ProductBrand> brands = productBrandRepository.findAll();
        List<ProductColor> colors = productColorRepository.findAll();
        Page<Product> productPage = productService.getPaging(categories.get(1).getId(), brands.get(0).getId(), colors.get(0).getId(), null, null, null);
        Assertions.assertEquals(productPage.getContent().size(), 0);
    }

    @Test
    public void getPaging_notFoundByBrand_test() throws Exception {
        List<ProductCategory> categories = productCategoryRepository.findAll();
        List<ProductBrand> brands = productBrandRepository.findAll();
        List<ProductColor> colors = productColorRepository.findAll();
        Page<Product> productPage = productService.getPaging(categories.get(0).getId(), brands.get(1).getId(), colors.get(0).getId(), null, null, null);
        Assertions.assertEquals(productPage.getContent().size(), 0);
    }

    @Test
    public void getPaging_notFoundByColor_test() throws Exception {
        List<ProductCategory> categories = productCategoryRepository.findAll();
        List<ProductBrand> brands = productBrandRepository.findAll();
        List<ProductColor> colors = productColorRepository.findAll();
        Page<Product> productPage = productService.getPaging(categories.get(0).getId(), brands.get(0).getId(), colors.get(1).getId(), null, null, null);
        Assertions.assertEquals(productPage.getContent().size(), 0);
    }

    @Test
    public void getPaging_notFoundByPrice_test() throws Exception {
        List<ProductCategory> categories = productCategoryRepository.findAll();
        List<ProductBrand> brands = productBrandRepository.findAll();
        List<ProductColor> colors = productColorRepository.findAll();
        Page<Product> productPage = productService.getPaging(categories.get(0).getId(), brands.get(0).getId(), colors.get(0).getId(), 5100000L, null, null);
        Assertions.assertEquals(productPage.getContent().size(), 0);
    }

    @Test
    public void getPaging_notFoundByPrice_test2() throws Exception {
        List<ProductCategory> categories = productCategoryRepository.findAll();
        List<ProductBrand> brands = productBrandRepository.findAll();
        List<ProductColor> colors = productColorRepository.findAll();
        Page<Product> productPage = productService.getPaging(categories.get(0).getId(), brands.get(0).getId(), colors.get(0).getId(), null, 4990000L, null);
        Assertions.assertEquals(productPage.getContent().size(), 0);
    }

    @Test
    public void getPaging_found_test() throws Exception {
        List<ProductCategory> categories = productCategoryRepository.findAll();
        List<ProductBrand> brands = productBrandRepository.findAll();
        List<ProductColor> colors = productColorRepository.findAll();
        Page<Product> productPage = productService.getPaging(categories.get(0).getId(), brands.get(0).getId(), colors.get(0).getId(), 4990000L, 5100000L, null);
        Assertions.assertEquals(productPage.getContent().size(), 1);

        Product product = productPage.getContent().get(0);
        Assertions.assertEquals(product.getName(), "Ultraboost");
        Assertions.assertNotNull(product.getCategory());
        Assertions.assertEquals(product.getCategory().getName(), "T-shirt");
        Assertions.assertNotNull(product.getBrand());
        Assertions.assertEquals(product.getBrand().getName(), "Adidas");
        Assertions.assertNotNull(product.getColor());
        Assertions.assertEquals(product.getColor().getName(), "Red");
    }

}
