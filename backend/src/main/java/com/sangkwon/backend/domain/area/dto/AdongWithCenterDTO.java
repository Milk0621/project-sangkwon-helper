package com.sangkwon.backend.domain.area.dto;

public class AdongWithCenterDTO {
	private String name;
	private int count;
	private Double centerLat;
	private Double centerLng;
	
	public AdongWithCenterDTO() {}

	public AdongWithCenterDTO(String name, int count, Double centerLat, Double centerLng) {
		this.name = name;
		this.count = count;
		this.centerLat = centerLat;
		this.centerLng = centerLng;
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

	public Double getCenterLat() {
		return centerLat;
	}

	public void setCenterLat(Double centerLat) {
		this.centerLat = centerLat;
	}

	public Double getCenterLng() {
		return centerLng;
	}

	public void setCenterLng(Double centerLng) {
		this.centerLng = centerLng;
	}
	
}
