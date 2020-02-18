package com.wyq.blog.service;

import com.wyq.blog.bean.User;
import com.wyq.blog.dao.UserRepository;
import com.wyq.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author forev
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Override
	public User checkUser(String username, String password) {
		User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
		return user;
	}
}
