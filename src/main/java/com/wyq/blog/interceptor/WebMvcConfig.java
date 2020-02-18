package com.wyq.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author forev
 * @Description
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/admin/**")
		.excludePathPatterns("/admin").excludePathPatterns("/admin/login");

	}

	/**
	 * 防止表单重复提交
	 */
	/*@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/success").setViewName("/admin/login");
	}*/
}
