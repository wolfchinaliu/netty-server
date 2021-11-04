package handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.ChatRequestMessage;
import message.ChatResponseMessage;
import session.SessionFactory;

@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        String to = msg.getTo();
        Channel channel = SessionFactory.getSession().getChannel(to);
        if (channel != null) {
            //如果存在的话
            channel.writeAndFlush(new ChatResponseMessage(msg.getFrom(), msg.getContent()));
        }else{
            //如果不存在
            ctx.channel().writeAndFlush(new ChatResponseMessage(false, "发送失败，当前用户不不在线"));
        }

    }
}
