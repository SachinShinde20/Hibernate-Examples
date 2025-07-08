package com.HibernetExamples;

// Pojo, Entity, Bean Class
public class Student {
	
	private int id;
	private String name;
	private String course;
	private String city;
	private long mobileNo;
	
	public Student() {
		super();
	}
	
	public Student(int id, String name, String cource, String city, long mobileNo) {
		super();
		this.id = id;
		this.name = name;
		this.course = cource;
		this.city = city;
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
	
	public String getCource() {
		return course;
	}
	
	public void setCource(String cource) {
		this.course = cource;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public long getMobileNo() {
		return mobileNo;
	}
	
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", course=" + course + ", city=" + city + ", mobileNo="
				+ mobileNo + "]";
	}
}
