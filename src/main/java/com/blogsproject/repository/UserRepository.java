package com.blogsproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blogsproject.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	public User findByUsername(String username);

	public User getById(Long id);
}
