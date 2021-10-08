package com.maitienhai.icommerce.repository;

import com.maitienhai.icommerce.domain.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductColorRepository extends JpaRepository<ProductColor, Long> {
}
