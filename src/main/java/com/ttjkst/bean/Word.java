package com.ttjkst.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="words")
public class Word implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="title",length=60)
	private String title;
	@Transient
	private String content;
	
	@JoinColumn(name="kind_id")
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Kinds.class,cascade=CascadeType.PERSIST)
	private Kinds kind;
	@Transient
	private String author;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="canshow")
	private boolean canShow;
	@Column(name="readed_time",length=100)
	private Long readedTimes;
	
	@OneToMany(mappedBy="leaveFor",cascade={CascadeType.REMOVE})
	private Set<LeaveWords> leaveWords;

	public Set<LeaveWords> getLeaveWords() {
		return leaveWords;
	}
	

	public Word() {
		super();
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





	public Kinds getKind() {
		return kind;
	}


	public void setKind(Kinds kind) {
		this.kind = kind;
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
