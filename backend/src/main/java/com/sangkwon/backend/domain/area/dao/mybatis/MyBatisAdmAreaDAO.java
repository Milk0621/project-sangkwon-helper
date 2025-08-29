package com.sangkwon.backend.domain.area.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sangkwon.backend.domain.area.dao.AdmAreaDAO;
import com.sangkwon.backend.domain.area.dto.AdongCenterDTO;
import com.sangkwon.backend.domain.area.dto.AdongWithCenterDTO;
import com.sangkwon.backend.domain.area.dto.AreaCountDTO;
import com.sangkwon.backend.domain.area.dto.SigunguCenterDTO;
import com.sangkwon.backend.domain.area.mapper.mybatis.AdmAreaMapper;

@Repository
public class MyBatisAdmAreaDAO implements AdmAreaDAO {
	
	private final AdmAreaMapper mapper;
	
	
	public MyBatisAdmAreaDAO(AdmAreaMapper mapper) {
		this.mapper = mapper;
	}


	@Override
	public List<AreaCountDTO> listSidoStats(String lclsCode, String mclsCode, String sclsCode) {
		return mapper.listSidoStats(lclsCode, mclsCode, sclsCode);
	}


	@Override
	public List<AreaCountDTO> listSigunguStats(String sido, String lclsCode, String mclsCode, String sclsCode) {
		return mapper.listSigunguStats(sido, lclsCode, mclsCode, sclsCode);
	}


	@Override
	public List<AdongWithCenterDTO> listDongStats(String sido, String sigungu, String lclsCode, String mclsCode, String sclsCode) {
		return mapper.listDongStats(sido, sigungu, lclsCode, mclsCode, sclsCode);
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
