package message;

import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class GroupCreatResponseMessage extends AbstractResponseMessage{

    public String groupName;

    public String createName;

    public GroupCreatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupCreatResponseMessage(boolean success, String reason, String groupName, String createName) {
        super(success, reason);
        this.groupName = groupName;
        this.createName = createName;
    }

    public GroupCreatResponseMessage(String groupName, String createName) {
        this.groupName = groupName;
        this.createName = createName;
    }

    @Override
    public int getMessageType() {
        return GroupCreatResponseMessage;
    }
}
