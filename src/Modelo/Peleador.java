package Modelo;


public class Peleador extends Entidad { 
    
    private int puntaje;
    protected int respawn;
    private float velocidad; 
    private EstadoPeleador estadoActual;
    private int frameActual; // Para las animaciones.
    private int contadorTicksAnimacion; // Para controlar la velocidad de la animación.
    private int ticksPorFrame = 8; // Número de ticks que cada frame debe durar.
    private boolean MirandoDerecha; // Para saber hacia dónde está mirando el peleador.
    
    private float velocidadY = 0;
    private float gravedad = 0.8f;
    private float fuerzaSalto = -25f;
    private  boolean enElSuelo = true; 
    private static final int ENFRIAMIENTO_ATAQUE = 30; //  aprox 0,5 s a 60 cuadros por segundo
    private int ticksEnfriamiento = 0;
    private static final int ALCANCE_ATAQUE = 350; // Distancia máxima para que un ataque sea efectivo
    private static final int DURACION_ATAQUE = 20; // ticks que dura la animación de ataque
    private int ticksAtaque = 0;



    public enum EstadoPeleador {
        QUIETO, MOVIENDOSE, SALTO ,ATACANDO, MUERTO
    }

    // El constructor recibe los datos y manda la mayoría al padre (Entidad) usando super
    public Peleador(String nombre, int posicionX, int posicionY, int vidaMaxima, int ataque, float velocidad) {
        super(nombre, posicionX, posicionY, vidaMaxima, ataque); 
        this.velocidad = velocidad;
        this.puntaje = 0;
        this.respawn = 1;
        this.estadoActual = EstadoPeleador.QUIETO;
        this.frameActual = 0;
        this.contadorTicksAnimacion = 0;
        this.MirandoDerecha = true;
        this.ancho = 400;
        this.alto = 400;
    }
       
    public void cambiarEstado(EstadoPeleador nuevoEstado) {
        if (this.estadoActual != nuevoEstado) {
            this.estadoActual = nuevoEstado;
            this.frameActual = 0; // Reinicia el frame al cambiar de estado
            this.contadorTicksAnimacion = 0; // Reinicia el contador de ticks
        }
    }

    public void actualizarAnimacion(int totalFramesEstado) {
        contadorTicksAnimacion++;
        if (contadorTicksAnimacion >= ticksPorFrame) {
            frameActual = (frameActual + 1) % totalFramesEstado; // Cicla entre los frames del estado actual
            contadorTicksAnimacion = 0; // Reinicia el contador de ticks
        }
    }


    // Método exclusivo de los peleadores para dañar a otros
    public void atacar(Entidad objetivo) {
        objetivo.recibirdanio(this.ataque);
    }
    
    public void respawnear() {
        if (respawn > 0 && vida <= 0) {
            vida = vidaMaxima;
            respawn--;
        }
    }
    
    

    public void morir() {
        if (vida <= 0 && respawn == 0) {
            // lógica de muerte
            System.out.println(nombre + " ha sido derrotado");
        }
    }

    public void saltar() {
        if(enElSuelo){
            this.velocidadY = fuerzaSalto;
            this.enElSuelo = false;
            cambiarEstado(EstadoPeleador.SALTO);
        }
    }
    public void aplicarFisica(){
        if(!enElSuelo){
            this.posicionY += velocidadY;
            this.velocidadY += gravedad;
        }
    }

    public void ejecutarAtaque (ActionType tipoAtaque, Peleador oponente){
        if(oponente == null || !oponente.estaVivo() || ticksEnfriamiento > 0) {
            return;
        }
        ticksEnfriamiento = ENFRIAMIENTO_ATAQUE;
        ticksAtaque = DURACION_ATAQUE; // Inicia la duración del ataque
        cambiarEstado(EstadoPeleador.ATACANDO);

    if (estaAlAlcance(oponente)) {
            int danioBase;
    switch (tipoAtaque) { //el switch determina el daño segun el ataque que se ejecute
        case PUNCH:   danioBase = ataque;      break;
        case KICK:    danioBase = ataque + 5;  break;
        case SPECIAL: danioBase = ataque + 15; break;
        default:      danioBase = ataque;
    }
    oponente.recibirdanio(danioBase);
    }
}

    // El rival tiene que estar adelante, dentro del alcance y a una altura similar (Dsps vemos si le implementamos hitbox entre ellos para que no se atraviesen.)
    private boolean estaAlAlcance(Peleador oponente) {
    int centroPropio = this.posicionX + (this.ancho / 2);
    int centroOponente = oponente.getPosicionX() + (oponente.ancho() / 2);
    int distancia = centroOponente - centroPropio; // positivo: el rival está a la derecha

    boolean rivalAdelante = isMirandoDerecha() ? distancia >= 0 : distancia <= 0;
    boolean alturaSimilar = Math.abs(this.posicionY - oponente.getPosicionY()) < (this.alto / 2);

    return rivalAdelante && Math.abs(distancia) <= ALCANCE_ATAQUE && alturaSimilar;
}

    // Getters y Setters exclusivos del Peleador
    public float getVelocidad() { return velocidad; }
    public int getPuntaje() { return puntaje; }
    public void sumarPuntaje(int puntos) { this.puntaje += puntos; }
    public EstadoPeleador getEstadoActual() { return estadoActual; }
    public int getFrameActual() { return frameActual; }
    public boolean isMirandoDerecha() { return MirandoDerecha; }
    public void setMirandoDerecha(boolean mirandoDerecha) { this.MirandoDerecha = mirandoDerecha; }
    public float getProgresoAtaque() {return 1f - ((float) ticksAtaque / DURACION_ATAQUE); } // Devuelve un valor entre 0 y 1 indicando el progreso del ataque

    public boolean isEnElSuelo(){
        return enElSuelo;
    }
    public void setEnElSuelo(boolean enElSuelo){
        this.enElSuelo = enElSuelo;
        if(enElSuelo){
            this.velocidadY = 0;
            if(this.estadoActual == EstadoPeleador.SALTO){
                cambiarEstado(EstadoPeleador.QUIETO);
            }
        }
    }
    public float getVelocidadY(){
        return velocidadY;
    }

    public void actualizarEnfriamiento() {
    if (ticksEnfriamiento > 0) {
        ticksEnfriamiento--;
    }
    // Cuando termina el ataque vuelve a SALTO si está en el aire, o a QUIETO si está en el suelo
    if (estadoActual == EstadoPeleador.ATACANDO) {
        ticksAtaque--;
        if (ticksAtaque <= 0) {
            cambiarEstado(enElSuelo ? EstadoPeleador.QUIETO : EstadoPeleador.SALTO);
        }
    }
}

}