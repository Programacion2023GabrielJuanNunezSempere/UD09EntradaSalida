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
        File file = new File(FICHERO);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextInt()) {
                    listaNum.add(scanner.nextInt());
                }
            } catch (FileNotFoundException e) {
                System.err.println("Error al leer el fichero");
            }
        }
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

    public void guardarLista() {
        try (PrintWriter writer = new PrintWriter(new File(FICHERO))) {
            for (int num : listaNum) {
                writer.println(num);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al guardar la lista en el fichero");
        }
    }

    public void mostrarList() {
        listaNum.stream().forEach(System.out::println);
    }

    public double media() {
        return listaNum.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    public int max() {
        return listaNum.stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(Integer.MIN_VALUE);
    }
}