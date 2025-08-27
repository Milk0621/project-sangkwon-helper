package com.sangkwon.backend.domain.category.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sangkwon.backend.domain.category.dto.CategoryDTO;
import com.sangkwon.backend.domain.category.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	private final CategoryService service;
	
	public CategoryController(CategoryService service) {
		this.service = service;
	}

	// 대분류 조회
    @GetMapping("/large")
    public List<CategoryDTO> listLarge() {
        return service.getLarge();
    }

    // 중분류 조회
    @GetMapping("/middle")
    public List<CategoryDTO> listMiddle(@RequestParam("largeCd") String largeCd) {
        return service.getMiddle(largeCd);
    }
}
