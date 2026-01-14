package Logistica;

public interface IContainer<T> {
    void add(T element);

    boolean remove(String id);

    T get(String id);
}
