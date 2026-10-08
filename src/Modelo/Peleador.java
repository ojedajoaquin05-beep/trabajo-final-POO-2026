package Modelo;

public class Peleador extends Entidad { 
    
    private int puntaje;
    protected int respawn;
    private float velocidad; 
    
    // El constructor recibe los datos y manda la mayoría al padre (Entidad) usando super
    public Peleador(String nombre, float posicionX, float posicionY, int vidaMaxima, int ataque, float velocidad) {
        super(nombre, posicionX, posicionY, vidaMaxima, ataque); 
        this.velocidad = velocidad;
        this.puntaje = 0;
        this.respawn = 1;
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
}