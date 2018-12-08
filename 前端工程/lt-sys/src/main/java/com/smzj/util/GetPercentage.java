package com.smzj.util;

import java.text.NumberFormat;

public class GetPercentage {
	/**
	 * 计算百分比
	 * @param num1 待计算的数
	 * @param num2 总数
	 * @return
	 */
	public static String getPercentage(Integer num1,Integer num2 ){
		if(num1 ==0 || num2 == 0) {
			return "0";
		}
		// 创建一个数值格式化对象
	    NumberFormat numberFormat = NumberFormat.getInstance();
	    // 设置精确到小数点后2位
	    numberFormat.setMaximumFractionDigits(2);
	    String percentage = numberFormat.format((float) num1 / (float) num2 * 100);
		return percentage;
	}
	/**
	 * 计算百分比
	 * @param num1 待计算的数
	 * @param num2 总数
	 * @return
	 */
	public static String getPercentageByDouble(double num1,double num2 ){
		if(num1 ==0 || num2 == 0) {
			return "0";
		}
		// 创建一个数值格式化对象
	    NumberFormat numberFormat = NumberFormat.getInstance();
	    // 设置精确到小数点后2位
	    numberFormat.setMaximumFractionDigits(2);
	    String percentage = numberFormat.format((float) num1 / (float) num2 * 100);
		return percentage;
	}
}
