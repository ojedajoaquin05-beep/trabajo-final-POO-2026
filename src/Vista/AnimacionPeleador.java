package Vista;

import Modelo.Peleador;
import java.awt.image.BufferedImage;

public class AnimacionPeleador {
    private final BufferedImage[] movimiento;
    private final BufferedImage[] salto;

    public AnimacionPeleador(String personaje) {
        SpritesPersonaje datos = SpritesPersonaje.valueOf(personaje.toUpperCase());
        this.movimiento = GestorSprites.recortarMatriz(datos.getRutaMovimiento(), 2, 2);
        this.salto = GestorSprites.recortarMatriz(datos.getRutaSalto(), 2, 2);
    }

    public BufferedImage getSpriteActual(Peleador p) {
        if (p.getEstadoActual() == Peleador.EstadoPeleador.SALTO) {
            float vy = p.getVelocidadY();
            int frame = vy < -10 ? 0 : vy < 0 ? 1 : vy < 10 ? 2 : 3;
            return salto[frame % salto.length];
        }
        return movimiento[p.getFrameActual() % movimiento.length];
    }
}