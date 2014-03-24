package com.academy.core.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;

public class Payment {

	@Indexed
	private String id;
	
	private BigDecimal amount;
	
	private Date date;
	
	private Date paidUntill;

	public String getId() {
		return id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Date getDate() {
		return date;
	}

	public Date getPaidUntill() {
		return paidUntill;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setPaidUntill(Date paidUntill) {
		this.paidUntill = paidUntill;
	}
	
}
