package session;

public class GroupSessionFactory {
    private static final GroupSessionMemoryImpl groupSessionMemory = new GroupSessionMemoryImpl();

    public static GroupSession getSession() {
        return groupSessionMemory;
    }
}
