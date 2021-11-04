package message;

import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class LoginResponseMessage extends AbstractResponseMessage {

    @Override
    public int getMessageType() {
        return LoginResponseMessage;
    }

    public LoginResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public LoginResponseMessage() {
    }
}
