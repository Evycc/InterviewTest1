package com.tai.util;

/**
 * 存储sql语句中括号信息链表
 * @author Evy
 *
 */
public class BracketsLink {
	private BracketsNode head;
	
	public BracketsLink() {
		this.head = null;
	}
	
	public void insert(BracketsNode node) {
		if (getHead() == null) {
			setHead(node);
		}else {
			node.setNext(getHead());
			setHead(node);
		}
	}
	
	public void update(BracketsNode oldNode, BracketsNode newNode) {
	}
	
	/**
	 * 删除表头
	 */
	public BracketsNode deleteFirst() {
		BracketsNode returnNode = getHead();
		
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
		BracketsNode cur = getHead();
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

	public BracketsNode getHead() {
		return head;
	}

	public void setHead(BracketsNode head) {
		this.head = head;
	}
}
