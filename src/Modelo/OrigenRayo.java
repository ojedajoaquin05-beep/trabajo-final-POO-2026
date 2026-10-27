package Modelo;

public enum OrigenRayo {
    CIEGO(10, 0.25f),     // desde los ojos 
    SAMURAI(294, 0.30f),  // desde la punta de la katana 
    RUGBIER(187, 0.34f),  // desde la punta del pelo, que apunta hacia adelante al disparar
    BORRACHO(47, 0.31f),  // desde la boca, despues de tomar de la botella
    MANOS(100, 0.45f);    // por defecto desde las manos

    private final int adelante;
    private final float altura;

    OrigenRayo(int adelante, float altura) {
        this.adelante = adelante;
        this.altura = altura;
    }

    public static OrigenRayo de(String nombrePersonaje) {
        for (OrigenRayo origen : values()) {
            if (origen.name().equalsIgnoreCase(nombrePersonaje)) {
                return origen;
            }
        }
        return MANOS;
    }

    public int getAdelante() { return adelante; }
    public float getAltura() { return altura; }
}
