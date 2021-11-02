package server;

public interface UserService {
    /**
     * 登录方法
     * @param userName 用户名
     * @param password 密码
     * @return 是否登录
     */
    boolean login(String userName, String password);

}
