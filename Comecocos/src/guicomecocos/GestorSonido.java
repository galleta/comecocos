package guicomecocos;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Clase encargada de gestionar y reproducir el sonido
 * 
 */
public class GestorSonido {

    public static final int SONIDO_INICIO = 0;
    public static final int SONIDO_WAKA = 1;
    public static final int SONIDO_MUERTE = 2;
    ComecocosFrame frame;

    public GestorSonido(ComecocosFrame cf) {
        this.frame = cf;
    }

    // Carga y reproduce un sonido
    public void reproducir(int sonido) {
        if (frame.cgui.haySonido()) {
            AudioInputStream sonido_entrante = null;
            try {
                Clip clip = null;
                InputStream ruta = frame.cgui.getRutaSonido(sonido);
                sonido_entrante = AudioSystem.getAudioInputStream(ruta);
                AudioFormat formato = sonido_entrante.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, formato);
                // ------------------------------ //
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(sonido_entrante);
                // ------------------------------ //
                clip.setFramePosition(0);
                clip.start();

            } catch (LineUnavailableException ex) {
                Logger.getLogger(GestorSonido.class.getName()).log(Level.SEVERE, null, ex);
                frame.cgui.setSonido(false);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(GestorSonido.class.getName()).log(Level.SEVERE, null, ex);
                frame.cgui.setSonido(false);
            } catch (IOException ex) {
                Logger.getLogger(GestorSonido.class.getName()).log(Level.SEVERE, null, ex);
                frame.cgui.setSonido(false);
            } finally {
                try {
                    sonido_entrante.close();
                } catch (IOException ex) {
                    Logger.getLogger(GestorSonido.class.getName()).log(Level.SEVERE, null, ex);
                    frame.cgui.setSonido(false);
                }
            }
        }
    }
}
