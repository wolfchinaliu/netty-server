package day1;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmbeddedChannelTest {
    public static void main(String[] args) {
        ChannelInboundHandlerAdapter channelInboundHandlerAdapter = new ChannelInboundHandlerAdapter(){
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.info("1");
                super.channelRead(ctx, msg);
            }
        };
        ChannelInboundHandlerAdapter channelInboundHandlerAdapter2 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.info("2");
                ctx.channel().writeAndFlush(msg);
                super.channelRead(ctx, msg);
            }
        };
        ChannelOutboundHandlerAdapter channelOutboundHandlerAdapter = new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                log.info("3");
                log.info(msg.toString());
                super.write(ctx, msg, promise);
            }
        };
        ChannelOutboundHandlerAdapter channelOutboundHandlerAdapter2 = new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                log.info("4");
                super.write(ctx, msg, promise);
            }
        };
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(channelInboundHandlerAdapter, channelInboundHandlerAdapter2, channelOutboundHandlerAdapter, channelOutboundHandlerAdapter2);
        embeddedChannel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("测试".getBytes()));

    }
}
