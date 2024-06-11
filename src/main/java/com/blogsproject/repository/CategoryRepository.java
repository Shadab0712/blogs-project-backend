package com.blogsproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blogsproject.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
