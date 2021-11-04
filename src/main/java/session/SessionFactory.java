package session;

public class SessionFactory {
    private static final SessionMemoryImpl sessionMemory = new SessionMemoryImpl();
    public static Session getSession(){
        return sessionMemory;
    }
}
