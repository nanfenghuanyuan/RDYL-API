package com.zh.module.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zh.module.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @描述 String字符串工具类
 * @author  
 * @版本 V1.0.0
 * @日期 2017-6-6
 */
public class StrUtils {

	/**
	 * @描述 去除换行<br>
	 * @param str
	 * @return
	 * @author  
	 * @版本 v1.0.0
	 * @日期 2017-6-9
	 */
	public static String trimEnter(String str){
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		String dest = m.replaceAll("");
		return dest;
	}
	
	/**
	 * @描述 字符串空值判断<br>
	 * @param str 字符串
	 * @return 是否为空
	 * @author  
	 * @版本 v1.0.0
	 * @日期 2017-6-12
	 */
	public static boolean isBlank(String str){
		return str == null || "".equals(str.trim());
	}
	
	/**
	 * @描述 获取随机数<br>
	 * @param len 随机数长度
	 * @return 随机数
	 * @author  
	 * @版本 v1.0.0
	 * @日期 2017-6-27
	 */
	public static String getCode(int len){
		Random ran = new Random();
		StringBuffer str = new StringBuffer();
		for(int i=0;i<len;i++){
			str.append(Constants.ROUND_NUM[ran.nextInt(len)]);
		}
		return str.toString();
	}
	
	/**
	 * @描述 对象格式化为JSON<br>
	 * @param obj 返回值对象
	 * @return JSON 字符串
	 * @author  
	 * @版本 v1.0.0
	 * @日期 2017-6-28
	 */	
	public static String response(Object obj){
		JSONObject json = (JSONObject) JSON.toJSON(obj);
		return json.toJSONString();
	}
	
	/**
	 * @描述 判空<br>
	 * @param str
	 * @return
	 * @author  
	 * @版本 v1.0.0
	 * @日期 2017-7-4
	 */
	public static boolean checkNull(String str){
		return str == null || "".equals(str.trim()) || str.length() == 0;
	}
	
	public static String phoneFormat(String phone){
		if(isBlank(phone)){
			return "";
		}
		return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
	}
	
	/**
	 * 替换匹配到的字符串为目标字符串
	 * @param str
	 * @param pattern 正则表达式
	 * @param replace 目标字符串
	 * @return String
	 * @date 2018-2-2
	 * @author lina
	 */
	public static String replaceAll(String str,String pattern,String replace){
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		String dest = m.replaceAll(replace);
		return dest;
	}
	

	
	/** 
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替 
     *  
     * @param content 
     *            传入的字符串 
     * @param frontNum 
     *            保留前面字符的位数 
     * @param endNum 
     *            保留后面字符的位数 
     * @return 带星号的字符串 
     */  
	public  static  String getStarString(String content, int frontNum, int endNum) {  
		  
	    if (frontNum >= content.length() || frontNum < 0) {  
	        return content;  
	    }  
	    if (endNum >= content.length() || endNum < 0) {  
	        return content;  
	    }  
	    if (frontNum + endNum >= content.length()) {  
	        return content;  
	    }  
	    String starStr = "";  
	    for (int i = 0; i < (content.length() - frontNum - endNum); i++) {  
	        starStr = starStr + "*";  
	    }  
	    return content.substring(0, frontNum) + starStr  
	            + content.substring(content.length() - endNum, content.length());  
	
	}  
	
	/**
	 * 判断是不是数字
	 * @param str
	 * @return boolean
	 * @date 2018-4-13
	 * @author lina
	 */
	public static boolean isInteger(String str) {  
	        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
	        return pattern.matcher(str).matches();  
	}
	
	/**
	 * 
	 * @param idcard
	 * @return
	 * @return String
	 * @date 2018-5-11
	 * @author lina
	 */
	public static String getBirthdayFromIdCard(String idcard){
		if(isBlank(idcard)||idcard.length()<14){
			return "";
		}
		String birth = idcard.substring(6,14);
		return birth.substring(0, 4)+"-"+birth.substring(4, 6)+"-"+birth.substring(6, 8);
	}
	
	/**
	 * 是否含表情
	 * @param source
	 * @return
	 * @return boolean
	 * @date 2018-5-24
	 * @author lina
	 */
	public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * 
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return source;
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }

	/**
	 * 生成随机数(字母加数字)
	 * @param length
	 * @return
	 */
	public static String getCharAndNumr(int length) {
		Random random = new Random();
		StringBuffer valSb = new StringBuffer();
		String charStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int charLength = charStr.length();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(charLength);
			valSb.append(charStr.charAt(index));
		}
		return valSb.toString();

	}
}
