package com.wyq.blog.service;

import com.wyq.blog.bean.User;

/**
 * @author forev
 */
public interface UserService {
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	User checkUser(String username,String password);
}
