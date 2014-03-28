package com.academy.core.query.handler;

import com.academy.core.query.Query;


public interface QueryHandler<C extends Query<T>, T> {

	T execute(C query);
	
}
