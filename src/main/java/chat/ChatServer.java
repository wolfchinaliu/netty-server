package chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import message.LoginRequestMessage;
import message.LoginResponseMessage;
import protocol.MessageCodecSharable;
import server.UserServiceFactory;
import session.GroupSessionMemoryImpl;
import session.SessionMemoryImpl;

@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);
        MessageCodecSharable messageCodec = new MessageCodecSharable();
        GroupSessionMemoryImpl groupSessionMemory = new GroupSessionMemoryImpl();
        SessionMemoryImpl sessionMemory = new SessionMemoryImpl();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(loggingHandler);
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0));
                            ch.pipeline().addLast(messageCodec);
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<LoginRequestMessage>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
                                    String userName = msg.getUserName();
                                    String password = msg.getPassword();
                                    boolean login = UserServiceFactory.getUserServer().login(userName, password);
                                    LoginResponseMessage loginResponseMessage;
                                    if (login) {
                                        //如果登录的话，返回消息
                                        loginResponseMessage = new LoginResponseMessage(true, "登录成功");
                                    } else {
                                        //登录失败
                                        loginResponseMessage = new LoginResponseMessage(false, "登录失败");
                                    }
                                    ctx.writeAndFlush(loginResponseMessage);
                                }
                            });
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.info("server error:{}", e.getMessage());
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
