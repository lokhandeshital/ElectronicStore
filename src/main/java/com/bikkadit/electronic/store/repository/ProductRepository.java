package com.bikkadit.electronic.store.repository;

import com.bikkadit.electronic.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

   List<Product> findByTitleContaining(String subString);

   List<Product> findByLiveTrue();

}
