package com.academy.core.command;

import com.academy.core.command.result.DeletePaymentResult;
import com.academy.core.dto.PaymentBean;

public class DeletePaymentCommand implements Command<DeletePaymentResult>{

	private PaymentBean payment;

	public DeletePaymentCommand(PaymentBean payment) {
		this.payment = payment;
	}

	public PaymentBean getPayment() {
		return payment;
	}
	
}
