package Modelo;
//abstract porque es el molde/base para otros pj's
public class entidad extends colision {
	private String nombre;
	private int posicionX;
	private int posicionY;
	private boolean seleccionado;
	private int vida;
	private int vidaMaxima;
	private int ataque;
	private boolean personaje1; 

	public entidad(String nombre, int posicionX, int posicionY, int vida, int ataque, boolean personage1) {
		this.nombre = nombre;
		this.posicionX = posicionX;
		this.posicionY = posicionY;
		this.vidaMaxima = vida;
		this.vida = vida;
		this.ataque = ataque;
		this.personaje1 = personaje1;
		this.seleccionado = false;
	}

	public void recibirdanio(int cantidad) {
		this.vida -= cantidad;
		if (this.vida < 0) {
			this.vida = 0;
		}
	}

	public boolean estaVivo() {
		return this.vida > 0;	
	}

	public String getNombre() {
		return nombre;
	}

	public int getPosicionX() {
		return posicionX;	
	}

	public void setPosicionX(int posicionX) {
		this.posicionX = posicionX;
	}

	public int getPosicionY() {
		return posicionY;
	}

	public void setPosicionY(int posicionY) {
		this.posicionY = posicionY;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public int getVida() {
		return vida;
	}

	public int getVidaMaxima() {
		return vidaMaxima;
	}

	public int getAtaque() {
		return ataque;
	}

	public boolean isPersonaje1() {
		return personaje1;
	}

}