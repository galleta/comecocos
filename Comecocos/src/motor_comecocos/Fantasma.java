package motor_comecocos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa a un fantasma
 * 
 */
public final class Fantasma extends Bicho implements Serializable {
    public static final int ESTADO_NORMAL = 0;
    public static final int ESTADO_ENCERRADO = 1;
    public static final int ESTADO_HUYENDO = 2;
    public static final int ESTADO_VOLVIENDO = 3;
    public static final int ESTADO_SALIENDO = 4;
    public static final int ESTADO_ENTRANDO = 5;
    
    // Ruta de direcciones que sigue el fantasma hasta alcanzar al comecocos
    private ArrayList<Integer> ruta;
    private int ia = 0; // Indica el tipo de IA que llevará
    private boolean ha_calculado_ruta; // Indica si ha calculado la ruta en esa esquina
    private long tiempo_limite_descanso; // Maximo tiempo para esperar en la caja
    private int tiempo_descanso; // Tiempo que lleva esperando a salir en la caja
    private int tiempo_huyendo = 0; // Tiempo que lleva huyendo

    public Fantasma(MotorComecocos motor, int nueva_ia) {
        super(motor);

        this.direccion = DERECHA;
        this.setIA(nueva_ia);
        this.setEstado(ESTADO_ENCERRADO);
        this.ha_calculado_ruta = false;
        this.tiempo_descanso = 0;
        this.tiempo_huyendo = 0;
    }

    @Override
    public void reset() {
        super.reset();
        this.direccion = DERECHA;
        this.setEstado(ESTADO_ENCERRADO);
        this.ha_calculado_ruta = false;
        this.tiempo_descanso = 0;
        this.tiempo_huyendo = 0;
    }

    @Override
    public void setEstado(int estado_nuevo) {
        super.setEstado(estado_nuevo);
        switch (estado_nuevo) {
            case ESTADO_SALIENDO:
                this.setDireccion(Bicho.ARRIBA);
                break;

            case ESTADO_ENTRANDO:
                this.setDireccion(Bicho.ABAJO);
                break;

            case ESTADO_HUYENDO:
                this.reiniciarTiempoHuyendo();
                break;

            case ESTADO_VOLVIENDO:
                break;

            case ESTADO_ENCERRADO:
                this.reiniciarTiempoDescanso();
                this.setDireccion(Bicho.IZQUIERDA);
                break;
        }
    }

    public void setIA(int nueva_ia) {
        this.ia = nueva_ia;
    }

    public int getIA() {
        return this.ia;
    }

    @Override
    public void setDireccion(int dir) {
        super.setDireccion(dir);
        this.ha_calculado_ruta = true;
    }

    public void setRuta(ArrayList<Integer> ruta_nueva) {
        this.ruta = ruta_nueva;
        this.ha_calculado_ruta = true;
    }

    public void siguienteDireccionRuta() {
        if (ruta != null) {
            if (!ruta.isEmpty()) {
                int sig = ruta.remove(0);
                this.direccion = sig;
            }
        }
    }

    @Override
    public void avanzar() {
        super.avanzar();
        this.ha_calculado_ruta = false;
    }

    boolean haCalculadoRuta() {
        return ha_calculado_ruta;
    }

    public void reiniciarTiempoDescanso() {
        this.tiempo_descanso = 0;
    }

    public void reiniciarTiempoHuyendo() {
        this.tiempo_huyendo = 0;
    }

    public void actualizarTiempoDescanso(long t) {
        this.tiempo_descanso += t;
    }

    public void actualizarTiempoHuyendo(long t) {
        this.tiempo_huyendo += t;
    }

    public boolean finalizadaHuida() {
        return this.tiempo_huyendo >= this.motor.getEstadoJuego().tiempo_huida;
    }

    public long tiempoParaFinalizarHuida() {
        return this.motor.getEstadoJuego().tiempo_huida - this.tiempo_huyendo;
    }

    public void setTiempoLimite(long t) {
        this.tiempo_limite_descanso = t;
    }

    public boolean listoParaSalir() {
        return this.tiempo_descanso >= tiempo_limite_descanso;
    }

    // Dependiendo del estado del fantasma tendra una velocidad mayor o menor
    @Override
    public int getVelocidad() {
        int v = super.getVelocidad();
        switch(this.getEstado()) {
            case Fantasma.ESTADO_HUYENDO:
                v += v / 3;
                break;
            case Fantasma.ESTADO_VOLVIENDO:
                v -= v / 4;
            default:
                break;
        }
        return v;
    }
}
