package protocol;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import message.LoginRequestMessage;
import org.junit.jupiter.api.Test;

class MessageCodecTest {

    @Test
    public void testCode() throws Exception {

        LengthFieldBasedFrameDecoder fieldBasedFrameDecoder = new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0);
        MessageCodecSharable loginMessageCodec = new MessageCodecSharable();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);
        EmbeddedChannel channel = new EmbeddedChannel(
                loggingHandler,
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                loginMessageCodec);
        LoginRequestMessage loginRequestMessage = new LoginRequestMessage();
        loginRequestMessage.setEmail("wolfchinaliu@163.com");
        loginRequestMessage.setPassword("123456");
        loginRequestMessage.setUserName("jerry");
        //出站
        channel.writeOutbound(loginRequestMessage);
//        //入栈
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
//        loginMessageCodec.encode(null, loginRequestMessage, byteBuf);
//        ByteBuf byteBuf1 = byteBuf.slice(0, 100);
//        //引用计算加一
//        byteBuf1.retain();
//        channel.writeInbound(byteBuf1);
//        ByteBuf byteBuf2 = byteBuf.slice(100, byteBuf.readableBytes() - 100);
//        channel.writeInbound(byteBuf2);
    }
}