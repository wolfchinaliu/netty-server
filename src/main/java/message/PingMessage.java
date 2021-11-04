package message;

import lombok.Data;

@Data
public class PingMessage extends Message{


    @Override
    public int getMessageType() {
        return 14;
    }
}
