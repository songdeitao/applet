package cn.xingfire.applet.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.xingfire.applet.dao.IAppDao;
import cn.xingfire.applet.entity.response.Apps;
import cn.xingfire.applet.entity.response.ErrorInfo;
import cn.xingfire.applet.entity.response.HomePage;
import cn.xingfire.applet.pojo.App;
import cn.xingfire.applet.service.IAppService;
import cn.xingfire.applet.util.BusinessUtil;
import cn.xingfire.applet.util.ErrorInfoUtil;

@Service("appService")
public class AppServiceImpl implements IAppService {
	@Resource
	private IAppDao appDao;

	@Override
	public App getInfo(String uuid) {
		return this.appDao.selectByPrimaryKey(uuid);
	}

	@Override
	public Apps getAppsByLevel(int level, int page, int size) {
		if (page <= 0) {
			page = 1;
		}
		if (size <= 0) {
			size = 1;
		}
		// 符合mysql 的 limit 语法，将page 转成 from 
		// return this.appDao.getAppsByLevel(level, (page - 1) * size, size);
		Apps appsRes = new Apps();
		ErrorInfo error = null;
		try {
			List<App> apps = this.appDao.getAppsByLevel(level, (page - 1) * size, size);
			BusinessUtil.operatorImages(apps);
			appsRes.total = this.appDao.getAppsTotal(level) + "";
			if (apps != null && apps.size() > 0) {
				appsRes.apps = apps;
			} else {
				appsRes.errorInfo = ErrorInfoUtil.NORESULT;
			}
		} catch (Exception e) {
			error = ErrorInfoUtil.EXCEPTION;
		}
		appsRes.errorInfo = error;
		return appsRes;
	}

	@Override
	public List<App> getLatestOnline() {
		return this.appDao.getLatestOnline();
	}

	@Override
	public HomePage getHomePageData(int level, int page, int size) {
		if (page <= 0) {
			page = 1;
		}
		if (size <= 0) {
			size = 1;
		}
		// 获取推荐数据
		Apps recommendApps = this.getRecommendApps();
		Apps apps = this.getAppsByLevel(level, (page - 1) * size, size);
		HomePage hp = new HomePage();
		hp.recommendApps = recommendApps;
		hp.pageApps = apps;
		return hp;
	}

	@Override
	public List<App> getAppsByKeyword(String keyword) {
		return this.appDao.getAppsByKeyword(keyword);
	}

	@Override
	public String getAppsTotal(int level) {
		return this.appDao.getAppsTotal(level) + "";
	}

	@Override
	public Apps getRecommendApps() {
		Apps appsRes = new Apps();
		try {
			// 获取推荐数据
			List<App> recommendApps = this.appDao.getRecommendApps();
			BusinessUtil.operatorImages(recommendApps);
			if (recommendApps != null && recommendApps.size() > 0) {
				appsRes.total = recommendApps.size() + "";
				appsRes.apps = recommendApps;
			} else {
				appsRes.errorInfo = ErrorInfoUtil.NORESULT;
			}
		} catch (Exception e) {
			appsRes.errorInfo = ErrorInfoUtil.EXCEPTION;
		}
		return appsRes;
	}

	@Override
	public Apps getAppsByCategory(int categoryId) {
		Apps appsRes = new Apps();
		try {
			// 获取推荐数据
			List<App> categoryApps = this.appDao.getAppsByCategory(categoryId);
			BusinessUtil.operatorImages(categoryApps);
			if (categoryApps != null && categoryApps.size() > 0) {
				appsRes.total = categoryApps.size() + "";
				appsRes.apps = categoryApps;
			} else {
				appsRes.errorInfo = ErrorInfoUtil.NORESULT;
			}
		} catch (Exception e) {
			appsRes.errorInfo = ErrorInfoUtil.EXCEPTION;
		}
		return appsRes;
	}

}
