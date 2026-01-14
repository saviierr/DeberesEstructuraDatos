package Logistica;

public class PrioritySorter {

    public static void insertionSort(Package<?>[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Package<?> key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j].getPriority() > key.getPriority()) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void selectionSort(Package<?>[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].getPriority() < arr[min].getPriority())
                    min = j;
            }
            Package<?> temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
    }
}
