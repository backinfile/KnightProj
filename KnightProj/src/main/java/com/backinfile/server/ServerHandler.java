package com.backinfile.server;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.backinfile.core.Log;
import com.backinfile.core.net.ChannelConnection;
import com.backinfile.core.net.GameServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
	private ChannelConnection connection;
	private AtomicLong idAllot = new AtomicLong(0);
	private AtomicLong connectionCount = new AtomicLong(0);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		Channel channel = ctx.channel();
		long id = idAllot.incrementAndGet();
		connection = new ChannelConnection(id, channel);
		if (GameServer.Instance != null) {
			GameServer.Instance.addConnection(connection);
		}
		connectionCount.incrementAndGet();
		Log.server.info("channelActive id={}, num={}", connection.getId(), connectionCount);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
		connection.addInput((byte[]) msg);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		connection.close();
		connectionCount.decrementAndGet();
		Log.server.info("channelInactive id=" + connection.getId());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		Log.server.error("exceptionCaught id=" + connection.getId() + " {}", cause.getMessage());
	}
}
