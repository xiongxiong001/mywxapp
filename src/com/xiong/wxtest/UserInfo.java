package com.xiong.wxtest;

public class UserInfo {
     private Integer id;
     private String name;
     private Integer age;
     private String address;
     private String tel;
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserInfo(Integer id, String name, String address, String tel) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.tel = tel;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", address=" + address
				+ ", tel=" + tel;
	}

	
	
     
}
