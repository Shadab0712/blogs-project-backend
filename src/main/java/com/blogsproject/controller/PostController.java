package com.blogsproject.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogsproject.configuration.AppConstants;
import com.blogsproject.dto.PostDTO;
import com.blogsproject.dto.PostResponse;
import com.blogsproject.service.FileService;
import com.blogsproject.service.PostService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class PostController {

	@Value("${project.image}")
	private String path;

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable Long userId,
			@PathVariable Long categoryId) {
		PostDTO createdPost = postService.createPost(postDTO, userId, categoryId);
		return new ResponseEntity<PostDTO>(createdPost, HttpStatus.CREATED);
	}

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Long userId) {
		List<PostDTO> postsByUser = postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDTO>>(postsByUser, HttpStatus.OK);
	}

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Long categoryId) {
		List<PostDTO> postsByCategory = postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDTO>>(postsByCategory, HttpStatus.OK);
	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDTO> getPostsById(@PathVariable Long postId) {
		PostDTO postById = postService.getPostById(postId);
		return new ResponseEntity<PostDTO>(postById, HttpStatus.OK);
	}

	@GetMapping("/post/allPosts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.Page_Number, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.Page_Size, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.Sort_By, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.Sort_Dir, required = false) String sortDir) {
		PostResponse postResponse = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Long postId) {
		PostDTO updatedPost = postService.updatePost(postDTO, postId);
		return new ResponseEntity<>(updatedPost, HttpStatus.OK);
	}

	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostDTO> results = postService.searchPosts(keywords);
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@DeleteMapping("/post/{postId}")
	public ResponseEntity<String> deletePostById(@PathVariable Long postId) {
		postService.deletePostById(postId);
		return new ResponseEntity<>("Post Deleted", HttpStatus.OK);
	}

	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Long postId) throws IOException {

		PostDTO postDTO = postService.getPostById(postId);
		String fileName = fileService.uploadImage(path, image);
		postDTO.setImageName(fileName);
		PostDTO updatedPost = postService.updatePost(postDTO, postId);
		return new ResponseEntity<>(updatedPost, HttpStatus.OK);
	}

	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}
