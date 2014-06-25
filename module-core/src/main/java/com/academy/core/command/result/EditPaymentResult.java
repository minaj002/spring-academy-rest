package com.academy.core.command.result;

public class EditPaymentResult implements CommandResult {

	private String paymentId;

	public EditPaymentResult(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentId() {
		return paymentId;
	} 
	
}
