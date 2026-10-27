package Modelo;

import java.awt.Rectangle;

public class Rayo {

    private static final int VELOCIDAD_CRECIMIENTO = 40; // pixeles por tick
    private static final int DURACION = 130;             // ticks que dura (aprox 2 s): alcanza para los 8 golpes
    private static final int DANIO_TOTAL = 40;           // dano que hace si toca al rival todo el tiempo
    private static final int GOLPES_TOTALES = 8;         // en cuantos golpes se reparte
    private static final int INTERVALO_GOLPE = 12;       // ticks entre un golpe y el siguiente
    private static final int ALTO_HITBOX = 200;

    private final int origenX;
    private final int y;          // altura del centro del rayo
    private final int direccion;  // -1 izquierda, 1 derecha

    private int largo = 0;
    private int ticks = 0;
    private int ticksParaGolpe = 0;
    private int golpesDados = 0;
    private boolean activo = true;
    private boolean impactando = false; // true mientras esta tocando al rival
    private boolean chocando = false;   // true mientras esta chocando contra el rayo del otro

    public Rayo(int origenX, int y, int direccion) {
        this.origenX = origenX;
        this.y = y;
        this.direccion = direccion;
    }

    // Un tick: el rayo crece hasta el borde del escenario y se apaga al cumplir su duracion 
    public void actualizar(int anchoEscenario) {
        ticks++;
        if (ticksParaGolpe > 0) {
            ticksParaGolpe--;
        }
        if (ticks >= DURACION) {
            activo = false;
        }
        int hastaElBorde = direccion > 0 ? anchoEscenario - origenX : origenX;
        largo = Math.min(largo + VELOCIDAD_CRECIMIENTO, Math.max(0, hastaElBorde));
        impactando = false; // se vuelve a marcar si sigue tocando al rival
        chocando = false;   // idem si sigue chocando con el otro rayo
    }

    // El rayo no atraviesa al rival: se frena como mucho en su centro
    public void limitarLargo(int maximo) {
        largo = Math.min(largo, Math.max(0, maximo));
    }

    public void marcarImpacto() { impactando = true; }
    public void marcarChoque() { chocando = true; }

    public boolean puedeGolpear() {
        return activo && ticksParaGolpe <= 0 && golpesDados < GOLPES_TOTALES;
    }

    // Cuenta un golpe; al dar el ultimo el rayo se apaga
    public void registrarGolpe() {
        golpesDados++;
        ticksParaGolpe = INTERVALO_GOLPE;
        if (golpesDados >= GOLPES_TOTALES) {
            activo = false;
        }
    }

    public void desactivar() { activo = false; }

    public int getDanioPorGolpe() { return DANIO_TOTAL / GOLPES_TOTALES; }

    public Rectangle getHitbox() {
        int izquierda = direccion > 0 ? origenX : origenX - largo;
        return new Rectangle(izquierda, y - ALTO_HITBOX / 2, largo, ALTO_HITBOX);
    }

    public boolean isActivo() { return activo; }
    public boolean isImpactando() { return impactando; }
    public boolean isChocando() { return chocando; }
    public int getOrigenX() { return origenX; }
    public int getY() { return y; }
    public int getDireccion() { return direccion; }
    public int getLargo() { return largo; }
    public int getTicks() { return ticks; }
}
