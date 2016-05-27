package com.sendmail.service;

import com.sendmail.model.MailModel;

/**
 * �ʼ�����Service
 * @author root
 *
 */
public interface MailSenderService {
      
    /** 
     * ����ģ���ʼ� 
     */  
    public void sendWithTemplate(MailModel mail); 
    /** 
     * ������ͨ�ı��ʼ� 
     */  
    public void sendText(MailModel mail); 
    /** 
     * ������ͨHtml�ʼ� 
     */  
    public void sendHtml(MailModel mail);  
    /** 
     * ������ͨ��һ��ͼƬ��Html�ʼ� 
     */  
    public void sendHtmlWithImage(MailModel mail);  
    /** 
     * ������ͨ��������Html�ʼ� 
     */  
    public void sendHtmlWithAttachment(MailModel mail);  
   /**
    * ͨ��ģ������HTML�ļ�ͬʱ,�滻ģ���е�ͼƬ 
    * @param mail
    */
    public void sendHtmlTempalateWithImage(MailModel mail);
}
