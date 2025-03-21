package com.sheryians.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sheryians.java.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
