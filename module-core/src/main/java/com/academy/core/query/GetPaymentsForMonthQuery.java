package com.academy.core.query;

import java.util.Date;

import com.academy.core.query.result.PaymentsForMonthResult;

public class GetPaymentsForMonthQuery implements Query<PaymentsForMonthResult> {

	private String userName;
	private Date paymentsForMonth;
	
	public GetPaymentsForMonthQuery(String userName, Date paymentsForMonth) {
		this.userName = userName;
		this.paymentsForMonth = paymentsForMonth;
	}

	public String getUserName() {
		return userName;
	}

	public Date getPaymentsForMonth() {
		return paymentsForMonth;
	} 
	
	

}
