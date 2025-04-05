package tp1eda;

public class TP1Eda {
    public static void main(String[] args) {
        ListaEnlazada lista = ListaEnlazada.crearLista();;
       
        lista.insertarAlFinal(10)
             .insertarAlFinal(20)
             .insertarAlFinal(30)
             .insertarAlInicio(5);

        System.out.println("Lista actual:");
        lista.mostrar();

        System.out.println("Primer elemento: " + lista.primerElemento());
        System.out.println("Cantidad de elementos: " + lista.cantidad());

        lista.borrarPrimero();
        System.out.println("Después de borrar el primer elemento:");
        lista.mostrar();

        lista.borrarUltimo();
        System.out.println("Después de borrar el último elemento:");
        lista.mostrar();

        System.out.println("¿El 20 pertenece a la lista? " + lista.pertenece(20));
        System.out.println("Valor en posición 1: " + lista.valorEnPosicion(1));

        lista.modificarValorEnPosicion(25, 1);
        System.out.println("Lista después de modificar la posición 1 con 25:");
        lista.mostrar();

        lista.insertarEnPosicion(15, 1);
        System.out.println("Lista después de insertar 15 en la posición 1:");
        lista.mostrar();
    }
}

class ListaEnlazada {
    private class Nodo {
        int dato;
        Nodo siguiente;

        Nodo(int dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private Nodo cabeza;
    private int tamaño;

    
    public boolean esVacia() {
        return cabeza == null;
    }
    public static ListaEnlazada crearLista() {
        return new ListaEnlazada();
    }

    public ListaEnlazada insertarAlInicio(int x) {
        Nodo nuevo = new Nodo(x);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        tamaño++;
        return this;
    }

    public ListaEnlazada insertarAlFinal(int x) {
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

    public void mostrar() {
        Nodo temp = cabeza;
        while (temp != null) {
            System.out.print(temp.dato + " -> ");
            temp = temp.siguiente;
        }
        System.out.println("null");
    }

    public int cantidad() {
        return tamaño;
    }

    public int primerElemento() {
        if (esVacia()) throw new RuntimeException("Lista vacía");
        return cabeza.dato;
    }

    public ListaEnlazada borrarPrimero() {
        if (!esVacia()) {
            cabeza = cabeza.siguiente;
            tamaño--;
        }
        return this;
    }

    public ListaEnlazada borrarUltimo() {
        if (esVacia()) return this;
        if (cabeza.siguiente == null) {
            cabeza = null;
        } else {
            Nodo temp = cabeza;
            while (temp.siguiente.siguiente != null) {
                temp = temp.siguiente;
            }
            temp.siguiente = null;
        }
        tamaño--;
        return this;
    }

    public boolean pertenece(int buscado) {
        Nodo temp = cabeza;
        while (temp != null) {
            if (temp.dato == buscado) return true;
            temp = temp.siguiente;
        }
        return false;
    }

    public ListaEnlazada borrarConValor(int buscado) {
        if (esVacia()) return this;
        while (cabeza != null && cabeza.dato == buscado) {
            cabeza = cabeza.siguiente;
            tamaño--;
        }
        Nodo temp = cabeza;
        while (temp != null && temp.siguiente != null) {
            if (temp.siguiente.dato == buscado) {
                temp.siguiente = temp.siguiente.siguiente;
                tamaño--;
            } else {
                temp = temp.siguiente;
            }
        }
        return this;
    }

    public int valorEnPosicion(int posicion) {
        if (posicion < 0 || posicion >= tamaño) throw new IndexOutOfBoundsException("Índice fuera de rango");
        Nodo temp = cabeza;
        for (int i = 0; i < posicion; i++) {
            temp = temp.siguiente;
        }
        return temp.dato;
    }

    public ListaEnlazada modificarValorEnPosicion(int valor, int posicion) {
        if (posicion < 0 || posicion >= tamaño) throw new IndexOutOfBoundsException("Índice fuera de rango");
        Nodo temp = cabeza;
        for (int i = 0; i < posicion; i++) {
            temp = temp.siguiente;
        }
        temp.dato = valor;
        return this;
    }

    public ListaEnlazada insertarEnPosicion(int valor, int posicion) {
        if (posicion < 0 || posicion > tamaño) throw new IndexOutOfBoundsException("Índice fuera de rango");
        if (posicion == 0) return insertarAlInicio(valor);
        Nodo nuevo = new Nodo(valor);
        Nodo temp = cabeza;
        for (int i = 0; i < posicion - 1; i++) {
            temp = temp.siguiente;
        }
        nuevo.siguiente = temp.siguiente;
        temp.siguiente = nuevo;
        tamaño++;
        return this;
    }
}
