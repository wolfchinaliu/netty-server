package day1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup workers = new NioEventLoopGroup();
        //1: 启动器
        new ServerBootstrap()
                .group(boss, workers)//Group组
                .channel(NioServerSocketChannel.class)//选择Selector
                //boss 负责处理连接
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    //channel代表和客户端进行数据读写的通道 initial 初始化，负责添加别的handler
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info(msg.toString());
                            }
                        });
                    }
                    //绑定服务器端口
                }).bind(8080);

    }
}
