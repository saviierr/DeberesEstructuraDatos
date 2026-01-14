package Logistica;

public class Main {

    public static void main(String[] args) {

        RouteManager<Package<String>> routes = new RouteManager<>();
        InventoryMap<String, Package<?>> inventory = new InventoryMap<>(10);

        Package<String> p1 = new Package<>("PKG1", "Laptop", Package.Type.FRAGILE, 1);
        Package<String> p2 = new Package<>("PKG2", "Books", Package.Type.STANDARD, 3);
        Package<String> p3 = new Package<>("PKG3", "Documents", Package.Type.EXPRESS, 2);

        routes.add(p1);
        routes.add(p2);
        routes.add(p3);

        inventory.put(p1.getId(), p1);
        inventory.put(p2.getId(), p2);
        inventory.put(p3.getId(), p3);

        System.out.println("=== Ruta de entrega ===");
        routes.printRoute();

        System.out.println("\n=== Busqueda rápida ===");
        System.out.println(inventory.get("PKG2"));

        Package<?>[] arr = { p1, p2, p3 };
        PrioritySorter.insertionSort(arr);

        System.out.println("\n=== Ordenado por prioridad ===");
        for (Package<?> p : arr)
            System.out.println(p);
    }
}
