package trabajofinalPOO2026;
//abstract porque es el molde/base para otros pj's
public abstract class Personaje {
	protected String nombre;
	protected int vida;
	protected int vidaMaxima = 100;
	protected int posicionX;
	protected int posicionY;
	protected int velocidad;
	//protected; accesible desde el mismo paquete y desde
	// las subclases
	
	//constructor de personaje
	//accesible para las clases hijas
	public Personaje(String nombre, int vida, int velocidad) {
		this.nombre = nombre;
		//this.vidaMaxima = vidaMaxima;
		this.vida = vidaMaxima;
		this.velocidad = velocidad;
		this.posicionX = 0;
		this.posicionY = 0;
	//inicializacion de objeto cuando se crea
	}
	
	public void mover(int x, int y) {
		posicionX += x;
		posicionY += y;
		//al ejecutar un valor a X o Y, se movera el pj
	}
		
	public void recibirDanio(int danio) {
		vida -= danio;
		//usando colaboracion entre objetos, se recibiria un
		//valor de daño X, restandole a la vida
	}
	
	public boolean estaVivo() {
		return vida > 0;
		//mientras que vida mayor a 0, estas vivo
	}
	
	public abstract void atacar();
	//todo personaje tiene que poder atacar, pero cada tipo
	//de personaje va a definir como lo hace (@Override)
}
