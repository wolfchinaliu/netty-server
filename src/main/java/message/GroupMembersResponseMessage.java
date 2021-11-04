package message;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

@ToString(callSuper = true)
@Data
public class GroupMembersResponseMessage extends AbstractResponseMessage {

    private Set<String> members;

    public GroupMembersResponseMessage(Set<String> members) {
        this.members = members;
    }

    public GroupMembersResponseMessage() {
    }

    public GroupMembersResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GroupMembersResponseMessage;
    }
}
