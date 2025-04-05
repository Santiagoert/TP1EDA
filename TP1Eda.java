package tp1eda;

public class TP1Eda {
    public static void main(String[] args) {
        // ========== Ejemplo de uso de Agenda ==========
        Agenda agendaPersonal = new Agenda();
        Agenda agendaTrabajo = new Agenda();

        // Agregar contactos a agenda personal
        agendaPersonal.agregar(new Contacto("Juan", "123456", "juan@personal.com"))
                      .agregar(new Contacto("María", "654321", "maria@personal.com"));

        // Agregar contactos a agenda trabajo
        agendaTrabajo.agregar(new Contacto("María", "987654", "maria@trabajo.com"))
                     .agregar(new Contacto("Pedro", "456123", "pedro@trabajo.com"));

        System.out.println("=== Agenda Personal ===");
        agendaPersonal.listar();
        
        System.out.println("\n=== Agenda Trabajo ===");
        agendaTrabajo.listar();

        // Combinar agendas
        combinarAgendas(agendaPersonal, agendaTrabajo);
        
        System.out.println("\n=== Agenda Combinada ===");
        agendaPersonal.listar();
    }

    public static void combinarAgendas(Agenda destino, Agenda fuente) {
        int total = fuente.cantidad();
        for(int i = 0; i < total; i++) {
            Contacto actual = fuente.enPosicion(i);
            if(destino.buscar(actual.getNombre()) == null) {
                destino.agregar(actual);
            }
        }
    }
}

class Contacto {
    private String nombre;
    private String telefono;
    private String email;

    public Contacto(String nombre, String telefono, String email) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format(
            "%-10s | Tel: %-8s | Email: %s", 
            nombre, telefono, email
        );
    }
}

class Agenda {
    private ListaEnlazada contactos;

    public Agenda() {
        this.contactos = new ListaEnlazada();
    }

    public Agenda agregar(Contacto c) {
        contactos.insertarAlFinal(c);
        return this;
    }

    public Contacto buscar(String nombre) {
        ListaEnlazada.Nodo temp = contactos.getCabeza();
        while(temp != null) {
            Contacto c = (Contacto) temp.dato;
            if(c.getNombre().equalsIgnoreCase(nombre)) return c;
            temp = temp.siguiente;
        }
        return null;
    }

    public void borrar(String telefono) {
        ListaEnlazada.Nodo actual = contactos.getCabeza();
        ListaEnlazada.Nodo anterior = null;
        
        while(actual != null) {
            Contacto c = (Contacto) actual.dato;
            if(c.getTelefono().equals(telefono)) {
                if(anterior == null) {
                    contactos.setCabeza(actual.siguiente);
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                contactos.setTamaño(contactos.getTamaño() - 1);
                return;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
    }

    public void listar() {
        ListaEnlazada.Nodo temp = contactos.getCabeza();
        while(temp != null) {
            System.out.println(temp.dato);
            temp = temp.siguiente;
        }
    }

    public int cantidad() {
        return contactos.getTamaño();
    }

    public Contacto enPosicion(int pos) {
        if(pos < 0 || pos >= contactos.getTamaño()) {
            throw new IndexOutOfBoundsException("Posición inválida: " + pos);
        }
        
        ListaEnlazada.Nodo temp = contactos.getCabeza();
        for(int i = 0; i < pos; i++) temp = temp.siguiente;
        return (Contacto) temp.dato;
    }
}

class ListaEnlazada {
    class Nodo {
        Object dato;
        Nodo siguiente;

        Nodo(Object dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private Nodo cabeza;
    private int tamaño;

    public Nodo getCabeza() { return cabeza; }
    public void setCabeza(Nodo cabeza) { this.cabeza = cabeza; }
    public int getTamaño() { return tamaño; }
    public void setTamaño(int t) { tamaño = t; }

    public ListaEnlazada insertarAlFinal(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if(cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo temp = cabeza;
            while(temp.siguiente != null) temp = temp.siguiente;
            temp.siguiente = nuevo;
        }
        tamaño++;
        return this;
    }

    public ListaEnlazada insertarAlInicio(Object dato) {
        Nodo nuevo = new Nodo(dato);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        tamaño++;
        return this;
      }
}
