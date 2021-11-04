package message;

import lombok.Data;

@Data
public class GroupQuitRequestMessage extends Message{

    private String userName;

    private String groupName;

    public GroupQuitRequestMessage(String userName, String groupName) {
        this.userName = userName;
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return GroupQuitRequestMessage;
    }
}
