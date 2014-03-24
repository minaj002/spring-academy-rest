package com.academy.core.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentBean {

	private String id;
	
	private BigDecimal amount;
	
	private Date paymentDate;
	
	private Date paidUntill;
	
	private String memberId;

	public String getId() {
		return id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public Date getPaidUntill() {
		return paidUntill;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setPaidUntill(Date paidUntill) {
		this.paidUntill = paidUntill;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
}
