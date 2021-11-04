package message;

import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class GroupQuitResponseMessage extends AbstractResponseMessage {

    public GroupQuitResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GroupQuitResponseMessage;
    }
}
