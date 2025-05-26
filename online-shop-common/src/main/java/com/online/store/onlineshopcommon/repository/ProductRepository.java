package com.online.store.onlineshopcommon.repository;

import com.online.store.onlineshopcommon.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
