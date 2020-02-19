package guicomecocos;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import motor_comecocos.DatosMapa;

/**
 * LLevaría los datos de las imagenes y sonidos del juego
 * 
 */
public final class ConfiguracionGUI {

    public static final int TECLA_ARRIBA_J1 = 0;
    public static final int TECLA_ABAJO_J1 = 1;
    public static final int TECLA_DERECHA_J1 = 2;
    public static final int TECLA_IZQUIERDA_J1 = 3;
    public static final int TECLA_ARRIBA_J2 = 4;
    public static final int TECLA_ABAJO_J2 = 5;
    public static final int TECLA_DERECHA_J2 = 6;
    public static final int TECLA_IZQUIERDA_J2 = 7;
    public static final int TECLA_PAUSA = 8;

    private int tema = 0;
    private boolean sonido = true;
    // Tamaño de los sprites
    private int tam_sprites = 21;
    private Map<Integer, ArrayList<BufferedImage>> sprites_originales;
    ArrayList<DatosMapa> mapas;
    ArrayList<ArrayList<String>> ruta_sonidos;
    private ArrayList<String> ruta_sprites;
    private String base_sonidos = "/data/sonidos/";
    private String base_sprites = "/data/sprites/";
    // Valor de las teclas para el movimiento
    private ArrayList<Integer> teclas;
    // Colores del mapa
    private Color borde;
    private Color fondo;
    private Color punto;

    // Genera toda la configuracion por defecto de la interfaz
    public ConfiguracionGUI() {
        tema = 0;
        borde = Color.RED;
        fondo = Color.BLACK;
        punto = Color.YELLOW;

        // Rutas de los sprites
        ruta_sprites = new ArrayList<String>();
        ruta_sprites.add(base_sprites + "arcade.png");
        ruta_sprites.add(base_sprites + "monstruo_galletas.png");
        ruta_sprites.add(base_sprites + "chiquito.png");
        ruta_sprites.add(base_sprites + "super_mario.png");

        // Cargar los diferentes mapas
        mapas = new ArrayList<DatosMapa>();
        this.cargarMapas();

        // Indicar las rutas de los sonidos
        ruta_sonidos = new ArrayList<ArrayList<String>>();
        ArrayList<String> sonidos_arcade = new ArrayList<String>();
        ArrayList<String> sonidos_monstruo_galletas = new ArrayList<String>();
        ArrayList<String> sonidos_chiquito = new ArrayList<String>();

        sonidos_arcade.add(base_sonidos + "intro01.wav");
        sonidos_monstruo_galletas.add(base_sonidos + "intro01.wav");
        sonidos_chiquito.add(base_sonidos + "intro02.wav");

        sonidos_arcade.add(base_sonidos + "waka01.wav");
        sonidos_monstruo_galletas.add(base_sonidos + "waka02.wav");
        sonidos_chiquito.add(base_sonidos + "waka03.wav");

        sonidos_arcade.add(base_sonidos + "game_over01.wav");
        sonidos_monstruo_galletas.add(base_sonidos + "game_over01.wav");
        sonidos_chiquito.add(base_sonidos + "game_over02.wav");

        ruta_sonidos.add(sonidos_arcade);
        ruta_sonidos.add(sonidos_monstruo_galletas);
        ruta_sonidos.add(sonidos_chiquito);
        ruta_sonidos.add(sonidos_arcade);

        // Teclas por defecto
        this.setTeclasPorDefecto();

        // Indicar el tema por defecto
        this.cambiarTema(tema);
    }

    // Carga los sprites del tema
    public void cargarSprites() {
        this.sprites_originales = new CargadorSprite().cargar(this.getClass().getResource(this.ruta_sprites.get(tema)).toString(), tam_sprites);
    }

    // Devuelve los sprites de un tema
    public Map<Integer, ArrayList<BufferedImage>> obtenerSprites() {
        return new HashMap<Integer, ArrayList<BufferedImage>>(this.sprites_originales);
    }

    void cambiarTema(int nuevo_tema) {
        this.tema = nuevo_tema;
        this.cargarSprites();
    }

    public void setTeclasPorDefecto() {
        this.teclas = new ArrayList<Integer>();

        this.teclas.add(38);
        this.teclas.add(40);
        this.teclas.add(39);
        this.teclas.add(37);
        this.teclas.add(87);
        this.teclas.add(83);
        this.teclas.add(68);
        this.teclas.add(65);
        this.teclas.add(32);
    }

    public void setTecla(int tecla, int valor) {
        this.teclas.set(tecla, valor);
    }

    public int getTecla(int tecla) {
        return this.teclas.get(tecla);
    }

    InputStream getRutaSonido(int sonido) {
        return getClass().getResourceAsStream(this.ruta_sonidos.get(tema).get(sonido));
    }

