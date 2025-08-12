package com.sangkwon.backend.domain.area.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sangkwon.backend.domain.area.dao.AdmAreaDAO;
import com.sangkwon.backend.domain.area.dto.AdongSearchDTO;

@Service
public class AdmAreaServiceImpl implements AdmAreaService {
	
	private final AdmAreaDAO admAreaDAO;

	public AdmAreaServiceImpl(AdmAreaDAO admAreaDAO) {
		this.admAreaDAO = admAreaDAO;
	}
	
	@Override
	public List<AdongSearchDTO> search(String query, Integer limit) {
		int lim = (limit == null) ? 10: limit;
		return admAreaDAO.search(query, lim);
	}
}
