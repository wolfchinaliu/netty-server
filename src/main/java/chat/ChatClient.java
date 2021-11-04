package chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import message.*;
import protocol.MessageCodecSharable;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
public class ChatClient {
    public static void main(String[] args) {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);
        MessageCodecSharable messageCodec = new MessageCodecSharable();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicBoolean LOGIN = new AtomicBoolean(false);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(nioEventLoopGroup);
            ChannelFuture channelFuture = bootstrap.handler(new ChannelInitializer<SocketChannel>() {


                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0));
//                    ch.pipeline().addLast(loggingHandler);
                    ch.pipeline().addLast(messageCodec);
                    ch.pipeline().addLast("client handle", new ChannelInboundHandlerAdapter(){
                        //连接建立以后的active事件
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) {
                            new Thread(() -> {
                                Scanner scanner = new Scanner(System.in);
                                System.out.println("请输入用户名：");
                                String userName = scanner.nextLine();
                                System.out.println("请输入密码");
                                String password = scanner.nextLine();
                                LoginRequestMessage loginRequestMessage = new LoginRequestMessage(userName, password);
                                ctx.writeAndFlush(loginRequestMessage);
                                System.out.println("等待后续操作......");
                                log.info("Thread ===========" + Thread.currentThread().getName());
                                try {
                                    countDownLatch.await();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (!LOGIN.get()) {
                                    log.info("登录失败.......");
                                    //登录失败，退出
                                    ctx.channel().close();
                                    return;
                                }
                                log.info("登录成功.......");
                                while (true) {
                                    //功能
                                    System.out.println("======================");
                                    System.out.println("send [userName] [content]");
                                    System.out.println("gsend [group name] [content]");
                                    System.out.println("gcreate [group name] [m1,m2,m3...]");
                                    System.out.println("gmembers [group name]");
                                    System.out.println("gjoin [group name]");
                                    System.out.println("gquit [group name]");
                                    System.out.println("quit");
                                    System.out.println("======================");
                                    String command = scanner.nextLine();
                                    String[] split = command.split(" ");
                                    String commandType = split[0];
                                    switch (commandType) {
                                        case "send":
                                            ctx.writeAndFlush(new ChatRequestMessage(userName, split[1], split[2]));
                                            break;
                                        case "gsend":
                                            ctx.writeAndFlush(new GroupChatRequestMessage(userName, split[1], split[2]));
                                            break;
                                        case "gcreate":
                                            Set<String> members = Arrays.stream(split[2].split(",")).collect(Collectors.toSet());
                                            ctx.writeAndFlush(new GroupCreateRequestMessage(split[1], members));
                                            break;
                                        case "gmembers":
                                            ctx.writeAndFlush(new GroupMemberRequestMessage(split[1]));
                                            break;
                                        case "gjoin":
                                            ctx.writeAndFlush(new GroupJoinRequestMessage(userName, split[1]));
                                            break;
                                        case "gquit":
                                            ctx.writeAndFlush(new GroupQuitRequestMessage(userName, split[1]));
                                            break;
                                        case "quit":
                                            System.out.println("退出客户端");
                                            ctx.channel().close();
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }, "login in").start();
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            if (msg instanceof LoginResponseMessage) {
                                LoginResponseMessage loginResponseMessage = (LoginResponseMessage) msg;
                                if (loginResponseMessage.isSuccess()) {
                                    //登录成功
                                    LOGIN.set(true);
                                }else{
                                    System.out.println("密码不正确");
                                }
                                countDownLatch.countDown();
                            }
                            log.info("message:{}", msg);
                        }
                    });
                }
            }).connect("localhost", 8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("client error", e);
        }finally {
            log.info("exit client .....");
            nioEventLoopGroup.shutdownGracefully();
        }
    }
}
