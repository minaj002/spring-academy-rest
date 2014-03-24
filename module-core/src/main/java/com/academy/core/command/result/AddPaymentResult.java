package com.academy.core.command.result;

public class AddPaymentResult implements CommandResult {

	private String paymentId;

	public AddPaymentResult(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentId() {
		return paymentId;
	} 
	
}
