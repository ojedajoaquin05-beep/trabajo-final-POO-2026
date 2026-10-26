package Vista;

import java.awt.Image;

public class GraficosCombate {

    private final AnimacionPeleador animacionP1;
    private final AnimacionPeleador animacionP2;
    private final Image fondo;

    public GraficosCombate(AnimacionPeleador animacionP1, AnimacionPeleador animacionP2, Image fondo) {
        this.animacionP1 = animacionP1;
        this.animacionP2 = animacionP2;
        this.fondo = fondo;
    }

    public AnimacionPeleador getAnimacionP1() { return animacionP1; }
    public AnimacionPeleador getAnimacionP2() { return animacionP2; }
    public Image getFondo() { return fondo; }
}