package com.sangkwon.backend.domain.area.dto;

public class AdongSearchDTO {
	private String adongCd;		// 행정동 코드
	private String fullName;	// "서울특벌시 강남구 역삼동"
	private Double centerLat;	// 평균 위도
	private Double centerLng;	// 평균 경도
	
	public AdongSearchDTO() {}
	
	public AdongSearchDTO(String adongCd, String fullName, Double centerLat, Double centerLng) {
		super();
		this.adongCd = adongCd;
		this.fullName = fullName;
		this.centerLat = centerLat;
		this.centerLng = centerLng;
	}

	public String getAdongCd() {
		return adongCd;
	}

	public void setAdongCd(String adongCd) {
		this.adongCd = adongCd;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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
