package com.sangkwon.backend.domain.category.mapper.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sangkwon.backend.domain.category.dto.CategoryDTO;

@Mapper
public interface CategoryMapper {
	List<CategoryDTO> largeUpjongList();
    List<CategoryDTO> middleUpjongList(@Param("largeCd") String largeCd);
}
