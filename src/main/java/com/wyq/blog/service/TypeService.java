package com.wyq.blog.service;

import com.wyq.blog.bean.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TypeService {


	Type saveType(Type type);

	Type getType(Long id);

	Type getTypeByName(String name);

	Page<Type> listType(Pageable pageable);

	List<Type> listType();

	List<Type> listTypeTop(Integer size);

	Type updateType(Long id,Type type);

	void removeType(Long id);
}
