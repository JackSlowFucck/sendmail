package com.sendmail.model;

import java.io.Serializable;
import java.util.Map;

public class MailModel implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * �ʼ�������
     */
    private String from;
    /**
     * �ʼ�������
     */
    private String to;
    /**
     * �ʼ�����
     */
    private String subject;
    /**
     * �ʼ�����
     */
    private String content;
    
    /**
     * ģ������
     */
    private String templateName;  
    /**
     * ģ������
     */
    @SuppressWarnings("rawtypes")
	private Map templateMode;
    /**
     * ����·��
     */
    private String attachPath;
    /**
     * ͼƬ·��
     */
    //Content="<html><head></head><body><img src=\"cid:image\"/></body></html>";  
    //ͼƬ���������ӣ�<img src='cid:image'/>  
    private String imagePath;
    /**
     * �Ƿ���Ҫ�����֤     
     */
    private boolean validate = false;

    
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	@SuppressWarnings("rawtypes")
	public Map getTemplateMode() {
		return templateMode;
	}

	@SuppressWarnings("rawtypes")
	public void setTemplateMode(Map templateMode) {
		this.templateMode = templateMode;
	}

	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}  
    
    
}
