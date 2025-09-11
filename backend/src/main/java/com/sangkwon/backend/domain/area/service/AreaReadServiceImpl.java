package com.sangkwon.backend.domain.area.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sangkwon.backend.domain.area.dao.AreaReadDAO;
import com.sangkwon.backend.domain.area.dto.AdongCenterDTO;
import com.sangkwon.backend.domain.area.dto.AdongWithCenterDTO;
import com.sangkwon.backend.domain.area.dto.AreaCountDTO;
import com.sangkwon.backend.domain.area.dto.SigunguCenterDTO;

@Service
public class AreaReadServiceImpl implements AreaReadService {
	
	private final AreaReadDAO admAreaDAO;

	public AreaReadServiceImpl(AreaReadDAO admAreaDAO) {
		this.admAreaDAO = admAreaDAO;
	}

	@Override
	public List<AreaCountDTO> listSidoStats() {
		return admAreaDAO.listSidoStats();
	}

	@Override
	public List<AreaCountDTO> listSigunguStats(String sido) {
		return admAreaDAO.listSigunguStats(sido);
	}

	@Override
	public List<AdongWithCenterDTO> listDongStats(String sido, String sigungu) {
		return admAreaDAO.listDongStats(sido, sigungu);
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
