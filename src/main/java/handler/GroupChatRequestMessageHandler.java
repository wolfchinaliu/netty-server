package handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.ChatResponseMessage;
import message.GroupChatRequestMessage;
import session.GroupSession;
import session.GroupSessionFactory;
import session.Session;
import session.SessionFactory;

import java.util.Set;

@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        String from = msg.getFrom();
        String groupName = msg.getGroupName();
        String content = msg.getContent();
        //获取对应的Session
        GroupSession groupSession = GroupSessionFactory.getSession();
        Session session = SessionFactory.getSession();
        Set<String> members = groupSession.getMembers(groupName);
        if (members == null) {
            //说明不存在对应的组
            ctx.channel().writeAndFlush(new ChatResponseMessage(false, "不存在对应的群组"));
        }else{
            for (String member : members) {
                //获取对应的群组人员对应在线的人员，返送消息的channel
                Channel channel = session.getChannel(member);
                if (channel != null) {
                    //给对应的在线的群组用户发送消息
                    channel.writeAndFlush(new ChatResponseMessage(from, content));
                }
            }
        }
    }
}
