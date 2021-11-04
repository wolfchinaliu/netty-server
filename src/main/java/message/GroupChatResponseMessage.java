package message;

import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class GroupChatResponseMessage extends AbstractResponseMessage {

    private String from;

    private String groupName;

    private String content;

    @Override
    public int getMessageType() {
        return ChatRequestMessage;
    }

    public GroupChatResponseMessage(String from, String groupName, String content) {
        this.from = from;
        this.groupName = groupName;
        this.content = content;
    }

    public GroupChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }
}
