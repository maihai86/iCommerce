package com.maitienhai.icommerce.service;

import com.maitienhai.icommerce.domain.Cart;
import com.maitienhai.icommerce.dto.request.CartAddRequest;

public interface CartService {

    Cart addProductToCart(CartAddRequest request);
}
