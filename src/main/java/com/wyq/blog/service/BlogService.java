package com.wyq.blog.service;

import com.wyq.blog.bean.Blog;
import com.wyq.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author forev
 * @Description
 */
public interface BlogService {
	Blog getBlog(Long id);

	Blog getAndConvert(Long id);

	Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

	Page<Blog> listBlog(Pageable pageable);

	Page<Blog> listBlog(Long tagId,Pageable pageable);

	Page<Blog> listBlog(String query,Pageable pageable);

	List<Blog> listRecommendBlogTop(Integer size);

	Map<String ,List<Blog>> archiveBlog();

	Long countBlog();

	Blog saveBlog(Blog blog);

	Blog updateBlog(Long id,Blog blog);

	void removeBlog(Long id);
}
