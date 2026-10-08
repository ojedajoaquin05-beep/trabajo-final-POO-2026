package Vista;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GestorSprites {
      
public static BufferedImage[] recortarMatriz(String rutaImagen, int columnas, int filas) {
        int totalFrames = columnas * filas;
        BufferedImage[] animacion = new BufferedImage[totalFrames];

        try {
            BufferedImage hojaCompleta = ImageIO.read(new File(rutaImagen));

            int anchoFrame = hojaCompleta.getWidth() / columnas;
            int altoFrame = hojaCompleta.getHeight() / filas;

            int indice = 0;
            for (int f = 0; f < filas; f++) {
                for (int c = 0; c < columnas; c++) {
                    animacion[indice++] = hojaCompleta.getSubimage(
                        c * anchoFrame, 
                        f * altoFrame, 
                        anchoFrame, 
                        altoFrame
                    );
                }
            }

        } catch (IOException e) {
            System.err.println("Error al cargar la imagen: " + rutaImagen);
            // esto de aca se crea para evitar que de un error de tipo nullpointerexception en caso de que no se pueda cargar la imagen, asi que se crea una imagen vacia para evitar el error
            for (int i = 0; i < totalFrames; i++) {
                animacion[i] = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            }
        }

        return animacion; 
    }
}

