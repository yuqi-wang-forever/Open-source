package com.wyq.blog.controller.admin;

import com.wyq.blog.bean.Type;
import com.wyq.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



/**
 * @Author forev
 * @Description
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
	@Autowired
	TypeService typeService;

	@GetMapping("/types")
	public String List(@PageableDefault(page =0,size = 3,sort = {"id"},direction = Sort.Direction.DESC)
							   Pageable pageable,Model model){
		model.addAttribute("page",typeService.listType(pageable));

		return "admin/types";
	}

	/**
	 * 跳转到修改页面
	 * @param model
	 * @return
	 */
	@GetMapping("/types/input")
	public String input(Model model){
		model.addAttribute("type",new Type());
		System.out.println(model);
		return "admin/types-input";
	}
	@GetMapping("/types/{id}/input")
	public String editInput(@PathVariable Long id, Model model){
		model.addAttribute("type",typeService.getType(id));
		return "admin/types-input";
	}

	@PostMapping("/types")
	public String post(@Validated Type type,BindingResult result, RedirectAttributes attributes){
		Type type2 = typeService.getTypeByName(type.getName());
		System.out.println(type2);
		if (type2 != null){
			result.rejectValue("name","nameError","该分类已存在，无需重复添加");
		}
		if (result.hasErrors()){
			return "admin/types-input";
		}
		Type type1 = typeService.saveType(type);
		if (type1 == null){
			//保存失败
			attributes.addFlashAttribute("message","操作失败");
		} else {
			//保存成功
			attributes.addFlashAttribute("message","操作成功");
		}

		return "redirect:/admin/types";
	}
	/**
	 * 修改方法
	 */
	@PutMapping("types/{id}")
	public String updatePost(@Validated Type type,BindingResult result,
						   RedirectAttributes attributes,@PathVariable Long id){
		Type type2 = typeService.getTypeByName(type.getName());
		System.out.println(type2);
		if (type2 != null){
			result.rejectValue("name","nameError","该分类已存在，无需重复添加");
		}
		if (result.hasErrors()){
			return "admin/types-input";
		}
		Type type1 = typeService.updateType(id, type);
		if (type1 == null){
			//保存失败
			attributes.addFlashAttribute("message","修改标签失败");
		} else {
			//保存成功
			attributes.addFlashAttribute("message","修改标签成功");
		}

		return "redirect:/admin/types";
	}
	@GetMapping("/types/{id}/delete")
	public String delete(@PathVariable Long id,RedirectAttributes attributes){
		typeService.removeType(id);
		attributes.addFlashAttribute("message","删除标签成功");
		return "redirect:/admin/types";
	}
}
