package com.backinfile.core.log;

public enum LoggerLevel {

	/** 内部日志，用于调试 */
	DEBUG("DEBUG", 0),

	/** 提示信息 */
	INFO("INFO", 1),

	/** 错误信息 */
	ERROR("ERROR", 2),

	;

	private String name;
	private int priority;

	private LoggerLevel(String name, int priority) {
		this.name = name;
		this.priority = priority;
	}

	public String getValue() {
		return name;
	}

	public boolean isFit(LoggerLevel level) {
		return this.priority >= level.priority;
	}
}
