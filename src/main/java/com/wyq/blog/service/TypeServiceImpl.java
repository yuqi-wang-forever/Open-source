package com.wyq.blog.service;

import com.wyq.blog.bean.Type;
import com.wyq.blog.dao.TypeRepository;
import com.wyq.blog.exception.DefinedNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


/**
 * @Author forev
 * @Description
 */
@Service

public class TypeServiceImpl implements TypeService {

	@Autowired
	private TypeRepository typeRepository;

	@Transactional
	@Override
	public Type saveType(Type type) {

		return typeRepository.save(type);
	}
	@Transactional
	@Override
	public Type getType(Long id) {
		return typeRepository.findById(id).get();
	}
	@Transactional
	@Override
	public Type getTypeByName(String name) {
		return typeRepository.findByName(name);
	}

	@Transactional
	@Override
	public Page<Type> listType(Pageable pageable) {
		return typeRepository.findAll(pageable);
	}

	@Override
	public List<Type> listType() {
		return typeRepository.findAll();
	}

	@Override
	public List<Type> listTypeTop(Integer size) {
		Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
		Pageable pageable = PageRequest.of(0,size,sort);
		return typeRepository.findTop(pageable);
	}

	@Transactional
	@Override
	public Type updateType(Long id, Type type) {
		Type t = typeRepository.findById(id).get();
		if (t == null){
			throw new DefinedNotFoundException("该类型不存在");
		}
		BeanUtils.copyProperties(type,t);
		return typeRepository.save(t);
	}
	@Transactional
	@Override
	public void removeType(Long id) {
		typeRepository.deleteById(id);
	}
}
