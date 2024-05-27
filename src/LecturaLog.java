import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LecturaLog {
    private static final String FICHERO_LOG = "Linux_2k.log";
    private String regex = "(\\w{3}\\s\\d{2}\\s\\d{2}:\\d{2}:\\d{2})\\scombo\\ssshd.*rhost=(\\S+)";
    private HashMap<LocalDateTime, String> accesosSSH;

    public void leerFichero() {
        accesosSSH = new HashMap<>();
        Pattern pattern = Pattern.compile(regex);
        DateTimeFormatter formateadorFecha = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm:ss", Locale.ENGLISH);

        try (Scanner scanner = new Scanner(new File(FICHERO_LOG))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                Matcher matcher = pattern.matcher(linea);
                if (matcher.find()) {
                    String fechaTiempoStr = "2023 " + matcher.group(1);
                    LocalDateTime fechaTiempo = LocalDateTime.parse(fechaTiempoStr, formateadorFecha);
                    String ip = matcher.group(2);
                    accesosSSH.put(fechaTiempo, ip);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al leer el fichero");
        }
    }
}