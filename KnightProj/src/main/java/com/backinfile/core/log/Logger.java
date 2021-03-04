package com.backinfile.core.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import com.backinfile.core.Const;
import com.backinfile.core.Log;
import com.backinfile.core.Settings;
import com.backinfile.support.Utils2;
import com.badlogic.gdx.Gdx;

public class Logger {
	private String tag;

	private static final String timePattern = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final String logPattern = "{0} {1} [{2}][{3}] {4}";

	private static final SimpleDateFormat timeFormat = new SimpleDateFormat(timePattern);
	private static final MessageFormat logFormat = new MessageFormat(logPattern);

	public Logger(String tag) {
		this.tag = tag.toUpperCase();
	}

	public void info(String message, Object... arguments) {
		if (arguments.length > 0) {
			message = MessageFormat.format(message, arguments);
		}
//		Gdx.app.log(tag, message);
		if (LoggerLevel.INFO.isFit(Settings.Instance.LOGGER_LEVEL)) {
			append(LoggerLevel.INFO, message, null);
		}
	}

	public void debug(String message, Object... arguments) {
		if (arguments.length > 0) {
			message = MessageFormat.format(message, arguments);
		}
//		Gdx.app.debug(tag, message);
		if (LoggerLevel.DEBUG.isFit(Settings.Instance.LOGGER_LEVEL)) {
			append(LoggerLevel.DEBUG, message, null);
		}
	}

	public void error(String message, Object... arguments) {
		if (arguments.length > 0) {
			message = MessageFormat.format(message, arguments);
		}
//		Gdx.app.error(tag, message);
		if (LoggerLevel.ERROR.isFit(Settings.Instance.LOGGER_LEVEL)) {
			append(LoggerLevel.ERROR, message, null);
		}
	}

	public void error(String message, Throwable throwable, Object... arguments) {
		if (arguments.length > 0) {
			message = MessageFormat.format(message, arguments);
		}
//		Gdx.app.error(tag, message, throwable);
		if (LoggerLevel.ERROR.isFit(Settings.Instance.LOGGER_LEVEL)) {
			append(LoggerLevel.ERROR, message, throwable);
		}
	}

	private static String getCallerInfo() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement element = stackTrace[4];
		return "(" + element.getFileName() + ":" + element.getLineNumber() + ")";
	}

	private void append(LoggerLevel level, String content, Throwable throwable) {

		String finalContent = content;
		if (throwable != null) {
			finalContent += "\n" + Utils2.getStackTraceAsString(throwable);
		}
		String time = timeFormat.format(new Date(System.currentTimeMillis()));
		String callerInfo = getCallerInfo();

		String finalLogContent = logFormat
				.format(new Object[] { time, callerInfo, tag, level.getValue(), finalContent }, new StringBuffer(),
						null)
				.toString();

		// to console
		System.out.println(finalLogContent);
		// to file
		log2File(finalLogContent);
	}

	private static FileWriter fileWriter;
	private static boolean logFileInited = false;

	public static void initLogFile() {
		if (logFileInited) {
			return;
		} else {
			logFileInited = true;
		}
		Log.game.info("init log file path:\"{0}\"", Const.LOG_FILENAME);

		File logFile = new File(Const.LOG_FILENAME);
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter writer = new FileWriter(logFile, true);
			if (writer != null) {
				fileWriter = writer;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void log2File(String content) {
		if (fileWriter != null) {
			try {
				fileWriter.append(content + "\n");
				fileWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
