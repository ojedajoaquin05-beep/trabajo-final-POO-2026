package Modelo;

import java.awt.Rectangle;

public class Peleador extends Entidad { 
    
    private int puntaje;
    protected int respawn;
    private float velocidad; 
    private EstadoPeleador estadoActual;
    private int frameActual; // Para las animaciones.
    private int contadorTicksAnimacion; // Para controlar la velocidad de la animación.
    private int ticksPorFrame = 8; // Número de ticks que cada frame debe durar.
    private boolean MirandoDerecha; // Para saber hacia dónde está mirando el peleador.

    
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

    // Getters y Setters exclusivos del Peleador
    public float getVelocidad() { return velocidad; }
    public int getPuntaje() { return puntaje; }
    public void sumarPuntaje(int puntos) { this.puntaje += puntos; }
    public EstadoPeleador getEstadoActual() { return estadoActual; }
    public int getFrameActual() { return frameActual; }
    public boolean isMirandoDerecha() { return MirandoDerecha; }
    public void setMirandoDerecha(boolean mirandoDerecha) { this.MirandoDerecha = mirandoDerecha; }


    @Override
    public Rectangle getLimites(){
        return new Rectangle(posicionX,posicionY, 64, 64); // hitbox de 64x64 para los peleadores
    }
}