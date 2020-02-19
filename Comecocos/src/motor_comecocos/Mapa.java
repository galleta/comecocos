package motor_comecocos;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Mapa del juego
 */
public class Mapa {
    private ArrayList<String> mapa; // Representa el mapa
    public static final int PUNTO_CHICO = 1;
    public static final int PUNTO_GRANDE = 2;
    private int max_puntos = 0;

    private EstadoJuego estado_juego;

    public Mapa(EstadoJuego estado) {
        this.estado_juego = estado;

        mapa = new ArrayList<String>();
        // Copiamos el mapa en el original y el que se modifica
        mapa.addAll(Arrays.asList(estado_juego.datos_mapa.mapa));
        this.calcularNumPuntos();
    }

    // Devuelve la casilla
    public char get(int x, int y) {
        String fila = mapa.get(y);
        char casilla = fila.charAt(x);
        if (casilla == '.' || casilla == 'o') {
            if (estado_juego.puntos_comidos.contains(new Point(x, y))) {
                casilla = ' ';
            }
        }
        return casilla;
    }

    public int getAlto() {
        return this.estado_juego.datos_mapa.alto;
    }

    public int getAncho() {
        return this.estado_juego.datos_mapa.ancho;
    }

    // Indica si se puede comer un punto en una posicion
    public boolean puedeComerPunto(Point posicion) {
        return (this.get(posicion.x, posicion.y) == '.' || this.get(posicion.x, posicion.y) == 'o');
    }

    // Come un punto de una posicion
    public int comerPunto(Point posicion) {
        if (this.get(posicion.x, posicion.y) == '.') {
            this.estado_juego.puntos_comidos.add(new Point(posicion));
            return PUNTO_CHICO;
        } else if (this.get(posicion.x, posicion.y) == 'o') {
            this.estado_juego.puntos_comidos.add(new Point(posicion));
            return PUNTO_GRANDE;
        }
        return 0;
    }

    public int getCocosRestantes() {
        return this.max_puntos - this.estado_juego.puntos_comidos.size();
    }

    // Indica si se puede mover a una casilla
    public boolean puedeMover(Point posicion) {
        Point nueva_posicion = new Point(posicion);
        // Filtro para los bordes
        if (nueva_posicion.x < 0) {
            nueva_posicion.x = this.getAncho() - 1;
        } else if (nueva_posicion.x > this.getAncho() - 1) {
            nueva_posicion.x = 0;
        } else if (nueva_posicion.y < 0) {
            nueva_posicion.y = this.getAlto() - 1;
        } else if (nueva_posicion.y > this.getAlto() - 1) {
            nueva_posicion.y = 0;
        }
        char casilla = this.get(nueva_posicion.x, nueva_posicion.y);
        if (casilla == ' ' || casilla == '.' || casilla == 'o') {
            return true;
        }
        return false;
    }

    // Indica si puede cambiar de direccion de una casilla a otra
    public boolean puedeCambiarDireccion(int direccion, Point posicion) {
        boolean cambiar_direccion = false;
        Point posicion_nueva = new Point(posicion);
        switch (direccion) {
            case Bicho.IZQUIERDA:
                posicion_nueva.x--;
                cambiar_direccion = this.puedeMover(posicion_nueva);
                break;
            case Bicho.DERECHA:
                posicion_nueva.x++;
                cambiar_direccion = this.puedeMover(posicion_nueva);
                break;
            case Bicho.ARRIBA:
                posicion_nueva.y--;
                cambiar_direccion = this.puedeMover(posicion_nueva);
                break;
            case Bicho.ABAJO:
                posicion_nueva.y++;
                cambiar_direccion = this.puedeMover(posicion_nueva);
                break;
        }
        return cambiar_direccion;
    }

    // Calcula el numero de puntos del mapa
    private void calcularNumPuntos() {
        this.max_puntos = 0;
        ListIterator<String> filas = this.mapa.listIterator();
        while (filas.hasNext()) {
            String fila = filas.next();
            for (int j = 0; j < fila.length(); j++) {
                if (fila.charAt(j) == '.' || fila.charAt(j) == 'o') {
                    this.max_puntos++;
                }
            }
        }
    }

