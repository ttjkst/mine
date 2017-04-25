package com.ttjkst.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="words")
public class Essay implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	private String id;
	
	@Transient
	private Set<String> tags;  
	@Column(name="title")
	private String title;
	@Transient
	private String content;
	
	@Transient
	private String author;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Transient
	private boolean canShow;
	@Column(name="readed_time",length=100)
	private Long readedTimes;
	
	@OneToMany(mappedBy="leaveFor",cascade={CascadeType.REMOVE})
	private Set<LeaveWords> leaveWords;

	public Set<LeaveWords> getLeaveWords() {
		return leaveWords;
	}
	

	public Essay() {
		this.createTime = new Date();
		this.readedTimes  = 0L;
		this.tags = new HashSet<>();
	}


	
	public Set<String> getTags() {
		return tags;
	}


	public void setTags(Set<String> tags) {
		this.tags = tags;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public boolean isCanShow() {
		return canShow;
	}


	public void setCanShow(boolean canShow) {
		this.canShow = canShow;
	}


	public Long getReadedTimes() {
		return readedTimes;
	}


	public void setReadedTimes(Long readedTimes) {
		this.readedTimes = readedTimes;
	}


	public void setLeaveWords(Set<LeaveWords> leaveWords) {
		this.leaveWords = leaveWords;
	}
}
