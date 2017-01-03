package com.ttjkst.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user")
public class Users {
	@Id
	@Column(name="id")
	private Long uId;
	@Column(name="user_name")
	private String username;
	@Column(name="pass_word")
	private String password;
	
	@Column(name="permission")
	private Integer permission;

	/**
	 * @return the uId
	 */
	@JsonIgnore
	public Long getuId() {
		return uId;
	}

	/**
	 * @param uId
	 *            the uId to set
	 */
	public void setuId(Long uId) {
		this.uId = uId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the permission
	 */
	@JsonIgnore
	public Integer getPermission() {
		return permission;
	}

	/**
	 * @param permission
	 *            the permission to set
	 */
	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Users [uId=" + uId + ", username=" + username + ", password="
				+ password + ", permission=" + permission + "]";
	}

	public Users() {
	
	}

	public Users(String username, String password, Integer permission) {
		super();
		this.username = username;
		this.password = password;
		this.permission = permission;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			if(this.uId==null){
				return true;
			}
		}
		return super.equals(obj);
	}

	public static void main(String[] args) {
		Users u = new Users();
		System.out.println(u.equals(null));
	}
}
