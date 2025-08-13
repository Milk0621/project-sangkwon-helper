package com.sangkwon.backend.domain.area.mapper.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sangkwon.backend.domain.area.dto.AreaCountDTO;

@Mapper
public interface AdmAreaMapper {
	List<AreaCountDTO> listSidoStats();
    List<AreaCountDTO> listSigunguStats(@Param("sido") String sido);
    int getSidoTotal(@Param("sido") String sido);
}
