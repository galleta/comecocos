package motor_comecocos;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Datos de un mapa
 * 
 */
public class DatosMapa implements Serializable {
    public int ancho;
    public int alto;
    // Posiciones iniciales de los bichos
    public ArrayList<Point> posiciones_iniciales;
    // Posiciones hasta donde saldria un fantasma de su caja
    public ArrayList<Point> posiciones_salida;
    public String[] mapa;
    public String nombre;

    public DatosMapa() {
        posiciones_iniciales = new ArrayList<Point>();
        posiciones_salida = new ArrayList<Point>();
    }
}
