package message;

import lombok.Data;

@Data
public class GroupChatRequestMessage extends Message {
    private String from;

    private String groupName;

    private String content;

    @Override
    public void setMessageType(int messageType) {
        super.setMessageType(messageType);
    }

    public GroupChatRequestMessage(String from, String groupName, String content) {
        this.from = from;
        this.content = content;
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return GroupChatRequestMessage;
    }

}
