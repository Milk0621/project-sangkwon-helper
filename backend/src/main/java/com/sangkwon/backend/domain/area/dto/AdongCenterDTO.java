package com.sangkwon.backend.domain.area.dto;

public class AdongCenterDTO {
	private String adongCd;
	private String sido;
	private String sigungu;
	private String dong;
	private Double centerLat;
	private Double centerLng;
	
	public AdongCenterDTO() {}

	public AdongCenterDTO(String adongCd, String sido, String sigungu, String dong, Double centerLat,
			Double centerLng) {
		super();
		this.adongCd = adongCd;
		this.sido = sido;
		this.sigungu = sigungu;
		this.dong = dong;
		this.centerLat = centerLat;
		this.centerLng = centerLng;
	}

	public String getAdongCd() {
		return adongCd;
	}

	public void setAdongCd(String adongCd) {
		this.adongCd = adongCd;
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

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
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
