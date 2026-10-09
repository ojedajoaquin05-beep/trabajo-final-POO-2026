import Controlador.ControladorMenu;
import Controlador.ControladorTeclado;
import Modelo.Juego;
import Vista.VentanaJuego;
import javax.swing.SwingUtilities;

public class main {
	public static VentanaJuego ventana;
	public static ControladorMenu controlador;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaJuego vista = new VentanaJuego();
            Juego modeloJuego = new Juego();
            vista.setModeloJuego(modeloJuego); // Establecer el modelo del juego en la vista
            ControladorTeclado teclado = new ControladorTeclado();
            ControladorMenu controlador = new ControladorMenu(vista,modeloJuego, teclado);
            vista.mostrarVentana();

        });
    }
    }

