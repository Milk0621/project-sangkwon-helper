package com.sangkwon.backend.domain.area.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sangkwon.backend.domain.area.dto.AdongCenterDTO;
import com.sangkwon.backend.domain.area.dto.AdongWithCenterDTO;
import com.sangkwon.backend.domain.area.dto.AreaCountDTO;
import com.sangkwon.backend.domain.area.dto.SigunguCenterDTO;
import com.sangkwon.backend.domain.area.service.AreaReadService;

@RestController
@RequestMapping("/api/areas")
public class AreaController {
	
	private final AreaReadService areaReadService;

	public AreaController(AreaReadService areaReadService) {
		this.areaReadService = areaReadService;
	}
	
	// 시/도 목록 + 수
    @GetMapping("/sidos")
    public List<AreaCountDTO> listSidos(){
        return areaReadService.listSidoStats();
    }

    // 특정 시/도 선택 시, 구/군 목록 + 수
    @GetMapping("/{sido}/sigungus")
    public List<AreaCountDTO> listSigungu(@PathVariable("sido") String sido){
        return areaReadService.listSigunguStats(sido);
    }
    
    @GetMapping("/{sido}/{sigungu}/center")
    public ResponseEntity<SigunguCenterDTO> getSigunguCenter(@PathVariable("sido") String sido, @PathVariable("sigungu") String sigungu){
    	SigunguCenterDTO dto = areaReadService.getSigunguCenter(sido, sigungu);
    	if (dto == null || dto.getCenterLat() == null || dto.getCenterLng() == null) {
            return ResponseEntity.notFound().build();
        }
    	return ResponseEntity.ok(dto);
    }
    
    // 특정 시/도와 구/군 선택 후 검색 시, 동 목록 + 수
    @GetMapping("/{sido}/{sigungu}/dong")
    public List<AdongWithCenterDTO> listDong(@PathVariable("sido") String sido, @PathVariable("sigungu") String sigungu){
    	return areaReadService.listDongStats(sido, sigungu);
    }
    
    @GetMapping("/{sido}/{sigungu}/{dong}/center")
    public ResponseEntity<AdongCenterDTO> getDongCenter(@PathVariable("sido") String sido, @PathVariable("sigungu") String sigungu, @PathVariable("dong") String dong){
    	AdongCenterDTO dto = areaReadService.getDongCenter(sido, sigungu, dong);
    	if (dto == null || dto.getCenterLat() == null || dto.getCenterLng() == null) {
            return ResponseEntity.notFound().build();
        }
    	return ResponseEntity.ok(dto);
    }

}
