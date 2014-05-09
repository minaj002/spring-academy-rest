package com.academy.rest.controller.command;

import java.util.List;

import org.joda.time.DateTime;
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

import com.academy.core.command.AddClassCommand;
import com.academy.core.command.DeleteClassCommand;
import com.academy.core.command.DeleteMemberCommand;
import com.academy.core.command.result.AddClassResult;
import com.academy.core.command.result.DeleteClassResult;
import com.academy.core.command.result.DeleteMemberResult;
import com.academy.core.command.service.CommandService;
import com.academy.core.dto.ClassAttendedBean;
import com.academy.core.dto.MemberBean;
import com.academy.rest.api.ClassAttended;
import com.academy.rest.api.Member;
import com.academy.rest.function.ClassAttendedToClassAttendedBeanFunction;
import com.academy.rest.function.MemberToMemberBeanFunction;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("/classes")
public class ClassesCommandController {

	private static Logger LOG = LoggerFactory
			.getLogger(ClassesCommandController.class);

	@Autowired
	CommandService commandService;

	private static Function<Member, MemberBean> MEMBER_TO_MEMBER_BEAN_FUNCTION = new MemberToMemberBeanFunction();
	
	private static Function<ClassAttended, ClassAttendedBean> CLASS_ATTENDED_TO_CLASS_ATTENDED_BEAN_FUNCTION = new ClassAttendedToClassAttendedBeanFunction(); 

	@RequestMapping(method = RequestMethod.POST, value = "/new")
	@ResponseBody
	public ResponseEntity<String> addClass(@RequestBody ClassAttended classAttended) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		String userName = context.getAuthentication().getName();
		List<MemberBean> memberBeans = Lists.newArrayList(Collections2.transform(classAttended.getMembers(), MEMBER_TO_MEMBER_BEAN_FUNCTION));
		AddClassCommand command = new AddClassCommand(DateTime.parse(classAttended.getDate()).toDate(),memberBeans);
				
		command.setUserName(userName);
		
		AddClassResult result = commandService.execute(command);

		if (!StringUtils.isEmpty(result.getId())) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@RequestMapping(method = RequestMethod.POST, value = "/delete")
	@ResponseBody
	public ResponseEntity<String> deleteClass(@RequestBody ClassAttended classAttended) {
		
		DeleteClassCommand command = new DeleteClassCommand(CLASS_ATTENDED_TO_CLASS_ATTENDED_BEAN_FUNCTION.apply(classAttended));
		
		 DeleteClassResult result = commandService.execute(command);
		
		if (!StringUtils.isEmpty(result.getClassId())) {
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
