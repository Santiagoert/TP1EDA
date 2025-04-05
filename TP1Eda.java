package tp1eda;

public class TP1Eda {
    public static void main(String[] args) {
        // ========== Ejemplo de uso de la Agenda ==========
        Agenda agenda = new Agenda();
          // qqqq
        // Agregar contactos
        agenda.agregar(new Contacto("Juan", "123456", "juan@mail.com"))
              .agregar(new Contacto("María", "654321", "maria@mail.com"))
              .agregar(new Contacto("Pedro", "987654", "pedro@mail.com"));

        // Listar contactos
        System.out.println("=== Lista de contactos ===");
        agenda.listar();

        // Buscar contacto
        System.out.println("\n=== Buscar contacto ===");
        Contacto buscado = agenda.buscar("María");
        System.out.println(buscado != null ? buscado : "No encontrado");

        // Borrar contacto
        System.out.println("\n=== Borrar contacto (tel: 654321) ===");
        agenda.borrar("654321");
        agenda.listar();

        // Contacto en posición
        System.out.println("\n=== Contacto en posición 1 ===");
        System.out.println(agenda.enPosicion(1));
    }
}

// ========== Clase Contacto ==========
class Contacto {
    private String nombre;
    private String telefono;
    private String email;

    public Contacto(String nombre, String telefono, String email) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format(
            "Nombre: %-10s | Tel: %-8s | Email: %s",
            nombre, telefono, email
        );
    }
}

// ========== Clase Agenda ==========
class Agenda {
    private ListaEnlazada contactos;

    public Agenda() {
        this.contactos = new ListaEnlazada();
    }

    // Agrega un contacto
    public Agenda agregar(Contacto c) {
        contactos.insertarAlFinal(c);
        return this;
    }

    // Busca por nombre
    public Contacto buscar(String nombre) {
        ListaEnlazada.Nodo temp = contactos.getCabeza();
        while (temp != null) {
            Contacto c = (Contacto) temp.dato;
            if (c.getNombre().equals(nombre)) return c;
            temp = temp.siguiente;
        }
        return null;
    }

    // Borra por teléfono
    public void borrar(String telefono) {
        ListaEnlazada.Nodo actual = contactos.getCabeza();
        ListaEnlazada.Nodo anterior = null;
        
        while (actual != null) {
            Contacto c = (Contacto) actual.dato;
            if (c.getTelefono().equals(telefono)) {
                if (anterior == null) {  // Es el primer nodo
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

    // Lista todos los contactos
    public void listar() {
        ListaEnlazada.Nodo temp = contactos.getCabeza();
        while (temp != null) {
            System.out.println(temp.dato);
            temp = temp.siguiente;
        }
    }

    // Cantidad de contactos
    public int cantidad() {
        return contactos.getTamaño();
    }

    // Contacto en posición específica
    public Contacto enPosicion(int posicion) {
        if (posicion < 0 || posicion >= contactos.getTamaño()) {
            throw new IndexOutOfBoundsException("Posición inválida");
        }
        
        ListaEnlazada.Nodo temp = contactos.getCabeza();
        for (int i = 0; i < posicion; i++) {
            temp = temp.siguiente;
        }
        return (Contacto) temp.dato;
    }
}

// ========== ListaEnlazada Modificada (para trabajar con objetos) ==========
class ListaEnlazada {
    public class Nodo {
        Object dato;  // Ahora almacena objetos
        Nodo siguiente;

        Nodo(Object dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private Nodo cabeza;
    private int tamaño;

    // Getters y Setters necesarios
    public Nodo getCabeza() { return cabeza; }
    public void setCabeza(Nodo cabeza) { this.cabeza = cabeza; }
    public int getTamaño() { return tamaño; }
    public void setTamaño(int tamaño) { this.tamaño = tamaño; }

    public boolean esVacia() {
        return cabeza == null;
    }

    public static ListaEnlazada crearLista() {
        return new ListaEnlazada();
    }

    public ListaEnlazada insertarAlInicio(Object x) {
        Nodo nuevo = new Nodo(x);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        tamaño++;
        return this;
    }

    public ListaEnlazada insertarAlFinal(Object x) {
        Nodo nuevo = new Nodo(x);
        if (esVacia()) {
            cabeza = nuevo;
        } else {
            Nodo temp = cabeza;
            while (temp.siguiente != null) {
                temp = temp.siguiente;
            }
            temp.siguiente = nuevo;
        }
        tamaño++;
        return this;
    }

    // Resto de métodos se mantienen similares, trabajando con Object
    // ...
}
