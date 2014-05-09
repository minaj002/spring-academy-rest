package com.academy.core.command.result;

public class DeletePaymentResult implements CommandResult {

	private String paymentId;

	public DeletePaymentResult(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentId() {
		return paymentId;
	}
	
}
