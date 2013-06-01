package me.kafeitu.weixin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * http[request|response]池
 * 
 * @author HenryYan
 */
public class ThreadContextHolder {

	private static ThreadLocal<HttpServletRequest> HttpRequestThreadLocalHolder = new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> HttpResponseThreadLocalHolder = new ThreadLocal<HttpServletResponse>();

	public static void setHttpRequest(HttpServletRequest request) {
		HttpRequestThreadLocalHolder.set(request);
	}

	public static HttpServletRequest getHttpRequest() {
		return HttpRequestThreadLocalHolder.get();
	}
	
	public static HttpSession getHttpSession() {
		return getHttpRequest().getSession();
	}

	public static void setHttpResponse(HttpServletResponse response) {
		HttpResponseThreadLocalHolder.set(response);
	}

	public static HttpServletResponse getHttpResponse() {
		return HttpResponseThreadLocalHolder.get();
	}
}