public class Triaje {
    public static void main(String[] args) {
        MinHeap<Paciente> colaPrioridad = new MinHeap<>();

        Paciente p1 = new Paciente("Juan", 2); // Urgente
        Paciente p2 = new Paciente("Maria", 1); // Critico
        Paciente p3 = new Paciente("Pedro", 3); // Leve
        Paciente p4 = new Paciente("Ana", 1); // Critico
        Paciente p5 = new Paciente("Luis", 2); // Urgente

        System.out.println("Insertando pacientes...");
        colaPrioridad.insertar(p1);
        colaPrioridad.insertar(p2);
        colaPrioridad.insertar(p3);
        colaPrioridad.insertar(p4);
        colaPrioridad.insertar(p5);

        System.out.println("\nAtendiendo pacientes (Orden de prioridad):");
        while (!colaPrioridad.esVacio()) {
            Paciente atendido = colaPrioridad.extraerMinimo();
            System.out.println(atendido);
        }
    }
}
