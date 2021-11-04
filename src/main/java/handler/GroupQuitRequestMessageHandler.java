package handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.GroupQuitRequestMessage;
import message.GroupQuitResponseMessage;
import session.Group;
import session.GroupSession;
import session.GroupSessionFactory;

@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        String userName = msg.getUserName();
        GroupSession groupSession = GroupSessionFactory.getSession();
        Group group = groupSession.quitGroup(groupName, userName);
        if (group != null) {
            ctx.channel().writeAndFlush(new GroupQuitResponseMessage(true, "退出成功"));
        }else{
            ctx.channel().writeAndFlush(new GroupQuitResponseMessage(false, "退出失败，不存在对应的群主"));
        }
    }
}
