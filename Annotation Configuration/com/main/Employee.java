package com.main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="emp_3")
public class Employee {
	
	@Id
	@Column(name="Emp_ID")
	private int id;
	
	@Column(name="Emp_Name", length=15, nullable=false, unique=true)
	private String name;
	
	// Default Column name = city, post, mobileNo.
	private String city;
	private String post;
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
