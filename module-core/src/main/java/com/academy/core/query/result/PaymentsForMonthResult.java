package com.academy.core.query.result;

import java.util.List;

import com.academy.core.dto.PaymentBean;
import com.google.common.collect.Lists;

public class PaymentsForMonthResult implements QueryResult {

	private List<PaymentBean> payments = Lists.newArrayList();

	public List<PaymentBean> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentBean> payments) {
		this.payments = payments;
	}
	
	public void addPayment(PaymentBean payment){
		payments.add(payment);
	}
	
}
