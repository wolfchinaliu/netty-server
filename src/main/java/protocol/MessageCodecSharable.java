package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import message.Message;

import java.util.List;

@ChannelHandler.Sharable
@Slf4j
@Data
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outList) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        //写入魔术
        out.writeBytes(new byte[]{1, 2, 3, 4});
        //版本
        out.writeByte(1);
        //序列化算法 约定0就是JDK序列化 1表示Json
        out.writeByte(0);
        //指定类型 1个字节
        out.writeByte(msg.getMessageType());
        //请求序号 4个字节
        out.writeInt(msg.getSequenceId());
        //对齐填充 一个字节
        out.writeByte(0xff);
        //获取内容的字节数组
        byte[] bytes = Serializer.Algorithm.Json.serialize(msg);
        //写入长度 一个字节
        out.writeInt(bytes.length);
        //写入内容
        out.writeBytes(bytes);
        outList.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> outList) throws Exception {
        //解码
        //魔数
        int magicNum = in.readInt();
        //版本
        byte version = in.readByte();
        //序列化算法
        int serializerType = in.readByte();
        //消息类型
        byte messageType = in.readByte();
        //序列号
        int sequenceId = in.readInt();
        //无意义的
        in.readByte();
        //正文长度
        int length = in.readInt();
        //缓冲区
        byte[] bytes = new byte[length];
        //读取内容
        in.readBytes(bytes, 0, length);
        //判断序列化类型
        Class<?> messageClass = Message.getMessageClass(messageType);
        Object result;
        switch (serializerType) {
            case 0:
                result = Serializer.Algorithm.Json.deserialize(messageClass, bytes);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + serializerType);
        }
        outList.add(result);
    }
}
