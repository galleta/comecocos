package guicomecocos;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * Clase que se encarga de cargar las imagenes desde un archivo de sprites
 * 
 */
public class CargadorSprite {
    // Definiciones constantes
    public static final int MUERTECOCO = 0;
    public static final int FRUTAS = 1;
    public static final int OJOS = 2;
    public static final int COMIBLES = 3;
    public static final int COCO1ARR = 4;
    public static final int COCO1ABJ = 5;
    public static final int COCO1IZQ = 6;
    public static final int COCO1DER = 7;
    public static final int COCO2ARR = 8;
    public static final int COCO2ABJ = 9;
    public static final int COCO2IZQ = 10;
    public static final int COCO2DER = 11;
    public static final int FANTROJOARR = 12;
    public static final int FANTROJOABJ = 13;
    public static final int FANTROJOIZQ = 14;
    public static final int FANTROJODER = 15;
    public static final int FANTROSAARR = 16;
    public static final int FANTROSAABJ = 17;
    public static final int FANTROSAIZQ = 18;
    public static final int FANTROSADER = 19;
    public static final int FANTAZULARR = 20;
    public static final int FANTAZULABJ = 21;
    public static final int FANTAZULIZQ = 22;
    public static final int FANTAZULDER = 23;
    public static final int FANTAMARARR = 24;
    public static final int FANTAMARABJ = 25;
    public static final int FANTAMARIZQ = 26;
    public static final int FANTAMARDER = 27;

    public CargadorSprite()
    {
    }

