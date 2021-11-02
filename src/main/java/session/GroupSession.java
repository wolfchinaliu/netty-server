package session;

import java.util.Set;

/**
 * 聊天组会话接口
 */
public interface GroupSession {

    /**
     * 创建一个聊天室，如果不存在才能创建，否则返回null
     * @param name 组名
     * @param members 人员名称集合
     * @return 组的信息
     */
    Group createGroup(String name, Set<String> members);

    /**
     * 加入聊天组
     * @param name 组名
     * @param member 成员名
     * @return 如果组不存在则返回Null，否则返回组对象
     */
    Group joinGroup(String name, String member);

    /**
     * 退出群组
     * @param name 组名
     * @param member 成员名称
     * @return 如果成功返回组，否则返回Null
     */
    Group quitGroup(String name, String member);

    /**
     * 移除群组
     * @param name 组名
     * @return 如果组不存在返回Null，否则返回Group
     */
    Group removeGroup(String name);

    /**
     * 获取群组人员
     * @param name 组名
     * @return 群组名称集合
     */
    Set<String> getMembers(String name);
}
