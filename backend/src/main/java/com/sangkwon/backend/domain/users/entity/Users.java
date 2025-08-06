package com.sangkwon.backend.domain.users.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, length = 10)
	private String name;
	
	private String number;
	private String industry;
	
	private LocalDateTime create_at;
	private LocalDateTime delete_at;
	
	public Users() {}
	
	public Users(Long id, String email, String password, String name, String number, String industry,
			LocalDateTime create_at, LocalDateTime delete_at) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.number = number;
		this.industry = industry;
		this.create_at = create_at;
		this.delete_at = delete_at;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
