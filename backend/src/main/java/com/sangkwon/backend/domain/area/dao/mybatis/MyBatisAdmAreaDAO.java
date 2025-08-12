package com.sangkwon.backend.domain.area.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sangkwon.backend.domain.area.dao.AdmAreaDAO;
import com.sangkwon.backend.domain.area.dto.AdongSearchDTO;
import com.sangkwon.backend.domain.area.mapper.mybatis.AdmAreaMapper;

@Repository
public class MyBatisAdmAreaDAO implements AdmAreaDAO {
	
	private final AdmAreaMapper mapper;
	
	
	public MyBatisAdmAreaDAO(AdmAreaMapper mapper) {
		this.mapper = mapper;
	}


	@Override
	public List<AdongSearchDTO> search(String query, int limit) {
		String q = (query == null) ? "" : query.trim();
		int lim = (limit <= 0 || limit > 20) ? 10 : limit;
		return mapper.searchAdongs(q, lim);
	}
	
}
