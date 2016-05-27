package com.sendmail.main;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sendmail.conf.Configure;


/**
 * �����ʼ�������
 * @author root
 *
 */
public class MailSend {
	
	
	//��ʼ��ϵͳ����
	static{
		String homeDir=System.getenv("SENDMAIL_HOME");
		
		//���û������SENDMAIL_HOME��������,��ȡ����Ŀ¼����������ļ���Just for test
		if(StringUtils.isEmpty(homeDir)){
			//eclipse
			homeDir=System.getProperty("user.dir").toString();
			//maven
			File file=new File(homeDir+"/conf/");
			if(!file.exists()){
				String homeDirT=System.getProperty("user.dir").toString()+"/src/main";
				
				file=new File(homeDirT); 
				if(!file.exists()){
					homeDir=new File(homeDir).getParent().toString();
				}else{
					homeDir=homeDirT;
				}
			}
		}
		
		
		String logDir=homeDir+"/logs/";
		String confDir=homeDir+"/conf/";
		String tmpDir=homeDir+"/temp/";
		String templateDir=homeDir+"/template/";
		
		//��������Ŀ¼
		Configure.setHomeDir(homeDir);
		Configure.setConfDir(confDir);
		Configure.setLogDir(logDir);
		Configure.setTmpDir(tmpDir);
		Configure.setTemplateDir(templateDir);
		
		//����log����
		String logConf=homeDir+"/lib/conf/log.conf";
		File file=new File(logConf);
		if(file.exists()){
			PropertyConfigurator.configure(logConf);
		}
		
		//������ʱ�ļ��к�log�ļ���
		file=new File(tmpDir);
		if(!file.exists()){
			file.mkdir();
		}
		
		file=new File(logDir);
		if(!file.exists()){
			file.mkdir();
		}
		String infoLog=logDir+"info_log/";
		String debugLog=logDir+"debug_log/";
		String errorLog=logDir+"error_log/";
		
		File infoLogFile=new File(infoLog);
		File debugLogFile=new File(debugLog);
		File errorLogFile=new File(errorLog);
		
		if(!infoLogFile.exists()){
			infoLogFile.mkdir();
		}
		if(!debugLogFile.exists()){
			debugLogFile.mkdir();
		}
		if(!errorLogFile.exists()){
			errorLogFile.mkdir();
		}
		
	}
	
	private static Logger logger = Logger.getLogger(MailSend.class);
	
	@SuppressWarnings("resource")
	public static void main(String args[]){
		
		logger.info("SendMail init....");
		
		//����û�������ָ���������ļ�·����ģ��·��,��ʹ���û��Զ���ĵ������ļ�·����ģ��·��
	    if(args.length>1){
	    	String confDir=null;
			String templateDir=null;
			
	    	for(int i=0;i<args.length;i++){
	    		
	    		if(args[i].equals("-config")){
	    			i++;
	    			confDir=args[i];
	    		}
	    		if(args[i].equals("-template")){
	    			i++;
	    			templateDir=args[i];
	    		}
	    	}
	    	if(!StringUtils.isEmpty(confDir)){
		    	//����ָ������Ŀ¼��ģ��Ŀ¼
				Configure.setConfDir(confDir+"/");
				logger.info("�û��Զ��������ļ�Ŀ¼��"+confDir);
	    	}
	    	if(!StringUtils.isEmpty(templateDir)){
		    	//����ָ������Ŀ¼��ģ��Ŀ¼
	    		Configure.setTemplateDir(templateDir+"/");
	    		logger.info("�û��Զ���ģ���ļ�Ŀ¼��"+templateDir);
	    	}
	    }
		
		
		//����Spring����
		logger.info("SendMail start....");
		new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		logger.info("SendMail start success!");
		
	}
}
