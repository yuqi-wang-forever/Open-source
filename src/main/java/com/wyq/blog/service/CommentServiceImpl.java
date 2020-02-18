package com.wyq.blog.service;

import com.wyq.blog.bean.Comment;
import com.wyq.blog.dao.CommentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author forev
 * @Description
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public List<Comment> listCommentByBlogId(Long blogId) {
		//通过创建评论时间排列 即后评论的在后
		Sort sort = Sort.by(Sort.Direction.ASC,"createTime");
		//返回通过博客id DESC排序查询的评论内容
		return commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
	}

	@Override
	public Comment saveComment(Comment comment) {
		//获取评论有没有父级评论id
		Long parentCommentId = comment.getParentComment().getId();
		if (parentCommentId != -1){
			//有则通过此id查询出代表的博客内容 并将其设置为父级佩伦
			comment.setParentComment(commentRepository.findById(parentCommentId).get());
		}else {
			//否则将父级内容设置为null
			comment.setParentComment(null);
		}
		//添加创建时间
		comment.setCreateTime(new Date());
		//返回保存评论方法
		return commentRepository.save(comment);
	}

	@Override
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	/**
	 * 循环每个顶级的评论节点
	 * @param comments
	 * @return
	 */
	private List<Comment> eachComment(List<Comment> comments) {
		List<Comment> commentsView = new ArrayList<>();
		for (Comment comment : comments) {
			Comment c = new Comment();
			BeanUtils.copyProperties(comment,c);
			commentsView.add(c);
		}
		//合并评论的各层子代到第一级子代集合中
		combineChildren(commentsView);
		return commentsView;
	}

	/**
	 *
	 * @param comments root根节点，blog不为空的对象集合
	 * @return
	 */
	private void combineChildren(List<Comment> comments) {

		for (Comment comment : comments) {
			List<Comment> replys1 = comment.getReplyComments();
			for(Comment reply1 : replys1) {
				//循环迭代，找出子代，存放在tempReplys中
				recursively(reply1);
			}
			//修改顶级节点的reply集合为迭代处理后的集合
			comment.setReplyComments(tempReplys);
			//清除临时存放区
			tempReplys = new ArrayList<>();
		}
	}

	//存放迭代找出的所有子代的集合
	private List<Comment> tempReplys = new ArrayList<>();
	private void recursively(Comment comment) {
		tempReplys.add(comment);//顶节点添加到临时存放集合
		if (comment.getReplyComments().size()>0) {
			List<Comment> replys = comment.getReplyComments();
			for (Comment reply : replys) {
				tempReplys.add(reply);
				if (reply.getReplyComments().size()>0) {
					recursively(reply);
				}
			}
		}
	}
}
