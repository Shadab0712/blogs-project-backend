package com.blogsproject.dto;

import java.io.Serializable;

public class CommentDTO implements Serializable {

	private static final long serialVersionUID = -3531248467843976005L;

	private Long commentId;
	private String content;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
