package com.sangkwon.backend.domain.area.service;

import java.util.List;

import com.sangkwon.backend.domain.area.dto.AreaCountDTO;

public interface AdmAreaService {
	List<AreaCountDTO> listSidoStats();
    List<AreaCountDTO> listSigunguStats(String sido);
    int getSidoTotal(String sido);
}
