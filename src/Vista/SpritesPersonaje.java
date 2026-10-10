package Vista;

// Guarda las rutas de los sprites de cada personaje.
// Para agregar un personaje nuevo solo hay que sumar una linea aca
public enum SpritesPersonaje {
    CIEGO("assets/Ciego/ciego_movimiento.png", "assets/Ciego/ciego salto 25.png", "assets/Ciego/ciego ataque 25.png"),
    SAMURAI("assets/Samurai/Smovimiento.png", "assets/Samurai/Ssalto.png", "assets/Samurai/Sataque.png"),
    RUGBIER("assets/Rugbier/Rmovimiento.png", "assets/Rugbier/Rsalto.png", "assets/Rugbier/Rataque.png"); 

    private final String rutaMovimiento;
    private final String rutaSalto;
    private final String rutaAtaque;
    SpritesPersonaje(String rutaMovimiento, String rutaSalto, String rutaAtaque) {
        this.rutaMovimiento = rutaMovimiento;
        this.rutaSalto = rutaSalto;
        this.rutaAtaque = rutaAtaque;
    }

    public String getRutaMovimiento() { return rutaMovimiento; }
    public String getRutaSalto() { return rutaSalto; }
    public String getRutaAtaque() { return rutaAtaque; }
}
