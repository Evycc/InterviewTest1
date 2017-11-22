package com.tai.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.tai.util.SqlUtil;

public class LiuXiaoTai {
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
		
		System.out.println();

		//测试4
		String sqlStr3 = "select * from T1 where a=:GG and 1 = 1 or 2 or :BB";
		str = SqlUtil.getRunnableSql(sqlStr3, map);
				
		System.out.println("测试sql语句4:\n" + sqlStr3);
		System.out.println("测试参数");
		map.forEach((s, o) -> System.out.print(s + ":" + o.toString() + "\t")); 
		System.out.println("\n返回sql语句:\n" + str);
		
		System.out.println();
		
		//测试5
		String sqlStr4 = "select * from T1 where 1=1 and (aa = :AA or bb = :BB) and cc = :CC";
		map.clear();
		map.put("CC", "333");
		map.put("DD", "444");
		map.put("BB", "123");
		str = SqlUtil.getRunnableSql(sqlStr4, map);
				
		System.out.println("测试sql语句5:\n" + sqlStr4);
		System.out.println("测试参数");
		map.forEach((s, o) -> System.out.print(s + ":" + o.toString() + "\t")); 
		System.out.println("\n返回sql语句:\n" + str);
		
		System.out.println();
		
		//测试6
		String sqlStr5 = "select * from T1 where 1=1 and"
				+ " (((a = :AA or B like :BB or c in :CC) and" 
				+ " d in :DD ) or e = :EE) and f like :FF";
		map.clear();
		map.put("AA", "11");
		map.put("BB", "22");
		map.put("aa", "first");
		str = SqlUtil.getRunnableSql(sqlStr5, map);
						
		System.out.println("测试sql语句6:\n" + sqlStr5);
		System.out.println("测试参数");
		map.forEach((s, o) -> System.out.print(s + ":" + o.toString() + "\t")); 
		System.out.println("\n返回sql语句:\n" + str);
		
		System.out.println();
		
		//测试7
		String sqlStr6 = "select * from T1 where 1=1 and "
		+ "(((a=:AA or B like :BB or c in :CC) and d in :DD) or e=:EE) and f like :FF";
		map.clear();
		map.put("bb", "1234"); 
		map.put("DD", "1234");   
		map.put("EE", "1234");   
		map.put("FF", "1234");     
		System.out.println("测试sql语句7:\n" + sqlStr6);
		System.out.println("测试参数");
		map.forEach((s, o) -> System.out.print(s + ":" + o.toString() + "\t")); 
		String sql = SqlUtil.getRunnableSql(sqlStr6, map);
		System.out.println("\n返回sql语句:\n" + sql);
		
		System.out.println();
		
		//测试8
		String sqlStr7 = "select * from T1 where 1=1 and ((a = :aa) or (b = :bb)) and e like :ee";
		map.clear();
		map.put("AA", "1234");
		map.put("BB", "first"); 
		map.put("CC", "1234");
		map.put("aa", "first");  
		System.out.println("测试sql语句8:\n" + sqlStr7);
		System.out.println("测试参数");
		map.forEach((s, o) -> System.out.print(s + ":" + o.toString() + "\t")); 
		str = SqlUtil.getRunnableSql(sqlStr7, map);
		System.out.println("\n返回sql语句:\n" + str);
	}
	
//	@Test
//	public void test() {
//		String sqlStr6 = "select * from T1 where 1=1 and "
//				+ "(((a=:AA or B like :BB or c in :CC) and d in :DD) or e=:EE) and f like :FF";
//		SqlUtil.getBracketsLink(new StringBuilder(sqlStr6));
//		
//		System.out.println();
//		
//		String sqlStr7 = "select * from T1 where 1=1 and ((a = :aa) or (b = :bb)) and e like :ee";
//		SqlUtil.getBracketsLink(new StringBuilder(sqlStr7));
//	}
}
