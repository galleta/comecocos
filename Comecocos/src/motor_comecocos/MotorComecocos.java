package motor_comecocos;

import guicomecocos.ComecocosFrame;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase principal del motor del juego. Interfaz del paquete motor_comecocos
 * 
 */
public class MotorComecocos {

    private EstadoJuego estado;
    private Mapa mapa;
    private Comecocos comecocos_muriendo = null; // Comecocos que esta muriendo
    private boolean pausado = false;
    private GestorMovimiento gm;
    private int ancho_celda;
    private int tam_bichos;
    private final ComecocosFrame frame;

    public MotorComecocos(ComecocosFrame cf) {
        this.frame = cf;

        this.ancho_celda = -1;
        this.tam_bichos = -1;
    }

    public boolean getPausa() {
        return pausado;
    }

    public EstadoJuego getEstadoJuego() {
        return this.estado;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public ArrayList<Comecocos> getComecocos() {
        return this.estado.comecocos;
    }

    public ArrayList<Fantasma> getFantasmas() {
        return this.estado.fantasmas;
    }

    private void inicializarJuego() {
        this.reproducirInicio();
        gm = new GestorMovimiento(this, false);
        gm.iniciar();
        gm.reanudar();
    }

    public int getNumFantasmas() {
        return getFantasmas().size();
    }

    public int getNumComecocos() {
        return getComecocos().size();
    }

    public int getPuntos(int jugador) {
        return estado.getPuntos(jugador);
    }

    public int getVidas(int jugador) {
        return estado.datos_jugador.get(jugador).vidas;
    }

    public void setPuntos(int jugador, int puntos_nuevos) {
        this.estado.sumarPuntos(jugador, puntos_nuevos);
    }

    public void sumarPuntos(int jugador, int puntos_nuevos) {
        this.estado.sumarPuntos(jugador, puntos_nuevos);
    }

    public void setNivel(int nivel_nuevo) {
        this.estado.nivel = nivel_nuevo;
    }

    public int getNivel() {
        return this.estado.nivel;
    }

    public void setTiempoRestante(int t_restante) {
        this.estado.tiempo_restante = t_restante;
    }

    public long getTiempoRestante() {
        return this.estado.tiempo_restante;
    }

    public void actualizarDibujo() {
        if (frame != null) {
            frame.getPanel().repaint();
        }
    }

    public void pausarJuego() {
        if (pausado != true) {
            this.gm.suspender();
            pausado = true;
        }
    }

    public void reanudarJuego() {
        if (pausado != false) {
            this.gm.reanudar();
            pausado = false;
        }
    }

    public void subirNivel() {
        gm.parar();
        estado.subirNivel();
        mapa = new Mapa(estado);
        this.colocarBichos();
        this.inicializarJuego();
    }

    void actualizarTiempoRestante(long tiempo_transcurrido) {
        this.estado.tiempo_restante -= tiempo_transcurrido;
    }

    // Lanza el evento de matar a los bichos por acabarse el tiempo para completar el nivel
    void setFinTiempo() {
        // Muerte por tiempo, ejecutar animacion de ambos cocos
        ListIterator<Fantasma> fi = this.getFantasmas().listIterator();
        while (fi.hasNext()) {
            fi.next().ocultar(true);
        }
        ListIterator<Comecocos> i = this.getComecocos().listIterator();
        while (i.hasNext()) {
            Bicho cc = i.next();
            if (cc.getEstado() == Comecocos.VIVO) {
                cc.setEstado(Comecocos.MURIENDO);
            }
        }
        this.estado.estado_juego = EstadoJuego.ESTADO_MURIENDO;
    }

    // Indica que un comecocos ha sido pillado por un fantasma
    public void setComecocosPillado(Comecocos cc, Fantasma f) {
        // Muerte por colisión, ejecutar animacion del coco y del fantasma
        ListIterator<Fantasma> fi = this.getFantasmas().listIterator();
        while (fi.hasNext()) {
            fi.next().ocultar(true);
        }
        f.ocultar(false);

        ListIterator<Comecocos> i = this.getComecocos().listIterator();
        while (i.hasNext()) {
            i.next().ocultar(true);
        }
        cc.ocultar(false);
        cc.setEstado(Comecocos.MURIENDO);
        this.estado.estado_juego = EstadoJuego.ESTADO_MURIENDO;
        this.comecocos_muriendo = cc;
        this.reproducirMuerte();
    }

    // Avanza un frame en la animacion de la muerte
    void avanzarAnimacionMuerte() {
        if (estado.animacion_muerte >= estado.max_animacion_muerte - 1) {
            estado.animacion_muerte = 0;
            this.eliminarVida();
        } else {
            this.estado.animacion_muerte++;
        }
    }

    public int getAnimMuerte() {
        return estado.animacion_muerte;
    }

    // Elimina una vida del/los comecocos muriendo y comprueba si ha acabado el juego
    private void eliminarVida() {
        if (this.comecocos_muriendo != null) {
            // Suprimimos una vida del comecocos
            this.estado.datos_jugador.get(this.getComecocos().indexOf(comecocos_muriendo)).vidas--;
            comecocos_muriendo = null;
        } else { // Muerte por tiempo
            ListIterator<Comecocos> i = getComecocos().listIterator();
            while (i.hasNext()) {
                Comecocos cc = i.next();
                if (cc.getEstado() != Comecocos.MUERTO) {
                    this.estado.datos_jugador.get(this.getComecocos().indexOf(cc)).vidas--;
                }
            }
        }
        // Comprobacion de si es fin del juego
        boolean game_over = true;
        for (int i = 0; i < estado.num_comecocos; i++) {
            if (this.getVidas(i) > 0) {
                game_over = false;
            } else {
                Comecocos cc = getComecocos().get(i);
                cc.setEstado(Comecocos.MUERTO);
                cc.ocultar(true);
            }
        }
        if (game_over) {
            this.estado.estado_juego = EstadoJuego.ESTADO_FIN_JUEGO;
            this.frame.finJuego();
        } else {
            // Recolocar los bichos en su sitio
            this.colocarBichos();
            this.estado.resetearTiempo();
            this.estado.estado_juego = EstadoJuego.ESTADO_JUGANDO;
        }
    }

    // Coloca los bichos en sus posiciones iniciales del mapa
    private void colocarBichos() {
        ListIterator<Comecocos> it_comecocos = getComecocos().listIterator();
        while (it_comecocos.hasNext()) {
            int indice = it_comecocos.nextIndex();
            Comecocos cc = it_comecocos.next();
            this.mapa.setPosicionInicial(cc, indice);
            if (this.getVidas(this.getComecocos().indexOf(cc)) > 0) {
                cc.setEstado(Comecocos.VIVO);
                cc.ocultar(false);
                cc.setDireccion(Bicho.IZQUIERDA);
            }
        }
        ListIterator<Fantasma> it = getFantasmas().listIterator();
        while (it.hasNext()) {
            int indice = it.nextIndex();
            Fantasma f = it.next();
            this.mapa.setPosicionInicial(f, 2 + indice);
            f.ocultar(false);
            f.setEstado(Fantasma.ESTADO_ENCERRADO);
        }
    }

    // Carga una partida de un archivo (cargando una instancia de EstadoJuego)
    public void cargarPartida(File archivo) {
        FileInputStream fis = null;
        try {
            if (this.gm != null) {
                this.gm.parar();
            }
            fis = new FileInputStream(archivo);
            ObjectInputStream ois = new ObjectInputStream(fis);
            estado = (EstadoJuego) ois.readObject();
            if (this.ancho_celda != -1) {
                this.setTamBichos(ancho_celda, tam_bichos);
            }
            ListIterator<Comecocos> it_comecocos = getComecocos().listIterator();
            while (it_comecocos.hasNext()) {
                Comecocos cc = it_comecocos.next();
                cc.setMotor(this);
            }
            ListIterator<Fantasma> it = getFantasmas().listIterator();
            while (it.hasNext()) {
                Fantasma f = it.next();
                f.setMotor(this);
            }

            mapa = new Mapa(estado);
            gm = new GestorMovimiento(this, true);
            gm.iniciar();
            gm.reanudar();
        } catch (Exception ex) {
            Logger.getLogger(MotorComecocos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(MotorComecocos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Lanza una nueva partida
    public final void nuevaPartida(int nivel_inicial, boolean multijugador, DatosMapa datos_mapa_nuevo) {
        if (this.gm != null) {
            this.gm.parar();
        }
        int num_jugadores = 1;
        if (multijugador) {
            num_jugadores = 2;
        }
        estado = new EstadoJuego(this, num_jugadores);
        estado.datos_mapa = datos_mapa_nuevo;
        estado.nivel = nivel_inicial;
        if (this.ancho_celda != -1) {
            this.setTamBichos(ancho_celda, tam_bichos);
        }
        mapa = new Mapa(estado);
        this.resetearTiempoJuego();
        this.colocarBichos();
        this.inicializarJuego();
    }

    // Indica si dos bichos colisionan
    protected boolean hayColision(Bicho b1, Bicho b2) {
        return (b1.getRectangulo().intersects(b2.getRectangulo()));
    }

    public void setTamBichos(int ancho_celda, int tam_caja) {
        this.tam_bichos = tam_caja;
        this.ancho_celda = ancho_celda;
        this.estado.tam_bichos = tam_caja;
        this.estado.ancho_celda = ancho_celda;
    }

    // Activa el modo en el que el comecocos puede comerse a los fantasmas
    public void activarCazaFantasmas() {
        this.estado.combo_fantasmas_comidos = 0; // Resetea el combo para evitar burradas de puntos
        ListIterator<Fantasma> fantasmas = this.estado.fantasmas.listIterator();
        while (fantasmas.hasNext()) {
            Fantasma f = fantasmas.next();
            if (f.getEstado() == Fantasma.ESTADO_NORMAL || f.getEstado() == Fantasma.ESTADO_HUYENDO) {
                f.setEstado(Fantasma.ESTADO_HUYENDO);
            }
        }
    }

    // Indica que un fantasma ha sido comido por un comecocos
    void setFantasmaPillado(Comecocos cc, Fantasma f) {
        f.setEstado(Fantasma.ESTADO_VOLVIENDO);
        this.estado.combo_fantasmas_comidos++;
        int jugador = this.getComecocos().indexOf(cc);
        // Sumamos los puntos
        this.estado.sumarPuntos(jugador, (int) (Math.pow(2, this.estado.combo_fantasmas_comidos) * 100));
    }

    // Actualiza el tiempo que lleva la fruta en pantalla o que le falta por salir
    void actualizarTiempoFruta(int t) {
        if (hayFruta()) { // Hay fruta
            this.getEstadoJuego().tiempo_fruta += t;
            if (this.getEstadoJuego().duracion_fruta < this.getEstadoJuego().tiempo_fruta) {
                this.eliminarFruta();
            }
        } else { // No hay
            this.getEstadoJuego().tiempo_desde_ultima_fruta += t;
            if (frutaLista()) {
                generarFruta();
            }
        }
    }

    public boolean hayFruta() {
        return getTipoFruta() > 0;
    }

    public boolean frutaLista() {
        return this.getEstadoJuego().tiempo_desde_ultima_fruta >= getEstadoJuego().tiempo_limite_fruta;
    }

    public void generarFruta() {
        if (getEstadoJuego().puntos_comidos.size() > 10) {
            Random r = new Random();
            int pos_punto = r.nextInt(getEstadoJuego().puntos_comidos.size() / 2);
            Point punto = getEstadoJuego().puntos_comidos.get(pos_punto);
            this.getEstadoJuego().posicion_fruta = punto;
            this.getEstadoJuego().tiempo_fruta = 0;
            this.getEstadoJuego().tipo_fruta = r.nextInt(4) + 1;

        } else {
            // Reiniciamos el contador de tiempo ya que no hay hueco aun
            eliminarFruta();
        }
    }

    public Point getPosFruta() {
        return new Point(getEstadoJuego().posicion_fruta);
    }

    public int getTipoFruta() {
        return estado.tipo_fruta;
    }

    public void comerFruta(Comecocos comecocos) {
        int num_jugador = getComecocos().indexOf(comecocos);
        getEstadoJuego().sumarPuntos(num_jugador, 500);
        this.eliminarFruta();
    }

    public void eliminarFruta() {
        estado.tiempo_desde_ultima_fruta = 0;
        estado.tipo_fruta = -1;
    }

    // Reproducir los sonidos
    public void reproducirWaka() {
        frame.reproducirWaka();
    }

    public void reproducirInicio() {
        frame.reproducirInicio();
    }

    public void reproducirMuerte() {
        frame.reproducirMuerte();
    }

    public int getNumJugadores() {
        return this.getEstadoJuego().num_comecocos;
    }

    // Guarda la partida
    public boolean guardarPartida(File archivo) {
        FileOutputStream fos = null;
        boolean ok = true;
        try {
            fos = new FileOutputStream(archivo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(estado);
            ok = true;
        } catch (Exception ex) {
            Logger.getLogger(MotorComecocos.class.getName()).log(Level.SEVERE, null, ex);
            ok = false;
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(MotorComecocos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ok;
    }

    public long getTiempoJuego() {
        return estado.tiempo_juego;
    }

    void actualizarTiempoJuego(int retardo_hebra) {
        this.estado.tiempo_juego += retardo_hebra;
    }

    void resetearTiempoJuego() {
        this.estado.tiempo_juego = 0;
    }
}
