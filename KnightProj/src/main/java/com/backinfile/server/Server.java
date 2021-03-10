package com.backinfile.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

import com.backinfile.core.Log;

public class Server {

	public void startServer(int port) throws InterruptedException {
		// 第一步，创建线程池
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			// 第二步，创建启动类
			ServerBootstrap b = new ServerBootstrap();
			// 第三步，配置各组件
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childOption(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_KEEPALIVE, true)
					.localAddress(new InetSocketAddress(port)).childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							ChannelPipeline pipeline = socketChannel.pipeline();
							pipeline.addLast(new Decoder(), new Encoder(), new ServerHandler());
						}

						@Override
						public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
							super.exceptionCaught(ctx, cause);
							Log.server.error("error", cause);
						}
					});

			Log.server.debug("start listen:{}", port);
			// 第四步，开启监听
			Channel channel = b.bind().sync().channel();
			Log.server.info("listened: {}", port);
//			channel.closeFuture().sync();
			channel.closeFuture().addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					Log.server.debug("close");
					bossGroup.shutdownGracefully();
					workerGroup.shutdownGracefully();
				}
			});
		} finally {
//			bossGroup.shutdownGracefully().sync();
//			workerGroup.shutdownGracefully().sync();
		}
	}

	public void stopServer() {
	}

	public static void main(String[] args) throws InterruptedException {
		Server server = new Server();
		server.startServer(5555);
	}
}