package com.academy.rest.controller.query;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.academy.core.dto.PaymentBean;
import com.academy.core.query.GetPaymentsForMonthQuery;
import com.academy.core.query.PaymentsForPeriodForMemberQuery;
import com.academy.core.query.result.PaymentsForMonthResult;
import com.academy.core.query.service.QueryService;
import com.academy.rest.api.Payment;
import com.academy.rest.function.PaymentBeanToPaymentFunction;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("/payments")
public class PaymentsQueryController {

    private static Logger LOG = LoggerFactory.getLogger(PaymentsQueryController.class);

    private static Function<PaymentBean, Payment> PAYMENT_BEAN_TO_PAYMENT_FUNCTION = new PaymentBeanToPaymentFunction();

    @Autowired
    QueryService queryService;

    @RequestMapping(method = RequestMethod.GET, value="/{date}")
    @ResponseBody
    public Collection<Payment> getPaymentsForMonth(@PathVariable String date) {

	String name = getUserName();

	LOG.debug("Getting payments for {}", name);

	DateTime month = DateTime.parse(date);

	GetPaymentsForMonthQuery query = new GetPaymentsForMonthQuery(name, month.toDate());
	PaymentsForMonthResult payments = queryService.execute(query);

	List<Payment> paymentsJson = Lists.newArrayList(Collections2.transform(payments.getPayments(), PAYMENT_BEAN_TO_PAYMENT_FUNCTION));

	return paymentsJson;
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/{date}/{period}/{id}")
    @ResponseBody
    public Collection<Payment> getPaymentsForMonthsForMember(@PathVariable String date, @PathVariable Integer period, @PathVariable String id) {
	
	String name = getUserName();
	
	LOG.debug("Getting payments for {}, id {}", name, id);
	
	DateTime month = DateTime.parse(date);
	
	PaymentsForPeriodForMemberQuery query = PaymentsForPeriodForMemberQuery.create(name).forMember(id).forPeriod(period).untilMonth(month.toDate());
	PaymentsForMonthResult payments = queryService.execute(query);
	
	List<Payment> paymentsJson = Lists.newArrayList(Collections2.transform(payments.getPayments(), PAYMENT_BEAN_TO_PAYMENT_FUNCTION));
	
	return paymentsJson;
    }

    private String getUserName() {
	return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
