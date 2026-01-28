import java.util.ArrayList;
import java.util.List;

public class MinHeap<T extends Comparable<T>> {
    private List<T> heap;

    public MinHeap() {
        this.heap = new ArrayList<>();
    }

    public void insertar(T element) {
        heap.add(element);
        flotarArriba(heap.size() - 1);
    }

    public T extraerMinimo() {
        if (heap.isEmpty())
            return null;

        if (heap.size() == 1)
            return heap.remove(0);

        T raiz = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));
        flotarAbajo(0);
        return raiz;
    }

    public boolean esVacio() {
        return heap.isEmpty();
    }

    public void flotarArriba(int index) {
        int parentIndex = (index - 1) / 2;

        if (index > 0 && heap.get(index).compareTo(heap.get(parentIndex)) < 0) {
            intercambiar(index, parentIndex);
            flotarArriba(parentIndex);
        }
    }

    public void flotarAbajo(int index) {
        int pequenio = index;
        int hijoIzq = (2 * index) + 1;
        int hijoDer = (2 * index) + 2;
        int size = heap.size();

        if (hijoIzq < size && heap.get(hijoIzq).compareTo(heap.get(pequenio)) < 0) {
            pequenio = hijoIzq;
        }

        if (hijoDer < size && heap.get(hijoDer).compareTo(heap.get(pequenio)) < 0) {
            pequenio = hijoDer;
        }

        if (pequenio != index) {
            intercambiar(index, pequenio);
            flotarAbajo(pequenio);
        }
    }

    public void intercambiar(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}