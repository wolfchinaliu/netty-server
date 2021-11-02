package server;

public class UserServiceFactory {



    public static UserService getUserServer(){
        UserServiceMemoryImpl userServiceMemory = new UserServiceMemoryImpl();
        userServiceMemory.registerUser("张三", "123456");
        userServiceMemory.registerUser("李四", "123456");
        userServiceMemory.registerUser("王五", "123456");
        userServiceMemory.registerUser("赵六", "000000");
        return userServiceMemory;
    }
}
