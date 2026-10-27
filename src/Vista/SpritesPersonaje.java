package Vista;

// Guarda las hojas de sprites de cada personaje: la ruta y como esta dividida (columnas x filas).
// Todas tienen una sola fila, cuadros de 512 de alto y el personaje a la misma escala con los pies a la misma altura
// (se arman con los cuadros de cada carpeta originales/). Para agregar un personaje nuevo solo hay que sumar una linea aca.
// Patada: el samurai y el rugbier tienen hoja propia; el ciego todavia usa la de ataque (cuando exista, se cambia aca)
public enum SpritesPersonaje {
    CIEGO(new Hoja("assets/Ciego/ciego_quieto.png", 4, 1), new Hoja("assets/Ciego/ciego_caminata.png", 4, 1),
          new Hoja("assets/Ciego/ciego_salto.png", 4, 1),
          new Hoja("assets/Ciego/ciego_ataque.png", 4, 1), new Hoja("assets/Ciego/ciego_ataque.png", 4, 1),
          new Hoja("assets/Ciego/ciego_especial.png", 5, 1), // concentra energia (3) y dispara por los ojos (2)
          new Hoja("assets/Ciego/ciego_muerte.png", 4, 1), new Hoja("assets/Ciego/ciego_herido.png", 4, 1)),
    // El ataque y el especial del samurai tienen cuadros mas anchos (1024x512) para que entre la katana entera
    SAMURAI(new Hoja("assets/Samurai/samurai_quieto.png", 4, 1), new Hoja("assets/Samurai/samurai_movimiento.png", 4, 1),
            new Hoja("assets/Samurai/samurai_salto.png", 4, 1),
            new Hoja("assets/Samurai/samurai_ataque.png", 4, 1), new Hoja("assets/Samurai/samurai_patada.png", 6, 1),
            new Hoja("assets/Samurai/samurai_especial.png", 5, 1), // desenvaina y carga la katana (3) y dispara en estocada (2)
            new Hoja("assets/Samurai/samurai_muerte.png", 4, 1), new Hoja("assets/Samurai/samurai_herido.png", 4, 1)),
    RUGBIER(new Hoja("assets/Rugbier/rugbier_quieto.png", 4, 1), new Hoja("assets/Rugbier/rugbier_movimiento.png", 4, 1),
            new Hoja("assets/Rugbier/rugbier_salto.png", 5, 1),
            new Hoja("assets/Rugbier/rugbier_ataque.png", 5, 1), new Hoja("assets/Rugbier/rugbier_patada.png", 6, 1),
            new Hoja("assets/Rugbier/rugbier_especial.png", 5, 1), // gira la cabeza (3) y dispara con el pelo apuntando (2)
            new Hoja("assets/Rugbier/rugbier_muerte.png", 4, 1), new Hoja("assets/Rugbier/rugbier_herido.png", 4, 1));

    // Una hoja de sprites: la imagen y en cuantas columnas y filas se recorta
    public record Hoja(String ruta, int columnas, int filas) { }

    private final Hoja quieto;
    private final Hoja movimiento;
    private final Hoja salto;
    private final Hoja ataque;
    private final Hoja patada;
    private final Hoja especial;
    private final Hoja muerto;
    private final Hoja herido;

    SpritesPersonaje(Hoja quieto, Hoja movimiento, Hoja salto, Hoja ataque, Hoja patada, Hoja especial, Hoja muerto, Hoja herido) {
        this.quieto = quieto;
        this.movimiento = movimiento;
        this.salto = salto;
        this.ataque = ataque;
        this.patada = patada;
        this.especial = especial;
        this.muerto = muerto;
        this.herido = herido;
    }

    public Hoja getQuieto() { return quieto; }
    public Hoja getMovimiento() { return movimiento; }
    public Hoja getSalto() { return salto; }
    public Hoja getAtaque() { return ataque; }
    public Hoja getPatada() { return patada; }
    public Hoja getEspecial() { return especial; }
    public Hoja getMuerto() { return muerto; }
    public Hoja getHerido() { return herido; }
}
