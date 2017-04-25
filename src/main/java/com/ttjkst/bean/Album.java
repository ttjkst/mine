package com.ttjkst.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="album")
public class Album {
	@Id
	private String id;
	@Column(name="des",length=255)
	private String des;
	
	@OneToOne(fetch=FetchType.EAGER)
	private Image cover;
	
	@OneToMany(targetEntity=Image.class)
	@JoinColumn(name="album_id")
	private List<Image> images;
	
	
	@Column(name="thinging")
	private String thinking;

	
	
	public String getThinking() {
		return thinking;
	}

	public void setThinking(String thinking) {
		this.thinking = thinking;
	}

	@Column(name="title",length=100)
	private String title;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Image getCover() {
		return cover;
	}

	public void setCover(Image cover) {
		this.cover = cover;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
