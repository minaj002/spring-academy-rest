package com.academy.core.command.handler;

import com.academy.core.command.Command;

public interface CommandHandler<C extends Command<T>, T> {

	T execute(C command);
	
}
