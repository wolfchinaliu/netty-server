package day3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;

@Slf4j
public class TestHttp {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup workers = new NioEventLoopGroup();
        //1: 启动器
        new ServerBootstrap()
                .group(boss, workers)//Group组
                .option(ChannelOption.SO_BACKLOG, 2)
                .channel(NioServerSocketChannel.class)//选择Selector
                //boss 负责处理连接
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    //channel代表和客户端进行数据读写的通道 initial 初始化，负责添加别的handler
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                        ch.pipeline().addLast(new HttpServerCodec());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                                log.info(msg.uri());
                                DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);
                                byte[] bytes = "<h1>Hello World</h1>".getBytes();
                                defaultFullHttpResponse.content().writeBytes(bytes);
                                defaultFullHttpResponse.headers().setInt(CONTENT_LENGTH, bytes.length);
                                ctx.writeAndFlush(defaultFullHttpResponse);
                            }
                        });
                    }
                    //绑定服务器端口
                }).bind(8080);

    }
}

