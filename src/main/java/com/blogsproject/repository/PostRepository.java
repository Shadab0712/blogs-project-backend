package com.blogsproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogsproject.model.Category;
import com.blogsproject.model.Post;
import com.blogsproject.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	public List<Post> findByCategory(Category category);

	public List<Post> findByUser(User user);

	public List<Post> findByTitleContaining(String title);

}
