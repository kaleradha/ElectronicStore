package com.shruteekatech.electronic.store.repository;

import com.shruteekatech.electronic.store.entity.Catagory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CatagoryRepository extends JpaRepository<Catagory,Long> {

    List<Catagory> findByTitleContaining(String keywords);

}
