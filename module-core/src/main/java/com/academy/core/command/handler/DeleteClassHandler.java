package com.academy.core.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.academy.core.command.DeleteClassCommand;
import com.academy.core.command.result.DeleteClassResult;
import com.academy.core.domain.ClassAttended;
import com.academy.core.dto.ClassAttendedBean;
import com.academy.core.function.ClassAttendedBeanToClassAttendedFunction;
import com.academy.repository.ClassAttendedRepository;
import com.google.common.base.Function;

@Component
public class DeleteClassHandler implements CommandHandler<DeleteClassCommand, DeleteClassResult> {

    @Autowired
    ClassAttendedRepository classAttendedRepository;

    private static final Function<ClassAttendedBean, ClassAttended> CLASS_ATTENDED_BEAN_TO_CLASS_ATTENDED = new ClassAttendedBeanToClassAttendedFunction();

    @Override
    public DeleteClassResult execute(DeleteClassCommand command) {

	ClassAttended classAttended = CLASS_ATTENDED_BEAN_TO_CLASS_ATTENDED.apply(command.getClassAttended());
	classAttendedRepository.delete(classAttended);
	return new DeleteClassResult(classAttended.getId());
    }

}
