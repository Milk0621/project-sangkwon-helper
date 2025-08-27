package com.sangkwon.backend.domain.category.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sangkwon.backend.domain.category.dao.CategoryDAO;
import com.sangkwon.backend.domain.category.dto.CategoryDTO;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryDAO categoryDao;
	
	public CategoryServiceImpl(CategoryDAO categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Override
	public List<CategoryDTO> getLarge() {
		return categoryDao.listLarge();
	}

	@Override
	public List<CategoryDTO> getMiddle(String largeCd) {
		return categoryDao.listMiddle(largeCd);
	}

}
