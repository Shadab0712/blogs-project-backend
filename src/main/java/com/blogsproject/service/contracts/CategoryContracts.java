package com.blogsproject.service.contracts;

import java.util.List;

import com.blogsproject.dto.CategoryDTO;

public interface CategoryContracts {

	public boolean createCategory(CategoryDTO categoryDTO);

	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);

	public CategoryDTO getCategoryById(Long categoryId);

	public List<CategoryDTO> getAllCategory();

	public boolean deleteCategoryById(Long categoryId);

}
