package com.sendmail.util;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * @author ��С��(zxy)
 * ����ͼ/��״ͼ/��״ͼ
 */
public  class ChartUtil {

	/**
	 * ��״ͼ
	 * @param data ����
	 * @param datadescription ��������
	 * @param chartTitle ͼ����
	 * @param charName ����ͼ������
	 * @param pieKeys �ֱ������ּ�
	 * @return
	 */
	public static JFreeChart createValidityComparePimChar(double[] data,String[] datadescription,String chartTitle, String charName, String[] pieKeys){
		PieDataset dataset = getDataPieSetByUtil(data,datadescription);//��ȡ���ݼ�
		return getValidityComparePimChar(dataset, chartTitle, charName, pieKeys);

	}
	/**
	 * ��״ͼ
	 * @param dataset ���ݼ�
	 * @param chartTitle ͼ����
	 * @param charName ����ͼ������
	 * @param pieKeys �ֱ������ּ�
	 * @return
	 */
	public static JFreeChart getValidityComparePimChar(PieDataset dataset,String chartTitle, String charName, String[] pieKeys){
		JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, // chart
		        // title
		        dataset,// data
		        true,// include legend
		        true, 
		        false
		        );
		// ʹ��˵����ǩ��������,ȥ���������
		// chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);��Ч��
		chart.setTextAntiAlias(false);
		// ͼƬ����ɫ
		chart.setBackgroundPaint(Color.white);
		// ����ͼ�����������������title
		Font font = new Font("����", Font.BOLD, 25);
		TextTitle title = new TextTitle(chartTitle);
		title.setFont(font);
		chart.setTitle(title);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		// ͼƬ����ʾ�ٷֱ�:Ĭ�Ϸ�ʽ

		// ָ����ͼ�����ߵ���ɫ
		// plot.setBaseSectionOutlinePaint(Color.BLACK);
		// plot.setBaseSectionPaint(Color.BLACK);

		// ����������ʱ����Ϣ
		plot.setNoDataMessage("�޶�Ӧ�����ݣ������²�ѯ��");

		// ����������ʱ����Ϣ��ʾ��ɫ
		plot.setNoDataMessagePaint(Color.red);

