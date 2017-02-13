package cn.xingfire.applet.entity.response;

/**
 * 错误信息
 * 
 * @author TCLDUSER
 * 
 */
public class ErrorInfo {
	public String errorCode;
	public String errorDesc;

	public ErrorInfo(String errorCode, String errorDesc) {
		super();
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

}
