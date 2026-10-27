package Vista;

// Nombre y fondo de cada escenario. La clave es la que entiende el modelo 
// Para agregar un escenario a la seleccion alcanza con sumar una linea aca
public enum FondoEscenario {
    ESCENARIO1("escenario1", "CIUDAD", "assets/Backgrounds/escenario1.png"),
    ESCENARIO2("escenario2", "ITALIA", "assets/Backgrounds/City4.png");

    private final String clave;
    private final String nombre;
    private final String rutaFondo;

    FondoEscenario(String clave, String nombre, String rutaFondo) {
        this.clave = clave;
        this.nombre = nombre;
        this.rutaFondo = rutaFondo;
    }

    // Busca el escenario por la clave que se eligio; si no existe usa el primero
    public static FondoEscenario de(String clave) {
        for (FondoEscenario fondo : values()) {
            if (fondo.clave.equalsIgnoreCase(clave)) {
                return fondo;
            }
        }
        return ESCENARIO1;
    }

    public String getClave() { return clave; }
    public String getNombre() { return nombre; }
    public String getRutaFondo() { return rutaFondo; }
}
