package session;

import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionMemoryImpl implements Session {

    private final Map<String, Channel> userNameChannelMap = new HashMap<>();

    private final Map<Channel, String> channelUserNameMap = new HashMap<>();

    private final Map<Channel, Map<String, Object>> channelAttributeMap = new HashMap<>();

    @Override
    public void bind(Channel channel, String username) {
        channelUserNameMap.put(channel, username);
        userNameChannelMap.put(username, channel);
        channelAttributeMap.put(channel, new ConcurrentHashMap<>());

    }

    @Override
    public void unbind(Channel channel) {
        String userName = channelUserNameMap.get(channel);
        userNameChannelMap.remove(userName);
        channelAttributeMap.remove(channel);
    }

    @Override
    public Object getAttribute(Channel channel, String name) {
        return channelAttributeMap.get(channel).get(name);
    }

    @Override
    public void setAttribute(Channel channel, String name, Object value) {
        Map<String, Object> attributeMap = channelAttributeMap.get(channel);
        attributeMap.put(name, value);
        channelAttributeMap.put(channel, attributeMap);
    }

    @Override
    public Channel getChannel(String userName) {
        return userNameChannelMap.get(userName);
    }

}
