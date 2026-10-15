package Persistencia;

import Modelo.Puntaje;
import Modelo.PuntajeDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class PuntajeDAOSQLite implements PuntajeDAO {

    private final Connection conexion; // llega por constructor, no se pide al Singleton aca

    public PuntajeDAOSQLite(Connection conexion) throws SQLException {
        if (conexion == null) {
            throw new IllegalArgumentException("La conexion es obligatoria");
        }
        this.conexion = conexion;
        try (Statement sentencia = conexion.createStatement()) {
            sentencia.execute("CREATE TABLE IF NOT EXISTS puntajes ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "jugador TEXT NOT NULL, puntos INTEGER NOT NULL, fecha TEXT NOT NULL)");
        }
    }

    @Override
    public boolean guardar(Puntaje puntaje) {
        String sql = "INSERT INTO puntajes (jugador, puntos, fecha) VALUES (?, ?, ?)";
        try (PreparedStatement sentencia = this.conexion.prepareStatement(sql)) {
            sentencia.setString(1, puntaje.getJugador());
            sentencia.setInt(2, puntaje.getPuntos());
            sentencia.setString(3, puntaje.getFecha().toString());
            return sentencia.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Puntaje> mejores(int cantidad) {
        String sql = "SELECT jugador, puntos, fecha FROM puntajes ORDER BY puntos DESC LIMIT ?";
        List<Puntaje> resultado = new ArrayList<>();
        try (PreparedStatement sentencia = this.conexion.prepareStatement(sql)) {
            sentencia.setInt(1, cantidad);
            try (ResultSet filas = sentencia.executeQuery()) {
                while (filas.next()) {
                    resultado.add(new Puntaje(filas.getString("jugador"),
                            filas.getInt("puntos"),
                            LocalDate.parse(filas.getString("fecha"))));
                }
            }
        } catch (SQLException e) {
            return resultado;
        }
        return resultado;
    }
}