package com.simple.madang.entity;

import java.util.Date;

public class Orders {
	int orderid;
	int custid;
	int bookid;
	int saleprice;
	Date orderDate;

	public Orders(int orderid, int custid, int bookid, int saleprice, Date orderDate) {
		this.orderid = orderid;
		this.custid = custid;
		this.bookid = bookid;
		this.saleprice = saleprice;
		this.orderDate = orderDate;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getCustid() {
		return custid;
	}

	public void setCustid(int custid) {
		this.custid = custid;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public int getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(int saleprice) {
		this.saleprice = saleprice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		return "Orders [orderid=" + orderid + ", custid=" + custid + ", bookid=" + bookid + ", saleprice=" + saleprice
				+ ", orderDate=" + orderDate + "]";
	}

}
