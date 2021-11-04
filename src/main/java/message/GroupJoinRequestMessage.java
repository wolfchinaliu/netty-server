package message;

import lombok.Data;

@Data
public class GroupJoinRequestMessage extends Message{

    private String groupName;

    private String userName;

    public GroupJoinRequestMessage(String userName, String groupName) {
        this.userName = userName;
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return GroupJoinRequestMessage;
    }
}
