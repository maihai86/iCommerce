package com.maitienhai.icommerce.service.impl;

import com.maitienhai.icommerce.domain.Cart;
import com.maitienhai.icommerce.domain.Product;
import com.maitienhai.icommerce.dto.request.CartAddRequest;
import com.maitienhai.icommerce.exception.BusinessException;
import com.maitienhai.icommerce.repository.CartRepository;
import com.maitienhai.icommerce.repository.ProductRepository;
import com.maitienhai.icommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Cart addProductToCart(CartAddRequest request) {
        Product product = validateProduct(request.getProductId());

        Optional<Cart> optExisted = cartRepository.findOneByProduct(product);
        if (!optExisted.isPresent()) {
            // insert new
            Cart newCart = Cart.builder()
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();

            return cartRepository.save(newCart);
        } else {
            Cart existed = optExisted.get();
            existed.setQuantity(existed.getQuantity() + request.getQuantity());

            return cartRepository.save(existed);
        }
    }

    private Product validateProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("err.product-not-found"));
    }
}
