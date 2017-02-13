package cn.xingfire.applet.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.xingfire.applet.dao.ICategoryDao;
import cn.xingfire.applet.entity.response.CategoryPage;
import cn.xingfire.applet.service.ICategoryService;
import cn.xingfire.applet.util.ErrorInfoUtil;

@Service("categoryService")
public class CategoryService implements ICategoryService {

	@Resource
	private ICategoryDao categoryDao;

	@Override
	public CategoryPage getCategoryPage() {
		CategoryPage cp = new CategoryPage();
		try {
			cp.firstGradeCategorys = this.categoryDao.getFirstGradeCategory();
			cp.secondGradeCategorys = this.categoryDao.getAllCategory();
		} catch (Exception e) {
			cp.errorInfo = ErrorInfoUtil.EXCEPTION;
		}
		return cp;
	}

}
