package com.backinfile.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;
import java.util.StringJoiner;

public class Utils2 {

	public static final String UTF8 = "utf-8";

	public static String getClassPath() {
		return Utils2.class.getClassLoader().getResource("").getPath();
	}

	public static String getProjectPath() {
		return "";
	}

	public static int getHashCode(String str) {
		int h = 0;
		for (int i = 0; i < str.length(); i++) {
			h = 31 * h + str.charAt(i);
		}
		return h;
	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str);
	}

	public static boolean contains(int[] values, int key) {
		for (int value : values) {
			if (value == key) {
				return true;
			}
		}
		return false;
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	public static String readline() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return "";
	}

	public static int bytes2Int(byte[] bytes, int offset) {
		int num = 0;
		for (int i = offset; i < offset + 4; i++) {
			num <<= 8;
			num |= (bytes[i] & 0xFF);
		}
		return num;
	}

	public static String getStackTraceAsString(Throwable throwable) {
		StringWriter stringWriter = new StringWriter();
		throwable.printStackTrace(new PrintWriter(stringWriter, false));
		stringWriter.flush();
		return stringWriter.getBuffer().toString();
	}

	public static void int2bytes(int num, byte[] bytes, int offset) {
		for (int i = 0; i < 4; i++) {
			bytes[offset + i] = (byte) (num >>> (24 - i * 8));
		}
	}

}
