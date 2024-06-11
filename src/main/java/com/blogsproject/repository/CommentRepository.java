package com.blogsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogsproject.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
