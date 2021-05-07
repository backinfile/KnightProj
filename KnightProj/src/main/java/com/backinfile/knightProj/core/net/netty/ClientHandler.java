package com.backinfile.knightProj.core.net.netty;

import com.backinfile.knightProj.core.GameMessage;
import com.backinfile.knightProj.core.Log;
import com.backinfile.knightProj.gen.pb.Msg.CSConnect;
import com.backinfile.knightProj.gen.pb.Msg.SCConnect;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		Log.client.info("channelActive");

		ctx.channel().writeAndFlush(new GameMessage(CSConnect.newBuilder().setName("哈哈啊")).getBytes());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
		Log.client.info("channelInactive");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		super.channelRead(ctx, msg);
		Log.client.info("channelRead");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
		Log.client.info("exceptionCaught");
	}

}
