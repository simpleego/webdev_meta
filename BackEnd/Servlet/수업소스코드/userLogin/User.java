package com.simple.user;

public class User {
	String id;
	String password;
	String name;
	String tel;
	String addr;
	
	public User(String id, String password, String name, String tel, String addr) {		
		this.id = id;
		this.password = password;
		this.name = name;
		this.tel = tel;
		this.addr = addr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", name=" + name + ", tel=" + tel + ", addr=" + addr + "]";
	}
	
}
