# gestion-pacientes-emergencias-java
**Implementar "Agregar Paciente".**
public void agregarPaciente(Paciente nuevoPaciente) {

       if (cabeza == null || nuevoPaciente.urgencia > cabeza.urgencia ||
            (nuevoPaciente.urgencia == cabeza.urgencia 
             nuevoPaciente.horaLlegada.compareTo(cabeza.horaLlegada) < 0)) {
            nuevoPaciente.siguiente = cabeza;
            cabeza = nuevoPaciente;
        } else {
            Paciente actual = cabeza;
            
            System.out.println("\n MENÚ DE SALA DE EMERGENCIAS ");
            System.out.println("1. Agregar un paciente a la lista");
            
 **Implementar "Mostrar Todos los Pacientes".**
 public void mostrarPacientes() {
        if (cabeza == null) {
            System.out.println("ℹ No hay pacientes en espera.");
            return;
        }
        System.out.println("\n---   Pacientes en Espera (" + contadorPacientes + ") ---");
        Paciente actual = cabeza;
        int i = 1;
        while (actual != null) {
            System.out.println(i + ". " + actual.toString());
            actual = actual.siguiente;
            i++;
        }
        System.out.println("------------------------------------");

**Implementar Atender siguiente paciente**
if (cabeza == null) {
            System.out.println("ℹ No hay pacientes para atender.");
            return;
        }
        Paciente atendido = cabeza;
        cabeza = cabeza.siguiente;
        contadorPacientes--;
        System.out.println(" Atendiendo al paciente: " + atendido.nombre + " (ID: " + atendido.id + ", Urgencia: " + atendido.urgencia + ")");
        System.out.println(" Paciente eliminado de la lista de espera.");

**Implementar Buscar paciente**
public Paciente buscarPacientePorID(int id) {
        Paciente actual = cabeza;
        while (actual != null) {
            if (actual.id == id) {
                return actual;
            }
            actual = actual.siguiente;
        }
        return null; 

**Implementar Eliminar paciente por ID**
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

**Implementar Numero pacientes en espera**
 public int getNumeroPacientesEnEspera() {
        return contadorPacientes;
    }
}
