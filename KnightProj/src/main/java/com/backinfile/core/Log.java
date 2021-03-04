package com.backinfile.core;

import com.backinfile.core.log.Logger;

public class Log {
	public static final Logger game = new Logger("game");
	public static final Logger core = new Logger("core");
	public static final Logger net = new Logger("net");
	public static final Logger event = new Logger("event");
	public static final Logger server = new Logger("server");
	public static final Logger client = new Logger("client");
	public static final Logger test = new Logger("test");
}
