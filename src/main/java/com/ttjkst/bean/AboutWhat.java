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
public class AboutWhat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=40)
	private String name;

	/**
	 * @return the aid
	 */
	/**
	 * @return the aid
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param aid
	 *            the aid to set
	 */
	public void setId(Long aid) {
		id = aid;
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

	public AboutWhat(Long aid, String name) {
		super();
		id = aid;
		this.name = name;
	}

	public AboutWhat() {
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
		return "AboutWhats [Aid=" + id + ", name=" + name + "]";
	}

}
