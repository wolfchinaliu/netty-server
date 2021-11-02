package message;

import lombok.Data;

/**
 * @author wolf
 * 登录请求
 */
@Data
public class LoginRequestMessage extends Message{

    private String userName;

    private String password;

    private String email;

    public LoginRequestMessage(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginRequestMessage(){

    }

    @Override
    public int getMessageType() {
        return LoginRequestMessage;
    }


}
