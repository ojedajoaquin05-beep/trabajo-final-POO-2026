package Vista;

// Guarda las rutas de los sprites de cada personaje.
// Para agregar un personaje nuevo solo hay que sumar una linea aca
public enum SpritesPersonaje {
    CIEGO("assets/Ciego/ciego_movimiento.png", "assets/Ciego/ciego salto 25.png"),
    SAMURAI("assets/Samurai/Smovimiento.png", "assets/Samurai/Ssalto.png"),
    RUGBIER("assets/Rugbier/Rmovimiento.png", "assets/Rugbier/Rsalto.png");

    private final String rutaMovimiento;
    private final String rutaSalto;

    SpritesPersonaje(String rutaMovimiento, String rutaSalto) {
        this.rutaMovimiento = rutaMovimiento;
        this.rutaSalto = rutaSalto;
    }

    public String getRutaMovimiento() { return rutaMovimiento; }
    public String getRutaSalto() { return rutaSalto; }
}
