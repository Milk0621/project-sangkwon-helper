package com.sangkwon.backend.domain.area.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sangkwon.backend.domain.area.dao.AdongStatsDAO;
import com.sangkwon.backend.domain.area.dto.CategoryStatDTO;
import com.sangkwon.backend.domain.area.mapper.mybatis.AdongStatsMapper;

@Repository
public class MyBatisAdongStatsDAO implements AdongStatsDAO {

	private final AdongStatsMapper mapper;
	
	public MyBatisAdongStatsDAO(AdongStatsMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public String findAdongCd(String sido, String sigungu, String dong) {
		return mapper.findAdongCd(sido, sigungu, dong);
	}

	@Override
	public Integer selectTotalStores(String adongCd) {
		return mapper.selectTotalStores(adongCd);
	}

	@Override
	public List<CategoryStatDTO> selectCategoryDistribution(String adongCd) {
		return mapper.selectCategoryDistribution(adongCd);
	}

}
