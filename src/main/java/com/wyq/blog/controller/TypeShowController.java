package com.wyq.blog.controller;

import com.wyq.blog.bean.Type;
import com.wyq.blog.service.BlogService;
import com.wyq.blog.service.TypeService;
import com.wyq.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author forev
 * @Description 类型分类博客
 */
@Controller
public class TypeShowController {

	@Autowired
	private TypeService typeService;

	@Autowired
	private BlogService blogService;

	@GetMapping("/types/{id}")
	public String types(@PageableDefault(size = 8,sort = {"updateTime"},direction =
			Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model){

		List<Type>  types = typeService.listTypeTop(10000);
		if (id == -1){
			//查询第一个类型的博客
			id = types.get(0).getId();
		}
		BlogQuery blogQuery = new BlogQuery();
		blogQuery.setTypeId(id);
		model.addAttribute("types",types);
		model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
		model.addAttribute("activeTypeId",id);
		return "types";
	}
}
