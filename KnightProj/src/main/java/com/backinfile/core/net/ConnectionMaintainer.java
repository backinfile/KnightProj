package com.backinfile.core.net;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.backinfile.core.DispatchThreads;
import com.backinfile.core.GameMessage;
import com.backinfile.core.Log;
import com.backinfile.support.Utils2;

public class ConnectionMaintainer {
	private final ConcurrentLinkedQueue<Connection> waitForRun = new ConcurrentLinkedQueue<>();
	private final DelayQueue<Connection> waitForReschedule = new DelayQueue<>();
	private final ConcurrentHashMap<Long, Connection> allConnections = new ConcurrentHashMap<>();

	private DispatchThreads dispatchThreads;
	private static final int DEFAULT_THREAD_NUM = 4;

	public void startUp() {
		startUp(DEFAULT_THREAD_NUM);
	}

	public void startUp(int threadNum) {
		Thread.currentThread().setName("Thread-NetManager");
		dispatchThreads = new DispatchThreads(threadNum, this::dispatchRun);
		dispatchThreads.start();
	}

	public void abort() {
		Log.core.info("ConnectionMaintainer closing...");
		dispatchThreads.abortSync();
		Log.core.info("ConnectionMaintainer closed");
	}

	public void addConnection(ChannelConnection connection) {
		waitForRun.add(connection);
		allConnections.put(connection.getId(), connection);
	}

	private void dispatchRun() {
		Connection connection = waitForRun.poll();
		if (connection != null) {
			pulseConnection(connection);
		} else {
			reSchedule(DEFAULT_THREAD_NUM);
			Utils2.sleep(1);
		}
	}

	private void pulseConnection(Connection connection) {
		if (connection.isAlive()) {
			connection.pulse();
			waitForReschedule.add(connection);
		} else {
			allConnections.remove(connection.getId());
		}

	}

	// 将已经被执行过的port重新放入执行队列
	private void reSchedule(int num) {
		for (int i = 0; i < num; i++) {
			Connection connection = waitForReschedule.poll();
			if (connection == null) {
				break;
			}
			waitForRun.add(connection);
		}
	}

	public GameMessage pollGameMessage(long id) {
		Connection channelConnection = allConnections.get(id);
		if (channelConnection != null) {
			return channelConnection.getGameMessage();
		}
		return null;
	}

	public Connection getConnection(long id) {
		return allConnections.get(id);
	}

}
