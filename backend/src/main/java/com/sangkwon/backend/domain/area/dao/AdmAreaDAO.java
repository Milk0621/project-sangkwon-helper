package com.sangkwon.backend.domain.area.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sangkwon.backend.domain.area.dto.AdongCenterDTO;
import com.sangkwon.backend.domain.area.dto.AdongWithCenterDTO;
import com.sangkwon.backend.domain.area.dto.AreaCountDTO;
import com.sangkwon.backend.domain.area.dto.SigunguCenterDTO;

public interface AdmAreaDAO {
	List<AreaCountDTO> listSidoStats();
    List<AreaCountDTO> listSigunguStats(@Param("sido") String sido);
	List<AdongWithCenterDTO> listDongStats(String sido, String sigungu);
	SigunguCenterDTO getSigunguCenter(String sido, String sigungu);
	AdongCenterDTO getDongCenter(String sido, String sigungu, String dong);
}
