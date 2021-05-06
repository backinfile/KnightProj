package com.backinfile.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.backinfile.mrpc.serilize.ISerializable;
import com.backinfile.mrpc.serilize.InputStream;
import com.backinfile.mrpc.serilize.OutputStream;
import com.backinfile.support.ReflectionUtils;
import com.backinfile.support.Utils2;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

public class GameMessage implements ISerializable {
	private Message message;

	private GameMessage() {
	}

	public GameMessage(Message message) {
		this.message = message;
	}

	public GameMessage(Message.Builder builder) {
		this(builder.build());
	}

	@Override
	public void writeTo(OutputStream out) {
		out.write(message);
	}

	@Override
	public void readFrom(InputStream in) {
		message = in.read();
	}

	public Message getMessage() {
		return message;
	}

	public byte[] getBytes() {
		byte[] byteArray = message.toByteArray();
		byte[] contentBytes = new byte[byteArray.length + 8];
		Utils2.int2bytes(byteArray.length + 8, contentBytes, 0);
		Utils2.int2bytes(getMessageHash(message), contentBytes, 4);
		System.arraycopy(byteArray, 0, contentBytes, 8, byteArray.length);
		return contentBytes;
	}


	public static GameMessage buildGameMessage(byte[] bytes, int offset, int len) {
		if (len < 8 || bytes.length < 8)
			return null;
//		int byteSize = Utils2.bytes2Int(bytes, 0);
		int msgHash = Utils2.bytes2Int(bytes, 4);
		if (map.containsKey(msgHash)) {
			Message.Builder builder = map.get(msgHash);
			builder.clear();
			try {
				builder.mergeFrom(bytes, 8, len - 8);
				GameMessage gameMessage = new GameMessage();
				gameMessage.message = builder.build();
				return gameMessage;
			} catch (InvalidProtocolBufferException e) {
				Log.core.error("decode game message failed class={}", e, builder.getClass().getName());
			}
		}

		return null;
	}

	public static int getMessageHash(Class<?> clazz) {
		return clazz.getName().hashCode();
	}

	public static int getMessageHash(Message message) {
		return message.getClass().getName().hashCode();
	}

	@Override
	public String toString() {
		if (message == null) {
			return "null";
		}
		return message.getClass().getSimpleName();
	}

	private static final Map<Integer, Message.Builder> map = new HashMap<>();

	public static void collectAllMessage() {
		if (!map.isEmpty()) {
			return;
		}
		Set<Class<?>> classes = ReflectionUtils.getClassesExtendsClass(Message.class);
		for (Class<?> clazz : classes) {
			try {
				Method method = clazz.getDeclaredMethod("newBuilder");
				Object value = method.invoke(null);
				map.put(getMessageHash(clazz), (Message.Builder) value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
