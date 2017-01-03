package com.ttjkst.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.junit.Ignore;
import org.springframework.stereotype.Repository;
@Repository
@Entity
@Table(name="words")
public class Words implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long wId;
	
	@Column(name="title",length=60)
	private String wTitle;
	@Column(name="path",length=100)
	private String wPath;
	
	@JoinColumn(name="kind_id")
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Kinds.class,cascade=CascadeType.PERSIST)
	private Kinds wKind;
	@Column(name="author",length=100)
	private String wAuthor;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="time_in_data")
	private Date wTimeOfInData;
	
	@Column(name="canshow")
	private boolean canShow;
	@Column(name="readed_time",length=100)
	private Long readedTimes;
	
	@Column(name="intor",length=100)
	private String introductionPath;
	
	@OneToMany(mappedBy="leaveFor",cascade={CascadeType.REMOVE})
	private Set<LeaveWords> leaveWords;

	public Set<LeaveWords> getLeaveWords() {
		return leaveWords;
	}
	

	public Words() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Words(Long wId, String wTitle, String wPath, Kinds wKind,
			String wAuthor, Date wTimeOfInData, boolean canShow,
			Long readedTimes, String introductionPath,
			Set<LeaveWords> leaveWords) {
		super();
		this.wId = wId;
		this.wTitle = wTitle;
		this.wPath = wPath;
		this.wKind = wKind;
		this.wAuthor = wAuthor;
		this.wTimeOfInData = wTimeOfInData;
		this.canShow = canShow;
		this.readedTimes = readedTimes;
		this.introductionPath = introductionPath;
		this.leaveWords = leaveWords;
	}


	/**
	 * @param leaveWords
	 *            the leaveWords to set
	 */
	public void setLeaveWords(Set<LeaveWords> leaveWords) {
		this.leaveWords = leaveWords;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the introductionPath
	 */
	public String getIntroductionPath() {
		return introductionPath;
	}

	/**
	 * @param introductionPath
	 *            the introductionPath to set
	 */
	public void setIntroductionPath(String introductionPath) {
		this.introductionPath = introductionPath;
	}

	/**
	 * @return the readedTimes
	 */
	public Long getReadedTimes() {
		return readedTimes;
	}

	/**
	 * @param readedTimes
	 *            the readedTimes to set
	 */
	public void setReadedTimes(Long readedTimes) {
		this.readedTimes = readedTimes;
	}

	/**
	 * @return the canShow
	 */
	@Ignore
	public boolean isCanShow() {
		return canShow;
	}

	/**
	 * @param canShow
	 *            the canShow to set
	 */
	public void setCanShow(boolean canShow) {
		this.canShow = canShow;
	}

	/**
	 * @return the wId
	 */

	public Long getwId() {
		return wId;
	}

	/**
	 * @param wId
	 *            the wId to set
	 */
	public void setwId(Long wId) {
		this.wId = wId;
	}

	/**
	 * @return the wTitle
	 */

	public String getwTitle() {
		return wTitle;
	}

	/**
	 * @param wTitle
	 *            the wTitle to set
	 */
	public void setwTitle(String wTitle) {
		this.wTitle = wTitle;
	}

	/**
	 * @return the wPath
	 */

	public String getwPath() {
		return wPath;
	}

	/**
	 * @param wPath
	 *            the wPath to set
	 */
	public void setwPath(String wPath) {
		this.wPath = wPath;
	}

	/**
	 * @return the wKind
	 */
	@Ignore
	public Kinds getwKind() {
		return wKind;
	}

	/**
	 * @param wKind
	 *            the wKind to set
	 */
	public void setwKind(Kinds wKind) {
		this.wKind = wKind;
	}

	/**
	 * @return the wAuthor
	 */
	
	public String getwAuthor() {
		return wAuthor;
	}

	/**
	 * @param wAuthor
	 *            the wAuthor to set
	 */
	public void setwAuthor(String wAuthor) {
		this.wAuthor = wAuthor;
	}

	/**
	 * @return the wTimeOfInData
	 */

	public Date getwTimeOfInData() {
		return wTimeOfInData;
	}

	/**
	 * @param wTimeOfInData
	 *            the wTimeOfInData to set
	 */
	public void setwTimeOfInData(Date wTimeOfInData) {
		this.wTimeOfInData = wTimeOfInData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Words [wId=" + wId + ", wTitle=" + wTitle + ", wPath=" + wPath
				+ ", wKind=" + wKind + ", wAuthor=" + wAuthor
				+ ", wTimeOfInData=" + wTimeOfInData + ", canShow=" + canShow
				+ ", readedTimes=" + readedTimes + ", introductionPath="
				+ introductionPath + "]";
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return this.wId==null||
				   this.wAuthor==null||
				   this.readedTimes==null||
				   this.wPath==null||
				   this.wTimeOfInData==null
				   ||this.wTitle==null;
		}
		return super.equals(obj);
	}
}
