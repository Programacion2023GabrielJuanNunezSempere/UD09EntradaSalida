package T09Tarea2;

import java.io.*;
import java.util.*;

public class Agenda {
    private List<Contacto> contactos;
    private static final String FICHERO_AGENDA = "agenda.dat";

    public Agenda() {
        contactos = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(FICHERO_AGENDA, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                contactos.add(Contacto.leerContacto(raf));
            }
        } catch (IOException e) {
            System.err.println("Error al leer el fichero");
        }
    }

    public void creaContacto(Contacto contacto) throws IOException {
        if (contactos.indexOf(contacto) == -1) {
            contactos.add(contacto);
            try (RandomAccessFile raf = new RandomAccessFile(FICHERO_AGENDA, "rw")) {
                raf.seek(raf.length());
                Contacto.escribirContacto(raf, contacto);
            }
        }
    }

    public void modificaContacto(Contacto contacto) throws IOException {
        int index = contactos.indexOf(contacto);
        if (index != -1) {
            contactos.set(index, contacto);
            try (RandomAccessFile raf = new RandomAccessFile(FICHERO_AGENDA, "rw")) {
                Contacto.escribirContacto(raf, contacto, index);
            }
        }
    }

    public void borrarContacto(Contacto contacto) throws IOException {
        int index = contactos.indexOf(contacto);
        if (index != -1) {
            contactos.remove(index);
            try (RandomAccessFile raf = new RandomAccessFile(FICHERO_AGENDA, "rw")) {
                Contacto contactoBorrado = new Contacto(contacto.getId(), "xxxxxxxx", "xxxxxxxx", "xxxxxxxx");
                Contacto.escribirContacto(raf, contactoBorrado, index);
            }
        }
    }

    public Contacto buscarContacto(int id) {
        for (Contacto contacto : contactos) {
            if (contacto.getId() == id) {
                return contacto;
            }
        }
        return null;
    }

    public void ordenarAgenda() throws IOException {
        Collections.sort(contactos);
        try (RandomAccessFile raf = new RandomAccessFile(FICHERO_AGENDA, "rw")) {
            for (Contacto contacto : contactos) {
                Contacto.escribirContacto(raf, contacto);
            }
        }
    }

    public void mostrarAgenda() {
        for (Contacto contacto : contactos) {
            System.out.println(contacto);
        }
    }

    public void mostrarCoincidencias(String nombre) {
        contactos.stream()
                .filter(contacto -> contacto.getNombre().contains(nombre))
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        while (!salir) {
            System.out.println("1. Mostrar Agenda\n2. Ordenar Agenda\n3. Crear Contacto\n4. Modifica Contacto\n5. Buscar\n6. Salir");
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    agenda.mostrarAgenda();
                    break;
                case 2:
                    try {
                        agenda.ordenarAgenda();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("Enter id, nombre, direccion, telefono for new Contacto:");
                    int id = scanner.nextInt();
                    String nombre = scanner.next();
                    String direccion = scanner.next();
                    String telefono = scanner.next();
                    Contacto newContacto = new Contacto(id, nombre, direccion, telefono);
                    try {
                        agenda.creaContacto(newContacto);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    System.out.println("Enter id, nombre, direccion, telefono for Contacto to modify:");
                    int modId = scanner.nextInt();
                    String modNombre = scanner.next();
                    String modDireccion = scanner.next();
                    String modTelefono = scanner.next();
                    Contacto modContacto = new Contacto(modId, modNombre, modDireccion, modTelefono);
                    try {
                        agenda.modificaContacto(modContacto);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    System.out.println("Enter id to search for Contacto:");
                    int searchId = scanner.nextInt();
                    Contacto foundContacto = agenda.buscarContacto(searchId);
                    if (foundContacto != null) {
                        System.out.println("Found Contacto: " + foundContacto);
                    } else {
                        System.out.println("No Contacto found with id: " + searchId);
                    }
                    break;
                case 6:
                    salir = true;
                    break;
            }
        }
    }
}
