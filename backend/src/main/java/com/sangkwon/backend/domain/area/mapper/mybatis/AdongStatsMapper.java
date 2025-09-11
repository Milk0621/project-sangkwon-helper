package com.sangkwon.backend.domain.area.mapper.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sangkwon.backend.domain.area.dto.CategoryStatDTO;

@Mapper
public interface AdongStatsMapper {
	String findAdongCd(
			@Param("sido") String sido,
            @Param("sigungu") String sigungu,
            @Param("dong") String dong
            );

	Integer selectTotalStores(@Param("adongCd") String adongCd);

	// 전체 업종 분포(백분율 포함, count DESC)
	List<CategoryStatDTO> selectCategoryDistribution(@Param("adongCd") String adongCd);
}
