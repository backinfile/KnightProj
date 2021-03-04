package com.backinfile.core.net;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.backinfile.core.DispatchThreads;
import com.backinfile.core.GameMessage;
import com.backinfile.core.Log;
import com.backinfile.support.Utils2;

public class ConnectionMaintainer {
	private final ConcurrentLinkedQueue<Connection> waitForRun = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<Connection> waitForReschedule = new ConcurrentLinkedQueue<>();
	private final ConcurrentHashMap<Long, Connection> allConnections = new ConcurrentHashMap<>();

	private final ConcurrentHashMap<Long, ConcurrentLinkedQueue<GameMessage>> clientMessages = new ConcurrentHashMap<>();

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

	public void addConnnect(Connection connection) {
		waitForRun.add(connection);
		allConnections.put(connection.id, connection);
	}

	private void dispatchRun() {
		Connection connection = waitForRun.poll();
		if (connection == null) {
			reSchedule(DEFAULT_THREAD_NUM);
			Utils2.sleep(1);
			return;
		}
		pulse(connection);
	}

	private void pulse(Connection connection) {
		if (connection.isAlive()) {
			connection.pulse();
			waitForReschedule.add(connection);

			collectMessage(connection);
		} else {
			allConnections.remove(connection.id);
		}

	}

	private void collectMessage(Connection connection) {
		ConcurrentLinkedQueue<GameMessage> reciveList = connection.getReciveList();
		if (!reciveList.isEmpty()) {
			while (!reciveList.isEmpty()) {
				GameMessage gameMessage = reciveList.poll();
				if (gameMessage != null) {
					clientMessages.putIfAbsent(connection.id, new ConcurrentLinkedQueue<>());
					ConcurrentLinkedQueue<GameMessage> queue = clientMessages.get(connection.id);
					if (queue != null) {
						queue.add(gameMessage);
					} else {
						Log.core.error("ignore gameMessage name={0} gameMessage ={1}", connection.name,
								gameMessage.toString());
					}
				}
			}
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
		ConcurrentLinkedQueue<GameMessage> queue = clientMessages.get(id);
		if (queue != null) {
			GameMessage gameMessage = queue.poll();
			if (queue.isEmpty() && !allConnections.contains(id)) {
				clientMessages.remove(id);
			}
			return gameMessage;
		}
		return null;
	}

}
