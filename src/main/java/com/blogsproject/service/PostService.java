package com.blogsproject.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogsproject.dto.PostDTO;
import com.blogsproject.dto.PostResponse;
import com.blogsproject.model.Category;
import com.blogsproject.model.Post;
import com.blogsproject.model.User;
import com.blogsproject.repository.CategoryRepository;
import com.blogsproject.repository.PostRepository;
import com.blogsproject.repository.UserRepository;
import com.blogsproject.service.contracts.PostContracts;

@Service
public class PostService implements PostContracts {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId) {
		User user = userRepository.findById(userId).orElseThrow(null);
		Category category = categoryRepository.findById(categoryId).orElseThrow(null);

		Post postEntity = modelMapper.map(postDTO, Post.class);
		postEntity.setImageName(postDTO.getImageName());
		postEntity.setAddedDate(new Date());
		postEntity.setUser(user);
		postEntity.setCategory(category);

		Post savedPost = postRepository.save(postEntity);
		return modelMapper.map(savedPost, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Long postId) {
		Optional<Post> findPostById = postRepository.findById(postId);
		if (findPostById.isPresent()) {
			Post getPost = findPostById.get();

			getPost.setTitle(postDTO.getTitle());
			getPost.setContent(postDTO.getContent());
			getPost.setImageName(postDTO.getImageName());

			Post updatedPost = postRepository.save(getPost);
			return modelMapper.map(updatedPost, PostDTO.class);
		}
		return null;
	}

	@Override
	public PostDTO getPostById(Long postId) {
		Optional<Post> findPostById = postRepository.findById(postId);
		if (findPostById.isPresent()) {
			Post getPost = findPostById.get();
			return modelMapper.map(getPost, PostDTO.class);
		}
		return null;
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		List<PostDTO> listOfAllPosts = new ArrayList<>();

		Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}

		Pageable page = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePost = postRepository.findAll(page);
		List<Post> allPosts = pagePost.getContent();
		allPosts.forEach(post -> {
			listOfAllPosts.add(modelMapper.map(post, PostDTO.class));
		});

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(listOfAllPosts);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public boolean deletePostById(Long postId) {
		postRepository.deleteById(postId);
		return true;
	}

	@Override
	public List<PostDTO> getPostsByCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(null);
		List<Post> posts = postRepository.findByCategory(category);

		List<PostDTO> postDTOs = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDTOs;
	}

	@Override
	public List<PostDTO> getPostsByUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(null);
		List<Post> postsByUser = postRepository.findByUser(user);

		List<PostDTO> postDTOs = postsByUser.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDTOs;
	}

	@Override
	public List<PostDTO> searchPosts(String keyword) {
		List<Post> findByTitle = postRepository.findByTitleContaining(keyword);
		List<PostDTO> listOfPostDTOs = findByTitle.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return listOfPostDTOs;
	}

}
