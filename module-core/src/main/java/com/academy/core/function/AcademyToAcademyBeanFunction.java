package com.academy.core.function;

import com.academy.core.domain.Academy;
import com.academy.core.dto.AcademyBean;
import com.google.common.base.Function;

public class AcademyToAcademyBeanFunction implements Function<Academy, AcademyBean> {

	@Override
	public AcademyBean apply(Academy from) {
		
		AcademyBean bean = new AcademyBean(); 
		bean.setEmail(from.getEmail());
		bean.setName(from.getName());

		return bean;
	}

}
