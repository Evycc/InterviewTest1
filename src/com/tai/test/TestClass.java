package com.tai.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.tai.util.SqlUtil;

public class TestClass {
	/**
	 *  1) sql: select * from T1 where 1=1 and 
	 *  (((a = :AA or B like :BB or c in :CC) and d in :DD ) or e = :EE) and f like :FF
	 *  2）参数：GG = "1223" , aa = "first"
	 *  最终得到 select * from T1 where 1=1  
	 *  也测一下：有些标记有参数，有些标记没有参数，看最终结果是否正确
	 *  
	 *  @author Evy
	 */
	@Test
	public void testSqlUtil() {
//		Map<String, Object> map = new HashMap<>();
//		map.put("FF", "11");
//		map.put("AA", "12");
//		SqlUtil.getRunnableSql("select * from T1 where 1=1 and"
//				+ " (((a = :AA or B like :BB or c in :CC) and"
//				+ " d in :DD ) or e = :EE) and f like :FF", map);
		
		Map<String, Object> map = new HashMap<>();
		map.put("BB", "11");
		map.put("CC", "12");
		SqlUtil.getRunnableSql("select * from T1 where 1=1 and"
				+ " (((a = :AA or B like :BB or c in :CC) and"
				+ " d in :DD ) or e = :EE) and f like :FF", map);
	}
	
//	@Test
//	public void testLink() {
//		SignLink link = new SignLink();
//		link.insert("test1");
//		link.insert("test2");
//		link.insert("test3");
//		link.insert("test4");
//		link.insert("test5");
//		link.display();
//		
//		link.deleteFirst();
//		link.display();
//	}
}
