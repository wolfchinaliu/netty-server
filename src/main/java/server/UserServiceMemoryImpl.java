package server;

import java.util.HashMap;
import java.util.Map;

public class UserServiceMemoryImpl implements UserService {
    private final Map<String, String> users = new HashMap<>();

    @Override
    public boolean login(String userName, String password) {
        String value = users.get(userName);
        if (value == null) {
            return false;
        }
        if (value.equalsIgnoreCase(password)) {
            return true;
        }
        return false;
    }

    public void registerUser(String name, String password) {
        users.put(name, password);
    }


}
