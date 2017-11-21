package com.tai.test;

/**
 * 存储sql标记的链表
 * @author Evy
 *
 */
public class SignLink {
	private SignNode head;
	
	public SignLink() {
		setHead(null);
	}
	
	/**
	 * 插入操作
	 * @param sign
	 */
	public void insert(String sign) {
		SignNode node = new SignNode(sign);
		
		if (getHead() == null) {
			setHead(node);
		}else {
			node.setNext(getHead());
			setHead(node);
		}
	}
	
	public void insert(SignNode node) {
		if (getHead() == null) {
			setHead(node);
		}else {
			node.setNext(getHead());
			setHead(node);
		}
	}
	
	/**
	 * 删除表头
	 */
	public SignNode deleteFirst() {
		SignNode returnNode = getHead();
		
		if (returnNode == null) {
			return returnNode;
		}
		
		if (returnNode.getNext() == null) {
			setHead(null);
		}else {
			setHead(returnNode.getNext());
		}
		
		return returnNode;
	}
	
	public void display() {
		SignNode cur = getHead();
		while(cur != null) {
			System.out.println(cur.toString());
			cur = cur.getNext();
		}
	}
	
	/**
	 * 判表空
	 * @return
	 */
	public boolean isEmpty() {
		return head == null ? true : false;
	}

	public SignNode getHead() {
		return head;
	}

	public void setHead(SignNode head) {
		this.head = head;
	}
	
}
