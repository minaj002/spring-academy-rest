package com.academy.core.command;

import com.academy.core.command.result.DeleteClassResult;
import com.academy.core.dto.ClassAttendedBean;

public class DeleteClassCommand implements Command<DeleteClassResult>{

	private ClassAttendedBean classAttended;

	public DeleteClassCommand(ClassAttendedBean classAttendedBean) {
		this.classAttended = classAttendedBean;
	}

	public ClassAttendedBean getClassAttended() {
	    return classAttended;
	}

	
}
