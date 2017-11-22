package com.tai.util;

/**
 * 存储sql标记的链表结点
 * @author Evy
 *
 */
public class SignNode {
	/**
	 * 存储的标记
	 */
	private String sign;
	/**
	 * 标记在StringBuild中的索引开头
	 */
	private int start;
	/**
	 * 标记在StringBuild中的索引结尾
	 */
	private int end;
	private SignNode next;
	private SignNode previous;
	
	public SignNode() {
		setSign("");
		setStart(0);
		setEnd(0);
		next = null;
	}
	
	public SignNode(String sign) {
		setSign(sign);
		setStart(0);
		setEnd(0);
		next = null;
		previous = null;
	}
	
	public SignNode(String sign, int start, int end) {
		setSign(sign);
		setStart(start);
		setEnd(end);
		next = null;
		previous = null;
	}

	public SignNode getPrevious() {
		return previous;
	}

	public void setPrevious(SignNode previous) {
		this.previous = previous;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public SignNode getNext() {
		return next;
	}

	public void setNext(SignNode next) {
		this.next = next;
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

	@Override
	public String toString() {
		return "sign=" + sign;
	}
}
