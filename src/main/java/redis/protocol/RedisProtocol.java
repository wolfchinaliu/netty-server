package redis.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class RedisProtocol {
    public static void main(String[] args)  {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        final byte[] line = {13, 10};
        try {
            ChannelFuture channelFuture = bootstrap.group(nioEventLoopGroup).handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    //连接建立
                                    log.info("连接建立");
                                    ByteBuf buffer = ctx.alloc().buffer();
                                    buffer.writeBytes("*3".getBytes());
                                    buffer.writeBytes(line);
                                    buffer.writeBytes("$3".getBytes());
                                    buffer.writeBytes(line);
                                    buffer.writeBytes("set".getBytes());
                                    buffer.writeBytes(line);
                                    buffer.writeBytes("$5".getBytes());
                                    buffer.writeBytes(line);
                                    buffer.writeBytes("email".getBytes());
                                    buffer.writeBytes(line);
                                    buffer.writeBytes("$20".getBytes());
                                    buffer.writeBytes(line);
                                    buffer.writeBytes("wolfchinaliu@163.com".getBytes());
                                    buffer.writeBytes(line);
                                    ctx.writeAndFlush(buffer);
                                }
                            });
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.info(msg.toString());
//                            super.channelRead(ctx, msg);
                        }
                    })
                    .channel(NioSocketChannel.class).
                    connect(new InetSocketAddress("localhost", 6379)).
                    sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.info("client error:{}", e.getMessage());
        }finally {
            //关闭连接
            nioEventLoopGroup.shutdownGracefully();
        }

    }
}
