package com.tai.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlUtil {

	/**
	 * 用参数替换sql的特殊标记，如果标记对应的参数存在，则直接替换，如果不存在，要把对应的一段条件去掉，
	 * 最后得出一个能用于查询的sql语句，返回能用于查询的sql语句。
	 * 
	 * @param labelSql
	 *            带有标记的sql语句
	 * 
	 * @param param
	 *            参数集合，如aa= "111" , bb= "222", cc= "333",dd = "444"
	 * 
	 * @return 返回sql语句
	 */
	public static String getRunnableSql(String labelSql, Map<String, Object> param) {
		StringBuilder sBuilder = new StringBuilder(5);

		// 从SQL语句中'where'开头构建可执行的SQL语句
		String buildBrfore = "where";
		int subIndex = labelSql.indexOf(buildBrfore);
		sBuilder.append(labelSql.substring(subIndex));

		buildSql(sBuilder, param);
		sBuilder.insert(0, labelSql.substring(0, subIndex));

		return sBuilder.toString();
	}

	/**
	 * 思路:将sql标记添加到链表，当不存在标记对应的参数时，将前一个标记为止的语句消除
	 * 
	 * @param str
	 * @param param
	 */
	private static void buildSql(StringBuilder str, Map<String, Object> param) {
		// 辅助标志,为0代表所有的标记都不存在对应的参数
		int paramNum = 0;
		
		//存储sql语句中括号的链表
		BracketsLink bLink = new BracketsLink();
		bLink = getBracketsLink(str);

		// 用正则表达式获取标记
		String signRegex = ":{1}[a-zA-Z]*|[a-zA-z0-9]+[\\s]*={1}[\\s]*[a-zA-z0-9]+"
				+ "|[0-9]+|[']{1}[a-zA-z0-9]*[']{1}";

		Pattern pattern = Pattern.compile(signRegex);
		Matcher matcher = pattern.matcher(str.toString());

		// 存储标记的链表
		SignLink link = new SignLink();

		while (matcher.find()) {
			String sign = matcher.group();
			int start = matcher.start();
			int end = matcher.end();
			link.insert(new SignNode(sign, start, end));
		}

		// 构建sql语句
		while (!link.isEmpty()) {
			SignNode node = link.deleteFirst();
			String signStr = node.getSign();
			if (signStr.matches("^:{1}[a-zA-Z]*$")) {
				signStr = node.getSign().substring(1);
				if (param.containsKey(signStr)) {
					// 存在标记,将标记替换为对应参数
					str.replace(node.getStart(), node.getEnd(), (String) param.get(signStr));
					paramNum++;
				} else {
					// 不存在标记，消除条件
					SignNode lastNode = link.getHead();

					if ((lastNode == null) && (paramNum == 0)) {
						// paramNum为0则将'where'语句去除
						int startIndex = str.indexOf("where");
						str.replace(startIndex, node.getEnd(), "");

					} else if ((lastNode == null) && (paramNum > 0)) {
						int startIndex = str.indexOf("where");
						str.replace(startIndex + "where".length(), node.getEnd(), "");

					} else {
						String rightBrackets = ")";
						int rbIndex = str.indexOf(rightBrackets, lastNode.getEnd()) + 1;
						int index = lastNode.getEnd();
						if (rbIndex > 0 && rbIndex <= node.getEnd()) {
							index = rbIndex;
						}
						delBracket(str, node, bLink);
						str.replace(index, node.getEnd(), "");
					}
				}
			}
		}

		// 清除多余括号
		removeExcessBrackets(str);
		// 清除多余条件
		removeExcessCondition(str);
	}

	/**
	 * 消除多余条件
	 */
	private static void removeExcessCondition(StringBuilder sBuilder) {
		// 用正则表达式获取标记
		String signRegex = "[a-zA-z0-9]+[\\s]*={1}[\\s]*[a-zA-z0-9]+" + "|[0-9]+|[']{1}[a-zA-z0-9]*[']{1}";

		Pattern pattern = Pattern.compile(signRegex);
		Matcher matcher = pattern.matcher(sBuilder.toString());

		// 存储标记的链表
		SignLink link = new SignLink();

		while (matcher.find()) {
			String sign = matcher.group();
			int start = matcher.start();
			int end = matcher.end();
			link.insert(new SignNode(sign, start, end));
		}

		SignNode lastNode = null;
		while (link.getHead() != null) {
			lastNode = link.deleteFirst();
		}

		// 存在表达式
		if (lastNode != null) {
			String tempStr = sBuilder.substring(0, lastNode.getStart());
			String whereStr = "where";
			// 如果第一个表达式的条件是where 结束方法
			boolean hasWhere = tempStr.indexOf(whereStr) > 0;
			if (hasWhere) {
				return;
			}
			// 如果第一个表达式的条件不是where 而是其他,则消除
			if (tempStr.matches("^[\\s]*[\\S]+[\\s]*$") && !hasWhere) {
				sBuilder.replace(0, lastNode.getStart(), whereStr + " ");
			}
		}

	}

	/**
	 * 清除多余括号
	 * 
	 * @param str
	 */
	private static void removeExcessBrackets(StringBuilder str) {
		SignLink link = new SignLink();

		int leftBrackets = 0;
		int rightBrackets = 0;

		// 用正则表达式获取左右括号
		String regex = "[(|)]";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str.toString());

		while (matcher.find()) {
			String sign = matcher.group();
			int start = matcher.start();
			int end = matcher.end();
			link.insert(new SignNode(sign, start, end));
		}

		// 统计左右括号数量
		char[] chars = str.toString().toCharArray();

		for (int i = 0; i < chars.length; i++) {
			switch (chars[i]) {
			case '(':
				leftBrackets++;
				break;
			case ')':
				rightBrackets++;
			default:
				break;
			}
		}

		if (leftBrackets != rightBrackets) {// 左右括号数量不等
			// true : 消除多余的前括号 false : 消除多余右括号
			boolean isLeft = leftBrackets > rightBrackets ? true : false;
			// 消除括号数量
			int index = 0;
			if (isLeft) {
				// 存在多余左括号
				index = leftBrackets - rightBrackets;
			} else {
				// 存在多余右括号
				index = rightBrackets - leftBrackets;
			}

			while (index > 0) {
				if (isLeft) {
					String brackets = "(";
					SignNode node = link.deleteFirst();
					if (node.getSign().equals(brackets)) {
						str.replace(node.getStart(), node.getEnd(), "");
						index--;
					}
				} else {
					String brackets = ")";
					SignNode node = link.deleteFirst();
					if (node.getSign().equals(brackets)) {
						str.replace(node.getStart(), node.getEnd(), "");
						index--;
					}
				}
			}
		}
	}
	
	/**
	 * 获取存储sql语句中括号关系的链表
	 * @param sBuilder
	 * @return
	 */
	public static BracketsLink getBracketsLink(StringBuilder sBuilder) {
		BracketsLink bracketsLink = new BracketsLink();
		
		// 用正则表达式获取左右括号
		String regex = "[(|)]";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(sBuilder.toString());
		
		BracketsNode previous = null;
		String leftBrackets = "(";
		String rightBrackets = ")";
		
		while (matcher.find()) {
			String bracket = matcher.group();
			int start = matcher.start();
			int end = matcher.end();
			bracketsLink.insert(new BracketsNode(start, end, bracket));
			if (bracket.equals(leftBrackets)) {//是左括号"(",记录
				previous = bracketsLink.getHead();
			}else {
				//寻找对应括号
				if (previous.getBorther() != null) {
					while(previous.getBorther() != null) {
						previous = previous.getNext();
					}
				}
				previous.setBorther(bracketsLink.getHead());
				bracketsLink.getHead().setBorther(previous);
			}
		}
		
		return bracketsLink;
	}
	
	/**
	 * 将移除条件节点后面括号标记为删除
	 * @param sBuilder
	 * @param delNode
	 * @param bLink
	 */
	private static void delBracket(StringBuilder sBuilder, SignNode delNode,
			BracketsLink bLink) {
		String lastStr = "";
		SignNode lastDelNode = delNode.getPrevious();
		if (sBuilder.length() == delNode.getEnd()) {
			return;
		}
		if (lastDelNode == null) {//删除结点为末位
			lastStr = sBuilder.substring(delNode.getEnd(), sBuilder.length());
		}else {
			if (lastDelNode.getStart() > sBuilder.length()) {
				lastStr = sBuilder.substring(delNode.getEnd(), sBuilder.length());
			}else {
				lastStr = sBuilder.substring(delNode.getEnd(), lastDelNode.getStart());
			}
		}
		
		int rbIndex = lastStr.indexOf(")");
		
		if (rbIndex > -1) {//后面存在括号,将对应括号标记为删除
			BracketsNode bNode = bLink.getHead();
			rbIndex += delNode.getEnd();
			if (bNode != null) {
				while((bNode != null) && (bNode.getStart() != rbIndex)) {
					bNode = bNode.getNext();
				}
				
				//标记为删除
				if (bNode == null) {
					return;
				}
				
				bNode.setDel(true);
				bNode.getBorther().setDel(true);
			}
		}else {
			return;
		}
		
//		delIsDelBracket(sBuilder, bLink);
	}
	
	/**
	 * 删除标记为true的括号
	 * @param bLink
	 */
	private static void delIsDelBracket(StringBuilder sBuilder, BracketsLink bLink) {
		BracketsNode cur = bLink.getHead();
		while (cur != null) {
			if (cur.isDel()) {//为删除状态
				if (cur.getEnd() < sBuilder.length()) {
					sBuilder.replace(cur.getStart(), cur.getEnd(), "");
				}
				cur.setDel(false);
				BracketsNode borther = cur.getBorther();
				if ((borther != null) && (borther.isDel())) {//对应节点为删除状态
					if (borther.getEnd() < sBuilder.length()) {
						sBuilder.replace(borther.getStart(), borther.getEnd(), "");
					}
					borther.setDel(false);
				}
			}
		}
	}
}
