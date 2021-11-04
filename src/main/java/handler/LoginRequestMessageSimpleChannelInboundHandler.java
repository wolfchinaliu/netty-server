package handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.LoginRequestMessage;
import message.LoginResponseMessage;
import server.UserServiceFactory;
import session.SessionFactory;

@ChannelHandler.Sharable
public class LoginRequestMessageSimpleChannelInboundHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String userName = msg.getUserName();
        String password = msg.getPassword();
        boolean login = UserServiceFactory.getUserServer().login(userName, password);
        LoginResponseMessage loginResponseMessage;
        if (login) {
            SessionFactory.getSession().bind(ctx.channel(), userName);
            //如果登录的话，返回消息
            loginResponseMessage = new LoginResponseMessage(true, "登录成功");
        } else {
            //登录失败
            loginResponseMessage = new LoginResponseMessage(false, "登录失败");
        }
        ctx.writeAndFlush(loginResponseMessage);
    }
}
