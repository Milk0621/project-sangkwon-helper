package com.sangkwon.backend.domain.area.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sangkwon.backend.domain.area.dao.AdongStatsDAO;
import com.sangkwon.backend.domain.area.dto.AdongStatsDTO;
import com.sangkwon.backend.domain.area.dto.CategoryStatDTO;

@Service
public class AdongStatsServiceImpl implements AdongStatsService {
	
	private final AdongStatsDAO dao;

	public AdongStatsServiceImpl(AdongStatsDAO dao) {
		this.dao = dao;
	}

	@Override
	public AdongStatsDTO getStats(String sido, String sigungu, String dong, int topN) {
		String adongCd = dao.findAdongCd(sido, sigungu, dong);
		if(adongCd == null) return null;
		
		int total = Optional.ofNullable(dao.selectTotalStores(adongCd)).orElse(0);
		List<CategoryStatDTO> distribution = dao.selectCategoryDistribution(adongCd);
		
		AdongStatsDTO dto = new AdongStatsDTO();
		dto.setAdongCd(adongCd);
		dto.setSido(sido);
		dto.setSigungu(sigungu);
		dto.setDong(dong);
		dto.setTotalStores(total);
		dto.setDistribution(distribution);
		
		if(!distribution.isEmpty()) {
			CategoryStatDTO top = distribution.get(0);
			dto.setTopCategoryName(top.getName());
			dto.setTopCategoryCount(top.getCount());
			dto.setCategoryRatio(top.getRatio());
			
			int n = Math.min(Math.max(topN, 1), distribution.size());
			List<CategoryStatDTO> topList = new ArrayList<>(distribution.subList(0, n));
			dto.setTop6(topList);
		}
		
		return dto;
	}
	
}
