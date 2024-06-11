package com.blogsproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogsproject.dto.CategoryDTO;
import com.blogsproject.model.Category;
import com.blogsproject.repository.CategoryRepository;
import com.blogsproject.service.contracts.CategoryContracts;

@Service
public class CategoryService implements CategoryContracts {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public boolean createCategory(CategoryDTO categoryDTO) {
		Category categoryEntity = modelMapper.map(categoryDTO, Category.class);
		categoryRepository.save(categoryEntity);
		return true;
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
		Optional<Category> findCategoryById = categoryRepository.findById(categoryId);
		if (findCategoryById.isPresent()) {
			Category category = findCategoryById.get();

			category.setCategoryTitle(categoryDTO.getCategoryTitle());
			category.setCategoryDescription(categoryDTO.getCategoryDescription());

			Category updatedCategory = categoryRepository.save(category);
			return modelMapper.map(updatedCategory, CategoryDTO.class);
		}
		return null;
	}

	@Override
	public CategoryDTO getCategoryById(Long categoryId) {
		Optional<Category> findCategoryById = categoryRepository.findById(categoryId);
		if (findCategoryById.isPresent()) {
			Category category = findCategoryById.get();
			return modelMapper.map(category, CategoryDTO.class);
		}
		return null;
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		List<CategoryDTO> allCategory = new ArrayList<>();
		Iterable<Category> findAllCategory = categoryRepository.findAll();
		findAllCategory.forEach(category -> {
			allCategory.add(modelMapper.map(category, CategoryDTO.class));
		});
		return allCategory;
	}

	@Override
	public boolean deleteCategoryById(Long categoryId) {
		categoryRepository.deleteById(categoryId);
		return true;
	}

}
