package com.academy.core.query.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.academy.core.domain.Academy;
import com.academy.core.dto.AcademyBean;
import com.academy.core.function.AcademyToAcademyBeanFunction;
import com.academy.core.query.GetAcademiesQuery;
import com.academy.core.query.result.GetAcademiesResult;
import com.academy.repository.AcademyRepository;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Component
public class GetAcademiesHandler implements	QueryHandler<GetAcademiesQuery, GetAcademiesResult> {

	@Autowired
	AcademyRepository academyRepository;
	
	private Function<Academy, AcademyBean> ACADEMY_TO_ACADEMY_BEAN_FUNCTION = new AcademyToAcademyBeanFunction();
	
	@Override
	public GetAcademiesResult execute(GetAcademiesQuery query) {

		List<Academy> academies = academyRepository.findAll();
		
		List<AcademyBean> academyBeans = Lists.newArrayList( Collections2.transform(academies, ACADEMY_TO_ACADEMY_BEAN_FUNCTION));
		
		return new GetAcademiesResult(academyBeans);
	}

}
