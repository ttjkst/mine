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
	
	@Transient
	private String title;
	@Transient
	private String content;
	
	@JoinColumn(name="kind_id")
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Kind.class,cascade=CascadeType.PERSIST)
	private Kind kind;
	@Transient
	private String author;
	
	@Transient
	private boolean hasNoProcessLw;
	
	@Transient
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
	

	public Word() {
		this.createTime = new Date();
		this.hasNoProcessLw = true;
		this.readedTimes  = 0L;
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





	public Kind getKind() {
		return kind;
	}


	public void setKind(Kind kind) {
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


	public boolean isHasNoProcessLw() {
		return hasNoProcessLw;
	}


	public void setHasNoProcessLw(boolean hasNoProcessLw) {
		this.hasNoProcessLw = hasNoProcessLw;
	}


	@Override
	public String toString() {
		return "Word [id=" + id + ", title=" + title + ", content=" + content + ", kind=" + kind + ", author=" + author
				+ ", hasNoProcessLw=" + hasNoProcessLw + ", createTime=" + createTime + ", canShow=" + canShow
				+ ", readedTimes=" + readedTimes + ", leaveWords=" + leaveWords + "]";
	}
	
	
}
