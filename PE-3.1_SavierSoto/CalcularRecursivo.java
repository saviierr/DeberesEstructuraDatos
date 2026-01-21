public class CalcularRecursivo {

    private static double MONTO_BASE = 55.0;
    private static double MONTO_DEPENDIENTE = 15.0;
    private static double FACTOR_RURAL = 1.15;

    public static double calcular(int dependientes, boolean esRural) {

        // Caso base (nunca se alcanza)
        if (dependientes == 0) {
            return esRural ? MONTO_BASE * FACTOR_RURAL : MONTO_BASE;
        }

        // no se reduce el numero de dependientes
        double montoAnterior = calcular(dependientes, esRural);
        double adicional = esRural ? MONTO_DEPENDIENTE * FACTOR_RURAL : MONTO_DEPENDIENTE;

        return montoAnterior + adicional;
    }

    public static void main(String[] args) {

        try {
            System.out.println("Intentando calcular...");
            System.out.println(calcular(3, false));
        } catch (StackOverflowError e) {
            System.out.println("ERROR: StackOverflowError detectado");
            System.out.println("La recursion nunca termino");
        }
    }
}
