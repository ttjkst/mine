package com.ttjkst.fileSystem;

import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix="my.fileSystem")
public class FileSytemProperties {
	private String esUrl;
	private String path;
	private boolean esIgnoreIndex;
	private String esIndex;
	private String esType;
	private String esClusterName;
	public String getEsUrl() {
		return esUrl;
	}
	public void setEsUrl(String esUrl) {
		this.esUrl = esUrl;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isEsIgnoreIndex() {
		return esIgnoreIndex;
	}
	public void setEsIgnoreIndex(boolean esIgnoreIndex) {
		this.esIgnoreIndex = esIgnoreIndex;
	}
	public String getEsIndex() {
		return esIndex;
	}
	public void setEsIndex(String esIndex) {
		this.esIndex = esIndex;
	}
	public String getEsType() {
		return esType;
	}
	public void setEsType(String esType) {
		this.esType = esType;
	}
	public String getEsClusterName() {
		return esClusterName;
	}
	public void setEsClusterName(String esClusterName) {
		this.esClusterName = esClusterName;
	}
	@Override
	public String toString() {
		return "FileSytemProperties [esUrl=" + esUrl + ", path=" + path + ", esIgnoreIndex=" + esIgnoreIndex
				+ ", esIndex=" + esIndex + ", esType=" + esType + ", esClusterName=" + esClusterName + "]";
	}

	
	
}
