package com.sangkwon.backend.domain.area.dto;

import java.util.List;

public class AdongStatsDTO {
	private String adongCd;
	private String sido;
	private String sigungu;
	private String dong;
	
	private int totalStores;
	private String topCategoryName;
	private int topCategoryCount;
	private double CategoryRatio;
	
	private List<CategoryStatDTO> top6;
	private List<CategoryStatDTO> distribution;
	
	public AdongStatsDTO() {}

	public AdongStatsDTO(String adongCd, String sido, String sigungu, String dong, int totalStores,
			String topCategoryName, int topCategoryCount, double categoryRatio, List<CategoryStatDTO> top6,
			List<CategoryStatDTO> distribution) {
		this.adongCd = adongCd;
		this.sido = sido;
		this.sigungu = sigungu;
		this.dong = dong;
		this.totalStores = totalStores;
		this.topCategoryName = topCategoryName;
		this.topCategoryCount = topCategoryCount;
		CategoryRatio = categoryRatio;
		this.top6 = top6;
		this.distribution = distribution;
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

	public int getTotalStores() {
		return totalStores;
	}

	public void setTotalStores(int totalStores) {
		this.totalStores = totalStores;
	}

	public String getTopCategoryName() {
		return topCategoryName;
	}

	public void setTopCategoryName(String topCategoryName) {
		this.topCategoryName = topCategoryName;
	}

	public int getTopCategoryCount() {
		return topCategoryCount;
	}

	public void setTopCategoryCount(int topCategoryCount) {
		this.topCategoryCount = topCategoryCount;
	}

	public double getCategoryRatio() {
		return CategoryRatio;
	}

	public void setCategoryRatio(double categoryRatio) {
		CategoryRatio = categoryRatio;
	}

	public List<CategoryStatDTO> getTop6() {
		return top6;
	}

	public void setTop6(List<CategoryStatDTO> top6) {
		this.top6 = top6;
	}

	public List<CategoryStatDTO> getDistribution() {
		return distribution;
	}

	public void setDistribution(List<CategoryStatDTO> distribution) {
		this.distribution = distribution;
	}
	
}
