package com.sangkwon.backend.domain.area.service;

import java.util.List;

import com.sangkwon.backend.domain.area.dto.AdongSearchDTO;

public interface AdmAreaService {
	List<AdongSearchDTO> search(String query, Integer limit);
}
