package com.blogsproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogsproject.dto.CommentDTO;
import com.blogsproject.dto.JWTLoginSucessReponse;
import com.blogsproject.service.CommentService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/post/{postId}/comment")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Long postId) {
		CommentDTO createComment = commentService.createComment(commentDTO, postId);
		return new ResponseEntity<>(createComment, HttpStatus.CREATED);
	}

	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<JWTLoginSucessReponse> deleteComment(@PathVariable Long commentId) {
		commentService.deleteComment(commentId);
		return new ResponseEntity<JWTLoginSucessReponse>(new JWTLoginSucessReponse("Comment Deleted"),
				HttpStatus.OK);
	}

}
