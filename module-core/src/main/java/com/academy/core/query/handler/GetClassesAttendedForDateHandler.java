package com.academy.core.query.handler;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.academy.core.domain.AcademyUser;
import com.academy.core.domain.ClassAttended;
import com.academy.core.dto.ClassAttendedBean;
import com.academy.core.function.ClassAttendedToClassAttendedBeanFunction;
import com.academy.core.query.GetClassesForDateQuery;
import com.academy.core.query.result.GetClassesForDateResult;
import com.academy.repository.AcademyUserRepository;
import com.academy.repository.ClassAttendedRepository;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Component
public class GetClassesAttendedForDateHandler implements QueryHandler<GetClassesForDateQuery, GetClassesForDateResult> {

    @Autowired
    AcademyUserRepository academyUserRepository;

    @Autowired
    ClassAttendedRepository classAttendedRepository;

    private static Function<ClassAttended, ClassAttendedBean> CLASS_ATTENDED_TO_CLASS_ATTENDED_BEAN_FUNCTION = new ClassAttendedToClassAttendedBeanFunction();

    @Override
    public GetClassesForDateResult execute(GetClassesForDateQuery query) {

	AcademyUser user = academyUserRepository.findByName(query.getUser());

	DateTime dateTime = new DateTime(query.getDate());

	DateTime startDate = dateTime.withDayOfMonth(1);
	DateTime endDate = startDate.plusMonths(1);

	List<ClassAttended> classesAttended = classAttendedRepository.findByAcademyAndDateIsBetween(user.getAcademy(), startDate.toDate(), endDate.toDate());
	List<ClassAttendedBean> classAttendedBeans = Lists.newArrayList(Collections2.transform(classesAttended, CLASS_ATTENDED_TO_CLASS_ATTENDED_BEAN_FUNCTION));

	return new GetClassesForDateResult(classAttendedBeans);
    }

}
