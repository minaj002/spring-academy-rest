package com.academy.rest.function;

import static com.academy.utils.DateTimeUtils.dateToString;

import com.academy.core.dto.PaymentBean;
import com.academy.rest.api.Payment;
import com.google.common.base.Function;

public class PaymentBeanToPaymentFunction implements
		Function<PaymentBean, Payment> {

	@Override
	public Payment apply(PaymentBean from) {
		Payment payment =new Payment();
		payment.setAmount(from.getAmount());
		payment.setId(from.getId());
		payment.setMemberId(from.getMemberId());
		payment.setPaidUntill(dateToString(from.getPaidUntill()));
		payment.setPaymentDate(dateToString(from.getPaymentDate()));
		return payment;
	}

}
