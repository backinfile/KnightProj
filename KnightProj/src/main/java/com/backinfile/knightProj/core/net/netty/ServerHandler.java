package com.backinfile.knightProj.core.net.netty;

import java.util.concurrent.atomic.AtomicLong;

import com.backinfile.knightProj.core.GameMessage;
import com.backinfile.knightProj.core.Log;
import com.backinfile.knightProj.core.net.ChannelConnection;
import com.backinfile.knightProj.server.RequestKey;
import com.backinfile.knightProj.server.connect.ConnectionGlobalService;
import com.backinfile.mrpc.core.Node;
import com.backinfile.mrpc.core.Params;
import com.backinfile.mrpc.core.Port;
import com.backinfile.mrpc.core.Proxy;

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

		Port port = Node.Instance.getPort(ConnectionGlobalService.PORT_NAME);
		if (port instanceof ConnectionGlobalService) {
			var service = (ConnectionGlobalService) port;
			service.post(() -> {
				service.addConnection(connection);
			});
		}

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