    void setPosicionInicial(Bicho b, int indice) {
       b.setPos(new Point(this.estado_juego.datos_mapa.posiciones_iniciales.get(indice)));
    }

    // Indica si un fantasma ha salido de la caja
    public boolean haSalido(Fantasma f, int indice) {
        return f.getPos().equals(this.estado_juego.datos_mapa.posiciones_salida.get(indice));
    }

    Point getPosicionSalida(int indice_bicho) {
        return this.estado_juego.datos_mapa.posiciones_salida.get(indice_bicho);
    }

    // Indica si un fantasma ha entrado en la caja
    boolean haEntrado(Fantasma f, int indice) {
        return f.getPos().equals(this.estado_juego.datos_mapa.posiciones_iniciales.get(indice + 2));
    }

    // ** //
    // A* //
    // ** //

    // Clase que representa los pesos de una casilla
    private class Casilla {
        public int x;
        public int y;
        private Casilla parent = null; // Casilla anterior
        private double coste_local; // Coste desde esta casilla al destino
        private double coste_padre; // Coste desde el padre hasta esta casilla
        private Set<Casilla> adyacentes = new HashSet<Casilla>(); // Casillas adyacentes

        private Casilla(Point origen) {
            x = origen.x;
            y = origen.y;
        }

        // Indica las casillas adyacentes a esta casilla a las que se pueda mover
        public void configurarAdyacencias(ArrayList<ArrayList<Casilla>> casillas_mapa, Mapa mapa) {
            this.adyacentes = new HashSet<Casilla>();

            if (x + 1 < mapa.getAncho()) {
                if (mapa.puedeMover(new Point(x + 1, y))) {
                    adyacentes.add(casillas_mapa.get(x + 1).get(y));
                }
            }
            if (x - 1 >= 0) {
                if (mapa.puedeMover(new Point(x - 1, y))) {
                    adyacentes.add(casillas_mapa.get(x - 1).get(y));
                }
            }
            if (y + 1 < mapa.getAlto()) {
                if (mapa.puedeMover(new Point(x, y + 1))) {
                    adyacentes.add(casillas_mapa.get(x).get(y + 1));
                }
            }
            if (y - 1 >= 0) {
                if (mapa.puedeMover(new Point(x, y - 1))) {
                    adyacentes.add(casillas_mapa.get(x).get(y - 1));
                }
            }
        }

        public Set<Casilla> obtenerAdyacencias() {
            return adyacentes;
        }

        private void setPadre(Casilla origen) {
            this.parent = origen;
        }

        public double getPasoATraves(Casilla origen, Casilla destino) {
            if (this == origen) {
                return 0.0;
            }

            return getCosteLocal(origen, destino) + getCostePadre(origen);
        }

        // Devuelve el coste de ir de una casilla origen a una casilla destino
        public double getCosteLocal(Casilla origen, Casilla destino) {

            if (this == origen) {
                return 0.0;
            }

            coste_local = 1.0 * (Math.abs(x - destino.x) + Math.abs(y - destino.y));
            return coste_local;
        }

        public double getCostePadre(Casilla origen) {

            if (this == origen) {
                return 0.0;
            }

            if (coste_padre == 0.0) {
                coste_padre = 1.0 + .5 * (parent.getCostePadre(origen) - 1.0);
            }

            return coste_padre;
        }
    }

    // Candidatos por comprobar y eliminados
    private List<Casilla> abiertos = new ArrayList<Casilla>();
    private List<Casilla> cerrados = new ArrayList<Casilla>();

