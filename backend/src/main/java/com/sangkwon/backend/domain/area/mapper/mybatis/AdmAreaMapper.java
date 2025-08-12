package com.sangkwon.backend.domain.area.mapper.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sangkwon.backend.domain.area.dto.AdongSearchDTO;

@Mapper
public interface AdmAreaMapper {
	List<AdongSearchDTO> searchAdongs(@Param("query") String query, @Param("limit") int limit);
}
