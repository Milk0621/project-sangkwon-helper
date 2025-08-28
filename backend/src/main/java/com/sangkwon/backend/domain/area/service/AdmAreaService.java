package com.sangkwon.backend.domain.area.service;

import java.util.List;

import com.sangkwon.backend.domain.area.dto.AdongCenterDTO;
import com.sangkwon.backend.domain.area.dto.AdongWithCenterDTO;
import com.sangkwon.backend.domain.area.dto.AreaCountDTO;
import com.sangkwon.backend.domain.area.dto.SigunguCenterDTO;

public interface AdmAreaService {
	List<AreaCountDTO> listSidoStats();
    List<AreaCountDTO> listSigunguStats(String sido);
	List<AdongWithCenterDTO> listDongStats(String sido, String sigungu, String lclsCode);
	SigunguCenterDTO getSigunguCenter(String sido, String sigungu);
	AdongCenterDTO getDongCenter(String sido, String sigungu, String dong);
}
