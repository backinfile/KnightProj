package com.backinfile.core.net.netty;

import java.net.SocketAddress;

import com.backinfile.core.Log;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class ExceptionHandler extends ChannelDuplexHandler {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		Log.game.error("exceptionCaught {}", cause.getMessage());
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		ctx.connect(remoteAddress, localAddress, promise.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) {
				if (!future.isSuccess()) {
					Log.game.error("connect failed");
				}
			}
		}));
	}
}