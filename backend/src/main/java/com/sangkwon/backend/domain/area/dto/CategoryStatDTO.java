package com.sangkwon.backend.domain.area.dto;

public class CategoryStatDTO {
	private String code;
	private String name;
	private int count;
	private double ratio;
	
	public CategoryStatDTO() {}

	public CategoryStatDTO(String code, String name, int count, double ratio) {
		this.code = code;
		this.name = name;
		this.count = count;
		this.ratio = ratio;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	
}
