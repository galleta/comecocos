package motor_comecocos;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Clase con todos los datos del juego
 * 
 */
public class EstadoJuego implements Serializable {

    public transient static final int ESTADO_MURIENDO = 0;
    public transient static final int ESTADO_JUGANDO = 1;
    public transient static final int ESTADO_FIN_JUEGO = 2;
    private transient MotorComecocos motor;
    // Estado actual del juego
    public int estado_juego;
    public int nivel;
    public long tiempo_restante;
    // Variables para gestionar las cajas de colision de los bichos
    public int tam_bichos;
    public int ancho_celda;
    // Los bichos
    public ArrayList<Comecocos> comecocos;
    public ArrayList<Fantasma> fantasmas;
    // Los puntos que se han comido del mapa
    public ArrayList<Point> puntos_comidos; // Representa los puntos ya comidos
    // Los datos de cada jugador (puntos y vidas)
    public ArrayList<DatosJugador> datos_jugador; // Datos de jugador
    // Frame actual de la animacion de muerte
    public int animacion_muerte;
    // Numero de frames de la animacion de muerte
    public int max_animacion_muerte;
    // Tiempo inicial del juego
    private long tiempo_inicial;
    // Duracion del tiempo de huida de cada fantasma
    public int tiempo_huida;
    public int num_comecocos;
    public int num_fantasmas;
    // Fantasmas comidos por el momento
    public int combo_fantasmas_comidos;
    // Variables referentes a la fruta
    public long tiempo_limite_fruta;
    public long tiempo_desde_ultima_fruta;
    public Point posicion_fruta;
    public long tiempo_fruta;
    public long duracion_fruta;
    public int tipo_fruta;
    // Datos del mapa
    public DatosMapa datos_mapa;
    // Tiempo que se lleva jugando
    public long tiempo_juego;

    // Genera los bichos
    private void crearBichos() {
        comecocos = new ArrayList<Comecocos>();
        fantasmas = new ArrayList<Fantasma>();
        for (int i = 0; i < this.num_comecocos; i++) {
            comecocos.add(new Comecocos(this.motor));
        }

        for (int i = 0; i < this.num_fantasmas; i++) {
            fantasmas.add(new Fantasma(this.motor, 25 * (i + 1)));
        }
    }

    // Datos de los jugadores
    public class DatosJugador implements Serializable {

        private int puntos;
        public int vidas;

        DatosJugador() {
            puntos = 0;
            vidas = 3;
        }
    }

    // Constructor
    EstadoJuego(MotorComecocos motor_cc, int numero_jugadores) {
        motor = motor_cc;
        num_comecocos = numero_jugadores;
        puntos_comidos = new ArrayList<Point>();
        datos_jugador = new ArrayList<DatosJugador>();

        animacion_muerte = 0;
        combo_fantasmas_comidos = 0;
        tam_bichos = 1;
        ancho_celda = 1;

        max_animacion_muerte = 6;

        tiempo_inicial = 180000;

        tiempo_huida = 15000;

        num_fantasmas = 4;

        tiempo_limite_fruta = 0;
        tiempo_desde_ultima_fruta = 0;

        tiempo_fruta = 0;
        duracion_fruta = 10000;
        tipo_fruta = 0;

        this.crearBichos();

        for (int i = 0; i < num_comecocos; i++) {
            datos_jugador.add(new DatosJugador());
        }
        estado_juego = ESTADO_JUGANDO;
        nivel = 1;
        tiempo_restante = tiempo_inicial;

    }

    // Actualiza los datos al subir de nivel
    public void subirNivel() {
        this.nivel++;
        this.tiempo_restante = tiempo_inicial;
        this.puntos_comidos = new ArrayList<Point>();

        this.crearBichos();
        combo_fantasmas_comidos = 0;
        int puntos_nivel = (int) (this.tiempo_restante / 100);
        ListIterator<DatosJugador> i = this.datos_jugador.listIterator();
        while (i.hasNext()) {
            DatosJugador datos = i.next();
            if (datos.vidas > 0) {
                datos.puntos += puntos_nivel;
            }
        }
    }

    public void resetearTiempo() {
        tiempo_restante = tiempo_inicial;
    }

    void sumarPuntos(int jugador, int puntos_nuevos) {
        this.datos_jugador.get(jugador).puntos += puntos_nuevos;
    }

    int getPuntos(int jugador) {
        return this.datos_jugador.get(jugador).puntos;
    }
}
