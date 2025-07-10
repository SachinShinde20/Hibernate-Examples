package com.main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Entity Class

@Entity
@Table(name="EMP_2") // Optional
public class Employee {
	
	@Id
	@Column(name="e_id") //Optional
	private int id;
	
	@Column(name="e_name", length=25)
	private String name;
	
	@Column(name="e_city")
	private String city;
	
	@Column(name="e_post")
	private String post;
	
	@Column(name="e_phone")
	private long mobileNo;
	
	public Employee() {
		super();
	}
	
	public Employee(int id, String name, String city, String post, long mobileNo) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.post = post;
		this.mobileNo = mobileNo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", city=" + city + ", post=" + post + ", mobileNo=" + mobileNo
				+ "]";
	}
}
