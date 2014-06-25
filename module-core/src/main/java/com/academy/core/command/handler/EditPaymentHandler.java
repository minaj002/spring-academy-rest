package com.academy.core.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.academy.core.command.EditPaymentCommand;
import com.academy.core.command.result.EditPaymentResult;
import com.academy.core.domain.Payment;
import com.academy.core.dto.PaymentBean;
import com.academy.core.function.PaymentBeanToPaymentFunction;
import com.academy.repository.PaymentRepository;
import com.google.common.base.Function;

@Component
public class EditPaymentHandler implements CommandHandler<EditPaymentCommand, EditPaymentResult> {

	@Autowired
	PaymentRepository paymentRepository;
	
	private static final Function<PaymentBean, Payment> PAYMENT_BEAN_TO_PAYMENT = new PaymentBeanToPaymentFunction();
	
	@Override
	public EditPaymentResult execute(EditPaymentCommand command) {

		Payment payment = PAYMENT_BEAN_TO_PAYMENT.apply(command.getPayment());
		payment = paymentRepository.save(payment);
		
		return new EditPaymentResult(payment.getId());
	}

	
}
