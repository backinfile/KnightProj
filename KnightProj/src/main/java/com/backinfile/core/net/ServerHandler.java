package com.backinfile.core.net;

import java.util.concurrent.atomic.AtomicLong;

import com.backinfile.core.GameMessage;
import com.backinfile.core.Log;
import com.backinfile.mrpc.core.Params;
import com.backinfile.mrpc.core.Proxy;
import com.backinfile.server.RequestKey;
import com.backinfile.server.connect.ConnectionGlobalService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
	private ChannelConnection connection;
	private static AtomicLong idAllot = new AtomicLong(0);
	private static AtomicLong connectionCount = new AtomicLong(0);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		Channel channel = ctx.channel();
		long id = idAllot.incrementAndGet();
		connection = new ChannelConnection(id, channel);

		var params = new Params("connection", connection);
		Proxy.request(ConnectionGlobalService.PORT_NAME, RequestKey.CONNECTION_GLOBAL_ADD_CONNECTION, params);

		connectionCount.incrementAndGet();
		Log.server.info("channelActive id={}, num={}", connection.getId(), connectionCount);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
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
		Log.server.error("exceptionCaught id=" + connection.getId() + " {}", cause.getMessage());
	}
}
