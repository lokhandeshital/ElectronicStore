package com.bikkadit.electronic.store.repository;

import com.bikkadit.electronic.store.model.Cart;
import com.bikkadit.electronic.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByUser(User user);


}
