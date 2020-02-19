package guicomecocos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import motor_comecocos.Bicho;
import motor_comecocos.Comecocos;
import motor_comecocos.EstadoJuego;
import motor_comecocos.Fantasma;
import motor_comecocos.Mapa;

/**
 * Panel que dibuja el juego
 * 
 */
public class PanelMapa extends javax.swing.JPanel {

    private ComecocosFrame frame;
    // Tamaño de una casilla
    private int ancho_celda = -1;
    // Factor del tamaño de los bichos en la casilla
    private float factor_comecocos = (float) 1.5;
    // Colorcillos
    private Color color_texto_fijo = Color.RED;
    private Color color_texto = Color.WHITE;
    private Color color_barra_fondo = Color.BLACK;
    // Tamaño de la barra de estado
    private int alto_barra = 50;
    // Sprites de los personajes
    Map<Integer, ArrayList<Image>> sprites;
    // Sprites achicados para las vidas del jugador
    ArrayList<Image> sprites_vida;
    // Tamaño del comecocos y los fantasmas (un poco mas grandes que una casilla)
    private int tam_bichos = 0;
    // Imagen de la presentacion del juego
    private Image presentacion;
    // Rectangulos indicando el tamaño del texto de la barra de estado
    Rectangle2D r_nivel;
    Rectangle2D r_tiempo_jugado;
    Rectangle2D r_puntos;
    Rectangle2D r_id_jugador;

    /** Creates new form PanelMapa */
    public PanelMapa() {
        initComponents();
    }

    public PanelMapa(ComecocosFrame cf) {
        this();
        frame = cf;
        this.presentacion = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/data/imagenes/inicio.jpg"));
        this.setFocusable(true);
        this.requestFocus();
    }

    @Override
    public void update(Graphics g) {
        //super.paintComponent(g);
    }

    public void recargarImagenes() {
        ancho_celda = -1;
    }

    // Dibuja el mapa. Si ancho_celda == -1, recalcula el tamaño de todos los objetos
    @Override
    public void paintComponent(Graphics g) {
        if (this.frame != null) {
            if (frame.getMotor() != null) {
                // Si cambia el tamaño de la ventana, reescalamos los objetos
                if (ancho_celda == -1) {
                    ancho_celda = Math.min(this.getWidth() / frame.getMotor().getMapa().getAncho(), (this.getHeight() - alto_barra) / frame.getMotor().getMapa().getAlto());
                    // Reescalar las imagenes
                    sprites = new HashMap<Integer, ArrayList<Image>>();
                    Map<Integer, ArrayList<BufferedImage>> sprites_originales = frame.cgui.obtenerSprites();
                    for (int i = 0; i < sprites_originales.size(); i++) {
                        sprites.put(i, new ArrayList<Image>());
                        ArrayList<BufferedImage> sprite_a_redimensionar = sprites_originales.get(i);
                        ListIterator<BufferedImage> j = sprite_a_redimensionar.listIterator();
                        while (j.hasNext()) {
                            BufferedImage bf = j.next();
                            sprites.get(i).add(bf.getScaledInstance((int) (ancho_celda * factor_comecocos), (int) (ancho_celda * factor_comecocos), BufferedImage.SCALE_SMOOTH));
                        }

                        tam_bichos = (int) (ancho_celda * factor_comecocos);
                        frame.getMotor().setTamBichos(ancho_celda, (int) (tam_bichos / 2));
                    }
                    // Calcular los rectangulos de los textos
                    Graphics2D g2d = (Graphics2D) g;
                    Font f = g2d.getFont();
                    r_nivel = f.getStringBounds("Tiempo: 000 Nivel: 00", g2d.getFontRenderContext());
                    r_tiempo_jugado = f.getStringBounds("Tiempo jugado: 00:00", g2d.getFontRenderContext());
                    r_puntos = f.getStringBounds("00000000", g2d.getFontRenderContext());
                    r_id_jugador = f.getStringBounds("1P: ", g2d.getFontRenderContext());
                    sprites_vida = new ArrayList<Image>();
                    BufferedImage sprite = (BufferedImage) sprites_originales.get(CargadorSprite.COCO1DER).get(0);
                    sprites_vida.add(sprite.getScaledInstance((int) r_id_jugador.getWidth(), (int) r_id_jugador.getWidth(), BufferedImage.SCALE_SMOOTH));
                    sprite = (BufferedImage) sprites_originales.get(CargadorSprite.COCO2DER).get(0);
                    sprites_vida.add(sprite.getScaledInstance((int) r_id_jugador.getWidth(), (int) r_id_jugador.getWidth(), BufferedImage.SCALE_SMOOTH));
                }

                // Limpiamos la imagen
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                // Dibujamos los elementos
                this.dibujarMapa(g);
                if (frame.getMotor().hayFruta()) {
                    this.dibujarFruta(g);
                }
                this.dibujarFantasmas(g);
                this.dibujarComecocos(g);
                this.dibujarBarra(g);

            } else { // Dibujar la presentacion
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
                int x = (this.getWidth() / 2) - (this.presentacion.getWidth(this) / 2);
                int y = (this.getHeight() / 2) - (this.presentacion.getHeight(this) / 2);

                g2d.drawImage(this.presentacion, x, y, this);
            }
        }
    }

