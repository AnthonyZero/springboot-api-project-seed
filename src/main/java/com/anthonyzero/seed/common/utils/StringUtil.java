package com.anthonyzero.seed.common.utils;

import java.util.List;


/**
 * 字符串工具类
 * @author pingjin create 2018年7月11日
 *
 */
public class StringUtil {
	/**
	 * 去掉字符串两边空白字符，如果全部是空白字符，则返回null
	 * 
	 * @param str
	 * @return
	 */
	public static String trimWhiteToNull(String str) {
		if (str == null) {
			return null;
		}
		
		if (str.isEmpty()) {
			return null;
		}
		
		str = str.trim();
		
		if (str.length() <= 0) {
			return null;
		}
		
		return str;
	}


	/**
	 * 是否有字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasText(String str) {
		return trimWhiteToNull(str) != null ? true : false;
	}
	
	/**
	 * 是否有字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasText(Object str) {
		return trimWhiteToNull(String.valueOf(str)) != null ? true : false;
	}
	
	/**
	 * replace substrings within string.
	 * 
	 * @param s
	 * @param sub
	 * @param with
	 * @return
	 */
    public static String replace(String s, String sub, String with) {
        int c = 0;
        int i = s.indexOf(sub, c);
        if (i == -1)
            return s;
        
        StringBuffer buf = new StringBuffer(s.length() + with.length());
        
        synchronized (buf) {
            do {
                buf.append(s.substring(c, i));
                buf.append(with);
                c = i + sub.length();
            } while ((i = s.indexOf(sub, c)) != -1);
            
            if (c < s.length())
                buf.append(s.substring(c, s.length()));
            
            return buf.toString();
        }
    }
	
	/**
	 * byte到十六进制字符串显示
	 * 
	 * @param b
	 * @param prefix true 带0x前缀
	 * @return
	 */
	public static String toHexString(byte b, boolean prefix) {		
		if (prefix) {
			return String.format("0x%0" + 2 + "X", b);
		}
		return String.format("%0" + 2 + "X", b);
	}
	
	/**
	 * 转为16进制字符串
	 * 
	 * @param data
	 * @return
	 */
	public static String toHexString(byte[] data) {
		StringBuffer sb = new StringBuffer();
		
		for (byte b : data) {
			sb.append(toHexString(b, false));
		}
		
		return sb.toString();
	}
	
	/**
	 * long到十六进制字符串显示
	 * 
	 * @param number
	 * @param prefix true 带0x前缀
	 * @return
	 */
    public static String toHexString(long number, boolean prefix) {
    	if (prefix) {
		    return String.format("0x%0" + 16 + "X", number);
    	}
    	return String.format("%0" + 16 + "X", number);
	}
    
    /**
	 * int到十六进制字符串显示
	 * 
	 * @param number
	 * @param prefix true 带0x前缀
	 * @return
	 */
    public static String toHexString(int number, boolean prefix) { 
    	if (prefix) {
    		return String.format("0x%0" + 8 + "X", number);
    	}
    	return String.format("%0" + 8 + "X", number);
	}
    
    /**
	 * int到十六进制字符串显示
	 * 
	 * @param number
	 * @param prefix true 带0x前缀
	 * @return
	 */
    public static String toHexString(short number, boolean prefix) { 
    	if (prefix) {
		    return String.format("0x%0" + 4 + "X", number);
    	}
    	return String.format("%0" + 4 + "X", number);
	}
    
	/**
	 * 替换字符串中的指定字符，如果为空则返回null
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceChar(String str,String fromStr,String toStr){
		if(str == null){
			return null;
		}

		if(str.indexOf(fromStr) < 0){
			return str;
		}

		str = str.replace(fromStr, toStr);
		
		return str;
	}
	
	/**
	 * 转成百分数
	 * 
	 * @param number
	 * @param pos 小数点后多少位
	 * @return
	 */
	public static String formatPercent(double number,int pos) {
        java.text.NumberFormat nf = java.text.NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(pos);
        return nf.format(number);
    }
	
	/** 
	 * 16进制字符串转byte数组
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	
	/** 
	 * Convert char to byte 
	 * 
	 * @param c char 
	 * @return byte 
	 */  
	private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}  
	
	/**
	 * 连接成字符串
	 * 
	 * @param split
	 * @param args
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String join(String split, List args) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < args.size(); i ++) {
			Object arg = args.get(i);
			
			sb.append(arg);
			
			if (i < args.size() - 1) {
				sb.append(split);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 连接成字符串
	 * 
	 * @param split
	 * @param args
	 * @return
	 */
	public static String join(String split, Object...args) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < args.length; i ++) {
			Object arg = args[i];
			
			sb.append(arg);
			
			if (i < args.length - 1) {
				sb.append(split);
			}
		}
		
		return sb.toString();
	}
}
