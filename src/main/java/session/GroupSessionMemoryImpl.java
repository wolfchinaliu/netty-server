package session;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GroupSessionMemoryImpl implements GroupSession {

    private final Map<String, Group> groupMap = new HashMap<>();

    @Override
    public Group createGroup(String name, Set<String> members) {
        Group group = new Group();
        group.setName(name);
        group.setMembers(members);
        groupMap.put(name, group);
        return group;
    }

    @Override
    public Group joinGroup(String name, String member) {
        Group group = groupMap.get(name);
        if (group == null) {
            return null;
        }
        group.getMembers().add(member);
        return group;
    }

    @Override
    public Group quitGroup(String name, String member) {
        Group group = groupMap.get(name);
        if (group == null) {
            return null;
        }
        group.getMembers().remove(member);
        return group;
    }

    @Override
    public Group removeGroup(String name) {
        return groupMap.remove(name);
    }

    @Override
    public Set<String> getMembers(String name) {
        Group group = groupMap.get(name);
        if (group != null) {
            return group.getMembers();
        }
        return null;
    }
}
