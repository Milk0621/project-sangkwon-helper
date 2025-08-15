package com.sangkwon.backend.domain.area.mapper.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sangkwon.backend.domain.area.dto.AdongCenterDTO;
import com.sangkwon.backend.domain.area.dto.AdongWithCenterDTO;
import com.sangkwon.backend.domain.area.dto.AreaCountDTO;
import com.sangkwon.backend.domain.area.dto.SigunguCenterDTO;

@Mapper
public interface AdmAreaMapper {
	List<AreaCountDTO> listSidoStats();
    List<AreaCountDTO> listSigunguStats(@Param("sido") String sido);
	List<AdongWithCenterDTO> listDongStats(@Param("sido") String sido, @Param("sigungu") String sigungu);
	SigunguCenterDTO getSigunguCenter(@Param("sido") String sido, @Param("sigungu") String sigungu);
	AdongCenterDTO getDongCenter(@Param("sido") String sido, @Param("sigungu") String sigungu, @Param("dong") String dong);
}
