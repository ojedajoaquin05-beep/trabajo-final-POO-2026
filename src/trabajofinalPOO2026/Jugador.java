package trabajofinalPOO2026;

public class Jugador extends Personaje { //extends, jugador es herencia de personaje
	
	private int puntaje;
	protected int respawn;
	
	public Jugador(String nombre, int vidaMaxima, int velocidad) {
		super(nombre,vidaMaxima, velocidad);
		this.puntaje = 0;
	}
	
	@Override // override, sobreescritura
			  //estoy modificando un metodo que ya existe en clase padre(personaje)
	public void atacar() {
        //ataque del jugador
    }
	
	public void controlarMovimiento(int x, int y) {
        mover(x * velocidad, y * velocidad);
    }
}
