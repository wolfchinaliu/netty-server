package message;

import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class RPCResponseMessage extends Message {
    /**
     * 返回值
     */
    private Object returnValue;

    /**
     * 异常值
     */
    private Exception exceptionValue;

    public RPCResponseMessage(Object returnValue, Exception exceptionValue) {
        this.returnValue = returnValue;
        this.exceptionValue = exceptionValue;
    }

    @Override
    public int getMessageType() {
        return RPCResponseMessage;
    }
}
