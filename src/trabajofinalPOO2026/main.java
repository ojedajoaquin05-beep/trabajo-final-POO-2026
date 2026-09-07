package trabajofinalPOO2026;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class main extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 7950439569607000893L;
	//final; definimos ahora el valor y no va a cambiar por el resto de la ejecucion del programa = un poco mas rapido la ejec.
	private static final int ANCHO =  800;
	private static final int ALTO = 600;
	private static final String NOMBREJUEGO = "The Last Pounch"; //constante; siempre se escribe con MAYUS
	
	private static JFrame ventana;
	//static, se puede usar sin hacer instancias de la clase, solo hay 1 en toda la clase(una ventada)
	private main() {
		
		setPreferredSize(new Dimension(ANCHO,ALTO));
		
		ventana = new JFrame(NOMBREJUEGO);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //sirve para cerrar la ventana
		ventana.setResizable(false); //el usuario no puede modificar el tamaño de la ventana
		ventana.setLayout(new BorderLayout());//gestor de ventana
		ventana.add(this, BorderLayout.CENTER);//ajustamos la ventana en el centro de la pantalla
		ventana.pack();//el contenido de la pantalla se ajusta a la ventana
		ventana.setLocationRelativeTo(null);//fijara la ventana en el centro
		ventana.setVisible(true);//ventana visible
	}

	public static void main(String[] args) {
				main main = new main();
		
				main.iniciar(); //con esto tenemos preparado el 2do Thread
	}
	
	private static Thread thread; //Thread
	
	private void iniciar() { //para iniciar el juego
		thread = new Thread(this, "Graficos"); //para iniciar el 2do THread, ejemplo...
		//encargada para indentificar el Thread y usarlo para los graficos
		thread.start();
		//a partir de aca, todo lo q' este en run, se ejecutara secuencialmente
	}
	private void detener() {
	}
	
	public void run() {
		//aca adentro iria el 2do Thread....
		System.out.println("el thread 2 se esta ejecutando...");
		
	}

}
