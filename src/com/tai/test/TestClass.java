package com.tai.test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.tai.util.SignLink;
import com.tai.util.SignNode;
import com.tai.util.SqlUtil;

public class TestClass {
	/**
	 * 1) sql: select * from T1 where 1=1 and (((a = :AA or B like :BB or c in :CC)
	 * and d in :DD ) or e = :EE) and f like :FF 2）参数：GG = "1223" , aa = "first"
	 * 最终得到 select * from T1 where 1=1 也测一下：有些标记有参数，有些标记没有参数，看最终结果是否正确
	 * 
	 * @author Evy
	 */
	 @Test
	public void testSqlUtil() {
		Map<String, Object> map = new HashMap<>();
		
		//测试1
		map.put("GG", "1223");
		map.put("aa", "first");
		String testSql = "select * from T1 where 1=1 and"
				+ " (((a = :AA or B like :BB or c in :CC) and"
				+ " d in :DD ) or e = :EE) and f like :FF";
		String str = SqlUtil.getRunnableSql(testSql, map);
		
		System.out.println("测试sql语句1:\n" + testSql);
		System.out.println("测试参数");
		map.forEach((s, o) -> System.out.print(s + ":" + o.toString() + "\t")); 
		System.out.println("\n返回sql语句:\n" + str);
		
		System.out.println();
		
		//测试2
		map.clear();
		map.put("AA", "123");
		map.put("FF", "34");
		
		str = SqlUtil.getRunnableSql(testSql, map);
		
		System.out.println("测试sql语句2:\n" + testSql);
		System.out.println("测试参数");
		map.forEach((s, o) -> System.out.print(s + ":" + o.toString() + "\t")); 
		System.out.println("\n返回sql语句:\n" + str);
		
		System.out.println();
		
		//测试3
		String sqlStr2 = "select * from T1 where a=:GG and 1 = 1";
		str = SqlUtil.getRunnableSql(sqlStr2, map);
		
		System.out.println("测试sql语句3:\n" + sqlStr2);
		System.out.println("测试参数");
		map.forEach((s, o) -> System.out.print(s + ":" + o.toString() + "\t")); 
		System.out.println("\n返回sql语句:\n" + str);
	}
}
