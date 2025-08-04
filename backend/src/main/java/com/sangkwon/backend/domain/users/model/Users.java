package com.sangkwon.backend.domain.users.model;

import java.time.LocalDateTime;

public class Users {
	private int id;
	private String email;
	private String password;
	private String name;
	private String number;
	private String industry;
	private LocalDateTime create_at;
	private LocalDateTime delete_at;
	
	public Users() {}
	
	public Users(int id, String email, String password, String name, String number, String industry,
			LocalDateTime create_at, LocalDateTime delete_at) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.number = number;
		this.industry = industry;
		this.create_at = create_at;
		this.delete_at = delete_at;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public LocalDateTime getCreate_at() {
		return create_at;
	}
	public void setCreate_at(LocalDateTime create_at) {
		this.create_at = create_at;
	}
	public LocalDateTime getDelete_at() {
		return delete_at;
	}
	public void setDelete_at(LocalDateTime delete_at) {
		this.delete_at = delete_at;
	}
	
}
