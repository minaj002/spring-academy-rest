package com.academy.core.query.service;

import com.academy.core.query.Query;

public interface QueryService {

	
	<Q extends Query<T>, T> T execute(final Q query);
	
	
}
