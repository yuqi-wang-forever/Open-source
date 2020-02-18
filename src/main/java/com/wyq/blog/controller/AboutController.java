package com.wyq.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author forev
 * @Description 关于我
 */
@Controller
public class AboutController {

	@GetMapping("/about")
	public String about(){
		return "about";
	}
}
