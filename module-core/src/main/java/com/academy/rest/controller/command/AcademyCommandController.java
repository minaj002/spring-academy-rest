package com.academy.rest.controller.command;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.academy.core.command.AddAcademyCommand;
import com.academy.core.command.result.AddAcademyResult;
import com.academy.core.command.service.CommandService;
import com.academy.rest.api.Academy;
import com.google.common.base.Function;

@Controller
@RequestMapping("/academy/new")
public class AcademyCommandController {

	private static Logger LOG = LoggerFactory
			.getLogger(AcademyCommandController.class);

	@Autowired
	CommandService commandService;

	private static Function<Academy, AddAcademyCommand> ACADEMY_TO_ADD_ACADEMY_COMMAND_FUNCTION = new AcademyToAddAcademyFunction();
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addNewMember(@RequestBody Academy academy) {

		AddAcademyResult result = commandService.execute(ACADEMY_TO_ADD_ACADEMY_COMMAND_FUNCTION.apply(academy));

		if (!StringUtils.isEmpty(result.getId())) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private static class AcademyToAddAcademyFunction implements
			Function<Academy, AddAcademyCommand> {

		@Override
		public AddAcademyCommand apply(Academy academy) {
			AddAcademyCommand command = new AddAcademyCommand(
					academy.getName(), academy.getEmail(), academy.getPassword());
			return command;
		}

	}

	@ExceptionHandler
	@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String handle(Exception exception){
		LOG.error("Exception while processing incoming request.", exception);
		return "Unexpected error!";
	}
	
}