    public void actualizar() {
        this.repaint();
    }

    // Dibuja el sprite de la fruta
    public void dibujarFruta(java.awt.Graphics go) {
        Graphics2D g = (Graphics2D) go;
        int x_offset = (this.getWidth() - frame.getMotor().getMapa().getAncho() * ancho_celda) / 2;
        int y_offset = alto_barra;

        AffineTransform af = g.getTransform();
        Image imagen = sprites.get(CargadorSprite.FRUTAS).get(frame.getMotor().getTipoFruta() - 1);
        Point pos_fruta = frame.getMotor().getPosFruta();
        Point pos = new Point(x_offset + (pos_fruta.x * ancho_celda) + (ancho_celda / 2) - (tam_bichos / 2), y_offset + (pos_fruta.y * ancho_celda) + (ancho_celda / 2) - (tam_bichos / 2));
        g.translate(pos.x, pos.y);
        g.drawImage(imagen, null, this);
        // Volvemos al estado anterior
        g.setTransform(af);
    }

    // Dibuja un bicho
    public void dibujarFigura(Bicho b, int tipo, java.awt.Graphics go) {
        if (!b.estaOculto()) {
            Graphics2D g = (Graphics2D) go;
            int x_offset = (this.getWidth() - frame.getMotor().getMapa().getAncho() * ancho_celda) / 2;
            int y_offset = alto_barra;
            int x_pos_offset = 0;
            int y_pos_offset = 0;
            switch (b.getDireccion()) {
                case Bicho.IZQUIERDA:
                    x_pos_offset = (int) (-1 * (ancho_celda * (b.getPosOffset() / (b.getMaxPosOffset() * 1.0))));
                    break;
                case Bicho.ARRIBA:
                    y_pos_offset = (int) (-1 * (ancho_celda * (b.getPosOffset() / (b.getMaxPosOffset() * 1.0))));
                    break;
                case Bicho.ABAJO:
                    y_pos_offset = (int) (ancho_celda * (b.getPosOffset() / (b.getMaxPosOffset() * 1.0)));
                    break;
                case Bicho.DERECHA:
                    x_pos_offset = (int) (ancho_celda * (b.getPosOffset() / (b.getMaxPosOffset() * 1.0)));
                    break;
            }
            // Guardamos el estado actual del graficos
            AffineTransform af = g.getTransform();
            Point pos = new Point(x_pos_offset + x_offset + (b.getPos().x * ancho_celda) + (ancho_celda / 2) - (tam_bichos / 2), y_pos_offset + y_offset + (b.getPos().y * ancho_celda) + (ancho_celda / 2) - (tam_bichos / 2));
            g.translate(pos.x, pos.y);

            if (b instanceof Comecocos) {
                Image imagen;
                if (b.getEstado() == Comecocos.VIVO) {
                    imagen = sprites.get(CargadorSprite.COCO1ARR + b.getDireccion() + ((tipo % 2) * 4)).get((int) (sprites.get(CargadorSprite.COCO1ARR).size() * (b.getPosOffset() / (b.getMaxPosOffset() * 1.0))));
                } else {
                    imagen = sprites.get(CargadorSprite.MUERTECOCO).get(this.frame.getMotor().getAnimMuerte());
                }
                g.drawImage(imagen, null, this);
            } else if (b instanceof Fantasma) {
                switch (b.getEstado()) {
                    case Fantasma.ESTADO_HUYENDO:
                        // Dibujamos el fantasma huyendo (y parpadeando si es el caso)
                        long t = ((Fantasma) b).tiempoParaFinalizarHuida();
                        int parpadeo = 0;
                        if (t >= 2000 && t < 5000) {
                            if (System.currentTimeMillis() % 200 > 100) {
                                parpadeo = 1;
                            }
                        } else if (t < 2000) {
                            if (System.currentTimeMillis() % 100 > 50) {
                                parpadeo = 1;
                            }
                        }
                        g.drawImage(sprites.get(CargadorSprite.COMIBLES).get((parpadeo * 2) + b.getPosOffset() % 2), null, this);
                        break;

                    case Fantasma.ESTADO_VOLVIENDO:
                    case Fantasma.ESTADO_ENTRANDO:
                        g.drawImage(sprites.get(CargadorSprite.OJOS).get(b.getDireccion()), null, this);
                        break;

                    default:
                        g.drawImage(sprites.get(CargadorSprite.FANTROJOARR + b.getDireccion() + ((tipo % 4) * 4)).get(b.getPosOffset() % 2), null, this);
                        break;
                }
            }
            // Volvemos al estado anterior
            g.setTransform(af);
        }
    }

