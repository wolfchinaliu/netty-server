package handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.GroupMemberRequestMessage;
import message.GroupMembersResponseMessage;
import session.GroupSession;
import session.GroupSessionFactory;

import java.util.Set;

@ChannelHandler.Sharable
public class GroupMembersRequestMessageHandler extends SimpleChannelInboundHandler<GroupMemberRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMemberRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        GroupSession groupSession = GroupSessionFactory.getSession();
        Set<String> members = groupSession.getMembers(groupName);
        if (members == null) {
            ctx.channel().writeAndFlush(new GroupMembersResponseMessage(false, "没有存在该群组"));
        }else{
            ctx.channel().writeAndFlush(new GroupMembersResponseMessage(members));
        }
    }
}
