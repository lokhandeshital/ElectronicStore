package com.bikkadit.electronic.store.repository;

import com.bikkadit.electronic.store.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
