package com.academy.core.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.academy.core.command.DeletePaymentCommand;
import com.academy.core.command.result.DeletePaymentResult;
import com.academy.core.domain.Payment;
import com.academy.core.dto.PaymentBean;
import com.academy.core.function.PaymentBeanToPaymentFunction;
import com.academy.repository.PaymentRepository;
import com.google.common.base.Function;

@Component
public class DeletePaymentHandler implements CommandHandler<DeletePaymentCommand, DeletePaymentResult> {

    @Autowired
    PaymentRepository paymentRepository;

    private static final Function<PaymentBean,Payment> PAYMENT_BEAN_TO_PAYMENT = new PaymentBeanToPaymentFunction();

    @Override
    public DeletePaymentResult execute(DeletePaymentCommand command) {

	Payment payment = PAYMENT_BEAN_TO_PAYMENT.apply(command.getPayment());
	paymentRepository.delete(payment);

	return new DeletePaymentResult(payment.getId());
    }

}
