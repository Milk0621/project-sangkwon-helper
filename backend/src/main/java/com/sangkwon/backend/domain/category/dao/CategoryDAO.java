package com.sangkwon.backend.domain.category.dao;

import java.util.List;

import com.sangkwon.backend.domain.category.dto.CategoryDTO;

public interface CategoryDAO {
	List<CategoryDTO> listLarge();
    List<CategoryDTO> listMiddle(String largeCd);
}
