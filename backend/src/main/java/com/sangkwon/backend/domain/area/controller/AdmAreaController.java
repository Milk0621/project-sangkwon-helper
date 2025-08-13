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

    // 특정 시/도 선택 시, 구/군 목록 + 수, 그리고 총합
    @GetMapping("/sidos/{sido}/sigungus")
    public Map<String,Object> listSigungu(@PathVariable("sido") String sido){
        var items = admAreaService.listSigunguStats(sido);
        var total = admAreaService.getSidoTotal(sido);
        return Map.of("sido", sido, "total", total, "items", items);
    }
}
