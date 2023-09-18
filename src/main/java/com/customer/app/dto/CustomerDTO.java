package com.customer.app.dto;

import javax.validation.constraints.NotNull;

public class CustomerDTO {
	@NotNull
	private String id;
	@NotNull
	private String name;
	@NotNull
	private String email;
	@NotNull
	private String phone;
	public CustomerDTO() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "CustomerDTO [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + "]";
	}

}
