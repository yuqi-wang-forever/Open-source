package com.wyq.blog.controller;

import com.wyq.blog.bean.Tag;
import com.wyq.blog.service.BlogService;
import com.wyq.blog.service.TagService;
import com.wyq.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author forev
 * @Description 类型分类博客
 */
@Controller
public class TagShowController {

	@Autowired
	private TagService tagService;

	@Autowired
	private BlogService blogService;

	@GetMapping("/tags/{id}")
	public String types(@PageableDefault(size = 8,sort = {"updateTime"},direction =
			Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model){

		List<Tag>  tags = tagService.listTagTop(10000);
		if (id == -1){
			//查询第一个标签的博客
			id = tags.get(0).getId();
		}
		model.addAttribute("tags",tags);
		model.addAttribute("page",blogService.listBlog(id,pageable));
		model.addAttribute("activeTagId",id);
		return "tags";
	}
}
