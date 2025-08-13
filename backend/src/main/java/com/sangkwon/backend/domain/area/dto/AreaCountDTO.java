package com.sangkwon.backend.domain.area.dto;

public class AreaCountDTO {
	private String name;
	private int count;
	
	public AreaCountDTO() {}

	public AreaCountDTO(String name, int count) {
		super();
		this.name = name;
		this.count = count;
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
	
}
