package com.sangkwon.backend.domain.area.dao;

import java.util.List;

import com.sangkwon.backend.domain.area.dto.AdongSearchDTO;

public interface AdmAreaDAO {
	List<AdongSearchDTO> search(String query, int limit);
}
