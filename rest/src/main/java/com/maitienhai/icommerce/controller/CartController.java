package com.maitienhai.icommerce.controller;

import com.maitienhai.icommerce.domain.Cart;
import com.maitienhai.icommerce.dto.common.ApiResponse;
import com.maitienhai.icommerce.dto.request.CartAddRequest;
import com.maitienhai.icommerce.dto.response.CartResponse;
import com.maitienhai.icommerce.mapper.CartMapper;
import com.maitienhai.icommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final CartMapper cartMapper;

    @PostMapping
    public ApiResponse<CartResponse> addProductToCart(@RequestBody @Valid CartAddRequest request) {
        Cart result = cartService.addProductToCart(request);
        return ApiResponse.success(cartMapper.domainToDto(result));
    }

}
