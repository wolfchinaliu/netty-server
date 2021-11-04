package handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.RPCResponseMessage;

@ChannelHandler.Sharable
public class RPCResponseMessageHandler extends SimpleChannelInboundHandler<RPCResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RPCResponseMessage msg) throws Exception {

    }
}
