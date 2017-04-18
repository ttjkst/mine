package com.ttjkst.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;
@Repository
@Entity
@Table(name="leave_Words")
public class LeaveWords {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=40)
	private String whose;
	
	
	
	@Column(length=40)
	private String qq;
	
	@Column(length=1000)
	private String saywhat;
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=Essay.class)
	private Essay leaveFor;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="time_in_date")
	private Date timeInData;
	
	@Column
	private Boolean hasRead;
	
	@Column(length=40)
	private String email;

	/**
	 * @return the hasRead
	 */
	public Boolean getHasRead() {
		return hasRead;
	}

	/**
	 * @param hasRead
	 *            the hasRead to set
	 */
	public void setHasRead(Boolean hasRead) {
		this.hasRead = hasRead;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the timeInData
	 */
	public Date getTimeInData() {
		return timeInData;
	}

	public void setTimeInData(Date timeInData) {
		this.timeInData = timeInData;
	}

	public Boolean isHasRead() {
		return hasRead;
	}

	public Essay getLeaveFor() {
		return leaveFor;
	}

	/**
	 * @param leaveFor
	 *            the leaveFor to set
	 */
	public void setLeaveFor(Essay leaveFor) {
		this.leaveFor = leaveFor;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the whose
	 */
	public String getWhose() {
		return whose;
	}

	/**
	 * @param whose
	 *            the whose to set
	 */
	public void setWhose(String whose) {
		this.whose = whose;
	}

	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq
	 *            the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return the saywhat
	 */
	public String getSaywhat() {
		return saywhat;
	}

	/**
	 * @param saywhat
	 *            the saywhat to set
	 */
	public void setSaywhat(String saywhat) {
		this.saywhat = saywhat;
	}

}
