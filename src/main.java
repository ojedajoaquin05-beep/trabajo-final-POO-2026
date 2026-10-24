import Controlador.ControladorMenu;
import Controlador.ControladorTeclado;
import Modelo.ConfiguracionDAO;
import Modelo.Juego;
import Modelo.PuntajeDAO;
import Persistencia.Conexion;
import Persistencia.ConfiguracionDAOSQLite;
import Persistencia.PuntajeDAOMemoria;
import Persistencia.PuntajeDAOSQLite;
import Vista.PanelOpciones;
import Vista.VentanaJuego;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaJuego vista = new VentanaJuego();

            // Singleton + DAO la conexion se pide una sola vez y viaja por constructor
            ConfiguracionDAO configuracion;
            PuntajeDAO puntajes;
            try {
                Connection c = Conexion.getInstancia().get();
                puntajes = new PuntajeDAOSQLite(c);
                configuracion = new ConfiguracionDAOSQLite(c);
                Juego juego = new Juego(configuracion, puntajes);
                vista.setModeloJuego(juego); // Establecer el modelo del juego en la vista
                ControladorTeclado teclado = new ControladorTeclado();
                ControladorMenu controlador = new ControladorMenu(vista,juego, teclado);
                int vol = configuracion.getVolumen();							// Recupera la configuración de volumen de música
                PanelOpciones.getSldVolumenMusica().setValue(vol);				// Coloca el valor en el control deslizante
                int musicaPelea = configuracion.getMusicaPelea();
                PanelOpciones.getSpnSeleccionMusica().setValue(musicaPelea);	// Lo mismo con la elección de música de pelea
                boolean mute = configuracion.getMute();
                PanelOpciones.getCkbMutearMusica().setSelected(mute);			// Mute
                vista.mostrarVentana();
            } catch (SQLException e) {
                System.err.println("No se pudo abrir la base, uso memoria: " + e.getMessage());
                puntajes = new PuntajeDAOMemoria();
                configuracion = null;
            }
        });
    }
}