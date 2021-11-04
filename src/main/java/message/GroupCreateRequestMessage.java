package message;

import lombok.Data;

import java.util.Set;

@Data
public class GroupCreateRequestMessage extends Message {

    private String groupName;

    private Set<String> members;

    public GroupCreateRequestMessage(String groupName, Set<String> members) {
        this.groupName = groupName;
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return GroupCreateRequestMessage;
    }
}
