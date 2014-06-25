package com.academy.core.query.handler;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.academy.core.domain.Payment;
import com.academy.core.dto.PaymentBean;
import com.academy.core.function.PaymentToPaymentBeanFunction;
import com.academy.core.query.PaymentsForPeriodForMemberQuery;
import com.academy.core.query.result.PaymentsForMonthResult;
import com.academy.repository.PaymentRepository;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Component
public class PaymentsForPeriodForMemberHandler implements QueryHandler<PaymentsForPeriodForMemberQuery, PaymentsForMonthResult> {

	@Autowired
	PaymentRepository paymentRepository;
	
	Function<Payment, PaymentBean> PAYMENT_TO_PAYMENT_BEAN_FUNCTION = new PaymentToPaymentBeanFunction();
	
	@Override
	public PaymentsForMonthResult execute(PaymentsForPeriodForMemberQuery query) {

		Date date = query.getPaymentsForMonth();
		
		DateTime endDate = new DateTime(date);
		DateTime startDate = endDate.minusMonths(query.getPeriod());
		
		List<Payment> payments = paymentRepository.findByMemberIdAndDateBetween(query.getMember(), startDate.toDate(), endDate.toDate());
		
		List<PaymentBean> paymentBeans = Lists.newArrayList(Collections2.transform(payments, PAYMENT_TO_PAYMENT_BEAN_FUNCTION));
		
		PaymentsForMonthResult result = new PaymentsForMonthResult();
		result.setPayments(paymentBeans);
		return result;
	}

}
