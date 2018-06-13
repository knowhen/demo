package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable = false, unique = true)
	private String phone;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String area;
	@Column
	private String relationship;

	public User() {
		super();
	}

	public User(String phone, String password, String area, String relationship) {
		super();
		this.phone = phone;
		this.password = password;
		this.area = area;
		this.relationship = relationship;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", phone=" + phone + ", password=" + password + ", area=" + area + ", relationship="
				+ relationship + "]";
	}

}
