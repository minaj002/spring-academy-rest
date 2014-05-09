package com.academy.core.query.handler;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.academy.core.domain.AcademyUser;
import com.academy.core.domain.Payment;
import com.academy.core.dto.PaymentBean;
import com.academy.core.function.PaymentToPaymentBeanFunction;
import com.academy.core.query.GetPaymentsForMonthQuery;
import com.academy.core.query.result.GetPaymentsForMonthResult;
import com.academy.repository.AcademyUserRepository;
import com.academy.repository.PaymentRepository;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Component
public class GetPaymentsForMonthHandler implements	QueryHandler<GetPaymentsForMonthQuery, GetPaymentsForMonthResult> {

	@Autowired 
	AcademyUserRepository academyUserRepository;

	@Autowired
	PaymentRepository paymentRepository;
	
	Function<Payment, PaymentBean> PAYMENT_TO_PAYMENT_BEAN_FUNCTION = new PaymentToPaymentBeanFunction();
	
	@Override
	public GetPaymentsForMonthResult execute(GetPaymentsForMonthQuery query) {

		AcademyUser user = academyUserRepository.findByName(query.getUserName());
		
		Date date = query.getPaymentsForMonth();
		
		DateTime dateTime = new DateTime(date);
		DateTime startDate = dateTime.withDayOfMonth(1);
		DateTime endDate = startDate.plusMonths(1);
		
		List<Payment> payments = paymentRepository.findByAcademyNameAndDateBetween(user.getAcademy().getName(), startDate.toDate(), endDate.toDate());
		
		List<PaymentBean> paymentBeans = Lists.newArrayList(Collections2.transform(payments, PAYMENT_TO_PAYMENT_BEAN_FUNCTION));
		
		GetPaymentsForMonthResult result = new GetPaymentsForMonthResult();
		result.setPayments(paymentBeans);
		return result;
	}

}
