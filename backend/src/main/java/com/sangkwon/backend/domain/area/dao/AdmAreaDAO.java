package com.sangkwon.backend.domain.area.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sangkwon.backend.domain.area.dto.AreaCountDTO;

public interface AdmAreaDAO {
	List<AreaCountDTO> listSidoStats();
    List<AreaCountDTO> listSigunguStats(@Param("sido") String sido);
    int getSidoTotal(@Param("sido") String sido);
}
