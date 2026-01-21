// import java.util.Scanner;

// public class BonoDesarrolloHumano {

// public static void main(String[] args) {
// Scanner scanner = new Scanner(System.in);
// double monto = 0.0;

// monto = 55.00;
// System.out.println("Monto base asignado: $" + monto);

// int numeroDependientes = -1;
// while (numeroDependientes < 0) {
// System.out.println("Ingrese el numero de dependientes:");
// if (scanner.hasNextInt()) {
// numeroDependientes = scanner.nextInt();
// if (numeroDependientes < 0) {
// System.out.println("Ingrese un nuevo numero de dependientes");
// scanner.next();
// }
// }
// }

// boolean ZonaRural = false;
// String respuesta;
// do {
// System.out.println("La famlia vive en zona rural");
// }
// scanner.close();
// }
// }

public class CalcularBase {
    private static double MONTO_BASE = 55.0;
    private static double MONTO_DEPENDIENTE = 15.0;
    private static double FACTOR_RURAL = 1.15;

    public static double calculo(int dependientes, boolean esRural) {
        double monto = MONTO_BASE;

        for (int i = 0; i < dependientes; i++) {
            monto += MONTO_DEPENDIENTE;

        }
        if (esRural) {
            monto = monto * FACTOR_RURAL;
        }
        return monto;
        // return esRural ? monto * FACTOR_RURAL : monto;
    }

    public static void main(String[] args) {
        System.out.println("Caso 1: Zona Urbana $" + calculo(0, false));
        System.out.println("Caso 2 Alddrin: Zona Urbana $" + calculo(3, false));
        System.out.println("Caso 3 Salinas: Zona Urbana $" + calculo(7, true));
    }
}