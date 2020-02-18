package com.wyq.blog.dao;

import com.wyq.blog.bean.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author forev
 */
public interface TagRepository extends JpaRepository<Tag,Long> {
	Tag findByName(String name);

	List<Tag> findAllById(String ids);

	@Query("select t from Tag t")
	List<Tag> findTop(Pageable pageable);


	//List<Tag> findAllById(Long ids);
}
