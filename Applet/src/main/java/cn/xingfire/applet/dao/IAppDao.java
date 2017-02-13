package cn.xingfire.applet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.xingfire.applet.pojo.App;

public interface IAppDao {
	App selectByPrimaryKey(String uuid);

	List<App> getAppsByLevel(@Param("level") int level,@Param("from") int from, @Param("size") int size);

	// 获取最新上线的app
	List<App> getLatestOnline();
	// 获取今日推荐是数据
	List<App> getRecommendApps();
	
	List<App> getAppsByKeyword(@Param("keyword") String keyword);
	
	int getAppsTotal(@Param("level") int level);
	
	List<App> getAppsByCategory(@Param("categoryId") int categoryId, @Param("from") int from, @Param("size") int size);
	
	int getAppsTotalByCategory(@Param("categoryId") int categoryId);
	
	List<App> getAppsByFirstCategory(@Param("categoryId") int categoryId, @Param("from") int from, @Param("size") int size);
	
	int getAppsTotalByFirstCategory(@Param("categoryId") int categoryId);
}