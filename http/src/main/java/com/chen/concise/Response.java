package com.chen.concise;

/**
 * 公共返回格式
 * param <T>
 */
public class Response<T> {


	/**
	 * ERROR_CODE : 0
	 * ERROR_EXP :
	 * ERROR_MSG :
	 * RESPONSE_DATA : {"AUTH_TOKEN":"a8ca30a5-229d-4154-9a01-67cad87006b3","EASEMOB_ID":"","PASSWORD":"hq4O5=6W7M8Q9","REAL_NAME":"技术部测试01","SITE_NAME":"技术部","SITE_NO":"190227160332163001","STAFF_NAME":"JSBtest01","STAFF_NO":"190228145844582002"}
	 */

	private int ERROR_CODE;
	private String ERROR_EXP;
	private String ERROR_MSG;
	private T RESPONSE_DATA;

	public int getERROR_CODE() {
		return ERROR_CODE;
	}

	public String getERROR_EXP() {
		return ERROR_EXP == null ? "" : ERROR_EXP;
	}

	public String getERROR_MSG() {
		return ERROR_MSG == null ? "" : ERROR_MSG;
	}

	public T getRESPONSE_DATA() {
		return RESPONSE_DATA;
	}

	public void setERROR_CODE(int ERROR_CODE) {
		this.ERROR_CODE = ERROR_CODE;
	}

	public void setERROR_EXP(String ERROR_EXP) {
		this.ERROR_EXP = ERROR_EXP;
	}

	public void setERROR_MSG(String ERROR_MSG) {
		this.ERROR_MSG = ERROR_MSG;
	}

	public void setRESPONSE_DATA(T RESPONSE_DATA) {
		this.RESPONSE_DATA = RESPONSE_DATA;
	}
}
