package com.maitienhai.icommerce.repository;

import com.maitienhai.icommerce.domain.Cart;
import com.maitienhai.icommerce.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findOneByProduct(Product product);

}