		// ͼƬ����ʾ�ٷֱ�:�Զ��巽ʽ��{0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ���� ,С�������λ
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
		        "{0}={1}({2})", NumberFormat.getNumberInstance(),
		        new DecimalFormat("0.00%")));
		// ͼ����ʾ�ٷֱ�:�Զ��巽ʽ�� {0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ����
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
		        "{0}={1}({2})"));

		plot.setLabelFont(new Font("SansSerif", Font.TRUETYPE_FONT, 12));

		// ָ��ͼƬ��͸����(0.0-1.0)
		plot.setForegroundAlpha(0.65f);
		// ָ����ʾ�ı�ͼ��Բ��(false)����Բ��(true)
		plot.setCircular(false, true);

		// ���õ�һ�� ����section �Ŀ�ʼλ�ã�Ĭ����12���ӷ���
		plot.setStartAngle(90);

		// // ���÷ֱ���ɫ
		plot.setSectionPaint(pieKeys[0], new Color(244, 194, 144));
		plot.setSectionPaint(pieKeys[1], new Color(144, 233, 144));
		/*------���������˵ײ��������������-----------*/   
		chart.getLegend().setItemFont(new Font("����", Font.PLAIN, 12)); 
		return chart;
	}
	
	/**
	 * ��״ͼ
	 * @param data ����
	 * @param rowKeys ��
	 * @param columnKeys ��
	 * @param xName x���˵���������࣬ʱ��ȣ�
	 * @param yName y���˵�������ٶȣ�ʱ��ȣ�
	 * @param chartTitle ͼ����
	 * @param charName ����ͼƬ������
	 * @return
	 */
	public static JFreeChart createBarChart(double[][] data, String[] rowKeys,String[] columnKeys, String xName,
	        String yName, String chartTitle, String charName){
		CategoryDataset dataset = getBarData(data,rowKeys,columnKeys);
		return getBarChart(dataset, xName, yName, chartTitle, charName);
	}	
	/**
	 * ��״ͼ
	 * @param dataset ���ݼ�
	 * @param xName x���˵���������࣬ʱ��ȣ�
	 * @param yName y���˵�������ٶȣ�ʱ��ȣ�
	 * @param chartTitle ͼ����
	 * @param charName ����ͼƬ������
	 * @return
	 */
	public static JFreeChart getBarChart(CategoryDataset dataset, String xName,
	        String yName, String chartTitle, String charName){
		JFreeChart chart = ChartFactory.createBarChart3D(chartTitle, // ͼ�����
				xName, // Ŀ¼�����ʾ��ǩ
				yName, // ��ֵ�����ʾ��ǩ
				dataset, // ���ݼ�
				PlotOrientation.VERTICAL, // ͼ����ˮƽ����ֱ
				true, // �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)
				false, // �Ƿ����ɹ���
				false // �Ƿ�����URL����
				);
		chart.setTextAntiAlias(false);
		chart.setBackgroundPaint(Color.WHITE);
		// ����ͼ�����������������title
		Font font = new Font("����", Font.BOLD, 25);
		TextTitle title = new TextTitle("����ɼ� �Ƚ�");
		title.setFont(font);
		chart.setTitle(title);
		
		// �����������
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);

		chart.setBackgroundPaint(Color.WHITE);

		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		// x�� // �����������Ƿ�ɼ�
		categoryplot.setDomainGridlinesVisible(true);
		// y�� //�����������Ƿ�ɼ�
		categoryplot.setRangeGridlinesVisible(true);

		categoryplot.setRangeGridlinePaint(Color.WHITE);// ����ɫ��

		categoryplot.setDomainGridlinePaint(Color.WHITE);// ����ɫ��

		categoryplot.setBackgroundPaint(Color.lightGray);

		// ����������֮��ľ���
		// categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));

		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		/*------����X�������ϵ�����-----------*/ 
		domainAxis.setLabelFont(labelFont);// �����
		 /*------����X��ı�������------------*/ 
		domainAxis.setTickLabelFont(labelFont);// ����ֵ
		//X��ֵ����б�� (45,90,STANDARD)��up/down
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD); // �����ϵ�
		// Lable
		// 45����б
		// ���þ���ͼƬ��˾���
		domainAxis.setLowerMargin(0.0);
		// ���þ���ͼƬ�Ҷ˾���
		domainAxis.setUpperMargin(0.0);

		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		/*------����Y�������ϵ�����-----------*/ 
	    numberaxis.setTickLabelFont(labelFont);// �����   
	    /*------����Y��ı�������------------*/ 
		numberaxis.setLabelFont(labelFont);// ����ֵ
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);
		
		/*------���������˵ײ��������������-----------*/   
		chart.getLegend().setItemFont(new Font("����", Font.PLAIN, 12));
		return chart;
	}
	
	/**
	 * ����ͼ
	 * @param chartTitle
	 * @param x x���˵���������࣬ʱ��ȣ�
	 * @param y y���˵�������ٶȣ�ʱ��ȣ�
	 * @param data 
	 * @param rowKeys
	 * @param columnKeys
	 * @param charName
	 * @return
	 */
	public static JFreeChart createTimeXYChar(String chartTitle, String x, String y,
			double[][] data, String[] rowKeys,String[] columnKeys, String charName){
		CategoryDataset dataset = getBarData(data,rowKeys,columnKeys);
		 return getTimeXYChar(chartTitle, x, y, dataset, charName);
	 }
	/**
	 * ����ͼ
	 * @param chartTitle
	 * @param x x���˵���������࣬ʱ��ȣ�
	 * @param y y���˵�������ٶȣ�ʱ��ȣ�
	 * @param dataset���ݼ�
	 * @param charName ����ͼƬ������
	 * @return
	 */
	public static JFreeChart getTimeXYChar(String chartTitle, String x, String y,
			CategoryDataset dataset, String charName){
		JFreeChart chart = ChartFactory.createLineChart(
				chartTitle,// ͼ�����
				x,// Ŀ¼�����ʾ��ǩ
				y,// ��ֵ�����ʾ��ǩ
				dataset,// ���ݼ�
				PlotOrientation.VERTICAL,// ͼ����ˮƽ����ֱ
				true,// �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)
				true, // �Ƿ����ɹ���
				false // �Ƿ�����URL����
				);

		chart.setTextAntiAlias(false);
		chart.setBackgroundPaint(Color.WHITE);
		// ����ͼ�����������������title
		Font font = new Font("����", Font.BOLD, 25);
		TextTitle title = new TextTitle(chartTitle);
		title.setFont(font);
		chart.setTitle(title);
		// �����������
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);

		chart.setBackgroundPaint(Color.WHITE);

		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		// x�� // �����������Ƿ�ɼ�
		categoryplot.setDomainGridlinesVisible(true);
		// y�� //�����������Ƿ�ɼ�
		categoryplot.setRangeGridlinesVisible(true);

		categoryplot.setRangeGridlinePaint(Color.WHITE);// ����ɫ��

		categoryplot.setDomainGridlinePaint(Color.WHITE);// ����ɫ��

		categoryplot.setBackgroundPaint(Color.lightGray);

		// ����������֮��ľ���
		// categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));

		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		/*------����X�������ϵ�����-----------*/ 
		domainAxis.setLabelFont(labelFont);// �����
		 /*------����X��ı�������------------*/ 
		domainAxis.setTickLabelFont(labelFont);// ����ֵ

		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // �����ϵ�
		// Lable
		// 45����б
		// ���þ���ͼƬ��˾���
		domainAxis.setLowerMargin(0.0);
		// ���þ���ͼƬ�Ҷ˾���
		domainAxis.setUpperMargin(0.0);

		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		/*------����Y�������ϵ�����-----------*/ 
	    numberaxis.setTickLabelFont(labelFont);// �����   
	    /*------����Y��ı�������------------*/ 
		numberaxis.setLabelFont(labelFont);// ����ֵ
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);
		
		/*------���������˵ײ��������������-----------*/   
		chart.getLegend().setItemFont(new Font("����", Font.PLAIN, 12));   

		// ���renderer ע���������������͵�lineandshaperenderer����
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
		        .getRenderer();

		lineandshaperenderer.setBaseShapesVisible(true); // series �㣨�����ݵ㣩�ɼ�
		lineandshaperenderer.setBaseLinesVisible(true); // series �㣨�����ݵ㣩�������߿ɼ�

		// ��ʾ�۵�����
		 lineandshaperenderer.setBaseItemLabelGenerator(new
		 StandardCategoryItemLabelGenerator());
		 lineandshaperenderer.setBaseItemLabelsVisible(true);
		 return chart;
	}
	
	// ��״ͼ,����ͼ ���ݼ�
	public static CategoryDataset getBarData(double[][] data, String[] rowKeys,String[] columnKeys){
		return DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);

	}
	// ��״ͼ ���ݼ�
	public static PieDataset getDataPieSetByUtil(double[] data,String[] datadescription){
		if (data != null && datadescription != null){
			if (data.length == datadescription.length){
				DefaultPieDataset dataset = new DefaultPieDataset();
				for (int i = 0; i < data.length; i++){
					dataset.setValue(datadescription[i], data[i]);
				}
				return dataset;
			}
		}
		return null;
	}
}
