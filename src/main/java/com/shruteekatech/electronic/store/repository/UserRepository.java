package com.shruteekatech.electronic.store.repository;


import com.shruteekatech.electronic.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     Optional<User> findByEmail(String email);
     List<User> findByNameContaining(String keywords);

}
