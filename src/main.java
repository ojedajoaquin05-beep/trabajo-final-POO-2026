import controlador.ControladorJuego;
import vista.VentanaJuego;

import javax.swing.SwingUtilities;

public class Main {
	public static VentanaJuego ventana;
	public static ControladorJuego controlador;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ventana = new VentanaJuego();
			controlador = new ControladorJuego();
			ventana.mostrarVentana();
		});
	}
}
