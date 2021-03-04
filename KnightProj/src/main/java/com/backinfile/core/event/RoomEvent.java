package com.backinfile.core.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.backinfile.core.Log;
import com.backinfile.server.room.Room;
import com.backinfile.support.ReflectionUtils;
import com.google.protobuf.Message;

public class RoomEvent {
	public static void fire(Room room, Message message) {
		fire(message.getClass().getName().hashCode(), room, message);
	}

	public static void fire(int hashCode, Room room, Message message) {
		List<Method> methods = methodMap.get(hashCode);
		if (message == null) {
			return;
		}
		for (Method method : methods) {
			try {
				method.invoke(null, room, message);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Log.event.error("method invoke error in fire event name={0}", e, message.getClass().getName());
			}
		}
	}

	private static final Map<Integer, List<Method>> methodMap = new HashMap<>();

	public static void collectEventListener() {
		Set<Class<?>> classes = ReflectionUtils.getClasses();
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
				if (!param1.isAssignableFrom(Room.class)) {
					continue;
				}
				if (!Message.class.isAssignableFrom(param2)) {
					continue;
				}
				int hashCode = param2.getName().hashCode();

				if (!methodMap.containsKey(hashCode)) {
					methodMap.put(hashCode, new ArrayList<>());
				}
				methodMap.get(hashCode).add(method);
			}
		}

	}
}
