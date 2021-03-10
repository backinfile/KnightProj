package com.backinfile.support;

public class Time2 {
	public static final int MILLI = 1;
	public static final int SECOND = 1000 * MILLI;
	public static final int MINUTE = 60 * SECOND;
	public static final int HOUR = 60 * MINUTE;
	public static final int Day = 24 * HOUR;

	public static long getCurrentTimestamp() {
		return System.currentTimeMillis();
	}
}
