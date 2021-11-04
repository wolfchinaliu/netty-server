package message;

import lombok.Data;

@Data
public class GroupMemberRequestMessage extends Message{

    private String groupName;

    public GroupMemberRequestMessage(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return GroupMembersRequestMessage;
    }
}
