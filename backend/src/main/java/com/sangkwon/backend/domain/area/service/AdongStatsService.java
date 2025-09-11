package com.sangkwon.backend.domain.area.service;

import com.sangkwon.backend.domain.area.dto.AdongStatsDTO;

public interface AdongStatsService {
	AdongStatsDTO getStats(String sido, String sigungu, String dong, int topN);
}
