package chat;

import handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import protocol.MessageCodecSharable;

@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);
        MessageCodecSharable messageCodec = new MessageCodecSharable();
        LoginRequestMessageSimpleChannelInboundHandler loginHandler = new LoginRequestMessageSimpleChannelInboundHandler();
        ChatRequestMessageHandler chatHandler = new ChatRequestMessageHandler();
        GroupChatRequestMessageHandler groupChatRequestMessageHandler = new GroupChatRequestMessageHandler();
        GroupCreateRequestMessageHandler groupCreateRequestMessageHandler = new GroupCreateRequestMessageHandler();
        GroupJoinRequestMessageHandler groupJoinRequestMessageHandler = new GroupJoinRequestMessageHandler();
        GroupMembersRequestMessageHandler groupMembersRequestMessageHandler = new GroupMembersRequestMessageHandler();
        GroupQuitRequestMessageHandler groupQuitRequestMessageHandler = new GroupQuitRequestMessageHandler();
        PingRequestMessageHandler pingRequestMessageHandler = new PingRequestMessageHandler();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(loggingHandler);
                            //连接超时设置
                            ch.pipeline().addLast(new IdleStateHandler(5, 0, 5));
                            ch.pipeline().addLast(new ChannelDuplexHandler() {

                                //用来触发特殊事件
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
                                    IdleState idleState = idleStateEvent.state();
                                    if (idleState == IdleState.READER_IDLE) {
                                        log.info("已经五秒没有读取到数据.........");
                                        ctx.channel().close();
                                    }
                                }
                            });
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0));
                            ch.pipeline().addLast(messageCodec);
                            ch.pipeline().addLast(loginHandler);
                            ch.pipeline().addLast(chatHandler);
                            ch.pipeline().addLast(groupChatRequestMessageHandler);
                            ch.pipeline().addLast(groupCreateRequestMessageHandler);
                            ch.pipeline().addLast(groupJoinRequestMessageHandler);
                            ch.pipeline().addLast(groupMembersRequestMessageHandler);
                            ch.pipeline().addLast(groupQuitRequestMessageHandler);
                            ch.pipeline().addLast(pingRequestMessageHandler);
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
