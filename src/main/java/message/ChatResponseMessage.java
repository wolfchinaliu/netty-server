package message;

import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class ChatResponseMessage extends AbstractResponseMessage {

    private String from;

    private String content;


    @Override
    public int getMessageType() {
        return ChatResponseMessage;
    }

    public ChatResponseMessage(String from, String content) {
        this.from = from;
        this.content = content;
    }

    public ChatResponseMessage() {

    }

    public ChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public String toString() {
        super.toString();
        return "ChatResponseMessage{" +
                "from='" + from + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
