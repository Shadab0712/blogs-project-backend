package com.blogsproject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogsproject.dto.CommentDTO;
import com.blogsproject.model.Comment;
import com.blogsproject.model.Post;
import com.blogsproject.repository.CommentRepository;
import com.blogsproject.repository.PostRepository;
import com.blogsproject.service.contracts.CommentContracts;

@Service
public class CommentService implements CommentContracts {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(null);
		Comment comment = modelMapper.map(commentDTO, Comment.class);
		comment.setPost(post);
		Comment savedComment = commentRepository.save(comment);
		return modelMapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}

}
