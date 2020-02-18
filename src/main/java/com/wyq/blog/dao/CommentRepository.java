package com.wyq.blog.dao;

import com.wyq.blog.bean.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

	//自定义方法

	/**
	 *
	 * @param blogId
	 * @param sort 跟据评论时间排序
	 * @return
	 */
	List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
