package com.bikkadit.electronic.store.repository;

import com.bikkadit.electronic.store.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {


}
