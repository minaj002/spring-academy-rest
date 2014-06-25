package com.academy.core.query;

import java.util.Date;

import com.academy.core.query.result.PaymentsForMonthResult;

public class PaymentsForPeriodForMemberQuery implements Query<PaymentsForMonthResult> {

    private String userName;
    private Date untilMonth;
    private Integer period;
    private String member;

    private PaymentsForPeriodForMemberQuery(String userName) {
	this.userName = userName;
    }

    public static PaymentsForPeriodForMemberQuery create(String userName) {
	return new PaymentsForPeriodForMemberQuery(userName);
    }

    public PaymentsForPeriodForMemberQuery untilMonth(Date untilMonth) {
	this.untilMonth = untilMonth;
	return this;
    }

    public PaymentsForPeriodForMemberQuery forPeriod(Integer period) {
	this.period = period;
	return this;
    }

    public PaymentsForPeriodForMemberQuery forMember(String member) {
	this.member = member;
	return this;
    }

    public Integer getPeriod() {
	return period;
    }

    public String getMember() {
	return member;
    }

    public String getUserName() {
	return userName;
    }

    public Date getPaymentsForMonth() {
	return untilMonth;
    }

}
