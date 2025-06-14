import java.util.InputMismatchException;
import java.util.Scanner;

class Paciente {
    int id;
    String nombre;
    int urgencia; 
    String horaLlegada;
    Paciente siguiente;

    public Paciente(int id, String nombre, int urgencia, String horaLlegada) {
        this.id = id;
        this.nombre = nombre;
        this.urgencia = urgencia;
        this.horaLlegada = horaLlegada;
        this.siguiente = null;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: '" + nombre + '\'' +
               ", Urgencia: " + urgencia + ", Hora Llegada: " + horaLlegada;
    }
}

class ListaPacientes {
    private Paciente cabeza;
    private int contadorPacientes;

    public ListaPacientes() {
        this.cabeza = null;
        this.contadorPacientes = 0;
    }

    public boolean existeID(int id) {
        Paciente actual = cabeza;
        while (actual != null) {
            if (actual.id == id) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public void agregarPaciente(Paciente nuevoPaciente) {

        if (cabeza == null || nuevoPaciente.urgencia > cabeza.urgencia ||
            (nuevoPaciente.urgencia == cabeza.urgencia &&
             nuevoPaciente.horaLlegada.compareTo(cabeza.horaLlegada) < 0)) {
            nuevoPaciente.siguiente = cabeza;
            cabeza = nuevoPaciente;
        } else {
            Paciente actual = cabeza;

            while (actual.siguiente != null &&
                   (nuevoPaciente.urgencia < actual.siguiente.urgencia ||
                    (nuevoPaciente.urgencia == actual.siguiente.urgencia &&
                     nuevoPaciente.horaLlegada.compareTo(actual.siguiente.horaLlegada) >= 0))) {
                actual = actual.siguiente;
            }
            nuevoPaciente.siguiente = actual.siguiente;
            actual.siguiente = nuevoPaciente;
        }
        contadorPacientes++;
        System.out.println(" Paciente '" + nuevoPaciente.nombre + "' agregado a la lista de espera.");
    }


    public void mostrarPacientes() {
        if (cabeza == null) {
            System.out.println("ℹ No hay pacientes en espera.");
            return;
        }
        System.out.println("\n---  लिस्टा डे Pacientes en Espera (" + contadorPacientes + ") ---");
        Paciente actual = cabeza;
        int i = 1;
        while (actual != null) {
            System.out.println(i + ". " + actual.toString());
            actual = actual.siguiente;
            i++;
        }
        System.out.println("------------------------------------");
    }

    public void atenderSiguientePaciente() {
        if (cabeza == null) {
            System.out.println("ℹ No hay pacientes para atender.");
            return;
        }
        Paciente atendido = cabeza;
        cabeza = cabeza.siguiente;
        contadorPacientes--;
        System.out.println("🩺 Atendiendo al paciente: " + atendido.nombre + " (ID: " + atendido.id + ", Urgencia: " + atendido.urgencia + ")");
        System.out.println(" Paciente eliminado de la lista de espera.");
    }

    public Paciente buscarPacientePorID(int id) {
        Paciente actual = cabeza;
        while (actual != null) {
            if (actual.id == id) {
                return actual;
            }
            actual = actual.siguiente;
        }
        return null; 
    }

    public boolean eliminarPacientePorID(int id) {
        if (cabeza == null) {
            return false;
        }
        if (cabeza.id == id) {
            System.out.println(" Paciente '" + cabeza.nombre + "' (ID: " + cabeza.id + ") eliminado.");
            cabeza = cabeza.siguiente;
            contadorPacientes--;
            return true;
        }
        Paciente actual = cabeza;
        Paciente anterior = null;
        while (actual != null && actual.id != id) {
            anterior = actual;
            actual = actual.siguiente;
        }
        if (actual != null) { 
            if (anterior != null) { 
                anterior.siguiente = actual.siguiente;
                System.out.println(" Paciente '" + actual.nombre + "' (ID: " + actual.id + ") eliminado.");
                contadorPacientes--;
                return true;
            }
        }
        return false; 
    }

    public int getNumeroPacientesEnEspera() {
        return contadorPacientes;
    }
}


public class SalaEmergencias {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ListaPacientes listaDeEspera = new ListaPacientes();
        System.out.println(" Sistema de Gestión de Sala de Emergencias ");

        int opcion = -1;

        do {
            limpiarConsola();
            System.out.println("\n MENÚ DE SALA DE EMERGENCIAS ");
            System.out.println("1. Agregar un paciente a la lista");
            System.out.println("2. Mostrar todos los pacientes en espera");
            System.out.println("3. Atender (eliminar) al siguiente paciente de la lista");
            System.out.println("4. Buscar un paciente por ID");
            System.out.println("5. Eliminar un paciente por ID (dar de alta/cancelar)");
            System.out.println("6. Mostrar cuántos pacientes hay en espera");
            System.out.println("0. Salir del sistema");
            System.out.print("Seleccione una opción: ");

            try {
                String entrada = scanner.nextLine();
                if (entrada.trim().isEmpty()) {
                    System.out.println(" Opción no puede estar vacía. Intente de nuevo.");
                    opcion = -1; 
                    esperar(scanner);
                    continue;
                }
                opcion = Integer.parseInt(entrada);

                switch (opcion) {
                    case 1:
                        agregarPacienteMenu(scanner, listaDeEspera);
                        break;
                    case 2:
                        listaDeEspera.mostrarPacientes();
                        break;
                    case 3:
                        listaDeEspera.atenderSiguientePaciente();
                        break;
                    case 4:
                        buscarPacienteMenu(scanner, listaDeEspera);
                        break;
                    case 5:
                        eliminarPacienteMenu(scanner, listaDeEspera);
                        break;
                    case 6:
                        System.out.println("ℹ Pacientes actualmente en espera: " + listaDeEspera.getNumeroPacientesEnEspera());
                        break;
                    case 0:
                        System.out.println(" Saliendo del sistema. ¡Gracias!");
                        break;
                    default:
                        System.out.println(" Opción no válida. Por favor, intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Error: Entrada inválida para la opción del menú. Debe ingresar un número.");
                opcion = -1; 
            }

            if (opcion != 0) {
                esperar(scanner);
            }

        } while (opcion != 0);

        scanner.close();
    }

    private static void agregarPacienteMenu(Scanner scanner, ListaPacientes lista) {
        System.out.println("\n--- Agregar Nuevo Paciente ---");
        int id = -1;
        String nombre;
        int urgencia = 0;
        String horaLlegada;

        boolean idValido = false;
        while (!idValido) {
            try {
                System.out.print("ID del paciente (entero único): ");
                String entradaId = scanner.nextLine();
                if (entradaId.trim().isEmpty()){
                     System.out.println(" Error: El ID no puede estar vacío.");
                     continue;
                }
                id = Integer.parseInt(entradaId);
                if (lista.existeID(id)) {
                    System.out.println(" Error: El ID " + id + " ya existe. Intente con otro.");
                } else {
                    idValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println(" Error: ID inválido. Debe ser un número entero.");
            }
        }

        while (true) {
            System.out.print("Nombre del paciente: ");
            nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println(" Error: El nombre del paciente no puede estar vacío.");
            } else {
                break;
            }
        }
        boolean urgenciaValida = false;
        while (!urgenciaValida) {
            try {
                System.out.print("Nivel de urgencia (1=menor, 5=mayor): ");
                String entradaUrgencia = scanner.nextLine();
                 if (entradaUrgencia.trim().isEmpty()){
                     System.out.println(" Error: El nivel de urgencia no puede estar vacío.");
                     continue;
                }
                urgencia = Integer.parseInt(entradaUrgencia);
                if (urgencia >= 1 && urgencia <= 5) {
                    urgenciaValida = true;
                } else {
                    System.out.println(" Error: Nivel de urgencia debe estar entre 1 y 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Error: Nivel de urgencia inválido. Debe ser un número entero.");
            }
        }
        while (true) {
            System.out.print("Hora de llegada (formato HH:MM): ");
            horaLlegada = scanner.nextLine().trim();
            if (horaLlegada.matches("\\d{2}:\\d{2}")) { 
                String[] partesHora = horaLlegada.split(":");
                try {
                    int hh = Integer.parseInt(partesHora[0]);
                    int mm = Integer.parseInt(partesHora[1]);
                    if (hh >=0 && hh <=23 && mm >= 0 && mm <=59) {
                        break;
                    } else {
                        System.out.println(" Error: Hora o minutos fuera de rango (HH: 00-23, MM: 00-59).");
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                     System.out.println(" Error: Formato de hora incorrecto. Use HH:MM.");
                }
            } else {
                System.out.println(" Error: Formato de hora incorrecto. Use HH:MM (ej. 14:30).");
            }
        }

        Paciente nuevoPaciente = new Paciente(id, nombre, urgencia, horaLlegada);
        lista.agregarPaciente(nuevoPaciente);
    }

    private static void buscarPacienteMenu(Scanner scanner, ListaPacientes lista) {
        System.out.println("\n--- Buscar Paciente por ID ---");
        if (lista.getNumeroPacientesEnEspera() == 0) {
            System.out.println("ℹ La lista de espera está vacía.");
            return;
        }
        System.out.print("Ingrese el ID del paciente a buscar: ");
        try {
            String entradaId = scanner.nextLine();
            if (entradaId.trim().isEmpty()){
                System.out.println(" Error: El ID de búsqueda no puede estar vacío.");
                return;
            }
            int idBusqueda = Integer.parseInt(entradaId);
            Paciente encontrado = lista.buscarPacientePorID(idBusqueda);
            if (encontrado != null) {
                System.out.println(" Paciente encontrado:");
                System.out.println(encontrado.toString());
            } else {
                System.out.println(" Paciente con ID " + idBusqueda + " no encontrado en la lista de espera.");
            }
        } catch (NumberFormatException e) {
            System.out.println(" Error: ID de búsqueda inválido. Debe ser un número entero.");
        }
    }

    private static void eliminarPacienteMenu(Scanner scanner, ListaPacientes lista) {
        System.out.println("\n--- Eliminar Paciente por ID ---");
        if (lista.getNumeroPacientesEnEspera() == 0) {
            System.out.println("ℹ La lista de espera está vacía.");
            return;
        }
        System.out.print("Ingrese el ID del paciente a eliminar: ");
        try {
            String entradaId = scanner.nextLine();
             if (entradaId.trim().isEmpty()){
                System.out.println(" Error: El ID a eliminar no puede estar vacío.");
                return;
            }
            int idEliminar = Integer.parseInt(entradaId);
            if (!lista.eliminarPacientePorID(idEliminar)) {
                System.out.println(" Paciente con ID " + idEliminar + " no encontrado para eliminar.");
            }
        } catch (NumberFormatException e) {
            System.out.println(" Error: ID a eliminar inválido. Debe ser un número entero.");
        }
    }

    private static void esperar(Scanner scanner) {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    private static void limpiarConsola() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
        }
    }
}