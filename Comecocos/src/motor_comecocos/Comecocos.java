package motor_comecocos;

import java.io.Serializable;

/**
 * Clase heredada de bicho para implementar a los comecocos
 * 
 */
public class Comecocos extends Bicho implements Serializable {
    public static final int VIVO = 0;
    public static final int MURIENDO = 1;
    public static final int MUERTO = 2;
    protected int direccion_sig; // Siguiente
    // Variable para la gestion de las direcciones
    protected int desechar_direccion;

    public Comecocos(MotorComecocos motor) {
        super(motor);
        this.desechar_direccion = 0;
        this.estado = VIVO;
    }

    @Override
    public void reset() {
        super.reset();
        this.estado = VIVO;
        this.direccion = Bicho.IZQUIERDA;
        this.direccion_sig = direccion;
    }

    @Override
    public void setDireccion(int dir) {
        if (this.estado == Comecocos.VIVO) {
            this.direccion_sig = dir;
            this.desechar_direccion = 0;

            if (this.pos_offset == 0) {
                if (this.motor.getMapa().puedeCambiarDireccion(direccion_sig, this.getPos())) {
                    this.direccion = (int) direccion_sig;
                }
            }
        }
    }

    // Aumenta la posicion del comecocos en la caja y si sobrepasa el maximo, avanza una casilla
    @Override
    public void aumentarPosOffset() {
        this.pos_offset++;
        if (this.pos_offset >= this.max_pos_offset) {
            this.pos_offset = 0;
            this.avanzar();

            // Comprobamos si puede cambiar de direccion
            if (this.motor.getMapa().puedeCambiarDireccion(direccion_sig, this.getPos())) {
                this.direccion = (int) direccion_sig;
                this.desechar_direccion = 0;
            } else {
                this.desechar_direccion++;
                if (this.desechar_direccion >= 3) {
                    this.direccion_sig = this.direccion;
                    this.desechar_direccion = 0;
                }
            }
        }
    }

    public int getDireccionSig() {
        return this.direccion_sig;
    }
}
