package com.academy.core.command;

import com.academy.core.command.result.EditPaymentResult;
import com.academy.core.dto.PaymentBean;

public class EditPaymentCommand implements Command<EditPaymentResult> {

	private PaymentBean payment;

	private String userName;
	
	public EditPaymentCommand(PaymentBean payment) {
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
