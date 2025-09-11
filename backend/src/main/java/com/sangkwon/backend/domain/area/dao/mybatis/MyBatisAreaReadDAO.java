package com.sangkwon.backend.domain.area.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sangkwon.backend.domain.area.dao.AreaReadDAO;
import com.sangkwon.backend.domain.area.dto.AdongCenterDTO;
import com.sangkwon.backend.domain.area.dto.AdongWithCenterDTO;
import com.sangkwon.backend.domain.area.dto.AreaCountDTO;
import com.sangkwon.backend.domain.area.dto.SigunguCenterDTO;
import com.sangkwon.backend.domain.area.mapper.mybatis.AreaReadMapper;

@Repository
public class MyBatisAreaReadDAO implements AreaReadDAO {
	
	private final AreaReadMapper mapper;
	
	
	public MyBatisAreaReadDAO(AreaReadMapper mapper) {
		this.mapper = mapper;
	}


	@Override
	public List<AreaCountDTO> listSidoStats() {
		return mapper.listSidoStats();
	}


	@Override
	public List<AreaCountDTO> listSigunguStats(String sido) {
		return mapper.listSigunguStats(sido);
	}


	@Override
	public List<AdongWithCenterDTO> listDongStats(String sido, String sigungu) {
		return mapper.listDongStats(sido, sigungu);
	}


	@Override
	public SigunguCenterDTO getSigunguCenter(String sido, String sigungu) {
		return mapper.getSigunguCenter(sido, sigungu);
	}


	@Override
	public AdongCenterDTO getDongCenter(String sido, String sigungu, String dong) {
		return mapper.getDongCenter(sido, sigungu, dong);
	}

}
