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
public class Kinds implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long kId;
	
	@Column(name="name",nullable=false,length=60)
	private String kName;
	
	
	@JoinColumn(name="ab_id")
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=AboutWhats.class,cascade=CascadeType.PERSIST)
	private AboutWhats aboutwhat;

	/**
	 * @return the kId
	 */
	public Long getkId() {
		return kId;
	}

	/**
	 * @param kId
	 *            the kId to set
	 */
	public void setkId(Long kId) {
		this.kId = kId;
	}

	/**
	 * @return the kName
	 */
	public String getkName() {
		return kName;
	}

	/**
	 * @param kName
	 *            the kName to set
	 */
	public void setkName(String kName) {
		this.kName = kName;
	}

	/**
	 * @return the aboutwhat
	 */
	public AboutWhats getAboutwhat() {
		return aboutwhat;
	}

	/**
	 * @param aboutwhat
	 *            the aboutwhat to set
	 */
	public void setAboutwhat(AboutWhats aboutwhat) {
		this.aboutwhat = aboutwhat;
	}

	public Kinds(Long kId, String kName, AboutWhats aboutwhat) {
		super();
		this.kId = kId;
		this.kName = kName;
		this.aboutwhat = aboutwhat;
	}

	public Kinds() {
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
		return "Kinds [kId=" + kId + ", kName=" + kName + ", aboutwhat="
				+ aboutwhat + "]";
	}

}
