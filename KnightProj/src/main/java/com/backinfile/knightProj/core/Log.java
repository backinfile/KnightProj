package com.backinfile.knightProj.core;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
	public static final Logger game = getLogger("game");
	public static final Logger core = getLogger("core");
	public static final Logger net = getLogger("net");
	public static final Logger event = getLogger("event");
	public static final Logger server = getLogger("server");
	public static final Logger client = getLogger("client");
	public static final Logger test = getLogger("test");
	public static final Logger chatRoom = getLogger("chatRoom");

	private static Logger getLogger(String name) {
		return LoggerFactory.getLogger(name.toUpperCase());
	}

	public static void setLevel(Level level) {
		Configurator.setRootLevel(level);
	}
}