    // Dibuja los fantasmas
    public void dibujarFantasmas(java.awt.Graphics g) {
        ListIterator<Fantasma> i = frame.getMotor().getFantasmas().listIterator();
        int j = 0; // Representa el tipo de bicho a dibujar (color del fantasma)
        while (i.hasNext()) {
            Fantasma f = i.next();
            this.dibujarFigura(f, j, g);
            j++;
        }
    }

    // Dibuja los comecocos
    public void dibujarComecocos(java.awt.Graphics g) {
        ListIterator<Comecocos> i = frame.getMotor().getComecocos().listIterator();
        int j = 0; // Representa el tipo de bicho a dibujar (jugador comecocos)
        while (i.hasNext()) {
            Comecocos f = i.next();
            this.dibujarFigura(f, j, g);
            j++;
        }
    }

    // Dibuja la barra de estado
    public void dibujarBarra(java.awt.Graphics go) {
        Graphics2D g = (Graphics2D) go;
        int alto_linea = 20;
        int x_offset = (this.getWidth() - frame.getMotor().getMapa().getAncho() * ancho_celda) / 2;
        int ancho_barra = (this.getWidth() - (x_offset * 2));
        AffineTransform af = g.getTransform();
        g.translate(x_offset, 0);

        g.setColor(color_barra_fondo);
        g.fillRect(x_offset, 0, ancho_celda * frame.getMotor().getMapa().getAncho(), alto_barra);

        int jugadores = frame.getMotor().getNumJugadores();

        // Preparamos los textos
        String tiempo_restante = "Tiempo: " + String.format("%03d", (int) (frame.getMotor().getTiempoRestante() / 1000));
        String nivel = "Nivel: " + String.format("%02d", frame.getMotor().getNivel());
        long t_jugado = frame.getMotor().getTiempoJuego() / 1000;
        String tiempo_jugado = "Tiempo jugado: " + String.format("%02d", t_jugado / 60) + ":" + String.format("%02d", t_jugado % 60);

        String texto_nivel = tiempo_restante + " " + nivel;

        g.setColor(color_texto_fijo);
        g.setColor(color_texto);

        // Dibujamos los textos centrales
        g.drawString(texto_nivel, (ancho_barra / 2) - (int) (r_nivel.getWidth() / 2), alto_barra - (alto_linea * 2));
        g.drawString(tiempo_jugado, (ancho_barra / 2) - (int) (r_tiempo_jugado.getWidth() / 2), alto_barra - (alto_linea * 1));

        // Dibujamos los datos del jugador 1
        String puntos_j1 = String.format("%08d", frame.getMotor().getPuntos(0));
        int vidas_j1 = frame.getMotor().getVidas(0);
        g.drawString("1P: ", 0, alto_barra - (alto_linea * 2) + 4);
        g.drawString(puntos_j1, 0, alto_barra - (alto_linea * 1));
        for (int i = 0; i < vidas_j1; i++) {
            int ancho = (int) (this.r_id_jugador.getWidth() * (i + 1));
            g.drawImage(sprites_vida.get(0), ancho, alto_barra - (alto_linea * 2) - (sprites_vida.get(0).getHeight(this) / 2), this);
        }

        if (jugadores > 1) { // Dibujamos los datos del jugador 2
            String puntos_j2 = String.format("%08d", frame.getMotor().getPuntos(1));
            int vidas_j2 = frame.getMotor().getVidas(1);
            int j2_offset = ancho_barra - (int) (this.r_id_jugador.getWidth() * 4);
            g.drawString("2P: ", j2_offset, alto_barra - (alto_linea * 2) + 4);
            for (int i = 0; i < vidas_j2; i++) {
                int ancho = j2_offset + (int) (this.r_id_jugador.getWidth() * (i + 1));
                g.drawImage(sprites_vida.get(1), ancho, alto_barra - (alto_linea * 2) - (sprites_vida.get(0).getHeight(this) / 2), this);
            }
            g.drawString(puntos_j2, j2_offset, alto_barra - (alto_linea * 1));
        }

        // Volvemos al estado anterior del graficos g
        g.setTransform(af);
    }

