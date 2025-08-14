package com.sangkwon.backend.domain.area.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sangkwon.backend.domain.area.dto.AreaCountDTO;
import com.sangkwon.backend.domain.area.service.AdmAreaService;

@RestController
@RequestMapping("/api/areas")
public class AdmAreaController {
	
	private final AdmAreaService admAreaService;

	public AdmAreaController(AdmAreaService admAreaService) {
		this.admAreaService = admAreaService;
	}
	
	// 시/도 목록 + 수
    @GetMapping("/sidos")
    public List<AreaCountDTO> listSidos(){
        return admAreaService.listSidoStats();
    }

    // 특정 시/도 선택 시, 구/군 목록 + 수
    @GetMapping("/{sido}/sigungus")
    public List<AreaCountDTO> listSigungu(@PathVariable("sido") String sido){
        return admAreaService.listSigunguStats(sido);
    }
    
    // 특정 시/도와 구/군 선택 후 검색 시, 동 목록 + 수
    @GetMapping("/{sido}/{sigungu}/dong")
    public List<AreaCountDTO> listDong(@PathVariable("sido") String sido, @PathVariable("sigungu") String sigungu){
    	return admAreaService.listDongStats(sido, sigungu);
    }
}
