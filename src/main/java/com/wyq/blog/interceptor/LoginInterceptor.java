package com.wyq.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author forev
 * @Description 登录拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	/**
	 * This implementation always returns {@code true}.
	 *
	 * @param request
	 * @param response
	 * @param handler
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
							 Object handler) throws Exception {
		if (request.getSession().getAttribute("user") == null){
			response.sendRedirect("/admin");
			return false;
		}
		return true;
	}
}
