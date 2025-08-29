package com.sangkwon.backend.domain.area.service;

import java.util.List;

import com.sangkwon.backend.domain.area.dto.AdongCenterDTO;
import com.sangkwon.backend.domain.area.dto.AdongWithCenterDTO;
import com.sangkwon.backend.domain.area.dto.AreaCountDTO;
import com.sangkwon.backend.domain.area.dto.SigunguCenterDTO;

public interface AdmAreaService {
	List<AreaCountDTO> listSidoStats(String lclsCode, String mclsCode, String sclsCode);
    List<AreaCountDTO> listSigunguStats(String sido, String lclsCode, String mclsCode, String sclsCode);
	List<AdongWithCenterDTO> listDongStats(String sido, String sigungu, String lclsCode, String mclsCode, String sclsCode);
	SigunguCenterDTO getSigunguCenter(String sido, String sigungu);
	AdongCenterDTO getDongCenter(String sido, String sigungu, String dong);
}
