package com.sheryians.java.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sheryians.java.dto.ProductDTO;
import com.sheryians.java.model.Category;
import com.sheryians.java.model.Product;
import com.sheryians.java.service.CategoryService;
import com.sheryians.java.service.ProductService;
import com.sheryians.java.service.UserService;



@Controller
public class AdminController {
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
	
	@Autowired 
	CategoryService categoryService;
	
	@Autowired 
	ProductService productService;
	
	@Autowired 
	UserService userService;
	
//	@GetMapping("/admin")
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminHome() {
		return "adminHome";
	}
	
//	@GetMapping("/admin/categories")
	@RequestMapping(value = "/admin/categories", method = RequestMethod.GET)
	public String getCat(Model model) {
		model.addAttribute("categories", categoryService.getAllCategories());
		return "categories";
	}

	
//	@GetMapping("/admin/categories/add")
	@RequestMapping(value = "/admin/categories/add", method = RequestMethod.GET)
	public String getCatAdd(Model model) {
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}

	
//	@PostMapping("/admin/categories/add")
	@RequestMapping(value = "/admin/categories/add", method = RequestMethod.POST)
	public String postCatAdd(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCat(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCat(@PathVariable int id, Model model) {
		Optional<Category> category = categoryService.getCategoryById(id);
		if(category.isPresent()) {
			model.addAttribute("category", category.get());
			return "categoriesAdd";
		}else
			return "404";
	}
	
	//PRODUCT
	@GetMapping("/admin/products")
	public String products(Model model) {
		model.addAttribute("products", productService.getAllProduct());
		return "products";
	}
	
	@GetMapping("/admin/products/add")
	public String productsAddGet(Model model) {
		model.addAttribute("productDTO", new ProductDTO()); 	
		model.addAttribute("categories", categoryService.getAllCategories());
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String productAddPost
			(@ModelAttribute("productDTO")ProductDTO productDAO, 
			@RequestParam("productImage")MultipartFile file,
			@RequestParam("imgName")String imgName) throws IOException{
		Product product = new Product();
		product.setId(productDAO.getId());
		product.setName(productDAO.getName());
		product.setCategory(categoryService.getCategoryById(productDAO.getCategoryId()).get());
		product.setPrice(productDAO.getPrice());
		product.setWeight(productDAO.getWeight());
		product.setDescription(productDAO.getDescription());
		
		String imageUUID;
		if(!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNamAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNamAndPath, file.getBytes());
		}else {
			imageUUID = imgName;
		}
		product.setImageName(imageUUID);
		productService.addProduct(product);
		
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable long id) {
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/update/{id}")
	public String updateProduct(@PathVariable long id, Model model) {
		Product product = productService.getProductById(id).get();
		ProductDTO productDAO = new ProductDTO();
		productDAO.setId(product.getId());
		productDAO.setName(product.getName());
		productDAO.setCategoryId(product.getCategory().getId());
		productDAO.setPrice(product.getPrice());
		productDAO.setWeight(product.getWeight());
		productDAO.setDescription(product.getDescription());
		productDAO.setImageName(product.getImageName());
		
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("productDTO", productDAO);
		return "productsAdd";
	}
	
	//USER
	@GetMapping("/admin/users")
	public String user(Model model) {
		model.addAttribute("listusers", userService.getAllUser());
		return "users";
	}

	@GetMapping("/admin/users/delete/{id}")
	public String deleteUser(@PathVariable Integer id) {
		userService.removeUserById(id);
		return "redirect:/admin/users";
	}
	

}
