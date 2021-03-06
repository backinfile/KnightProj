package com.backinfile.knightProj.core.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.backinfile.knightProj.core.Const;
import com.backinfile.knightProj.core.GameMessage;
import com.backinfile.knightProj.core.Log;
import com.backinfile.mrpc.core.Params;
import com.backinfile.mrpc.utils.ReflectionUtils;
import com.google.protobuf.Message;

public class Event {

	public static void fire(EventKey eventKey, Object... param) {
		fire(eventKey, new Params(param));
	}

	public static void fire(EventKey eventKey, Params param) {
		List<Method> methods = eventHandler.get(eventKey);
		if (methods == null) {
			return;
		}
		for (Method method : methods) {
			try {
				method.invoke(null, param);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Log.event.error("method invoke error in fire event key={}", e, eventKey.toString());
			}
		}
	}

	public static void fireMessage(Message message, Params param) {
		int hashCode = GameMessage.getMessageHash(message);
		List<Method> methods = messageHandler.get(hashCode);
		if (methods == null) {
			return;
		}
		for (Method method : methods) {
			try {
				method.invoke(null, message, param);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Log.event.error("method invoke error in fire message event name={}", e, message.getClass().getName());
			}
		}
	}

	private static final Map<Integer, List<Method>> messageHandler = new HashMap<>();
	private static final Map<EventKey, List<Method>> eventHandler = new HashMap<>();

	public static void collectEventListener() {
		Set<Class<?>> classes = ReflectionUtils.getClassesExtendsClass(Const.PACKAGE_NAME, Object.class);
		for (Class<?> clazz : classes) {
			for (Method method : clazz.getMethods()) {
				if (!Modifier.isStatic(method.getModifiers())) {
					continue;
				}
				if (!method.isAnnotationPresent(MsgHandler.class)) {
					continue;
				}
				if (method.getParameterCount() != 2) {
					continue;
				}
				Class<?>[] parameterTypes = method.getParameterTypes();
				Class<?> param1 = parameterTypes[0];
				Class<?> param2 = parameterTypes[1];
				if (!Params.class.isAssignableFrom(param2)) {
					continue;
				}
				if (!Message.class.isAssignableFrom(param1)) {
					continue;
				}
				int hashCode = GameMessage.getMessageHash(param1);

				if (!messageHandler.containsKey(hashCode)) {
					messageHandler.put(hashCode, new ArrayList<>());
				}
				messageHandler.get(hashCode).add(method);
			}
		}

		for (Class<?> clazz : classes) {
			for (Method method : clazz.getMethods()) {
				if (!Modifier.isStatic(method.getModifiers())) {
					continue;
				}
				EventListener annotation = method.getAnnotation(EventListener.class);
				if (annotation == null) {
					continue;
				}
				if (method.getParameterCount() != 1) {
					continue;
				}
				EventKey key = annotation.value();

				Class<?>[] parameterTypes = method.getParameterTypes();
				Class<?> param1 = parameterTypes[0];
				if (!Params.class.isAssignableFrom(param1)) {
					continue;
				}
				if (!eventHandler.containsKey(key)) {
					eventHandler.put(key, new ArrayList<>());
				}
				eventHandler.get(key).add(method);
			}
		}
	}
}
