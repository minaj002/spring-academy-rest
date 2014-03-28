package com.academy.core.query.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.academy.core.query.Query;
import com.academy.core.query.handler.QueryHandler;



@SuppressWarnings({"rawtypes","unchecked"})
@Component
public class QueryServiceBean implements QueryService {

	private static final Logger log = LoggerFactory.getLogger(QueryServiceBean.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	private Map<Class<?>, String> queryHandlerBeanNames= new ConcurrentHashMap<Class<?>, String>();
	
	@Transactional
	@Override
	public <Q extends Query<T>, T> T execute(Q query) {
		log.info("Execute query {}", toString(query));
		
		return doExecute(query);
	}

	public <Q extends Query<T>, T> T doExecute(Q query){
		
		QueryHandler<Q, T> handler = createHandler(query);
		if(handler == null){
			throw new IllegalStateException("query handler not found for query "+ query.getClass().getName()); 
		}
		
		T result = handler.execute(query);
		return result;
	}
	
	
	private String toString (Object object){
		return ToStringBuilder.reflectionToString(object, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	private <Q extends Query<T>,T> QueryHandler<Q, T> createHandler(Q query){
		String beanName = queryHandlerBeanNames.get(query.getClass());
		QueryHandler handler = applicationContext.getBean(beanName, QueryHandler.class);
		return handler;
	}
	
	@PostConstruct
	public void init(){
		Map<String, QueryHandler> beans = applicationContext.getBeansOfType(QueryHandler.class);
		for (Map.Entry<String, QueryHandler>  entry : beans.entrySet()){
			
			QueryHandler bean = entry.getValue();
			String beanName = entry.getKey();
			Class<? extends QueryHandler>  clazz = bean.getClass();
			Type[] interfaces = clazz.getGenericInterfaces();
			ParameterizedType type;
			if(interfaces.length==0){
				type = (ParameterizedType) clazz.getGenericSuperclass();
			}
			else{
				type = (ParameterizedType)interfaces[0];
			}
			Class<?> queryClass = (Class<?>) type.getActualTypeArguments()[0];
			queryHandlerBeanNames.put(queryClass, beanName);
		}
	}
	
}
