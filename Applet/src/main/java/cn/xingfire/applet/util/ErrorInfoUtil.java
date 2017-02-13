package cn.xingfire.applet.util;

import cn.xingfire.applet.entity.response.ErrorInfo;

/**
 * 错误信息
 * 
 * @author TCLDUSER
 * 
 */
public class ErrorInfoUtil {
	public static String NORESULT_CODE = "100001";
	public static String NORESULT_DESC = "查无结果";
	public static String EXCEPTION_CODE = "100002";
	public static String EXCEPTION_DESC = "允许微橙偷个懒，稍后再试";
	public static ErrorInfo NORESULT = new ErrorInfo(NORESULT_CODE, NORESULT_DESC);
	public static ErrorInfo EXCEPTION = new ErrorInfo(EXCEPTION_CODE, EXCEPTION_DESC);
}
