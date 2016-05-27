package com.sendmail.chart;

import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartTheme;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import com.sendmail.chart.model.BarChartModel;
import com.sendmail.conf.Configure;

public class BarChart {
	private static Logger logger = Logger.getLogger(BarChart.class);
	public String writeImage(BarChartModel model) throws Exception {
		
		//����
		String imageId=model.getId();
		String title=model.getTitle();
		String rowName=model.getRowName();
		String columnName=model.getColumnName();
		logger.info("static image title��"+title);
		logger.info("static image row name��"+rowName);
		logger.info("static image column name��"+columnName);
		
		double[][] data = model.getData();
		String[] rowKeys =model.getRowKeys();
		String[] columnKeys =model.getColumnKeys();
		
		//���Linux�����������
		ChartTheme chartTheme=ChartFactory.getChartTheme();  
        if(chartTheme instanceof StandardChartTheme){  
            StandardChartTheme sct=(StandardChartTheme)chartTheme;  
            Font extraLarge=sct.getExtraLargeFont();  
            if(extraLarge!=null) sct.setExtraLargeFont(new Font("����",extraLarge.getStyle(),extraLarge.getSize()));  
            Font large=sct.getLargeFont();  
            if(large!=null) sct.setLargeFont(new Font("����",large.getStyle(),large.getSize()));  
            Font regular=sct.getRegularFont();  
            if(regular!=null) sct.setRegularFont(new Font("����",regular.getStyle(),regular.getSize()));  
            Font small=sct.getSmallFont();  
            if(small!=null) sct.setSmallFont(new Font("����",small.getStyle(),small.getSize()));  
        } 
        
		//����ͼ��
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
		JFreeChart chart = ChartFactory.createBarChart3D(title, rowName, columnName,dataset, PlotOrientation.VERTICAL, true, false, false);
        
		//��������
        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();  
        CategoryAxis domainAxis = categoryplot.getDomainAxis();  
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("����", Font.PLAIN, 20));      
        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));  
        domainAxis.setLabelFont(new Font("����", Font.PLAIN, 12));  
        numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));  
        numberaxis.setLabelFont(new Font("����", Font.PLAIN, 12));  
        chart.getLegend().setItemFont(new Font("����", Font.PLAIN, 12));
        
        DateFormat format=new SimpleDateFormat("yyyyMMdd");
        String dateStr=format.format(new Date());
        
        if(imageId==null){
        	imageId=title;
        }
        if(title==null){
        	imageId="ͳ��ͼ";
        }
        //����ͼƬ
        String imagePath=Configure.getTmpDir()+imageId+"-"+dateStr+".jpg";
        File file =new File(imagePath);
        
        if(!file.exists()){
			FileOutputStream outImage = null;
			try {
				outImage = new FileOutputStream(imagePath);
				ChartUtilities.writeChartAsJPEG(outImage, 1.0f, chart, 800, 600,null);
			} finally {
				try {
					outImage.close();
				} catch (Exception e) {
				}
			}
        }
        
		return imagePath;
	}
	
}
