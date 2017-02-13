package cn.xingfire.applet.dao;

import java.util.List;

import cn.xingfire.applet.pojo.Category;

public interface ICategoryDao {
	List<Category> getAllCategory();
	List<Category> getFirstGradeCategory();
}
