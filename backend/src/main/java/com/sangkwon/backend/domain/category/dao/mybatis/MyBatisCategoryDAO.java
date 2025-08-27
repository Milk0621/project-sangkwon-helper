package com.sangkwon.backend.domain.category.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sangkwon.backend.domain.category.dao.CategoryDAO;
import com.sangkwon.backend.domain.category.dto.CategoryDTO;
import com.sangkwon.backend.domain.category.mapper.mybatis.CategoryMapper;

@Repository
public class MyBatisCategoryDAO implements CategoryDAO {
	
	private final CategoryMapper mapper;

	public MyBatisCategoryDAO(CategoryMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<CategoryDTO> listLarge() {
		return mapper.largeUpjongList();
	}

	@Override
	public List<CategoryDTO> listMiddle(String largeCd) {
		return mapper.middleUpjongList(largeCd);
	}
	
}
