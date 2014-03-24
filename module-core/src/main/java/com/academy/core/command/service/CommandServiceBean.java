package com.academy.core.command.service;

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

import com.academy.core.command.Command;
import com.academy.core.command.handler.CommandHandler;



@SuppressWarnings({"rawtypes","unchecked"})
@Component
public class CommandServiceBean implements CommandService {

	private static final Logger log = LoggerFactory.getLogger(CommandServiceBean.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	private Map<Class<?>, String> commandHandlerBeanNames= new ConcurrentHashMap<Class<?>, String>();
	
	@Transactional
	@Override
	public <C extends Command<T>, T> T execute(C command) {
		log.info("Execute command {}", toString(command));
		
		return doExecute(command);
	}

	public <C extends Command<T>, T> T doExecute(C command){
		
		CommandHandler<C, T> handler = createHandler(command);
		if(handler == null){
			throw new IllegalStateException("Command handler not found for command "+ command.getClass().getName()); 
		}
		
		T result = handler.execute(command);
		return result;
	}
	
	
	private String toString (Object object){
		return ToStringBuilder.reflectionToString(object, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	private <C extends Command<T>,T> CommandHandler<C, T> createHandler(C command){
		String beanName = commandHandlerBeanNames.get(command.getClass());
		CommandHandler handler = applicationContext.getBean(beanName, CommandHandler.class);
		return handler;
	}
	
	@PostConstruct
	public void init(){
		Map<String, CommandHandler> beans = applicationContext.getBeansOfType(CommandHandler.class);
		for (Map.Entry<String, CommandHandler>  entry : beans.entrySet()){
			
			CommandHandler bean = entry.getValue();
			String beanName = entry.getKey();
			Class<? extends CommandHandler>  clazz = bean.getClass();
			Type[] interfaces = clazz.getGenericInterfaces();
			ParameterizedType type;
			if(interfaces.length==0){
				type = (ParameterizedType) clazz.getGenericSuperclass();
			}
			else{
				type = (ParameterizedType)interfaces[0];
			}
			Class<?> commandClass = (Class<?>) type.getActualTypeArguments()[0];
			commandHandlerBeanNames.put(commandClass, beanName);
		}
	}
	
}
