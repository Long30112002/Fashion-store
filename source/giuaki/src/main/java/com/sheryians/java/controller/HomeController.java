package com.sheryians.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sheryians.java.global.GlobalData;
import com.sheryians.java.model.Product;
import com.sheryians.java.service.CategoryService;
import com.sheryians.java.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping({"/", "/home"})
	public String home(Model model) {
		model.addAttribute("cartCount",GlobalData.cart.size());
		return "index";
	}
	
	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("products", productService.getAllProduct());
		model.addAttribute("cartCount",GlobalData.cart.size());

		return "shop";		
	}
	
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id) {
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("products", productService.getAllByProductsByCategoryId(id));
		return "shop";		
	}
	
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewCategory(Model model, @PathVariable int id) {
		model.addAttribute("product", productService.getProductById(id).get());
		model.addAttribute("cartCount",GlobalData.cart.size());
		return "viewProduct";		
	}
	
	@GetMapping("/search")
	public String search(@Param("keyword") String keyword, Model model) {
		List<Product> search_Products = productService.search(keyword);
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("searchResult", search_Products);
		
		System.out.println("Key word:" +keyword);
		return "search";
	}
}