    // Dibuja una casilla
    public void dibujarCasilla(java.awt.Graphics g, int x, int y) {
        Mapa mapa = frame.getMotor().getMapa();

        int x_offset = (this.getWidth() - mapa.getAncho() * ancho_celda) / 2;
        int y_offset = alto_barra;

        // Pintamos el fondo de la casilla
        g.setColor(frame.cgui.getColorFondo());
        //g.drawRect(x_offset + x * ancho_celda, y_offset + y * ancho_celda, ancho_celda, ancho_celda);
        g.fillRect(x_offset + x * ancho_celda, y_offset + y * ancho_celda, ancho_celda, ancho_celda);

        // Pintamos la pared
        g.setColor(frame.cgui.getColorBorde());
        int tam_punto_gordo = ancho_celda / 2;
        int tam_punto = ancho_celda / 5;

        int ini_x = x_offset + x * ancho_celda;
        int ini_y = y_offset + y * ancho_celda;

        switch (mapa.get(x, y)) {
            case '.': // Punto
                g.setColor(frame.cgui.getColorPunto());
                g.drawOval(ini_x + (ancho_celda / 2) - tam_punto / 2, ini_y + (ancho_celda / 2) - tam_punto / 2, tam_punto, tam_punto);
                g.fillOval(ini_x + (ancho_celda / 2) - tam_punto / 2, ini_y + (ancho_celda / 2) - tam_punto / 2, tam_punto, tam_punto);
                break;
            case 'P': // Puerta de fantasmas
                g.drawLine(ini_x, ini_y + ((ancho_celda / 4) * 3), ini_x + (ancho_celda - 1), ini_y + ((ancho_celda / 4) * 3));
                break;
            case 'o': // Punto gordo
                g.setColor(frame.cgui.getColorPunto());
                g.drawOval(ini_x + (ancho_celda / 2) - (tam_punto_gordo / 2), ini_y + (ancho_celda / 2) - (tam_punto_gordo / 2), tam_punto_gordo, tam_punto_gordo);
                g.fillOval(ini_x + (ancho_celda / 2) - (tam_punto_gordo / 2), ini_y + (ancho_celda / 2) - (tam_punto_gordo / 2), tam_punto_gordo, tam_punto_gordo);
                break;
            case ' ': // Nada
                break;
            case '1':
                g.drawLine(ini_x, ini_y, ini_x + ancho_celda - 1, ini_y);
                g.drawLine(ini_x, ini_y, ini_x, ini_y + ancho_celda - 1);
                g.drawLine(ini_x + ((ancho_celda - 1) / 2), ini_y + ancho_celda - 1, ini_x + ancho_celda - 1, ini_y + ((ancho_celda - 1) / 2));
                break;
            case '2':
                g.drawLine(ini_x, ini_y, ini_x + ancho_celda - 1, ini_y);
                g.drawLine(ini_x + ancho_celda - 1, ini_y, ini_x + ancho_celda - 1, ini_y + ancho_celda - 1);
                g.drawLine(ini_x, ini_y + ((ancho_celda - 1) / 2), ini_x + ((ancho_celda - 1) / 2), ini_y + ancho_celda - 1);
                break;
            case '3':
                g.drawLine(ini_x, ini_y, ini_x, ini_y + (ancho_celda - 1));
                g.drawLine(ini_x, ini_y + (ancho_celda - 1), ini_x + (ancho_celda - 1), ini_y + (ancho_celda - 1));
                g.drawLine(ini_x + ((ancho_celda - 1) / 2), ini_y, ini_x + (ancho_celda - 1), ini_y + ((ancho_celda - 1) / 2));
                break;
            case '4':
                g.drawLine(ini_x + ancho_celda - 1, ini_y, ini_x + ancho_celda - 1, ini_y + ancho_celda - 1);
                g.drawLine(ini_x, ini_y + (ancho_celda - 1), ini_x + (ancho_celda - 1), ini_y + (ancho_celda - 1));
                g.drawLine(ini_x + ((ancho_celda - 1) / 2), ini_y, ini_x, ini_y + ((ancho_celda - 1) / 2));
                break;
            case '5':
                g.drawLine(ini_x + ((ancho_celda - 1) / 2), ini_y + ancho_celda - 1, ini_x + ancho_celda - 1, ini_y + ((ancho_celda - 1) / 2));
                break;
            case '6':
                g.drawLine(ini_x, ini_y + ((ancho_celda - 1) / 2), ini_x + ((ancho_celda - 1) / 2), ini_y + ancho_celda - 1);
                break;
            case '7':
                g.drawLine(ini_x + ((ancho_celda - 1) / 2), ini_y, ini_x + (ancho_celda - 1), ini_y + ((ancho_celda - 1) / 2));
                break;
            case '8':
                g.drawLine(ini_x + ((ancho_celda - 1) / 2), ini_y, ini_x, ini_y + ((ancho_celda - 1) / 2));
                break;
            case 'A':
                g.drawLine(ini_x, ini_y, ini_x + (ancho_celda - 1), ini_y);
                g.drawLine(ini_x, ini_y + ((ancho_celda - 1) / 2), ini_x + (ancho_celda - 1), ini_y + ((ancho_celda - 1) / 2));
                break;
            case 'B':
                g.drawLine(ini_x, ini_y + (ancho_celda - 1), ini_x + (ancho_celda - 1), ini_y + (ancho_celda - 1));
                g.drawLine(ini_x, ini_y + ((ancho_celda - 1) / 2), ini_x + (ancho_celda - 1), ini_y + ((ancho_celda - 1) / 2));
                break;
            case 'a':
                g.drawLine(ini_x, ini_y + ((ancho_celda - 1) / 2), ini_x + (ancho_celda - 1), ini_y + ((ancho_celda - 1) / 2));
                break;
            case 'b':
                g.drawLine(ini_x, ini_y + ((ancho_celda - 1) / 2), ini_x + (ancho_celda - 1), ini_y + ((ancho_celda - 1) / 2));
                break;
            case 'I':
                g.drawLine(ini_x, ini_y, ini_x, ini_y + (ancho_celda - 1));
                g.drawLine(ini_x + ((ancho_celda - 1) / 2), ini_y, ini_x + ((ancho_celda - 1) / 2), ini_y + (ancho_celda - 1));
                break;
            case 'i':
                g.drawLine(ini_x + ((ancho_celda - 1) / 2), ini_y, ini_x + ((ancho_celda - 1) / 2), ini_y + (ancho_celda - 1));
                break;
            case 'D':
                g.drawLine(ini_x + (ancho_celda - 1), ini_y, ini_x + (ancho_celda - 1), ini_y + (ancho_celda - 1));
                g.drawLine(ini_x + ((ancho_celda - 1) / 2), ini_y, ini_x + ((ancho_celda - 1) / 2), ini_y + (ancho_celda - 1));
                break;
            case 'd':
                g.drawLine(ini_x + ((ancho_celda - 1) / 2), ini_y, ini_x + ((ancho_celda - 1) / 2), ini_y + (ancho_celda - 1));
                break;
            case 'e':
                g.drawLine(ini_x, ini_y, ini_x + ancho_celda - 1, ini_y);
                g.drawLine(ini_x + ((ancho_celda - 1) / 2), ini_y + ancho_celda - 1, ini_x + ancho_celda - 1, ini_y + ((ancho_celda - 1) / 2));
                break;
            case 'f':
                g.drawLine(ini_x, ini_y, ini_x + ancho_celda - 1, ini_y);
                g.drawLine(ini_x, ini_y + ((ancho_celda - 1) / 2), ini_x + ((ancho_celda - 1) / 2), ini_y + ancho_celda - 1);
                break;
            case 'g':
                g.drawLine(ini_x, ini_y, ini_x, ini_y + (ancho_celda - 1));
                g.drawLine(ini_x + ((ancho_celda - 1) / 2), ini_y, ini_x + (ancho_celda - 1), ini_y + ((ancho_celda - 1) / 2));
                break;
            case 'm':
                g.drawLine(ini_x + ancho_celda - 1, ini_y, ini_x + ancho_celda - 1, ini_y + ancho_celda - 1);
                g.drawLine(ini_x + ((ancho_celda - 1) / 2), ini_y, ini_x, ini_y + ((ancho_celda - 1) / 2));
                break;

            case 'h':
                g.drawLine(ini_x, ini_y, ini_x, ini_y + ancho_celda - 1);
                g.drawLine(ini_x + ((ancho_celda - 1) / 2), ini_y + ancho_celda - 1, ini_x + ancho_celda - 1, ini_y + ((ancho_celda - 1) / 2));
                break;
            case 'n':
                g.drawLine(ini_x + ancho_celda - 1, ini_y, ini_x + ancho_celda - 1, ini_y + ancho_celda - 1);
                g.drawLine(ini_x, ini_y + ((ancho_celda - 1) / 2), ini_x + ((ancho_celda - 1) / 2), ini_y + ancho_celda - 1);
                break;
        }
    }

