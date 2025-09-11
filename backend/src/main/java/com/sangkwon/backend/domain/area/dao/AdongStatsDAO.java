package com.sangkwon.backend.domain.area.dao;

import java.util.List;

import com.sangkwon.backend.domain.area.dto.CategoryStatDTO;

public interface AdongStatsDAO {
	String findAdongCd(String sido, String sigungu, String dong);
    Integer selectTotalStores(String adongCd);
    List<CategoryStatDTO> selectCategoryDistribution(String adongCd);
}
