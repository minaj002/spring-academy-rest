package com.academy.rest.controller.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.academy.core.command.AddPaymentCommand;
import com.academy.core.command.DeleteMemberCommand;
import com.academy.core.command.DeletePaymentCommand;
import com.academy.core.command.result.AddPaymentResult;
import com.academy.core.command.result.DeletePaymentResult;
import com.academy.core.command.service.CommandService;
import com.academy.core.dto.PaymentBean;
import com.academy.rest.api.Member;
import com.academy.rest.api.Payment;
import com.academy.rest.function.PaymentToPaymentBeanFunction;
import com.google.common.base.Function;

@Controller
@RequestMapping("/payments")
public class PaymentsCommandController {

    private static Logger LOG = LoggerFactory.getLogger(PaymentsCommandController.class);

    @Autowired
    CommandService commandService;

    private static Function<Payment, PaymentBean> PAYMENT_TO_PAYMENT_BEAN_FUNCTION = new PaymentToPaymentBeanFunction();

    @RequestMapping(method = RequestMethod.POST, value = "/new")
    @ResponseBody
    public ResponseEntity<String> addNewPayment(@RequestBody Payment payment) {

	SecurityContext context = SecurityContextHolder.getContext();

	String userName = context.getAuthentication().getName();

	AddPaymentCommand command = new AddPaymentCommand(PAYMENT_TO_PAYMENT_BEAN_FUNCTION.apply(payment));
	command.setUserName(userName);

	AddPaymentResult result = commandService.execute(command);

	if (!StringUtils.isEmpty(result.getPaymentId())) {
	    return new ResponseEntity<>(HttpStatus.CREATED);
	} else {
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    @ResponseBody
    public ResponseEntity<String> deletePayment(@RequestBody Payment payment) {

	DeletePaymentCommand command = new DeletePaymentCommand(PAYMENT_TO_PAYMENT_BEAN_FUNCTION.apply(payment));

	DeletePaymentResult result = commandService.execute(command);

	if (!StringUtils.isEmpty(result.getPaymentId())) {
	    return new ResponseEntity<>(HttpStatus.CREATED);
	} else {
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handle(Exception exception) {
	LOG.error("Exception while processing incoming request.", exception);
	return "Unexpected error!";
    }

}
