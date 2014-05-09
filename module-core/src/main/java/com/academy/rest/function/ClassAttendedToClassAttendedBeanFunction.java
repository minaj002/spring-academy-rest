package com.academy.rest.function;

import org.joda.time.DateTime;

import com.academy.core.dto.ClassAttendedBean;
import com.academy.rest.api.ClassAttended;
import com.google.common.base.Function;

public class ClassAttendedToClassAttendedBeanFunction implements Function<ClassAttended, ClassAttendedBean> {


    @Override
    public ClassAttendedBean apply(ClassAttended from) {

	ClassAttendedBean classAttended = new ClassAttendedBean();
	classAttended.setDate(DateTime.parse(from.getDate()).toDate());

	return classAttended;
    }

}
