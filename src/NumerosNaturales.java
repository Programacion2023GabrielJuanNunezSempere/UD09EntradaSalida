import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NumerosNaturales {
    private List<Integer> listaNum;
    private static final String FICHERO = "numNaturales.txt";

    public NumerosNaturales() {
        listaNum = new ArrayList<>();
    }

    public void leerNumerosUsuario() {
        Scanner scanner = new Scanner(System.in);
        int numero;
        System.out.println("Introduce numeros naturales o -1 para terminar");
        while (true) {
            numero = scanner.nextInt();
            if (numero == -1) {
                break;
            }
            if (numero > 0) {
                listaNum.add(numero);
            } else {
                System.out.println("Solo se permiten numeros positivos");
            }
        }
    }
}