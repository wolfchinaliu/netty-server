package session;


import io.netty.channel.Channel;

public interface Session {

    /**
     * 绑定会话
     * @param channel 那个channel要绑定会话
     * @param username 会话绑定用户名
     */
    void bind(Channel channel, String username);

    /**
     * 解绑会话
     * @param channel 那个channel要解绑会话
     */
    void unbind(Channel channel);

    /**
     * 获取属性
     * @param channel channel
     * @param name 名称
     * @return 属性
     */
    Object getAttribute(Channel channel, String name);

    /**
     * 设置属性
     * @param channel channel
     * @param name 属性名
     * @param value 值
     */
    void setAttribute(Channel channel, String name, Object value);

    /**
     * 通过用户名获取Channel
     * @param userName 用户名
     * @return channel
     */
    Channel getChannel(String userName);
}
