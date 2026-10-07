import controlador.ControladorJuego;
import vista.VentanaJuego;

import javax.swing.SwingUtilities;

public class Main {
	public static VentanaJuego ventana;
	public static ControladorJuego controlador;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Class<?> ventanaClass = Class.forName("vista.VentanaJuego");
                ventana = ventanaClass.getDeclaredConstructor().newInstance();

                Class<?> controladorClass = Class.forName("controlador.ControladorJuego");
                controlador = controladorClass.getDeclaredConstructor().newInstance();

                ventanaClass.getMethod("mostrarVentana").invoke(ventana);
            } catch (Exception e) {
                throw new RuntimeException("No se pudo inicializar la ventana o el controlador.", e);
            }
        });
    }
}
