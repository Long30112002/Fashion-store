package com.sheryians.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sheryians.java.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findAllByCategory_Id(int id);
	
	@Query(value = "select * from product where name like %?%", nativeQuery = true)
	public List<Product> search(String keyword);
}
