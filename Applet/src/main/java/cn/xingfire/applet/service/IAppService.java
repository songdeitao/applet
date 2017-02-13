package cn.xingfire.applet.service;

import java.util.List;

import cn.xingfire.applet.entity.response.Apps;
import cn.xingfire.applet.entity.response.HomePage;
import cn.xingfire.applet.pojo.App;

public interface IAppService {
	public App getInfo(String uuid);

	public Apps getAppsByLevel(int level, int page, int size);
	
	Apps getRecommendApps();
	
	List<App> getLatestOnline();
	
	HomePage getHomePageData(int level, int page, int size);
	
	List<App> getAppsByKeyword(String keyword);
	
	String getAppsTotal(int level);
	
	Apps getAppsByCategory(int categoryId);
}
