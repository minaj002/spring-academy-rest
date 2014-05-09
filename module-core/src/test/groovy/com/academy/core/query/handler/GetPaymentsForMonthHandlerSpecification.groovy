package com.academy.core.query.handler

import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

import com.academy.config.SecurityConfig;
import com.academy.core.domain.Academy
import com.academy.core.domain.AcademyUser
import com.academy.core.domain.Payment
import com.academy.core.query.GetAcademyMembersQuery
import com.academy.core.query.GetPaymentsForMonthQuery
import com.academy.core.query.handler.GetAcademyMembersHandler;
import com.academy.core.query.result.GetAcademyMembersResult;
import com.academy.core.query.result.GetPaymentsForMonthResult
import com.academy.repository.AcademyUserRepository;
import com.academy.repository.PaymentRepository;

import org.joda.time.DateTime;
import spock.lang.Specification

@ContextConfiguration(classes = [SecurityConfig])
class GetPaymentsForMonthHandlerSpecification extends Specification {

	def paymentRepository = Mock(PaymentRepository)
	def academyUserRepository = Mock(AcademyUserRepository)
	
	def "Testing that GetPaymentsForMonthHandler returns result with all members"(){

		given: "List of Members in database"
		
			List mockedList=[]
			Payment payment
			
			(0L..7L).each{ item ->
				payment = new Payment()
				payment.academyName = "academyName"
				payment.amount = 43
				payment.memberId= "$item"
				mockedList.add(payment)
				payment=null
			}
			
			assert mockedList.size()==8
		
			paymentRepository.findByAcademyNameAndDateBetween("owner", DateTime.parse("2012-01-01").toDate(), DateTime.parse("2012-02-01").toDate())>> mockedList
			
			AcademyUser user= new AcademyUser()
			Academy academy = new Academy()
			academy.setName("owner")
			user.setAcademy(academy);
			
			academyUserRepository.findByName("owner")>>user
		
		when: "Calling handlers execute method"
		
			GetPaymentsForMonthHandler handler = new GetPaymentsForMonthHandler()
			handler.paymentRepository=paymentRepository
			handler.academyUserRepository=academyUserRepository
			GetPaymentsForMonthQuery query = new GetPaymentsForMonthQuery("owner", DateTime.parse("2012-01-31").toDate());
			GetPaymentsForMonthResult result = handler.execute(query);
		
		then: "return Result with List af all members"
			
			result.getPayments().size()==8
			result.getPayments().get(0).getAmount() == 43
			result.getPayments()[2].getAmount()==43
			result.getPayments()[5].getAmount()==43
			result.getPayments()[5].getMemberId()=="5"
	}
}
