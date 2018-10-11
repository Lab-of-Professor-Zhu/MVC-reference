package com.edncp.vo;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyUtil {
    public static void main(String[] args) {
        System.out.println(MyUtil.dateToStrLong(new Date()));
    }


    // ==================== 字符串去重 ====================
	public String removeRepeatingStringElement(String originalString) {
        String[] originalStringArray = originalString.split(","); // 把传入的字符串变成数组
        List<String> resultList = new ArrayList<String>();
        String resultString = "";
        
        for(int i=0;i<originalStringArray.length;i++) {   
             if(!resultList.contains(originalStringArray[i]))   
            	 resultList.add(originalStringArray[i]);         
        }   
        
        // 把去重后的字符串数组拼成字符串, 用逗号连接
        for (int i=0; i<resultList.size()-1; i++) {
        	resultString = resultString + resultList.get(i) + ",";
        }
        resultString = resultString + resultList.get(resultList.size()-1);
          
        return resultString;
	}

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getCurrentShortDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    // ==================== 获取现在的时间 ====================
    public static String getCurrentStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    // ==================== 将短时间格式时间转换为字符串 yyyy-MM-dd (传入Date)[返回String] ====================
    public static String dateToStrShort(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    // ==================== 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss (传入Date)[返回String] ====================
    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }
}
