package com.tai.util;

/**
 * 记录sql语句中括号位置的链表节点
 * @author Evy
 *
 */
public class BracketsNode {
	/**
	 * 删除标志
	 * 为true则表示删除
	 */
	private boolean isDel;
	private int start;
	private int end;
	private String bracket;
	private BracketsNode next;
	/**
	 * 对应括号节点信息
	 */
	private BracketsNode borther;
	
	public BracketsNode() {
		setDel(false);
		setStart(-1);
		setEnd(-1);
		setBorther(null);
		setBracket("");
		setNext(null);
	}

	public BracketsNode(int start, int end, String bracket) {
		super();
		this.start = start;
		this.end = end;
		this.bracket = bracket;
	}

	@Override
	public String toString() {
		return "BracketsNode [isDel=" + isDel + ", start=" + start + ", end=" + end + ", bracket=" + bracket + "]";
	}

	public String getBracket() {
		return bracket;
	}

	public void setBracket(String bracket) {
		this.bracket = bracket;
	}

	public BracketsNode getNext() {
		return next;
	}

	public void setNext(BracketsNode next) {
		this.next = next;
	}

	public boolean isDel() {
		return isDel;
	}

	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public BracketsNode getBorther() {
		return borther;
	}

	public void setBorther(BracketsNode borther) {
		this.borther = borther;
	}
	
	
}
