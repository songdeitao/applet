package cn.xingfire.applet.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xingfire.applet.entity.response.CategoryPage;
import cn.xingfire.applet.service.ICategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	@Resource
	private ICategoryService categoryService;

	@ResponseBody
	@RequestMapping(value = "/getAllCategory", produces = "application/json;charset=UTF-8")
	public CategoryPage getInfo(HttpServletRequest request) {
		return this.categoryService.getCategoryPage();
	}
	
}
