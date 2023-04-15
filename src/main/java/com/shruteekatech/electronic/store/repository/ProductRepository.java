package com.shruteekatech.electronic.store.repository;

import com.shruteekatech.electronic.store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
  Page<Product> findByTitleContaining(String subTitle,Pageable pageable);

    Page<Product>findByliveTrue(Pageable pageable);
}
