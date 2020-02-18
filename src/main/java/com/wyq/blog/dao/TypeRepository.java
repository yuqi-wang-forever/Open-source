package com.wyq.blog.dao;

import com.wyq.blog.bean.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

/**
 * @author forev
 */
public interface TypeRepository extends JpaRepository<Type, Long> {

	Type findByName(String name);

	@Query("select t from Type t")
	List<Type> findTop(Pageable pageable);
}
