package com.academy.core.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Lists;

@Document
public class ClassAttended {
	
	@Id
	private String id;
	
	private Date date;
	
	@DBRef
	private Academy academy; 
	
	@DBRef
	private List<Member> members = Lists.newArrayList();

	public String getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
	
	public void addMember(Member member){
		members.add(member);
	}

	public Academy getAcademy() {
		return academy;
	}

	public void setAcademy(Academy academy) {
		this.academy = academy;
	}
	
}
