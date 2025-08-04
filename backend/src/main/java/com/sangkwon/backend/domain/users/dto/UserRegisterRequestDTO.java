package com.sangkwon.backend.domain.users.dto;

public class UserRegisterRequestDTO {
	private String email;
	private String password;
	private String name;
	private String number;
	private String industry;
	
	public UserRegisterRequestDTO() {}
	
	public UserRegisterRequestDTO(String email, String password, String name, String number, String industry) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.number = number;
		this.industry = industry;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
}
