package trabajofinalPOO2026;
//abstract porque es el molde/base para otros pj's
public abstract class Personaje {
	protected String nombre;
	protected int vida;
	protected int vidaMaxima = 100;
	protected int posicionX;
	protected int posicionY;
	protected int velocidad;
	protected int ataqueneutro;
	protected int ataquelateral;
	protected int ataquearriba;
	protected int ataquebajo;
	protected int especialneutro;
	protected int especiallateral;
	protected int especialarriba;
	protected int especialbajo;

	//protected; accesible desde el mismo paquete y desde
	// las subclases
	
	//constructor de personaje
	//accesible para las clases hijas
	public Personaje(String nombre, int vida, int velocidad) {
		if (nombre == null || nombre == "") {
			throw new IllegalArgumentException("NOMBRE INVALIDO");
		}
		if ( vida > 100 || vida < 0) {
			throw new IllegalArgumentException("VALOR DE VIDA ERROEA");
		}
		/*
		  if ( velocidad > 100 || vida < 0) {
		 
			throw new IllegalArgumentException("MUCHA/BAJA VELOCIDAD");
		}
		*/
		this.nombre = nombre;
		this.vida = vidaMaxima; //this.vidaMaxima = vidaMaxima;
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
		
	/*public void recibirDanio(int danio) {
		vida -= danio;							joaluna: Let me cook about this
		//usando colaboracion entre objetos, se recibiria un
		//valor de daño X, restandole a la vida
	} */
	
	public boolean estaVivo() {
		return vida > 0;
		//mientras que vida mayor a 0, estas vivo
	}
	
	public abstract void atacar(Personaje objetivo);
	//todo personaje tiene que poder atacar, pero cada tipo
	//de personaje va a definir como lo hace (@Override)

	public void DanioAtkBase(int danio) {
		vida -= danio;							
		//usando colaboracion entre objetos, se recibiria un
		//valor de daño del ataque base X, restandole a la vida
	}

	public void DanioAtkLateral(int danio) {
		vida -= danio;							
		//usando colaboracion entre objetos, se recibiria un
		//valor de daño del ataque lateral X, restandole a la vida
	}

	public void DanioAtkUp(int danio) {
		vida -= danio;		 /* up solo porque es mas corto que arriba */					
		//usando colaboracion entre objetos, se recibiria un
		//valor de daño del ataque arriba X, restandole a la vida
	}

	public void DanioAtkBajo(int danio) {
		vida -= danio;							
		//usando colaboracion entre objetos, se recibiria un
		//valor de daño del ataque bajo X, restandole a la vida
	}

	public void DanioEspBase(int danio) {
		vida -= danio;							
		//usando colaboracion entre objetos, se recibiria un
		//valor de especial del ataque base X, restandole a la vida
	}

		public void DanioEspLateral(int danio) {
		vida -= danio;							
		//usando colaboracion entre objetos, se recibiria un
		//valor de especial del ataque lateral X, restandole a la vida
	}

	public void DanioEspUp(int danio) {
		vida -= danio;							
		//usando colaboracion entre objetos, se recibiria un
		//valor de especial del ataque arriba X, restandole a la vida
	}

		public void DanioEspBajo(int danio) {
		vida -= danio;							
		//usando colaboracion entre objetos, se recibiria un
		//valor de especial del ataque bajo X, restandole a la vida
	}
}