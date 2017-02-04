package com.ttjkst.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

@Repository
@Entity
@Table(name="kind")
public class Kind implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name",nullable=false,length=60)
	private String name;
	
	
	@JoinColumn(name="ab_id")
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=AboutWhat.class,cascade=CascadeType.PERSIST)
	private AboutWhat aboutwhat;

	/**
	 * @return the kId
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param kId
	 *            the kId to set
	 */
	public void setId(Long kId) {
		this.id = kId;
	}

	/**
	 * @return the kName
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param kName
	 *            the kName to set
	 */
	public void setName(String kName) {
		this.name = kName;
	}

	/**
	 * @return the aboutwhat
	 */
	public AboutWhat getAboutwhat() {
		return aboutwhat;
	}

	/**
	 * @param aboutwhat
	 *            the aboutwhat to set
	 */
	public void setAboutwhat(AboutWhat aboutwhat) {
		this.aboutwhat = aboutwhat;
	}

	public Kind(Long kId, String kName, AboutWhat aboutwhat) {
		super();
		this.id = kId;
		this.name = kName;
		this.aboutwhat = aboutwhat;
	}

	public Kind() {
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
		return "Kinds [kId=" + id + ", kName=" + name + ", aboutwhat="
				+ aboutwhat + "]";
	}

}