    // Dibuja todas las casillas del mapa
    public void dibujarMapa(java.awt.Graphics g) {
        Mapa mapa = this.frame.getMotor().getMapa();
        for (int i = 0; i < mapa.getAncho(); i++) {
            for (int j = 0; j < mapa.getAlto(); j++) {
                this.dibujarCasilla(g, i, j);
            }
        }
    }

    // Cambia la direccion de un bicho (usado por las teclas)
    private void cambiarDireccion(Bicho b, int direccion) {
        if (b != null) {
            b.setDireccion(direccion);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (frame.getMotor() != null) {
            // Cambia de direccion o pausa el juego dependiendo de la tecla pulsada
            if (frame.getMotor().getEstadoJuego().estado_juego == EstadoJuego.ESTADO_JUGANDO) {
                Integer tecla_pulsada = new Integer(evt.getKeyCode());
                if (tecla_pulsada == this.frame.cgui.getTecla(ConfiguracionGUI.TECLA_PAUSA)) {
                    if (!this.frame.getPausa()) {
                        if (this.frame.getMotor().getPausa()) {
                            this.frame.getMotor().reanudarJuego();
                        } else {
                            this.frame.getMotor().pausarJuego();
                        }
                    }
                } else if (tecla_pulsada == this.frame.cgui.getTecla(ConfiguracionGUI.TECLA_IZQUIERDA_J1)) { // Jugador 1
                    this.cambiarDireccion(frame.getMotor().getComecocos().get(0), Bicho.IZQUIERDA);
                } else if (tecla_pulsada == this.frame.cgui.getTecla(ConfiguracionGUI.TECLA_DERECHA_J1)) {
                    this.cambiarDireccion(frame.getMotor().getComecocos().get(0), Bicho.DERECHA);
                } else if (tecla_pulsada.equals(this.frame.cgui.getTecla(ConfiguracionGUI.TECLA_ARRIBA_J1))) {
                    this.cambiarDireccion(frame.getMotor().getComecocos().get(0), Bicho.ARRIBA);
                } else if (tecla_pulsada == this.frame.cgui.getTecla(ConfiguracionGUI.TECLA_ABAJO_J1)) {
                    this.cambiarDireccion(frame.getMotor().getComecocos().get(0), Bicho.ABAJO);
                } else if (tecla_pulsada == this.frame.cgui.getTecla(ConfiguracionGUI.TECLA_IZQUIERDA_J2)) { // Jugador 1
                    this.cambiarDireccion(frame.getMotor().getComecocos().get(1), Bicho.IZQUIERDA);
                } else if (tecla_pulsada == this.frame.cgui.getTecla(ConfiguracionGUI.TECLA_DERECHA_J2)) {
                    this.cambiarDireccion(frame.getMotor().getComecocos().get(1), Bicho.DERECHA);
                } else if (tecla_pulsada == this.frame.cgui.getTecla(ConfiguracionGUI.TECLA_ARRIBA_J2)) {
                    this.cambiarDireccion(frame.getMotor().getComecocos().get(1), Bicho.ARRIBA);
                } else if (tecla_pulsada == this.frame.cgui.getTecla(ConfiguracionGUI.TECLA_ABAJO_J2)) {
                    this.cambiarDireccion(frame.getMotor().getComecocos().get(1), Bicho.ABAJO);
                }
            }
        }

    }//GEN-LAST:event_formKeyPressed

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered

    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited

    }//GEN-LAST:event_formMouseExited

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // Despausar juego
        if (this.frame.getMotor() != null) {
            if (!this.frame.getPausa()) {
                this.frame.getMotor().reanudarJuego();
            }
        }
    }//GEN-LAST:event_formFocusGained

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost
        // Pausar juego
        if (this.frame.getMotor() != null) {
            if (!this.frame.getPausa()) {
                this.frame.getMotor().pausarJuego();
            }
        }
    }//GEN-LAST:event_formFocusLost

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        ancho_celda = -1;
        this.actualizar();
    }//GEN-LAST:event_formComponentResized

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        requestFocus();
    }//GEN-LAST:event_formMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
