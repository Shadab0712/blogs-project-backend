package com.blogsproject.service.contracts;

import com.blogsproject.dto.CommentDTO;

public interface CommentContracts {

	public CommentDTO createComment(CommentDTO commentDTO, Long postId);

	public void deleteComment(Long commentId);

}
