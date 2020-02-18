package com.wyq.blog.service;

import com.wyq.blog.bean.Blog;
import com.wyq.blog.bean.Type;
import com.wyq.blog.dao.BlogRepository;
import com.wyq.blog.exception.DefinedNotFoundException;
import com.wyq.blog.util.MarkDownUtils;
import com.wyq.blog.util.MyBeanUtils;
import com.wyq.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @Author forev
 * @Description
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService {
	@Autowired
	private BlogRepository blogRepository;
	@Override
	public Blog getBlog(Long id) {
		return blogRepository.findById(id).get();
	}

	@Override
	public Blog getAndConvert(Long id) {
		Blog blog = blogRepository.findById(id).get();
		if (blog == null){
			throw new DefinedNotFoundException("博客不存在");
		}
		/**
		 * 新建一个对象 把值传入新 的blog1
		 * 防止转换之后把文本改为html样式
		 * 以方便后期修改博客
		 */
		Blog blog1 = new Blog();
		BeanUtils.copyProperties(blog,blog1);
		String content = blog1.getContent();
		MarkDownUtils.markdownToHtmlExtensions(content);
		blog1.setContent(MarkDownUtils.markdownToHtmlExtensions(content));

		blogRepository.updateViews(id);
		return blog1;
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
		return blogRepository.findAll(new Specification<Blog>() {
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
					predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
				}
				if (blog.getTypeId() != null) {
					predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
				}
				if (blog.isRecommend()) {
					predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
				}
				cq.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		},pageable);
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable) {
		return blogRepository.findAll(pageable);
	}

	@Override
	public Page<Blog> listBlog(Long tagId, Pageable pageable) {
		return blogRepository.findAll(new Specification<Blog>() {
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query,
										 CriteriaBuilder criteriaBuilder) {
				Join join = root.join("tags");
				return criteriaBuilder.equal(join.get("id"),tagId);
			}
		}, pageable);
	}

	@Override
	public Page<Blog> listBlog(String query, Pageable pageable) {
		return blogRepository.findByQuery(query,pageable);
	}

	@Override
	public List<Blog> listRecommendBlogTop(Integer size) {
		Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
		Pageable pageable = PageRequest.of(0, size,sort);
		return blogRepository.findTop(pageable);
	}

	@Override
	public Map<String, List<Blog>> archiveBlog() {
		List<String> years = blogRepository.findGroupByYear();
		Map<String ,List<Blog>> map = new HashMap<>();
		for (String year :years) {
			map.put(year,blogRepository.findByYear(year));
		}
		return map;
	}

	@Override
	public Long countBlog() {
		return blogRepository.count();
	}

	@Override
	public Blog saveBlog(Blog blog) {
		if (blog.getId() == null){
			blog.setCreateTime(new Date());
			blog.setUpdateTime(new Date());
			blog.setViews(0);
		} else {
			blog.setUpdateTime(new Date());
		}
		return blogRepository.save(blog);
	}

	@Override
	public Blog updateBlog(Long id, Blog blog) {
		Blog blog1 = blogRepository.findById(id).get();
		if (blog1 == null){
			throw new DefinedNotFoundException("该博客不存在");
		}
		BeanUtils.copyProperties(blog,blog1, MyBeanUtils.getNullPropertyNames(blog));
		blog1.setUpdateTime(new Date());
		return blogRepository.save(blog1);
	}

	@Override
	public void removeBlog(Long id) {
		blogRepository.deleteById(id);
	}
}
