package cn.xingfire.applet.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.xingfire.applet.pojo.App;

public class BusinessUtil {
	/**
	 * 对图片的url进行单独处理
	 * 
	 * @param apps
	 */
	public static void operatorImages(List<App> apps) {
		if (apps != null && apps.size() > 0) {
			for (App app : apps) {
				if (app != null) {
					app.setIcon(AppletUtil.IMAGESPATH + app.getIcon());
					app.setQrCode(AppletUtil.IMAGESPATH + app.getQrCode());
					List<String> screenshots = CommonUtil.toStringArray(app.getScreenshots());
					List<String> screenshotList = new ArrayList<>();
					if (screenshots.size() > 0) {
						for (String str : screenshots) {
							if (StringUtils.isNotBlank(str)) {
								screenshotList.add(AppletUtil.IMAGESPATH + str);
							}
						}
					}
					app.setScreenshotList(screenshotList);
					app.setScreenshots("");
					app.setLabelList(CommonUtil.toStringArray(app.getLabels()));
					app.setLabels("");
				}
			}
		}
	}
}
