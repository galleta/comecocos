package motor_comecocos;

import java.awt.Point;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

/**
 * Hebra que gestiona los eventos del juego
 * 
 */
public class GestorMovimiento implements Runnable {
    // Retraso de ejecucion
    private int retardo_dibujado;
    private int retardo_hebra;

    // Control de la hebra
    private boolean continuar = true;
    private boolean suspendFlag = true;

    private MotorComecocos motor;
    private Thread hebra;

    // Variables para controlar la actualizacion del juego
    private long tiempo_ultima_actualizacion = 0;
    private ArrayList<Long> tiempo_ultimo_movimiento;
    private long tiempo_ultima_hebra = 0;

    // Aleatorio para generar las acciones de la IA
    private Random r;

    // Variables para controlar el nivel
    private double factor_velocidad = 0;
    private int max_nivel = 10;
    private int tiempo_frame_animacion_muerte = 200;

    // Cargado indica si es una partida cargada o no
    public GestorMovimiento(MotorComecocos mc, boolean cargado) {
        motor = mc;
        retardo_hebra = 10;
        retardo_dibujado = 20;

        double factor_nivel = 0;
        // Establecemos las condiciones del nivel
        if (motor.getNivel() < max_nivel) {
            factor_velocidad = 2.0 - motor.getNivel() / max_nivel;
            factor_nivel = max_nivel - motor.getNivel();
        } else {
            factor_velocidad = 1;
            factor_nivel = 1;
        }

        hebra = new Thread(this);
        tiempo_ultima_actualizacion = System.currentTimeMillis();
        tiempo_ultima_hebra = System.currentTimeMillis();
        long t = System.currentTimeMillis();
        tiempo_ultimo_movimiento = new ArrayList<Long>();
        for (int i = 0; i < mc.getComecocos().size() + mc.getFantasmas().size(); i++) {
            tiempo_ultimo_movimiento.add(t);
        }
        int semilla = 0;
        r = new Random(semilla);
        // Ejecutamos la configuracion inicial de los bichos si no se ha cargado la partida
        if (!cargado) {
            ListIterator<Fantasma> f = motor.getFantasmas().listIterator();
            int retraso = 0;

            if (max_nivel <= motor.getNivel()) {
                factor_nivel = 1;
            }
            while (f.hasNext()) {
                Fantasma fantasma = f.next();
                fantasma.setTiempoLimite((long) (5 * factor_nivel) * 500 * retraso);
                fantasma.reiniciarTiempoDescanso();
                fantasma.setEstado(Fantasma.ESTADO_ENCERRADO);
                retraso++;
            }
            motor.getEstadoJuego().tiempo_limite_fruta = 20000;
            motor.getEstadoJuego().tiempo_desde_ultima_fruta = 0;
        }
    }

    // Inicia la ejecucion de la hebra
    public void iniciar() {
        hebra.start();
    }

