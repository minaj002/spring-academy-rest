package com.academy.core.command.service;

import com.academy.core.command.Command;

public interface CommandService {

	
	<C extends Command<T>, T> T execute(final C command);
	
	
}
