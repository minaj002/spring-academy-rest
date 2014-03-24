package com.academy.rest.api;

import java.math.BigDecimal;

public class Payment {

	private String id;
	
	private BigDecimal amount;
	
	private String paymentDate;
	
	private String paidUntill;
	
	private String memberId;

	public String getId() {
		return id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public String getPaidUntill() {
		return paidUntill;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setPaidUntill(String paidUntill) {
		this.paidUntill = paidUntill;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	
	
}
