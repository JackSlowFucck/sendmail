package com.sendmail.service.impl;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.sendmail.chart.BarChart;
import com.sendmail.chart.model.BarChartModel;
import com.sendmail.conf.Configure;
import com.sendmail.model.MailModel;
import com.sendmail.service.MailSenderService;

/**
 * �ʼ�����Service
 * @author root
 *
 */
public class MailSenderServiceImpl implements MailSenderService{
	
	private JavaMailSender mailSender;  
    private VelocityEngine velocityEngine;
    private SimpleMailMessage simpleMailMessage;
    /**
     * ͨ��ģ������HTML�ļ�ͬʱ,�滻ģ���е�ͼƬ 
     * @param mail
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void sendHtmlTempalateWithImage(MailModel mail){
    	 mailSender = this.getMailSender();  
         MimeMessage mimeMessage = mailSender.createMimeMessage();  
         try {  
             MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);  
             messageHelper.setTo(mail.getTo());  
             messageHelper.setFrom(simpleMailMessage.getFrom());  
             messageHelper.setSubject(mail.getSubject());  
             
             String result = null;  
             try {  
                 result = VelocityEngineUtils.mergeTemplateIntoString(this.getVelocityEngine(), mail.getTemplateName(), Configure.CHAR_SET_GBK,mail.getTemplateMode());
             } catch (Exception e) {
             	e.printStackTrace();
             }  
             messageHelper.setText(result,true);

             List imagesList=(List) mail.getTemplateMode().get("mail_images");
             
             if(imagesList!=null){
	             for(int i=0;i<imagesList.size();i++){
	            	 Map imagesMap=(Map)imagesList.get(i);
	            	 
		             Set set = imagesMap.keySet();
		             for(Iterator iter = set.iterator(); iter.hasNext();){
		            	 
			              String key = (String)iter.next();
			              Map image = (Map)imagesMap.get(key);
			              BarChartModel barChartModel=new BarChartModel(image);
			              
			              String imagePath=Configure.getTmpDir()+"/error.jpg";
						  try {
								imagePath = new BarChart().writeImage(barChartModel);
						   } catch (Exception e) {
								e.printStackTrace();
						  }
			            	 
			              File imageFile=new File(imagePath);
			              FileSystemResource img = new FileSystemResource(imageFile);
			              messageHelper.addInline(key,img);
		             }
	             }
             }
         } catch (MessagingException e) {  
             e.printStackTrace();  
         }  
         mailSender.send(mimeMessage);  
    }
    /** 
     * ����ģ���ʼ� 
     * 
     */  
    @SuppressWarnings("unchecked")
	public void sendWithTemplate(MailModel mail){  
        mailSender = this.getMailSender();  
        simpleMailMessage.setTo(mail.getTo()); //������    
        simpleMailMessage.setFrom(simpleMailMessage.getFrom()); //������,�������ļ���ȡ��  
        simpleMailMessage.setSubject(mail.getSubject());  
        String result = null;  
        try {  
            result = VelocityEngineUtils.mergeTemplateIntoString(this.getVelocityEngine(), mail.getTemplateName(), "UTF-8",mail.getTemplateMode());
           
        } catch (Exception e) {
        	e.printStackTrace();
     
        }  
        simpleMailMessage.setText(result);  
        mailSender.send(simpleMailMessage);  
    }  
    /** 
     * ������ͨ�ı��ʼ� 
     * 
     */  
    public void sendText(MailModel mail){  
        mailSender = this.getMailSender();  
        simpleMailMessage.setTo(mail.getTo()); //������    
        simpleMailMessage.setFrom(simpleMailMessage.getFrom()); //������,�������ļ���ȡ��  
        simpleMailMessage.setSubject(mail.getSubject());  
        simpleMailMessage.setText(mail.getContent());  
        mailSender.send(simpleMailMessage);  
    }  
    /** 
     * ������ͨHtml�ʼ� 
     * 
     */  
    public void sendHtml(MailModel mail){  
        mailSender = this.getMailSender();  
        MimeMessage mimeMessage = mailSender.createMimeMessage();  
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);  
        try {  
            messageHelper.setTo(mail.getTo());  
            messageHelper.setFrom(simpleMailMessage.getFrom());  
            messageHelper.setSubject(mail.getSubject());  
            messageHelper.setText(mail.getContent(),true);        
        } catch (MessagingException e) {  
            e.printStackTrace();  
        }  
        mailSender.send(mimeMessage);  
    }  
    /** 
     * ������ͨ��һ��ͼƬ��Html�ʼ� 
     * 
     */  
    public void sendHtmlWithImage(MailModel mail){  
        mailSender = this.getMailSender();  
        MimeMessage mimeMessage = mailSender.createMimeMessage();  
        try {  
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);  
            messageHelper.setTo(mail.getTo());  
            messageHelper.setFrom(simpleMailMessage.getFrom());  
            messageHelper.setSubject(mail.getSubject());  
            messageHelper.setText(mail.getContent(),true);

            File imageFile=new File(mail.getImagePath());
            FileSystemResource img = new FileSystemResource(imageFile);
            messageHelper.addInline("image",img);
            
        } catch (MessagingException e) {  
            e.printStackTrace();  
        }  
        mailSender.send(mimeMessage);  
    }  
    /** 
     * ������ͨ��������Html�ʼ� 
     * 
     */  
    public void sendHtmlWithAttachment(MailModel mail){  
        mailSender = this.getMailSender();  
        MimeMessage mimeMessage = mailSender.createMimeMessage();  
        try {  
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);  
            messageHelper.setTo(mail.getTo());  
            messageHelper.setFrom(simpleMailMessage.getFrom());  
            messageHelper.setSubject(mail.getSubject());  
            messageHelper.setText(mail.getContent(),true);  
            FileSystemResource file = new FileSystemResource(new File(mail.getAttachPath()));  
            messageHelper.addAttachment(file.getFilename(),file);  
        } catch (MessagingException e) {  
            e.printStackTrace();  
        }  
        mailSender.send(mimeMessage);  
    }
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	public SimpleMailMessage getSimpleMailMessage() {
		return simpleMailMessage;
	}
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}  
   
}
