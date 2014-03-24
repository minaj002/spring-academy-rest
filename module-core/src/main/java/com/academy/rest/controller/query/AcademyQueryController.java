package com.academy.rest.controller.query;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.academy.core.dto.AcademyBean;
import com.academy.core.query.GetAcademiesQuery;
import com.academy.core.query.result.GetAcademiesResult;
import com.academy.core.query.service.QueryService;
import com.academy.rest.api.Academy;
import com.academy.rest.function.AcademyBeanToAcademyFunction;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("/academies")
public class AcademyQueryController {

	private static Logger LOG = LoggerFactory
			.getLogger(AcademyQueryController.class);
	
	private static Function<AcademyBean, Academy> ACADEMY_BEAN_TO_ACADEMY_FUNCTION = new AcademyBeanToAcademyFunction();
	
	@Autowired
	QueryService queryService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Academy> getAllAcademies() {
		
		GetAcademiesQuery query = new GetAcademiesQuery();
		GetAcademiesResult academies = queryService.execute(query);
		
		List<Academy> academyJson =Lists.newArrayList(Collections2.transform(academies.getAcademies(), ACADEMY_BEAN_TO_ACADEMY_FUNCTION));
		
		return academyJson;
	}

}
