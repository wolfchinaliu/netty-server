package message;

import lombok.Data;

@Data
public class ChatRequestMessage extends Message{

    private String from;

    private String to;

    private String content;

    public ChatRequestMessage(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }


    @Override
    public int getMessageType() {
        return ChatRequestMessage;
    }
}
