package Vista;

import java.awt.Font;
import java.io.File;

// Las comparten el HUD y el reloj
final class Tipografias {

    static final Font VIDA = cargarTipografia("assets/Tipografias/determination.ttf", 24.0f);
    static final Font DISPLAY = cargarTipografia("assets/Tipografias/DSEG7Modern-Bold.ttf", 48.0f);

    private Tipografias() { } // solo tiene constantes, no se crean objetos

    private static Font cargarTipografia(String ruta, float tamanio) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(ruta)).deriveFont(tamanio);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la tipografia " + ruta);
            return new Font("Arial", Font.BOLD, (int) tamanio);
        }
    }
}
