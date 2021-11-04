package message;

import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class GroupJoinResponseMessage extends AbstractResponseMessage{

    public GroupJoinResponseMessage() {
    }

    public GroupJoinResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GroupJoinResponseMessage;
    }
}
