package message;

import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class RPCRequestMessage extends Message{

    /**
     * 调用的接口全名限定名称，服务端根据它找到实现
     */
    private String interfaceName;


    /**
     * 方法名
     */
    private String methodName;

    /**
     * 返回值类型
     */
    private Class<?> returnType;


    /**
     * 方法的请求参数类型数组
     */
    private Class[] parameterTypes;

    /**
     * 方法的请求参数值
     */
    private Object[] parameterValue;

    public RPCRequestMessage(String interfaceName, String methodName, Class<?> returnType, Class[] parameterTypes, Object[] parameterValue) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.parameterValue = parameterValue;
    }

    @Override
    public int getMessageType() {
        return RPCRequestMessage;
    }
}
