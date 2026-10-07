package Modelo;

public class estadoCombate {
    
    private final String nombre;
    private int vida;
    private String currentAnimation;

    public estadoCombate(String nombre, int vida, String currentAnimation) {
        this.nombre = nombre;
        this.vida = vida;
        this.currentAnimation = currentAnimation;
    }

    public String getNombre() {
        return nombre;
    }
    public int getVida() {
        return vida;
    }
    public void setVida(int vida) {
        this.vida = Math.max(0 , vida);
    }
    public String getCurrentAnimation() {
        return currentAnimation;
    }
    public void setCurrentAnimation(String currentAnimation) {
        this.currentAnimation = currentAnimation;
    }
}

