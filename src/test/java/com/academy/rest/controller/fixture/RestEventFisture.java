package com.academy.rest.controller.fixture;



import com.academy.rest.api.Member;
import com.google.gson.Gson;

public class RestEventFisture {

	public static String newMemberJSON(){
		
		Member member = new Member();
		member.setFirstName("firstName");
		member.setLastName("lastName");
		
		Gson gson = new Gson();
		
		gson.toJson(member);
		
		return gson.toJson(member);
	}
	
}
