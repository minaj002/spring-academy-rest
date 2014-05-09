package com.academy.core.function;

import com.academy.core.domain.ClassAttended;
import com.academy.core.dto.ClassAttendedBean;
import com.google.common.base.Function;

public class ClassAttendedBeanToClassAttendedFunction implements Function<ClassAttendedBean, ClassAttended> {

    @Override
    public ClassAttended apply(ClassAttendedBean from) {

	ClassAttended bean = new ClassAttended();
	bean.setDate(from.getDate());
	bean.setId(from.getId());

	return bean;
    }

}
