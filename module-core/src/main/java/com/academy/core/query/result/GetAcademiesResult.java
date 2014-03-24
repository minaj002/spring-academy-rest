package com.academy.core.query.result;

import java.util.List;

import com.academy.core.dto.AcademyBean;

public class GetAcademiesResult implements QueryResult {

	private List<AcademyBean> academies;

	public GetAcademiesResult(List<AcademyBean> academies) {
		this.academies = academies;
	}

	public List<AcademyBean> getAcademies() {
		return academies;
	}

	public void setAcademies(List<AcademyBean> academies) {
		this.academies = academies;
	}
	
}
