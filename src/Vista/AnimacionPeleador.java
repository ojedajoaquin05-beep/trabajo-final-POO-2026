package Vista;

import Modelo.Peleador;
import java.awt.image.BufferedImage;

public class AnimacionPeleador {
    private final BufferedImage[] quieto;
    private final BufferedImage[] movimiento;
    private final BufferedImage[] salto;
    private final BufferedImage[] ataque;
    private final BufferedImage[] patada;
    private final BufferedImage[] especial;
    private final BufferedImage[] muerte;
    private final BufferedImage[] herido;

    public AnimacionPeleador(String personaje) {
        SpritesPersonaje datos = SpritesPersonaje.valueOf(personaje.toUpperCase());
        this.quieto = recortar(datos.getQuieto());
        this.movimiento = recortar(datos.getMovimiento());
        this.salto = recortar(datos.getSalto());
        this.ataque = recortar(datos.getAtaque());
        this.patada = recortar(datos.getPatada());
        this.especial = recortar(datos.getEspecial());
        this.muerte = recortar(datos.getMuerto());
        this.herido = recortar(datos.getHerido());
    }

    // Cada hoja dice en cuantas columnas y filas se corta (no todas son 2x2)
    private static BufferedImage[] recortar(SpritesPersonaje.Hoja hoja) {
        return GestorSprites.recortarMatriz(hoja.ruta(), hoja.columnas(), hoja.filas());
    }

    public BufferedImage getSpriteActual(Peleador p) {
    switch (p.getEstadoActual().getAnimacion()) {
        case QUIETO:
            return quieto[(p.getFrameActual() / 2) % quieto.length]; // respira: mas lento que la caminata
        case ATACANDO:
            return segunProgreso(ataque, p.getProgresoAtaque());
        case PATADA:
            return segunProgreso(patada, p.getProgresoAtaque());
        case ESPECIAL:
            if (!p.isRayoActivo()) {
                // cargando: recorre todos los cuadros menos los 2 ultimos (que son los del disparo)
                return segunProgreso(especial, p.getProgresoAtaque() * (especial.length - 2) / especial.length);
            }
            // disparando: alterna los 2 ultimos cuadros para que la energia titile
            return especial[especial.length - 2 + p.getFrameActual() % 2];
        case SALTO:
            float vy = p.getVelocidadY();
            int fase = vy < -10 ? 0 : vy < 0 ? 1 : vy < 10 ? 2 : 3; // 4 fases del salto segun la velocidad
            return salto[fase * salto.length / 4]; // se reparten entre los cuadros que tenga la hoja
        case MUERTO:
            return muerte[Math.min(p.getFrameActual(), muerte.length - 1)]; // la caida se ve una vez y queda en el ultimo cuadro
        case HERIDO:
            return herido[p.getFrameActual() % 2]; // Alterna entre dos frames de herido
        default: // CAMINANDO y BLOQUEANDO
            return movimiento[p.getFrameActual() % movimiento.length];
    }
}

    // Elige el cuadro segun cuanto avanzo la accion (0 = recien empieza, 1 = termino), asi se ve fluido
    private static BufferedImage segunProgreso(BufferedImage[] cuadros, float progreso) {
        int i = (int) (progreso * cuadros.length);
        return cuadros[Math.max(0, Math.min(i, cuadros.length - 1))];
    }
}