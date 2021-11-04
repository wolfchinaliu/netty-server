package handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.RPCRequestMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@ChannelHandler.Sharable
public class RPCRequestMessageHandler extends SimpleChannelInboundHandler<RPCRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RPCRequestMessage msg) throws Exception {

    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException {
        RPCRequestMessage rpcRequestMessage = new RPCRequestMessage(
                "rpc.service.HelloService",
                "sayHello",
                String.class,
                new Class[]{String.class},
                new Object[]{"你好，世界!"}
                );
        Class<?> targetClass = Class.forName(rpcRequestMessage.getInterfaceName());
        Object instance = targetClass.newInstance();
        Method method = instance.getClass().getMethod(rpcRequestMessage.getMethodName(), rpcRequestMessage.getParameterTypes());
        Object result = method.invoke(instance, rpcRequestMessage.getParameterValue());
        System.out.println(result);
    }
}
