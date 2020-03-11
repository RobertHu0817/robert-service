package com.ll.platform.robertService.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title BeanUtil.java
 * @Description 实体类工具类
 * @author Robert
 * @date 2019年12月9日
 */
public final class TransUtil {

	/**
	 * bean转map
	 * @param obj bean
	 */
	public static void transBean2Map(Object obj) {
		Map<String, Object> map = new HashMap<>();
		transBean2Map(obj, map, false);
	}

	/**
	 * bean转map
	 * @param obj bean
	 * @param ignoreNullValue 是否忽略空值
	 */
	public static void transBean2Map(Object obj, boolean ignoreNullValue) {
		Map<String, Object> map = new HashMap<>();
		transBean2Map(obj, map, ignoreNullValue);
	}
	
	/**
	 * bean转map
	 * @param obj
	 * @param map
	 * @param ignoreNullValue
	 */
	public static void transBean2Map(Object obj, Map<String, Object> map, boolean ignoreNullValue) {
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String key = propertyDescriptor.getName();
				// 排除class属性
				if (!key.equals("class")) {
					Object value = propertyDescriptor.getReadMethod().invoke(obj);
					if (ignoreNullValue && (value == null)) {
						continue;
					}
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * collection转string，与AbstractCollection.toString()区别在于去除[ ]且不自动附加空格
	 * @param collection 集合
	 * @return 字符串
	 */
	public static String coll2String(Collection<?> collection) {
		if(collection.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object object : collection) {
			sb.append(object.toString()).append(",");
		}
		return sb.substring(0, sb.length() - 1);
	}
}
