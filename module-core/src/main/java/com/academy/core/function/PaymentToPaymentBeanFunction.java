package com.academy.core.function;

import com.academy.core.domain.Payment;
import com.academy.core.dto.PaymentBean;
import com.google.common.base.Function;

public class PaymentToPaymentBeanFunction implements Function<Payment, PaymentBean> {

	@Override
	public PaymentBean apply(Payment from) {
		PaymentBean payment = new PaymentBean();
		payment.setAmount(from.getAmount());
		payment.setId(from.getId());
		payment.setPaymentDate(from.getDate());
		payment.setPaidUntill(from.getPaidUntill());
		payment.setMemberId(from.getMemberId());
		return payment;
	}

}
