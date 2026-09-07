package trabajofinalPOO2026;

public abstract class Escenario {
    protected int solido;
    protected int tiempo;
    
    //constructor
    public Escenario(int solido, int tiempo) {
    	if(solido == 0 || solido < 0) {
    		throw new IllegalArgumentException("NO HAY SUELO");
    	}
    	if(tiempo == 0 || tiempo < 0) {
    		throw new IllegalArgumentException("NO SE PUEDE INICIAR CON UN VALOR DE TIEMPO MAYOR O IGUAL A 0");
    	}
    	this.solido = solido;
    	this.tiempo = tiempo;
    }
    public void sueloSolido () {
        //if (condition) {    
        //}
        //esto se va a usar para comprobar que el suelo donde este el personaje es solido
    }

    public boolean tiempoCero () {
    	return true;
        //return tiempo = 0;
        //Esto va a comprobar si el tiempo se termino/llego a 0
    }
}
