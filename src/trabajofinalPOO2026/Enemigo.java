package trabajofinalPOO2026;

public class Enemigo extends Personaje {
	
	public Enemigo(String nombre, int vidaMaxima, int velocidad, int dificultad) {
        super(nombre, vidaMaxima, velocidad);
        //this.dificultad = dificultad;
    }

    @Override
    public void atacar(Personaje jugador) {
    	//Personaje objetivo; estoy haciendo objetivo a un hijo de Personaje
        //ataque del enemigo
    }

}
