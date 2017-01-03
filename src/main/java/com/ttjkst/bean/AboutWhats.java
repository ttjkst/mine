package com.ttjkst.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;
@Repository
@Entity
@Table(name="about_what")
public class AboutWhats implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Aid;
	
	@Column(length=40)
	private String name;

	/**
	 * @return the aid
	 */
	/**
	 * @return the aid
	 */
	public Long getAid() {
		return Aid;
	}

	/**
	 * @param aid
	 *            the aid to set
	 */
	public void setAid(Long aid) {
		Aid = aid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public AboutWhats(Long aid, String name) {
		super();
		Aid = aid;
		this.name = name;
	}

	public AboutWhats() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AboutWhats [Aid=" + Aid + ", name=" + name + "]";
	}

}
