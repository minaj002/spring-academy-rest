package com.academy.core.command;

import com.academy.core.command.result.AddPaymentResult;
import com.academy.core.dto.PaymentBean;

public class AddPaymentCommand implements Command<AddPaymentResult> {

	private PaymentBean payment;

	private String userName;
	
	public AddPaymentCommand(PaymentBean payment) {
		this.payment = payment;
	}

	public PaymentBean getPayment() {
		return payment;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