    // Carga un conjunto de imagenes desde un archivo de sprites
    public Map<Integer, ArrayList<BufferedImage> > cargar(String ruta, int tamImagen)
    {
        URL url = null;
        try {
            url = new URL(ruta);
        } catch (MalformedURLException ex) {
            Logger.getLogger(CargadorSprite.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<Integer, ArrayList<BufferedImage> > imagenes = new HashMap<Integer, ArrayList<BufferedImage> >();

        // Limpio los arraylist por si se ha cargado otro sprite
        int vuelta = 0;
        Image img = Toolkit.getDefaultToolkit().getImage(url); // cargo la imagen de disco
        BufferedImage im = null, imagen = new BufferedImage(new ImageIcon(img).getIconWidth(), new ImageIcon(img).getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        // Eliminamos el color de fondo
        Graphics2D g = imagen.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(img, 0, 0, null);
        g.dispose();

        Color c = new Color(255, 0, 255); // Color a eliminar
        for (int i = 0; i < imagen.getHeight(); i++) {
            for (int j = 0; j < imagen.getWidth(); j++) {
                if (imagen.getRGB(j, i) == c.getRGB()) {
                    imagen.setRGB(j, i, 0x8F1C1C);
                }
            }
        }

        // Genero los ArrayLists
        for (int k = 0; k < 28; k++)
        {
            imagenes.put(CargadorSprite.MUERTECOCO + k, new ArrayList<BufferedImage>());
        }

        // Meto el comecocos del jugador 1 a la Izquierda
        for(int iterador = 0; iterador < 3; iterador++)
        {
            im = imagen.getSubimage (iterador*tamImagen, vuelta, tamImagen, tamImagen);
            imagenes.get(CargadorSprite.COCO1IZQ).add(im);
        }
        // Meto el comecocos del jugador 1 arriba
        for(int iterador = 0; iterador < 3; iterador++)
        {
            AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(90.0), ((BufferedImage)imagenes.get(CargadorSprite.COCO1IZQ).get(iterador)).getWidth()/2, ((BufferedImage)imagenes.get(CargadorSprite.COCO1IZQ).get(iterador)).getHeight()/2);
            AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            BufferedImage cocoInvertido = atop.filter(((BufferedImage)imagenes.get(CargadorSprite.COCO1IZQ).get(iterador)), null);
            imagenes.get(CargadorSprite.COCO1ARR).add(cocoInvertido);
        }
         // Meto el comecocos del jugador 1 abajo
        for(int iterador = 0; iterador < 3; iterador++)
        {
            AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(270.0), ((BufferedImage)imagenes.get(CargadorSprite.COCO1IZQ).get(iterador)).getWidth()/2, ((BufferedImage)imagenes.get(CargadorSprite.COCO1IZQ).get(iterador)).getHeight()/2);
            AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            BufferedImage cocoInvertido = atop.filter(((BufferedImage)imagenes.get(CargadorSprite.COCO1IZQ).get(iterador)), null);
            imagenes.get(CargadorSprite.COCO1ABJ).add(cocoInvertido);
        }
        // Meto el comecocos del jugador 1 a la derecha
        for(int iterador = 0; iterador < 3; iterador++)
        {
            BufferedImage cocoInvertido = ((BufferedImage)imagenes.get(CargadorSprite.COCO1IZQ).get(iterador));

            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-cocoInvertido.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            cocoInvertido = op.filter(cocoInvertido, null);
            imagenes.get(CargadorSprite.COCO1DER).add(cocoInvertido);
        }
        vuelta += tamImagen;
        // Meto el comecocos del jugador 2 a la izquierda
        for(int iterador = 0; iterador < 3; iterador++)
        {
            im = imagen.getSubimage (iterador*tamImagen, vuelta, tamImagen, tamImagen);
            imagenes.get(CargadorSprite.COCO2IZQ).add(im);
        }
        // Meto el comecocos del jugador 2 arriba
        for(int iterador = 0; iterador < 3; iterador++)
        {
            AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(90.0), ((BufferedImage)imagenes.get(CargadorSprite.COCO2IZQ).get(iterador)).getWidth()/2, ((BufferedImage)imagenes.get(CargadorSprite.COCO2IZQ).get(iterador)).getHeight()/2);
            AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            BufferedImage cocoInvertido = atop.filter(((BufferedImage)imagenes.get(CargadorSprite.COCO2IZQ).get(iterador)), null);
            imagenes.get(CargadorSprite.COCO2ARR).add(cocoInvertido);
        }
         // Meto el comecocos del jugador 2 abajo
        for(int iterador = 0; iterador < 3; iterador++)
        {
            AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(270.0), ((BufferedImage)imagenes.get(CargadorSprite.COCO2IZQ).get(iterador)).getWidth()/2, ((BufferedImage)imagenes.get(CargadorSprite.COCO2IZQ).get(iterador)).getHeight()/2);
            AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            BufferedImage cocoInvertido = atop.filter(((BufferedImage)imagenes.get(CargadorSprite.COCO2IZQ).get(iterador)), null);
            imagenes.get(CargadorSprite.COCO2ABJ).add(cocoInvertido);
        }
        // Meto el comecocos del jugador 1 a la derecha
        for(int iterador = 0; iterador < 3; iterador++)
        {
            BufferedImage cocoInvertido = ((BufferedImage)imagenes.get(CargadorSprite.COCO2IZQ).get(iterador));

            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-cocoInvertido.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            cocoInvertido = op.filter(cocoInvertido, null);
            imagenes.get(CargadorSprite.COCO2DER).add(cocoInvertido);
        }
        // Fantasmas
        for (int fantasma = 0; fantasma < 4; fantasma++)
        {
            for (int k = 0; k < 2; k++)
            {
                vuelta += tamImagen;
                for(int iterador = 0; iterador < 4; iterador++)
                {
                    im = imagen.getSubimage (iterador * tamImagen, vuelta, tamImagen, tamImagen);
                    imagenes.get(CargadorSprite.FANTROJOARR + (iterador * 1) + (fantasma * 4)).add(im);
                }
            }
        }
        vuelta += tamImagen;
        // Meto el fantasma cuando se puede comer
        for(int iterador = 0; iterador < 4; iterador++)
        {
            im = imagen.getSubimage (iterador*tamImagen, vuelta, tamImagen, tamImagen);
            imagenes.get(CargadorSprite.COMIBLES).add(im);
        }
        vuelta += tamImagen;
        // Meto los ojos de los fantasmas
        for(int iterador = 0; iterador < 4; iterador++)
        {
            im = imagen.getSubimage (iterador*tamImagen, vuelta, tamImagen, tamImagen);
            imagenes.get(CargadorSprite.OJOS).add(im);
        }
        vuelta += tamImagen;
        // Meto las frutas
        for(int iterador = 0; iterador < 4; iterador++)
        {
            im = imagen.getSubimage (iterador*tamImagen, vuelta, tamImagen, tamImagen);
            imagenes.get(CargadorSprite.FRUTAS).add(im);
        }
        vuelta += tamImagen;
         // Meto la muerte del coco
        for(int iterador = 0; iterador < 4; iterador++)
        {
            im = imagen.getSubimage (iterador*tamImagen, vuelta, tamImagen, tamImagen);
            imagenes.get(CargadorSprite.MUERTECOCO).add(im);
        }
        vuelta += tamImagen;
        for(int iterador = 0; iterador < 2; iterador++)
        {
            im = imagen.getSubimage (iterador*tamImagen, vuelta, tamImagen, tamImagen);
            imagenes.get(CargadorSprite.MUERTECOCO).add(im);
        }
        return imagenes;
    }
}
