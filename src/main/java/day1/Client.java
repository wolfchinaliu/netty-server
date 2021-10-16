package day1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        //启动类
        ChannelFuture channelFuture = new Bootstrap()
                //添加 EventLoop
                .group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    //在连接建立后被调用
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new StringEncoder());
                    }//连接到服务器，异步非阻塞调用，真正执行的是connect是NIO线程
                }).connect(new InetSocketAddress("localhost", 8080));
        //无阻塞向下执行获取channel
        channelFuture.sync();
        Channel channel = channelFuture.channel();
        new Thread(() -> {
            System.out.println("输入线程");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if (line.endsWith("q")) {
                    //退出连接
                    System.out.println("退出连接");
                    channel.close();
                    break;
                } else {
                    //将消息发送到服务器上
                    channel.writeAndFlush(line);
                }
            }
        }, "input").start();
        ChannelFuture closeFuture = channel.closeFuture();
        //阻塞调用
//        System.out.println("close channel");
//        closeFuture.sync();
//        eventExecutors.shutdownGracefully();
        //异步调用
        closeFuture.addListener((ChannelFutureListener) future -> {
            System.out.println("channel关闭");
            eventExecutors.shutdownGracefully();
        });
        //异步调用
//        channelFuture.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                Channel channel = future.channel();
//                channel.writeAndFlush("你好世界");
//            }
//        });

    }
}
