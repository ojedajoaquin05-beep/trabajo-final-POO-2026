import Controlador.ControladorMenu;
import Controlador.ControladorTeclado;
import Modelo.Juego;
import Modelo.PuntajeDAO;
import Persistencia.Conexion;
import Persistencia.PuntajeDAOMemoria;
import Persistencia.PuntajeDAOSQLite;
import Vista.VentanaJuego;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class main {
	public static VentanaJuego ventana;
	public static ControladorMenu controlador;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaJuego vista = new VentanaJuego();

            // Singleton + DAO la conexion se pide una sola vez y viaja por constructor
            PuntajeDAO puntajes;
            try {
                Connection c = Conexion.getInstancia().get();
                puntajes = new PuntajeDAOSQLite(c);
            } catch (SQLException e) {
                System.err.println("No se pudo abrir la base, uso memoria: " + e.getMessage());
                puntajes = new PuntajeDAOMemoria();
            }

            Juego juego = new Juego(puntajes);
            vista.setModeloJuego(juego); // Establecer el modelo del juego en la vista
            ControladorTeclado teclado = new ControladorTeclado();
            ControladorMenu controlador = new ControladorMenu(vista,juego, teclado);
            vista.mostrarVentana();
        });
    }
}