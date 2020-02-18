package com.wyq.blog.controller;

import com.wyq.blog.bean.Comment;
import com.wyq.blog.bean.User;
import com.wyq.blog.service.BlogService;
import com.wyq.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @Author forev
 * @Description 评论信息
 */
@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private BlogService blogService;

	@Value("${comment.avatar}")
	private String avatar;

	@GetMapping("/comments/{id}")
	public String comments(@PathVariable Long id, Model model){
		//将通过博客id查询出的所有评论内容存入model("comments")
		model.addAttribute("comments",commentService.listCommentByBlogId(id));
		//返回到博客页面的评论处（局部加载）
		return "blog :: commentList";
	}

	@PostMapping("/comments")
	public String post(Comment comment, HttpSession session){
		Long blogId = comment.getBlog().getId();
		comment.setBlog(blogService.getBlog(blogId));

		User user = (User)session.getAttribute("user");

		if (user != null){
			comment.setAvatar(user.getAvatar());
			comment.setAdminComment(true);
			//前端会获取昵称 故不需要传
		} else {
			//设置头像
			comment.setAvatar(avatar);
		}
		//将新评论内容存入
		commentService.saveComment(comment);
		//comment.getBlog().getId()页面hidden传入的blog.id
		return "redirect:/comments/" + blogId;
	}
}
