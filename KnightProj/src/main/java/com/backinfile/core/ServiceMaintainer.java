package com.backinfile.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import com.backinfile.support.Utils2;

public class ServiceMaintainer {
	private final ConcurrentLinkedQueue<Service> waitForRun = new ConcurrentLinkedQueue<>();
	private final DelayQueue<Service> waitForReschedule = new DelayQueue<>();
	private final ConcurrentHashMap<String, Service> allServices = new ConcurrentHashMap<>();

	private DispatchThreads dispatchThreads;
	private static final int DEFAULT_THREAD_NUM = 4;

	public void startUp() {
		startUp(DEFAULT_THREAD_NUM);
	}

	public void startUp(int threadNum) {
		dispatchThreads = new DispatchThreads(threadNum, this::dispatchRun);
		dispatchThreads.start();
	}

	public void abort() {
		dispatchThreads.abortSync();
	}

	public void addService(String name, Service service) {
		waitForReschedule.add(service);
		allServices.put(name, service);
	}

	private void dispatchRun() {
		Service service = waitForRun.poll();
		if (service != null) {
			pulseService(service);
		} else {
			reSchedule(DEFAULT_THREAD_NUM);
			Utils2.sleep(1);
		}
	}

	private void pulseService(Service service) {
		service.pulse();
		waitForReschedule.add(service);
	}

	// 将已经被执行过的port重新放入执行队列
	private void reSchedule(int num) {
		for (int i = 0; i < num; i++) {
			Service service = waitForReschedule.poll();
			if (service == null) {
				break;
			}
			waitForRun.add(service);
		}
	}

	public Service getService(String name) {
		return allServices.get(name);
	}
}
