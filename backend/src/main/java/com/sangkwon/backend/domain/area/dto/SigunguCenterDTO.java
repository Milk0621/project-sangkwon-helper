package com.sangkwon.backend.domain.area.dto;

public class SigunguCenterDTO {
	private String sido;
	private String sigungu;
	private String centerLat;
	private String centerLng;
	
	public SigunguCenterDTO() {}

	public SigunguCenterDTO(String sido, String sigungu, String centerLat, String centerLng) {
		super();
		this.sido = sido;
		this.sigungu = sigungu;
		this.centerLat = centerLat;
		this.centerLng = centerLng;
	}

	public String getSido() {
		return sido;
	}

	public void setSido(String sido) {
		this.sido = sido;
	}

	public String getSigungu() {
		return sigungu;
	}

	public void setSigungu(String sigungu) {
		this.sigungu = sigungu;
	}

	public String getCenterLat() {
		return centerLat;
	}

	public void setCenterLat(String centerLat) {
		this.centerLat = centerLat;
	}

	public String getCenterLng() {
		return centerLng;
	}

	public void setCenterLng(String centerLng) {
		this.centerLng = centerLng;
	}
	
}
