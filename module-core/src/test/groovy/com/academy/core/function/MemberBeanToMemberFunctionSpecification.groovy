package com.academy.core.function

import org.joda.time.DateTime;
import com.academy.core.domain.Address
import com.academy.core.domain.Member
import com.academy.core.dto.MemberBean
import spock.lang.Specification

class MemberBeanToMemberFunctionSpecification extends Specification {

	
	def "Testing that MemberBeanToMemberFunction correctly transform given MemberBean to Member"(){
	
		given: "MemberBean"
		
			MemberBean bean = new MemberBean()
			bean.setFirstName("firstName")
			bean.setLastName("lastName")
			bean.setDateOfBirth(DateTime.parse("2012-12-12").toDate());
			bean.setStreet("street");
			bean.setCity("city");
			bean.setEmail("email");
			bean.setPhone("phone");
			
		when: "MemberBeanToMemberFunction apply"
		
			MemberBeanToMemberFunction memberBeanToMemberFunction = new MemberBeanToMemberFunction()
			
			Member member = memberBeanToMemberFunction.apply(bean)
			Address address= member.address
		
		then: "Should transform all fields of MemberBean to Member"
	
			member.firstName=="firstName"
			member.lastName=="lastName"
			member.dateOfBirth==DateTime.parse("2012-12-12").toDate()
			member.email=="email"
			member.phone=="phone"
			
			address.city=="city"
			address.street=="street"
		
	}
	
}
