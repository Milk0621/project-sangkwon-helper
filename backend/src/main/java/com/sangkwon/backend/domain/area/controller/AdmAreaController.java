package com.sangkwon.backend.domain.area.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sangkwon.backend.domain.area.dto.AdongSearchDTO;
import com.sangkwon.backend.domain.area.service.AdmAreaService;

@RestController
@RequestMapping("/api/adongs")
public class AdmAreaController {
	
	private final AdmAreaService admAreaService;

	public AdmAreaController(AdmAreaService admAreaService) {
		this.admAreaService = admAreaService;
	}
	
	@GetMapping("/search")
	public List<AdongSearchDTO> search(@RequestParam String query, @RequestParam(required = false) Integer limit){
		return admAreaService.search(query, limit);
	}
}
