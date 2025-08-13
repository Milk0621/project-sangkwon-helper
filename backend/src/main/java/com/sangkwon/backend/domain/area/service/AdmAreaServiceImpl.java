package com.sangkwon.backend.domain.area.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sangkwon.backend.domain.area.dao.AdmAreaDAO;
import com.sangkwon.backend.domain.area.dto.AreaCountDTO;

@Service
public class AdmAreaServiceImpl implements AdmAreaService {
	
	private final AdmAreaDAO admAreaDAO;

	public AdmAreaServiceImpl(AdmAreaDAO admAreaDAO) {
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
	public int getSidoTotal(String sido) {
		return admAreaDAO.getSidoTotal(sido);
	}
	
}
