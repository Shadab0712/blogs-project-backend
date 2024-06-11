package com.blogsproject.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogsproject.dto.CategoryDTO;
import com.blogsproject.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/create")
	public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		categoryService.createCategory(categoryDTO);
		return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long categoryId) {
		CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}

	@GetMapping("/allCategory")
	public ResponseEntity<List<CategoryDTO>> getAllCategory() {
		List<CategoryDTO> allCategory = categoryService.getAllCategory();
		return new ResponseEntity<>(allCategory, HttpStatus.OK);

	}

	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
			@PathVariable Long categoryId) {
		CategoryDTO updateCategory = categoryService.updateCategory(categoryDTO, categoryId);
		return new ResponseEntity<>(updateCategory, HttpStatus.OK);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		categoryService.deleteCategoryById(categoryId);
		return new ResponseEntity<>("Category deleted", HttpStatus.OK);
	}

}
