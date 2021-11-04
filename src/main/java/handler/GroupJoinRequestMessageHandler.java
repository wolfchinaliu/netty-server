package handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.GroupJoinRequestMessage;
import message.GroupJoinResponseMessage;
import session.Group;
import session.GroupSession;
import session.GroupSessionFactory;

@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        String userName = msg.getUserName();
        GroupSession session = GroupSessionFactory.getSession();
        Group group = session.joinGroup(groupName, userName);
        if (group != null) {
            ctx.channel().writeAndFlush(new GroupJoinResponseMessage(true, "添加成功"));
        }else{
            ctx.channel().writeAndFlush(new GroupJoinResponseMessage(false, "不存在该群组"));
        }
    }
}
