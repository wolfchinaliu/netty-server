package handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.GroupChatResponseMessage;
import message.GroupCreateRequestMessage;
import session.Group;
import session.GroupSession;
import session.GroupSessionFactory;

import java.util.Set;

@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        Set<String> members = msg.getMembers();
        String groupName = msg.getGroupName();
        GroupSession groupSession = GroupSessionFactory.getSession();
        Group group = groupSession.createGroup(groupName, members);
        if (group == null) {
            ctx.channel().writeAndFlush(new GroupChatResponseMessage(false, "已经存在该群组"));
        }else{
            ctx.channel().writeAndFlush(new GroupChatResponseMessage(true, "创建成功"));
        }
    }
}
