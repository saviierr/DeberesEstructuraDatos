package Logistica;

public class RouteManager<T extends Package<?>> implements IContainer<T> {

    private Node<T> head;
    private Node<T> tail;
    private Node<T> current;

    public RouteManager() {
        head = tail = current = null;
    }

    // O(1)
    @Override
    public void add(T element) {
        Node<T> node = new Node<>(element);

        if (head == null) {
            head = tail = current = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    // O(n) por búsqueda (O(1) si hay referencia directa)
    @Override
    public boolean remove(String id) {
        Node<T> temp = head;

        while (temp != null) {
            if (temp.data.getId().equals(id)) {

                if (temp.prev != null)
                    temp.prev.next = temp.next;
                else
                    head = temp.next;

                if (temp.next != null)
                    temp.next.prev = temp.prev;
                else
                    tail = temp.prev;

                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    public T get(String id) {
        Node<T> temp = head;
        while (temp != null) {
            if (temp.data.getId().equals(id))
                return temp.data;
            temp = temp.next;
        }
        return null;
    }

    // Navegación O(1)
    public void moveNext() {
        if (current != null && current.next != null)
            current = current.next;
    }

    public void movePrev() {
        if (current != null && current.prev != null)
            current = current.prev;
    }

    public void printRoute() {
        Node<T> temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }
}
