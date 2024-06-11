package com.blogsproject.service.contracts;

import java.util.List;

import com.blogsproject.dto.PostDTO;
import com.blogsproject.dto.PostResponse;

public interface PostContracts {

	public PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId);

	public PostDTO updatePost(PostDTO postDTO, Long postId);

	public PostDTO getPostById(Long postId);

	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy , String sortDir);

	public boolean deletePostById(Long postId);

	public List<PostDTO> getPostsByCategory(Long categoryId);

	public List<PostDTO> getPostsByUser(Long userId);

	public List<PostDTO> searchPosts(String keyword);

}
