package com.wyq.blog.dao;

import com.wyq.blog.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author forev
 */
public interface UserRepository extends JpaRepository<User,Long> {
	User findByUsernameAndPassword(String username,String password);
}