    // bucle de ejecucion de la hebra
    public void run() {
        try {
            while (continuar) {
                synchronized (this) {
                    while (suspendFlag) {
                        wait();
                    }
                }
                long tiempo_actual = System.currentTimeMillis();
                // Comprobamos si hay que actualizar
                if (tiempo_actual >= tiempo_ultima_hebra + retardo_hebra) {
                    tiempo_ultima_hebra = tiempo_actual;
                    // Comprobamos el estado del juego
                    switch (motor.getEstadoJuego().estado_juego) {
                        case EstadoJuego.ESTADO_JUGANDO:
                            motor.actualizarTiempoJuego(retardo_hebra);
                            // Movimiento de los comecocos
                            boolean reproducir_paso = false;
                            ListIterator<Comecocos> it_comecocos = motor.getComecocos().listIterator();
                            while (it_comecocos.hasNext()) {
                                int indice = it_comecocos.nextIndex();
                                Comecocos comecocos = it_comecocos.next();
                                if (comecocos.getEstado() == Comecocos.VIVO) {
                                    if (tiempo_actual >= tiempo_ultimo_movimiento.get(indice) + comecocos.getVelocidad()) {
                                        tiempo_ultimo_movimiento.set(indice, tiempo_actual);
                                        Point nueva_posicion = new Point(comecocos.getPos());
                                        // Comprobacion de siguiente casilla
                                        switch (comecocos.getDireccion()) {
                                            case Bicho.ABAJO:
                                                nueva_posicion.y++;
                                                break;

                                            case Bicho.ARRIBA:
                                                nueva_posicion.y--;
                                                break;

                                            case Bicho.IZQUIERDA:
                                                nueva_posicion.x--;
                                                break;

                                            case Bicho.DERECHA:
                                                nueva_posicion.x++;
                                                break;
                                        }
                                        // Comprobamos si puede mover
                                        if (motor.getMapa().puedeMover(nueva_posicion)) {
                                            comecocos.aumentarPosOffset();
                                            // Si ha dado un paso completo el comecocos reproducimos el sonido
                                            if (comecocos.getPosOffset() == 0) {
                                                reproducir_paso = true;
                                            }
                                            // Comprobamos si come
                                            if (motor.getMapa().puedeComerPunto(comecocos.getPos())) {
                                                int tipo_punto = motor.getMapa().comerPunto(comecocos.getPos());
                                                switch (tipo_punto) {
                                                    case Mapa.PUNTO_CHICO:
                                                        motor.sumarPuntos(indice, 10);
                                                        break;
                                                    case Mapa.PUNTO_GRANDE:
                                                        motor.sumarPuntos(indice, 50);
                                                        motor.activarCazaFantasmas();
                                                        break;
                                                }
                                                // Comprobamos si puede subir de nivel
                                                if (motor.getMapa().getCocosRestantes() == 0) {
                                                    motor.subirNivel();
                                                }
                                            } else if (motor.hayFruta()) { // Comprobamos si come fruta
                                                if (comecocos.getPos().equals(motor.getPosFruta())) {
                                                    motor.comerFruta(comecocos);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            // Reproducir sonido si se ha movido el comecocos
                            if (reproducir_paso) {
                                motor.reproducirWaka();
                            }

                            // Movimiento de los fantasmas
                            ListIterator<Fantasma> it_fantasmas = motor.getFantasmas().listIterator();
                            while (it_fantasmas.hasNext()) {
                                int indice = it_fantasmas.nextIndex();
                                Fantasma fantasma = it_fantasmas.next();
                                if (tiempo_actual >= tiempo_ultimo_movimiento.get(indice + motor.getNumComecocos()) + fantasma.getVelocidad() * factor_velocidad) {
                                    // Gestion de la IA
                                    if (fantasma.getEstado() == Fantasma.ESTADO_SALIENDO) {
                                        if (fantasma.getPosOffset() == 0 && motor.getMapa().haSalido(fantasma, indice)) {
                                            fantasma.setEstado(Fantasma.ESTADO_NORMAL);
                                        }
                                    } else if (fantasma.getEstado() == Fantasma.ESTADO_ENTRANDO) {
                                        if (fantasma.getPosOffset() == 0 && motor.getMapa().haEntrado(fantasma, indice)) {
                                            fantasma.setEstado(Fantasma.ESTADO_ENCERRADO);
                                        }
                                    }

                                    // No hay else para que cuando acabe de salir que funcione esta condicion tambien
                                    switch (fantasma.getEstado()) {
                                        case Fantasma.ESTADO_SALIENDO:
                                            break;
                                        case Fantasma.ESTADO_VOLVIENDO:
                                            Point posicion_salida_fantasma = this.motor.getMapa().getPosicionSalida(this.motor.getEstadoJuego().fantasmas.indexOf(fantasma));
                                            fantasma.setRuta(this.motor.getMapa().obtenerRuta(fantasma.getPos(), posicion_salida_fantasma));
                                            fantasma.siguienteDireccionRuta();
                                            if (fantasma.getPosOffset() == 0 && fantasma.getPos().equals(posicion_salida_fantasma)) {
                                                fantasma.setEstado(Fantasma.ESTADO_ENTRANDO);
                                            }
                                            break;
                                        case Fantasma.ESTADO_NORMAL:
                                            if (fantasma.getPosOffset() == 0 && !fantasma.haCalculadoRuta()) {
                                                // Comprobamos si puede cambiar de direccion
                                                ArrayList<Integer> alternativas = new ArrayList();
                                                for (int j = 0; j < 4; j++) {
                                                    if (fantasma.getDireccion() != j && motor.getMapa().puedeCambiarDireccion(j, fantasma.getPos())) {
                                                        alternativas.add(j);
                                                    }
                                                }
                                                // Caso especial, que no pueda seguir moviendo por entrar en un callejon sin salida
                                                boolean atrancado = false;
                                                if (alternativas.size() == 1 && !this.motor.getMapa().puedeCambiarDireccion(fantasma.getDireccion(), fantasma.getPos())) {
                                                    atrancado = true;
                                                }

                                                // Si puede cambiar, ejecutamos una busqueda hacia el comecocos
                                                if (alternativas.size() > 1 || atrancado) {
                                                    int prob = r.nextInt(100); // Probabilidad
                                                    if (prob < fantasma.getIA()) { // Si es menor que la IA necesaria, buscar ruta
                                                        ListIterator<Comecocos> it_cc = this.motor.getComecocos().listIterator();
                                                        ArrayList<Integer> menor_ruta = null; // Menor ruta entre los 2 comecocos (la que menos cambios de direccion contenga)
                                                        while (it_cc.hasNext()) {
                                                            Comecocos cc = it_cc.next();
                                                            if (cc.getEstado() == Comecocos.VIVO) {
                                                                ArrayList<Integer> ruta = this.motor.getMapa().obtenerRuta(fantasma.getPos(), cc.getPos());
                                                                if (menor_ruta == null) {
                                                                    menor_ruta = ruta;
                                                                } else if (menor_ruta.size() > ruta.size()) {
                                                                    menor_ruta = ruta;
                                                                }
                                                            }
                                                        }
                                                        fantasma.setRuta(menor_ruta);
                                                        fantasma.siguienteDireccionRuta();

                                                    } else { // Si no pasa la probabilidad, movimiento aleatorio
                                                        boolean fin = false;
                                                        while (!fin) {
                                                            int direccion_nueva = alternativas.get(r.nextInt(alternativas.size()));
                                                            boolean opuestas = ((fantasma.getDireccion() + direccion_nueva) % 4) == 1 && alternativas.size() > 1;
                                                            if (motor.getMapa().puedeCambiarDireccion(direccion_nueva, fantasma.getPos()) && !opuestas) {
                                                                fantasma.setDireccion(direccion_nueva);
                                                                fin = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            break;
                                        case Fantasma.ESTADO_ENCERRADO:
                                            fantasma.actualizarTiempoDescanso((long) (fantasma.getVelocidad() * factor_velocidad));
                                            if (fantasma.listoParaSalir()) {
                                                fantasma.setEstado(Fantasma.ESTADO_SALIENDO);
                                            }
                                            break;
                                        case Fantasma.ESTADO_ENTRANDO:
                                            break;
                                        case Fantasma.ESTADO_HUYENDO:
                                            fantasma.actualizarTiempoHuyendo((long) (fantasma.getVelocidad() * factor_velocidad));
                                            if (fantasma.finalizadaHuida()) {
                                                fantasma.setEstado(Fantasma.ESTADO_NORMAL);
                                            }
                                            if (fantasma.getPosOffset() == 0 && !fantasma.haCalculadoRuta()) {
                                                // Comprobamos si puede cambiar de direccion
                                                ArrayList<Integer> alternativas = new ArrayList();
                                                ArrayList<Integer> alternativas_filtradas = new ArrayList();
                                                for (int j = 0; j < 4; j++) {
                                                    if (fantasma.getDireccion() != j && motor.getMapa().puedeCambiarDireccion(j, fantasma.getPos())) {
                                                        alternativas.add(j);
                                                        alternativas_filtradas.add(j);
                                                    }
                                                    for (int it = 0; it < motor.getNumComecocos(); it++) {
                                                        int direccion_hacia_coco = motor.getMapa().obtenerRuta(fantasma.getPos(), motor.getComecocos().get(it).getPos()).get(0);
                                                        int i = alternativas_filtradas.indexOf(direccion_hacia_coco);
                                                        if (i != -1) {
                                                            alternativas_filtradas.remove(i); // Indexof porque remove puede aceptar int como 2 funciones a la vez en este caso
                                                        }
                                                    }
                                                }
                                                if (alternativas_filtradas.size() > 0) {
                                                    int direccion_nueva = alternativas_filtradas.get(r.nextInt(alternativas_filtradas.size()));
                                                    fantasma.setDireccion(direccion_nueva);
                                                                                                    // Caso especial, que no pueda seguir moviendo por entrar en un callejon sin salida

                                                }
                                                
                                                boolean atrancado = false;
                                                if (alternativas.size() == 1 && !this.motor.getMapa().puedeCambiarDireccion(fantasma.getDireccion(), fantasma.getPos())) {
                                                    atrancado = true;
                                                } else if (alternativas.size() > 1 || atrancado) {
                                                    int direccion_nueva = alternativas.get(r.nextInt(alternativas.size()));
                                                    fantasma.setDireccion(direccion_nueva);
                                                }
                                            }

                                            break;
                                    }
                                    tiempo_ultimo_movimiento.set(indice + motor.getNumComecocos(), tiempo_actual);
                                    Point nueva_posicion = new Point(fantasma.getPos());
                                    // Comprobacion de siguiente casilla
                                    switch (fantasma.getDireccion()) {
                                        case Bicho.ABAJO:
                                            nueva_posicion.y++;
                                            break;

                                        case Bicho.ARRIBA:
                                            nueva_posicion.y--;
                                            break;

                                        case Bicho.IZQUIERDA:
                                            nueva_posicion.x--;
                                            break;

                                        case Bicho.DERECHA:
                                            nueva_posicion.x++;
                                            break;
                                    }
                                    // Aumentar el paso en la casilla
                                    if (fantasma.getEstado() != Fantasma.ESTADO_ENCERRADO && (motor.getMapa().puedeMover(nueva_posicion) || fantasma.getEstado() == Fantasma.ESTADO_SALIENDO || fantasma.getEstado() == Fantasma.ESTADO_ENTRANDO)) {
                                        fantasma.aumentarPosOffset();
                                    }
                                }
                            }

                            // Generar frutas
                            this.motor.actualizarTiempoFruta(this.retardo_hebra);

                            // Comprobación de colision
                            // Comprobamos si un comecocos toca un fantasma
                            ListIterator<Comecocos> it_coco = motor.getComecocos().listIterator();
                            while (it_coco.hasNext()) {
                                Comecocos cc = it_coco.next();
                                if (cc.getEstado() == Comecocos.VIVO) {
                                    // Compruebo con cada uno de los fantasmas
                                    ListIterator<Fantasma> it_fanta = motor.getFantasmas().listIterator();
                                    boolean pillado = false;
                                    while (it_fanta.hasNext() && !pillado) {
                                        Fantasma f = it_fanta.next();
                                        if (motor.hayColision(cc, f)) {
                                            if (f.getEstado() == Fantasma.ESTADO_NORMAL || f.getEstado() == Fantasma.ESTADO_SALIENDO) {
                                                motor.setComecocosPillado(cc, f);
                                                pillado = true; // Mal asunto
                                            } else if (f.getEstado() == Fantasma.ESTADO_HUYENDO) {
                                                motor.setFantasmaPillado(cc, f);
                                            }
                                        }
                                    }
                                }
                            }
                            // Comprobacion del tiempo restante
                            motor.actualizarTiempoRestante(retardo_hebra);
                            if (motor.getTiempoRestante() < 0) {
                                motor.setFinTiempo();
                            }

                            break;
                        case EstadoJuego.ESTADO_MURIENDO:
                            // Avanzar frame en la animacion del comecocos
                            if (tiempo_actual >= tiempo_ultimo_movimiento.get(0) + tiempo_frame_animacion_muerte) {
                                tiempo_ultimo_movimiento.set(0, tiempo_actual);
                                this.motor.avanzarAnimacionMuerte();
                            }

                            break;
                    }

                    // Dibujado
                    if (tiempo_actual >= tiempo_ultima_actualizacion + retardo_dibujado) {
                        tiempo_ultima_actualizacion = tiempo_actual;
                        this.actualizarDibujo();
                    }
                }
            }// end while(continuar)
        } catch (InterruptedException e) {
            System.out.println("Hilo GestorMovimiento interrumpido");
        }
    }

    /**
     * Detiene momentaneamente la ejecución de la hebra, haciendo que la Figura actual
     * quede parada.
     */
    synchronized public void suspender() {
        this.actualizarDibujo();
        suspendFlag = true;
    }

    /**
     * Reanuda el movimiento de la hebra. La Figura actual vuelve  a moverse.
     */
    public synchronized void reanudar() {
        this.actualizarDibujo();
        suspendFlag = false;
        notify();
    }

    /**
     * Termina la ejecución de la hebra.
     */
    public void parar() {
        continuar = false;
    }

    /**
     * Nos dice si la hebra está o no parada.
     * @return true si la hebra de movimiento está parada, false en otro caso
     */
    synchronized public boolean getParado() {
        return suspendFlag;
    }

    private void actualizarDibujo() {
        motor.actualizarDibujo();
    }
}