    // Obtiene la ruta de direcciones mas eficiente para llegar al comecocos
    public ArrayList<Integer> obtenerRuta(Point orig, Point dest) {
        // Inicializacion de los conjuntos
        abiertos = new ArrayList<Casilla>();
        cerrados = new ArrayList<Casilla>();
        ArrayList<ArrayList<Casilla>> casillas_mapa = new ArrayList<ArrayList<Casilla>>();
        for (int i = 0; i < this.getAncho(); i++) {
            ArrayList<Casilla> p = new ArrayList();
            for (int j = 0; j < this.getAlto(); j++) {
                p.add(new Casilla(new Point(i, j)));
            }
            casillas_mapa.add(p);
        }

        Casilla origen = new Casilla(orig);
        Casilla destino = new Casilla(dest);
        casillas_mapa.get(origen.x).set(origen.y, origen);
        casillas_mapa.get(destino.x).set(destino.y, destino);


        for (int i = 0; i < this.getAncho(); i++) {
            for (int j = 0; j < this.getAlto(); j++) {
                casillas_mapa.get(i).get(j).configurarAdyacencias(casillas_mapa, this);
            }
        }

        ArrayList<Integer> ruta = new ArrayList<Integer>();
        Set<Casilla> adyacencias = origen.obtenerAdyacencias();

        for (Casilla adyacencia : adyacencias) {
            adyacencia.setPadre(origen);
            if (adyacencia != origen) {
                abiertos.add(adyacencia);
            }
        }

        // Comienzo del algoritmo
        while (abiertos.size() > 0) {
            // Obtenemos la mejor casilla siguiente
            Casilla best = null;

            for (Casilla casilla : abiertos) {
                if (best == null || casilla.getPasoATraves(origen, destino) < best.getPasoATraves(origen, destino)) {
                    best = casilla;
                }
            }

            abiertos.remove(best);
            cerrados.add(best);
            // Comprobar si el mejor es el final
            if (best == destino) {
                Casilla c = destino;
                boolean fin = false;
                // Ordenamos las casillas en un arraylist
                ArrayList<Casilla> casillas = new ArrayList();
                while (!fin) {
                    if (c.parent == origen) {
                        fin = true;
                    }
                    casillas.add(0, c);
                    c = c.parent;
                }
                ListIterator<Casilla> li = casillas.listIterator();
                Casilla pre = origen; // Casilla anterior

                // Comprobamos que direcciones tomar
                while (li.hasNext()) {
                    Casilla cas = li.next();
                    if (cas == origen) {
                        cas = li.next();
                    }
                    if (cas.y != pre.y) { // Horizontal
                        if (cas.y == pre.y + 1) {
                            if (ruta.isEmpty() || ruta.get(ruta.size() - 1) != Bicho.ABAJO) {
                                ruta.add(Bicho.ABAJO);
                            }
                        } else if (cas.y == pre.y - 1) {
                            if (ruta.isEmpty() || ruta.get(ruta.size() - 1) != Bicho.ARRIBA) {
                                ruta.add(Bicho.ARRIBA);
                            }
                        }
                    } else if (cas.x != pre.x) {
                        if (cas.x == pre.x - 1) {
                            if (ruta.isEmpty() || ruta.get(ruta.size() - 1) != Bicho.IZQUIERDA) {
                                ruta.add(Bicho.IZQUIERDA);
                            }
                        } else if (cas.x == pre.x + 1) {
                            if (ruta.isEmpty() || ruta.get(ruta.size() - 1) != Bicho.DERECHA) {
                                ruta.add(Bicho.DERECHA);
                            }
                        }
                    }
                    pre = cas;
                }
                // Devolvemos las direcciones (FIN DEL ALGORITMO)
                return ruta;
            } else { // Comprobamos las adyacencias
                Set<Casilla> vecinos = best.obtenerAdyacencias();
                for (Casilla vecino : vecinos) {
                    if (abiertos.contains(vecino)) {
                        Casilla temp = new Casilla(new Point(vecino.x, vecino.y));
                        temp.setPadre(best);
                        if (temp.getPasoATraves(origen, destino) >= vecino.getPasoATraves(origen, destino)) {
                            continue;
                        }
                    }

                    if (cerrados.contains(vecino)) {
                        Casilla temp = new Casilla(new Point(vecino.x, vecino.y));
                        temp.setPadre(best);
                        if (temp.getPasoATraves(origen, destino) >= vecino.getPasoATraves(origen, destino)) {
                            continue;
                        }
                    }

                    vecino.setPadre(best);

                    abiertos.remove(vecino);
                    cerrados.remove(vecino);
                    abiertos.add(0, vecino);
                }
            }
        }
        return null;
    }
}
