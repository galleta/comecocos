package motor_comecocos;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 *
 * 
 *
 * Representa a un comecocos o a un fantasma en el juego
 */
public abstract class Bicho implements Serializable {
    // Direcciones
    public static final int ARRIBA = 0;
    public static final int ABAJO = 1;
    public static final int IZQUIERDA = 2;
    public static final int DERECHA = 3;

    protected int estado;

    protected int direccion;
    // Desplazamiento del centro de una casilla
    protected int pos_offset;
    protected final int max_pos_offset = 4;
    // Tiempo de viaje entre casillas
    protected final int tiempo_casilla = 300;
    // Velocidad de movimiento que se necesita para moverse (a mas velocidad necesaria mas tiempo tardara en pasar de una casilla a otra)
    protected int velocidad;

    protected boolean oculto;
    protected Point posicion;
    protected Rectangle caja_colision;
    protected transient MotorComecocos motor;

    public Bicho(MotorComecocos motor_comecocos) {
        this.posicion = new Point(0, 0);

        direccion = IZQUIERDA;

        this.motor = motor_comecocos;
        velocidad = tiempo_casilla / max_pos_offset;
        oculto = false;
    }

    // Indica que el bicho no se debera de dibujar
    public void ocultar(boolean ocultar) {
        oculto = ocultar;
    }

    public boolean estaOculto() {
        return oculto;
    }

    public int getVelocidad() {
        return this.velocidad;
    }

    public void setVelocidad(int v) {
        this.velocidad = v;
    }

    public Point getPos() {
        return new Point(this.posicion);
    }

    public void setPos(Point x) {
        this.posicion = x;
        this.setPosOffset(0);
    }

    // Devuelve el numero de pasos maximo para pasar a otra casilla
    public int getMaxPosOffset() {
        return this.max_pos_offset;
    }

    public void setEstado(int estado_nuevo) {
        this.estado = estado_nuevo;
    }

    public int getEstado() {
        return this.estado;
    }
    
    public void setMotor(MotorComecocos motor) {
        this.motor = motor;
    }

    // Actualiza el rectangulo de colision del bicho
    private void actualizarRectangulo() {
        int ancho_celda = motor.getEstadoJuego().ancho_celda;
        int tam_bicho = motor.getEstadoJuego().tam_bichos;

        int x_pos_offset = 0;
        int y_pos_offset = 0;
        switch (this.getDireccion()) {
            case Bicho.IZQUIERDA:
                x_pos_offset = (int) (-1 * (ancho_celda * (this.getPosOffset() / (this.getMaxPosOffset() * 1.0))));
                break;
            case Bicho.ARRIBA:
                y_pos_offset = (int) (-1 * (ancho_celda * (this.getPosOffset() / (this.getMaxPosOffset() * 1.0))));
                break;
            case Bicho.ABAJO:
                y_pos_offset = (int) (ancho_celda * (this.getPosOffset() / (this.getMaxPosOffset() * 1.0)));
                break;
            case Bicho.DERECHA:
                x_pos_offset = (int) (ancho_celda * (this.getPosOffset() / (this.getMaxPosOffset() * 1.0)));
                break;
        }
        Point pos = new Point(x_pos_offset + (posicion.x * ancho_celda) + (ancho_celda / 2) - (tam_bicho / 2), y_pos_offset + (posicion.y * ancho_celda) + (ancho_celda / 2) - (tam_bicho / 2));
        this.setRectangulo(new Rectangle(pos.x, pos.y, tam_bicho, tam_bicho));
    }

    // Aumenta la posicion del comecocos en la caja y si sobrepasa el maximo, avanza una casilla
    public void aumentarPosOffset() {
        this.pos_offset++;
        if (this.pos_offset >= this.max_pos_offset) {
            this.pos_offset = 0;
            this.avanzar();
        }
    }

    // Establece el paso que tiene el bicho dentro de la casilla
    public void setPosOffset(int offpos) {
        this.pos_offset = offpos;
    }

    public int getPosOffset() {
        return this.pos_offset;
    }

    /**
     * Mueve la Figura actual una casilla en la dirección indicado por direccion (ABAJO,IZQUIERDA o ARRIBA)
     */
    public void avanzar() {
        switch (direccion) {
            case ABAJO:
                posicion.y++;
                break;

            case ARRIBA:
                posicion.y--;
                break;

            case IZQUIERDA:
                posicion.x--;
                break;

            case DERECHA:
                posicion.x++;
                break;
        }
        // Filtro para los bordes
        if (posicion.x < 0) {
            posicion.x = motor.getMapa().getAncho() - 1;
        } else if (posicion.x > motor.getMapa().getAncho() - 1) {
            posicion.x = 0;
        } else if (posicion.y < 0) {
            posicion.y = motor.getMapa().getAlto() - 1;
        } else if (posicion.y > motor.getMapa().getAlto() - 1) {
            posicion.y = 0;
        }
        this.setPos(posicion);
    }

    public void setDireccion(int dir) {
        this.direccion = dir;
    }

    public int getDireccion() {
        return this.direccion;
    }

    private void setRectangulo(Rectangle rectangle) {
        this.caja_colision = rectangle;
    }

    public Rectangle getRectangulo() {
        this.actualizarRectangulo();
        return new Rectangle(this.caja_colision);
    }

    void reset() {
        this.posicion = new Point(0, 0);

        direccion = IZQUIERDA;

        velocidad = tiempo_casilla / max_pos_offset;
        oculto = false;
    }
}
