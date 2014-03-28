package academy

import com.academy.core.dto.MemberBean;
import com.academy.core.query.GetAcademyMembersQuery

import spock.lang.Specification

import com.academy.rest.controller.AcademyQueryController;
import com.academy.core.query.result.GetAcademyMembersResult;
import com.academy.core.query.service.QueryService;

class AcademyQueryControllerSpecification extends Specification {

	
	def queryService = Mock(QueryService)
	
	def "Testing that controller returns correct number of members" (){
		
		given: "List of Members"
		List mockedList=[]
		
					MemberBean member
		
					def log=0;
					(0L..9L).each { item ->
								member=new MemberBean()
								member.setFirstName("firtsName$item")
								member.setLastName("lastName$item")
								mockedList.add(member)
								member=null;
					}
		
					assert mockedList.size()==10
					
					GetAcademyMembersResult members = new GetAcademyMembersResult();
					members.setMembers(mockedList);
					
		queryService.execute(_)>>members
		
		when: "Calling controllers getAllMembers() method"
		AcademyQueryController controller = new AcademyQueryController();
		
		controller.queryService=queryService
		List result = controller.getAllOrders();
		
		then: "return list with all members"
				result.size()==10
				result.get(0).getFirstName()=="firtsName0"
				result.get(2).getFirstName()=="firtsName2"
				result.get(5).getFirstName()=="firtsName5"
				result.get(5).getLastName()=="lastName5"
				
		
		
	}
	
}
