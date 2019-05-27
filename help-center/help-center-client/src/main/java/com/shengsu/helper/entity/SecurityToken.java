package com.shengsu.helper.entity;

/**
 * 临时token对象
 */
public class SecurityToken {
    private String accessKeyId ;
    private String accessKeySecret;
    private String stsToken;
    private String bucket;
    private String fileDir;
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public String getStsToken() {
		return stsToken;
	}
	public void setStsToken(String stsToken) {
		this.stsToken = stsToken;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	
}
