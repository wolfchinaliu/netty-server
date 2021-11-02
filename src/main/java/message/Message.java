package message;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义协议要素
 *
 * @author wolf
 */
@Data
public abstract class Message implements Serializable {

    //魔数：用来第一时间判定是否是无效数据包 类似于双方暗号

    //版本号：可以支持协议的升级

    //序列化算法：消息正文到底采用那种序列化反序列化方式，由此可以扩展，例如：json、protobuf、hessian、jdk

    //指令类型：是登录、注册、单聊、群聊....跟业务相关

    //请求序号：为双工通信，提供异步能力

    //正文长度

    //消息正文


    public static Class<?> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }


    private int sequenceId;

    private int messageType;

    public static final int LoginRequestMessage = 0;

    public static final int LoginResponseMessage = 1;

    public static final int ChatRequestMessage = 2;

    public static final int ChatResponseMessage = 3;

    public static final int GroupCreateRequestMessage = 4;

    public static final int GroupCreatResponseMessage = 5;

    public static final int GroupJoinRequestMessage = 6;

    public static final int GroupJoinResponseMessage = 7;

    public static final int GroupQuitRequestMessage = 8;

    public static final int GroupQuitResponseMessage = 9;

    public static final int GroupChatRequestMessage = 10;

    public static final int GroupChatResponseMessage = 11;

    public static final int GroupMembersRequestMessage = 12;

    public static final int GroupMembersResponseMessage = 13;

    private static final Map<Integer, Class<?>> messageClasses = new HashMap<>();

}
