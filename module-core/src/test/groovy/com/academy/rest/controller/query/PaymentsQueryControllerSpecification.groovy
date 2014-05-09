package com.academy.rest.controller.query

import javax.servlet.http.HttpServletRequest;

import com.academy.config.CoreConfig;
import com.academy.config.MVCConfig;
import com.academy.config.SecurityConfig;
import com.academy.core.dto.MemberBean;
import com.academy.core.dto.PaymentBean
import com.academy.core.query.GetAcademyMembersQuery

import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

import spock.lang.*

import com.academy.rest.controller.query.MembersQueryController;
import com.academy.core.query.result.GetAcademyMembersResult;
import com.academy.core.query.result.GetPaymentsForMonthResult;
import com.academy.core.query.service.QueryService;

import org.joda.time.DateTime;

@ContextConfiguration(classes = [SecurityConfig])
class PaymentsQueryControllerSpecification extends Specification {

	def queryService = Mock(QueryService)
	SecurityContextHolder holder
	
	def setup(){
	
		holder = new SecurityContextHolder()
		Authentication auth = new UsernamePasswordAuthenticationToken("user","pass",null);
		holder.getContext().setAuthentication(auth);
		
	}
	
	def "Testing that controller returns correct number of payments" (){
		
		given: "List of Payments"
			List mockedList=[]
		
					PaymentBean payment
		
					def log=0;
					(0L..9L).each { item ->
								payment=new PaymentBean()
								payment.amount = BigDecimal.valueOf(43)
								payment.memberId = "aaaaa$item"
								payment.paidUntill = DateTime.parse("2014-02-26").toDate()
								payment.paymentDate = DateTime.parse("2014-01-26").toDate()
								mockedList.add(payment)
								payment=null;
					}
		
					assert mockedList.size()==10
					
					GetPaymentsForMonthResult payments = new GetPaymentsForMonthResult();
					payments.setPayments(mockedList);
					
					queryService.execute(_)>>payments
		
		
		
		when: "Calling controllers getAllMembers() method"
			PaymentsQueryController controller = new PaymentsQueryController();
			controller.getUserName()>>"name"
			controller.queryService=queryService
			List result = controller.getPaymentsForMonth("2014-02-12");
			
		then: "return list with all members"
				result.size()==10
				result.get(0).amount==43
				result.get(2).memberId=="aaaaa2"
				result.get(5).memberId=="aaaaa5"
		
	}
	
	
}
