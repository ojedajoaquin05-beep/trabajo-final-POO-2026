package trabajofinalPOO2026;

public class Jugador extends Personaje { //extends, jugador es herencia de personaje
	
	private int puntaje;
	protected int respawn;
	
	public Jugador(String nombre, int vidaMaxima, int velocidad) {
		super(nombre,vidaMaxima, velocidad);
		this.puntaje = 0;
		this.respawn = 1;
	}
	
	@Override // override, sobreescritura
	public void atacar(Personaje objetivo) {
		//Personaje objetivo; estoy haciendo objetivo a un hijo de Personaje
        //ataque del jugador
    }
	
	public void controlarMovimiento(int x, int y) {
        mover(x * velocidad, y * velocidad);
    }

		public void moverIzquierda (int x) {
		x = x+1;
	}

	public void moverderecha (int x) {
		x = x-1 ;
	}

	public void saltar (int y) {
		y = y+1;
	}

	public void caer (int y) {
		y = y+1;
	}

	public void respawnear(int respawn, int vida, int vidaMaxima) {
		while (respawn > 0) {
			if (vida <= 0) {
				vida = vidaMaxima;
			}
			respawn--;
		}
	}
	
	public void morir(int respawn, int vida) {
		if (vida <= 0 && respawn == 0) {
			// lógica de muerte
		}
	}
}
