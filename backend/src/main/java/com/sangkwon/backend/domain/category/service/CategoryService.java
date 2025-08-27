package com.sangkwon.backend.domain.category.service;

import java.util.List;

import com.sangkwon.backend.domain.category.dto.CategoryDTO;

public interface CategoryService {
	List<CategoryDTO> getLarge();
    List<CategoryDTO> getMiddle(String largeCd);
}
