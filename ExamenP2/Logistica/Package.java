package Logistica;

public class Package<T> {

    public enum Type {
        FRAGILE, STANDARD, EXPRESS
    }

    private String id;
    private T content;
    private Type type;
    private int priority;

    public Package(String id, T content, Type type, int priority) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Package " + id + " [" + type + "] Priority=" + priority;
    }
}
