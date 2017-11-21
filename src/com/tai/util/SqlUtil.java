package com.tai.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tai.test.SignLink;
import com.tai.test.SignNode;

/**
 * 已知带有特殊标记的sql语句和前端传过来的参数集合，用参数替换sql的特殊标记，如果标记对应的参数存在，
 * 则直接替换，如果不存在，要把对应的一段条件去掉，最后得出一个能用于查询的sql语句。要求给出完整代码，代码加上必要的注释。
 * 
 * 例如:
 * 已知 1）带有特殊标记的sql语句
 * select * from T1 where 1=1 and a = :aa  and (b =:bb or b =:cc) and e like :ee
 * 
 * 2）前端传过来的参数有 aa= "111" , bb= "222", cc= "333",dd = "444"
 * 
 * 得到 select * from T1 where 1=1 and a = 111 and (b = 222 or b = 333)
 * @author Evy
 *
 */
public class SqlUtil {
	/**
	 * 用参数替换sql的特殊标记，如果标记对应的参数存在，则直接替换，如果不存在，要把对应的一段条件去掉，
	 * 最后得出一个能用于查询的sql语句，返回能用于查询的sql语句。
	 * 
	 * @param labelSql	 带有标记的sql语句, 如
	 * select * from T1 where 1=1 and a = :aa  and (b =:bb or b =:cc) and e like :ee
	 * 
	 * @param param	参数集合，如aa= "111" , bb= "222", cc= "333",dd = "444"
	 * 
	 * @return	 能用于查询的sql,如select * from T1 where 1=1 and a = 111 and (b = 222 or b = 333)
	 */
	public static String getRunnableSql(String labelSql, Map<String,Object> param){
		
		StringBuilder sBuilder = new StringBuilder(5);
		//从SQL语句中'where'开头构建可执行的SQL语句
		String buildBrfore = "where";
		int subIndex = labelSql.indexOf(buildBrfore);
		sBuilder.append(labelSql.substring(subIndex));
		
		buildSql(sBuilder, param);
		
		return labelSql;
	}
	
	/**
	 * 思路:将sql标记添加到链表，当不存在标记对应的参数时，将前一个标记为止的语句消除
	 * @param str
	 * @param param
	 */
	private static void buildSql(StringBuilder str, Map<String,Object> param) {
		//辅助标志,为0代表所有的标记都不存在对应的参数
		int paramNum = 0;
		
		//用正则表达式获取标记
		String signRegex = ":{1}[a-zA-Z]*";
		
		Pattern pattern = Pattern.compile(signRegex);
		Matcher matcher = pattern.matcher(str.toString());
		
		//存储标记的链表
		SignLink link = new SignLink();
		
		while(matcher.find()) {
			String sign = matcher.group();
			int start = matcher.start();
			int end = matcher.end();
			link.insert(new SignNode(sign, start, end));
		}
		
		link.display();
		
		//构建sql语句
		while(!link.isEmpty()) {
			SignNode node = link.deleteFirst();
			String signStr = node.getSign().substring(1);
			if (param.containsKey(signStr)) {
				//存在标记
				str.replace(node.getStart(), node.getEnd(), (String) param.get(signStr));
				paramNum++;
			}else {
				//不存在标记，消除条件
				SignNode lastNode = link.getHead();
				
				if ((lastNode == null) && (paramNum == 0)) {
					//paramNum为0则将'where'语句去除
					int startIndex = str.indexOf("where");
					System.out.println("startIndex: " + startIndex);
					str.replace(startIndex, node.getEnd(), (String) param.get(signStr));
					
				}else if ((lastNode == null) && (paramNum > 0)) {
					int startIndex = str.indexOf("where");
					System.out.println("startIndex: " + startIndex);
					str.replace(startIndex + "where".length(), node.getEnd(), (String) param.get(signStr));
					
				}else {
					str.replace(lastNode.getEnd(), node.getEnd(), "");
				}
			}
		}
		
		System.out.println(str.toString());
	}
}
