package Modelo;

public class Enemigo extends Entidad {
	
	public Enemigo(String nombre, int vidaMaxima, int velocidad, int dificultad) {
        super(nombre, vidaMaxima, velocidad);
        //this.dificultad = dificultad;
    }

    @Override
    public void atacar(Entidad jugador) {
    	//Personaje objetivo; estoy haciendo objetivo a un hijo de Personaje
        //ataque del enemigo
    }

        public void decidiraccion ()
    {
            
    }


}
