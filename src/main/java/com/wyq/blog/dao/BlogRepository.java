package com.wyq.blog.dao;

import com.wyq.blog.bean.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author forev
 * @Description
 */
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
	@Query("select b from Blog b where b.recommend = true  ")
	List<Blog> findTop(Pageable pageable);

	/**
	 * 1代表query 2代表pageable
	 * @param query
	 * @param pageable
	 * @return
	 */
	@Query("select b from Blog b where b.title like ?1 or b.content like ?1")
	Page<Blog> findByQuery(String query,Pageable pageable);

	@Transactional
	@Modifying
	@Query("update Blog b set b.views = b.views+1 where b.id =?1")
	int updateViews(Long id);

	@Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by " +
			"function('date_format',b.updateTime,'%Y') order by function('date_format',b.updateTime,'%Y') desc")
	List<String> findGroupByYear();

	@Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1 ")
	List<Blog> findByYear(String year);
}
