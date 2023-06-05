package com.bikkadit.electronic.store.repository;

import com.bikkadit.electronic.store.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByUserNameContaining(String keyword);

    Optional<User> findByEmail(String email);

}
