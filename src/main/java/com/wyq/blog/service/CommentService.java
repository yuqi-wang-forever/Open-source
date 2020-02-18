package com.wyq.blog.service;

import com.wyq.blog.bean.Comment;

import java.util.List;

/**
 * 评论业务
 */
public interface CommentService {

	//通过blog.id查询所有评论
	List<Comment> listCommentByBlogId(Long id);

	//保存评论方法
	Comment saveComment(Comment comment);

	List<Comment> findAll();
}