    // Activa/Desactiva el sonido
    void setSonido(boolean b) {
        this.sonido = b;
    }

    boolean haySonido() {
        return this.sonido;
    }

    int getTema() {
        return this.tema;
    }

    Color getColorBorde() {
        return this.borde;
    }

    void setColorBorde(Color nuevo) {
        this.borde = nuevo;
    }

    Color getColorFondo() {
        return this.fondo;
    }

    void setColorFondo(Color nuevo) {
        this.fondo = nuevo;
    }

    Color getColorPunto() {
        return this.punto;
    }

    void setColorPunto(Color nuevo) {
        this.punto = nuevo;
    }

    // Establece los mapas del juego
    private void cargarMapas() {
        DatosMapa mapa = new DatosMapa();

        mapa.alto = 31;
        mapa.ancho = 28;
        String mapa_predefinido_1[] = {
            "1AAAAAAAAAAAAfeAAAAAAAAAAAA2",
            "I............di............D",
            "I.5BB6.5BBB6.di.5BBB6.5BB6.D",
            "IoD  I.D   I.di.D   I.D  IoD",
            "I.7AA8.7AAA8.78.7AAA8.7AA8.D",
            "I..........................D",
            "I.5bb6.56.5bbbbbb6.56.5bb6.D",
            "I.7aa8.di.7aa65aa8.di.7aa8.D",
            "I......di....di....di......D",
            "3BBBB6.d7bb6 di 5bb8i.5BBBB4",
            "     I.d5aa8 78 7aa6i.D     ",
            "     I.di          di.D     ",
            "     I.di 5BPPPPB6 di.D     ",
            "AAAAA8.78 D      I 78.7AAAAA",
            "      .   D      I   .      ",
            "BBBBB6.56 D      I 56.5BBBBB",
            "     I.di 7AAAAAA8 di.D     ",
            "     I.di          di.D     ",
            "     I.di 5bbbbbb6 di.D     ",
            "1AAAA8.78 7aa65aa8 78.7AAAA2",
            "I............di............D",
            "I.5bb6.5bbb6.di.5bbb6.5bb6.D",
            "I.7a6i.7aaa8.78.7aaa8.d5a8.D",
            "Io..di................di..oD",
            "gb6.di.56.5bbbbbb6.56.di.5bm",
            "ha8.78.di.7aa65aa8.di.78.7an",
            "I......di....di....di......D",
            "I.5bbbb87bb6.di.5bb87bbbb6.D",
            "I.7aaaaaaaa8.78.7aaaaaaaa8.D",
            "I..........................D",
            "3BBBBBBBBBBBBBBBBBBBBBBBBBB4"
        };

        // Posiciones comecocos
        mapa.posiciones_iniciales.add(new Point(12, 17));
        mapa.posiciones_iniciales.add(new Point(14, 17));

        // Posiciones fantasmas
        mapa.posiciones_iniciales.add(new Point(12, 14));
        mapa.posiciones_salida.add(new Point(12, 11));
        mapa.posiciones_iniciales.add(new Point(13, 14));
        mapa.posiciones_salida.add(new Point(13, 11));
        mapa.posiciones_iniciales.add(new Point(14, 14));
        mapa.posiciones_salida.add(new Point(14, 11));
        mapa.posiciones_iniciales.add(new Point(15, 14));
        mapa.posiciones_salida.add(new Point(15, 11));
        mapa.mapa = mapa_predefinido_1;
        mapa.nombre = "Estandar";

        this.mapas.add(mapa);

        mapa = new DatosMapa();

        mapa.alto = 21;
        mapa.ancho = 29;
        String mapa_predefinido_2[] = {
            "1AAAAAAAAAAAAAAAAAAAAAAAAAAA2",
            "Io.........................oD",
            "I.5BBBBBB6.5BBB6.5BBBBBBBB6.D",
            "I.D      I.D   I.D        I.D",
            "I.D 1AAAA8.7AAA8.7AA2  1AA8.D",
            "I.D I...............D  D....D",
            "I.D 3BB6.5BBBBBBBB6.D  D.56.D",
            "I.D    I D        I D  D.DI.D",
            "I.D 1AA8.7AAAAAAAA8.D  D.DI.D",
            " .D I...............D  D.DI. ",
            "I.D I.5BPPPPB6.1AA2.D  D.DI.D",
            "I.D I.D      I.I  D.D  D.DI.D",
            "I.D I.D      I.I  6B4  D.DI.D",
            "I.D I.D      I.I       D.DI.D",
            "I.7A8.7AAAAAA8.3BBBBBBB4.78.D",
            "I...........................D",
            "I.5BBBBBBBBBBBBBBBBBBBBBBB6.D",
            "I.D                       I.D",
            "I.7AAAAAAAAAAAAAAAAAAAAAAA8.D",
            "Io.........................oD",
            "3BBBBBBBBBBBBBBBBBBBBBBBBBBB4"
        };

        // Posiciones comecocos
        mapa.posiciones_iniciales.add(new Point(8, 7));
        mapa.posiciones_iniciales.add(new Point(19, 7));

        // Posiciones fantasmas
        mapa.posiciones_iniciales.add(new Point(8, 12));
        mapa.posiciones_salida.add(new Point(8, 9));
        mapa.posiciones_iniciales.add(new Point(9, 12));
        mapa.posiciones_salida.add(new Point(9, 9));
        mapa.posiciones_iniciales.add(new Point(10, 12));
        mapa.posiciones_salida.add(new Point(10, 9));
        mapa.posiciones_iniciales.add(new Point(11, 12));
        mapa.posiciones_salida.add(new Point(11, 9));
        mapa.mapa = mapa_predefinido_2;
        mapa.nombre = "Fantasia";

        this.mapas.add(mapa);

        mapa = new DatosMapa();

        mapa.alto = 47;
        mapa.ancho = 27;
        String mapa_predefinido_3[] = {
            "1AAAAAAAAAAAAAAAAAAAAAAAAA2",
            "Io.......................oD",
            "I.5BBBBBBBBB6.5BBBBBBBBB6.D",
            "I.D         I.D         I.D",
            "I.D 1AAAAAAA8.D 1AAAAA2 I.D",
            "I.D I.........D I.....D I.D",
            "I.D 3BBBBBBBBB4 I.5B6.D I.D",
            "I.D             I.D I.D I.D",
            "I.D 1AAAAAAAAAAA8.D I.D I.D",
            "I.D I.............D I.D I.D",
            "I.D 3BBBBBBB6.5B6.D I.D I.D",
            "I.D         I.D I.D I.D I.D",
            "I.7AAAAAAAAA8.D I.D IoD I.D",
            "I.............D I.D 3B4 I.D",
            "I.5B6.5BB6.5BB8 I.D     I.D",
            "I.D I.D  I.D    I.D     I.D",
            "I.7A8.D  I.D 1AA8.D     I.D",
            "I.....D  I.D Io...D     I.D",
            "I.5BBB8  I.D 3BBBB4     I.D",
            "I.D      I.D            I.D",
            "I.D  1AAA8.7AAAAAAAAAAAA8.D",
            "I.D  I....................D",
            "I.D  I.5BPPPPPPPPPPB6.5B6.D",
            "I.D  I.D            I.D I.D",
            "I.D  I.D            I.D I.D",
            "I.D  I.D            I.D I.D",
            "I.D  I.7AAAAAAAAAAAA8.D I.D",
            "I D  I................D I D",
            "I.D  3BBBB6.5BBBBBBBBB4 I.D",
            "I.D       I.D           I.D",
            "I.7AAAAAAA8.7AAAAAAAAAAA8.D",
            "I.........................D",
            "I.5BBBBBBBBBBBBBBBBBBBBB6.D",
            "I.D                     I.D",
            "I.7AAAAAAA2  5B6 5B6  1A8.D",
            "I.........D  DoI D.I  I...D",
            "I.5BB6.5BB4  D.I D.I  3B6.D",
            "I.D  I.D     D.I D.I    I.D",
            "I.D  3B4     D.3B4.7AAAA8.D",
            "I.D          D............D",
            "I.7AAAAAAA2  7AAAAAAAAAA2.D",
            "I.........D             D.D",
            "I.1BBBBBBB4             D.D",
            "I.I                     D.D",
            "I.3BBBBBBBBBBBBBBBBBBBBB4.D",
            "Io.......................oD",
            "3BBBBBBBBBBBBBBBBBBBBBBBBB4"
        };


        // Posiciones comecocos
        mapa.posiciones_iniciales.add(new Point(1, 27));
        mapa.posiciones_iniciales.add(new Point(25, 27));

        // Posiciones fantasmas
        mapa.posiciones_iniciales.add(new Point(10, 24));
        mapa.posiciones_salida.add(new Point(10, 21));
        mapa.posiciones_iniciales.add(new Point(12, 24));
        mapa.posiciones_salida.add(new Point(12, 21));
        mapa.posiciones_iniciales.add(new Point(14, 24));
        mapa.posiciones_salida.add(new Point(14, 21));
        mapa.posiciones_iniciales.add(new Point(16, 24));
        mapa.posiciones_salida.add(new Point(16, 21));
        mapa.mapa = mapa_predefinido_3;
        mapa.nombre = "Laberinto";

        this.mapas.add(mapa);
    }
}
