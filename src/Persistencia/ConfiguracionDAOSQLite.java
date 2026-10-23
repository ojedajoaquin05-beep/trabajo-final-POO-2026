package Persistencia;

import Modelo.Configuracion;
import Modelo.ConfiguracionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConfiguracionDAOSQLite implements ConfiguracionDAO {

    private final Connection conexion; // llega por constructor, no se pide al Singleton aca

    public ConfiguracionDAOSQLite(Connection conexion) throws SQLException {
        if (conexion == null) {
            throw new IllegalArgumentException("La conexion es obligatoria");
        }
        this.conexion = conexion;
        try (Statement sentencia = conexion.createStatement()) {
            sentencia.execute("CREATE TABLE IF NOT EXISTS configuracion (clave TEXT PRIMARY KEY, valor TEXT)");
        }
        try (Statement sentencia = conexion.createStatement()) {
        	sentencia.execute("INSERT OR IGNORE INTO configuracion (clave, valor) VALUES ('volMusica', 127)");
        }
    }

    @Override
    public boolean setVolumen(int vol) {
        String sql = "UPDATE configuracion SET valor = ? WHERE clave = 'volMusica'";
        try (PreparedStatement sentencia = this.conexion.prepareStatement(sql)) {
            sentencia.setString(1, String.valueOf(vol));
            return sentencia.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
	public int getVolumen() {
        String sql = "SELECT valor FROM configuracion WHERE clave = 'volMusica'";
        int resultado = 127;	// por defecto toma volumen maximo
        try (PreparedStatement sentencia = this.conexion.prepareStatement(sql)) {
            try (ResultSet filas = sentencia.executeQuery()) {
                while (filas.next()) {
                    resultado = filas.getInt("valor");
                }
            }
        } catch (SQLException e) {
            return 127;
        }
        return resultado;
    }
}