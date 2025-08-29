package com.sangkwon.backend.domain.area.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sangkwon.backend.domain.area.dao.AdmAreaDAO;
import com.sangkwon.backend.domain.area.dto.AdongCenterDTO;
import com.sangkwon.backend.domain.area.dto.AdongWithCenterDTO;
import com.sangkwon.backend.domain.area.dto.AreaCountDTO;
import com.sangkwon.backend.domain.area.dto.SigunguCenterDTO;

@Service
public class AdmAreaServiceImpl implements AdmAreaService {
	
	private final AdmAreaDAO admAreaDAO;

	public AdmAreaServiceImpl(AdmAreaDAO admAreaDAO) {
		this.admAreaDAO = admAreaDAO;
	}

	@Override
	public List<AreaCountDTO> listSidoStats(String lclsCode, String mclsCode, String sclsCode) {
		return admAreaDAO.listSidoStats(lclsCode, mclsCode, sclsCode);
	}

	@Override
	public List<AreaCountDTO> listSigunguStats(String sido, String lclsCode, String mclsCode, String sclsCode) {
		return admAreaDAO.listSigunguStats(sido, lclsCode, mclsCode, sclsCode);
	}

	@Override
	public List<AdongWithCenterDTO> listDongStats(String sido, String sigungu, String lclsCode, String mclsCode, String sclsCode) {
		return admAreaDAO.listDongStats(sido, sigungu, lclsCode, mclsCode, sclsCode);
	}

	@Override
	public SigunguCenterDTO getSigunguCenter(String sido, String sigungu) {
		return admAreaDAO.getSigunguCenter(sido, sigungu);
	}

	@Override
	public AdongCenterDTO getDongCenter(String sido, String sigungu, String dong) {
		return admAreaDAO.getDongCenter(sido, sigungu, dong);
	}
	
}
