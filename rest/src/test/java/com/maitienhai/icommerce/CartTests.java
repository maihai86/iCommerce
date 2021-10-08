package com.maitienhai.icommerce;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maitienhai.icommerce.domain.*;
import com.maitienhai.icommerce.dto.request.CartAddRequest;
import com.maitienhai.icommerce.exception.BusinessException;
import com.maitienhai.icommerce.repository.*;
import com.maitienhai.icommerce.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@SpringBootTest(classes = ICommerceApplication.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class CartTests {

    @Autowired
    CartService cartService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductBrandRepository productBrandRepository;

    @Autowired
    ProductColorRepository productColorRepository;

    @BeforeAll
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
    }

    @AfterAll
    public void cleanup() {
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
        productBrandRepository.deleteAll();
        productColorRepository.deleteAll();
    }

    @Test
    void addProductToCart_productIsMandatory_test() throws Exception {
        CartAddRequest request = CartAddRequest.builder()
                .productId(null)
                .quantity(1)
                .build();
        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/carts")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void addProductToCart_quantityIsMandatory_test() throws Exception {
        List<Product> products = productRepository.findAll();
        CartAddRequest request = CartAddRequest.builder()
                .productId(1L)
                .quantity(null)
                .build();
        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/carts")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void addProductToCart_quantityMustPositive_test() throws Exception {
        List<Product> products = productRepository.findAll();
        CartAddRequest request = CartAddRequest.builder()
                .productId(1L)
                .quantity(-1)
                .build();
        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/carts")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void addProductToCart_productNotFound_test() throws Exception {
        List<Product> products = productRepository.findAll();
        CartAddRequest request = CartAddRequest.builder()
                .productId(products.get(products.size() - 1).getId() + 1)
                .quantity(1)
                .build();
        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
            cartService.addProductToCart(request);
        });

        Assertions.assertEquals("err.product-not-found", exception.getErrorCode());
    }

    @Test
    @Transactional
    void addProductToCart_insertOK_test() throws Exception {
        List<Product> products = productRepository.findAll();
        CartAddRequest request = CartAddRequest.builder()
                .productId(products.get(products.size() - 1).getId())
                .quantity(1)
                .build();
        Cart inserted = cartService.addProductToCart(request);

        Assertions.assertNotNull(inserted);
        Assertions.assertNotNull(inserted.getProduct());
        Assertions.assertEquals(request.getQuantity(), inserted.getQuantity());
        Assertions.assertEquals(request.getProductId(), inserted.getProduct().getId());
        Assertions.assertEquals(products.get(products.size() - 1).getName(), inserted.getProduct().getName());
    }

    @Test
    @Transactional
    void addProductToCart_appendOK_test() throws Exception {
        List<Product> products = productRepository.findAll();

        // insert new
        CartAddRequest request = CartAddRequest.builder()
                .productId(products.get(products.size() - 1).getId())
                .quantity(1)
                .build();
        Cart inserted = cartService.addProductToCart(request);

        // append
        CartAddRequest appendReq = CartAddRequest.builder()
                .productId(products.get(products.size() - 1).getId())
                .quantity(2)
                .build();
        Cart appended = cartService.addProductToCart(appendReq);

        Assertions.assertNotNull(appended);
        Assertions.assertNotNull(appended.getProduct());
        Assertions.assertEquals(request.getQuantity() + appendReq.getQuantity(), appended.getQuantity());
        Assertions.assertEquals(request.getProductId(), appended.getProduct().getId());
        Assertions.assertEquals(appendReq.getProductId(), appended.getProduct().getId());
        Assertions.assertEquals(products.get(products.size() - 1).getName(), appended.getProduct().getName());
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    protected <T> T mapFromJson(String json, TypeReference<T> typeReference)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, typeReference);
    }

}
