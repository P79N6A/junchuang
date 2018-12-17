package com.teachingplan.console.common;

import java.util.List;
import java.util.Map;

public class StringUtil {
	
	/**
	 * List<Map<String, Object>>特定字段转换为指定分隔符字符串
	 * @param list 输入列表
	 * @param field 字段名
	 * @param separator 分隔符
	 * @return
	 */
	public String convertListMapToString(List<Map<String, Object>> list,String field,String separator) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> temp = list.get(i);
			if(temp.containsKey(field)) {
				if(i == (list.size() -1)){
					result.append(temp.get(field));
					break;
				}
				result.append(temp.get(field) + separator);
			}
		}
		return result.toString();
	}

	public static String joinString(String[] list,String splitor){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<list.length;i++){
			sb.append(list[i]);
			if(i<list.length-1){
				sb.append(splitor);
			}
		}
		return sb.toString();
	}

	public static boolean isNullOrEmpty(String str){
		return !hasLength(str);
	}

	public static boolean isNullOrBlank(String str) {
		return !hasText(str);
	}

	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}
}
