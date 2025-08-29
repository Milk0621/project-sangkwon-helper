package com.sangkwon.backend.domain.area.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sangkwon.backend.domain.area.dto.AdongCenterDTO;
import com.sangkwon.backend.domain.area.dto.AdongWithCenterDTO;
import com.sangkwon.backend.domain.area.dto.AreaCountDTO;
import com.sangkwon.backend.domain.area.dto.SigunguCenterDTO;

public interface AdmAreaDAO {
	List<AreaCountDTO> listSidoStats(String lclsCode, String mclsCode, String sclsCode);
    List<AreaCountDTO> listSigunguStats(String sido, String lclsCode, String mclsCode, String sclsCode);
	List<AdongWithCenterDTO> listDongStats(String sido, String sigungu, String lclsCode, String mclsCode, String sclsCode);
	SigunguCenterDTO getSigunguCenter(String sido, String sigungu);
	AdongCenterDTO getDongCenter(String sido, String sigungu, String dong);
}
