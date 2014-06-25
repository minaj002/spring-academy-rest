package com.academy.rest.controller.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.academy.core.command.AddMemberCommand;
import com.academy.core.command.DeleteMemberCommand;
import com.academy.core.command.EditMemberCommand;
import com.academy.core.command.result.AddMemberResult;
import com.academy.core.command.result.DeleteMemberResult;
import com.academy.core.command.result.EditMemberResult;
import com.academy.core.command.service.CommandService;
import com.academy.core.dto.MemberBean;
import com.academy.rest.api.Member;
import com.academy.rest.function.MemberToMemberBeanFunction;
import com.google.common.base.Function;

@Controller
@RequestMapping("/members")
public class MembersCommandController {

	private static Logger LOG = LoggerFactory
			.getLogger(MembersCommandController.class);

	@Autowired
	CommandService commandService;

	private static Function<Member, MemberBean> MEMBER_TO_MEMBER_BEAN_FUNCTION = new MemberToMemberBeanFunction();

	@RequestMapping(method = RequestMethod.POST, value = "/new")
	@ResponseBody
	public ResponseEntity<String> addNewMember(@RequestBody Member member) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		String userName = context.getAuthentication().getName();

		AddMemberCommand command = new AddMemberCommand(MEMBER_TO_MEMBER_BEAN_FUNCTION.apply(member));
		command.setUserName(userName); 
		
		AddMemberResult result = commandService.execute(command);

		if (!StringUtils.isEmpty(result.getMemberId())) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/edit")
	@ResponseBody
	public ResponseEntity<String> editMember(@RequestBody Member member) {
	    
	    SecurityContext context = SecurityContextHolder.getContext();
	    
	    String userName = context.getAuthentication().getName();
	    
	    EditMemberCommand command = new EditMemberCommand(MEMBER_TO_MEMBER_BEAN_FUNCTION.apply(member));
	    command.setUserName(userName); 
	    
	    EditMemberResult result = commandService.execute(command);
	    
	    if (!StringUtils.isEmpty(result.getMemberId())) {
		return new ResponseEntity<>(HttpStatus.OK);
	    } else {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/delete")
	@ResponseBody
	public ResponseEntity<String> deleteMember(@RequestBody Member member) {
		
		DeleteMemberCommand command = new DeleteMemberCommand(MEMBER_TO_MEMBER_BEAN_FUNCTION.apply(member));
		
		DeleteMemberResult result = commandService.execute(command);
		
		if (!StringUtils.isEmpty(result.getMemberId())) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}


	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String handle(Exception exception) {
		LOG.error("Exception while processing incoming request.", exception);
		return "Unexpected error!";
	}

}
