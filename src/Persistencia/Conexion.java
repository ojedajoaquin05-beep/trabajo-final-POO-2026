package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton una sola conexión para todo el programa.
// Dos conexiones al mismo archivo SQLite se bloquean entre sí.
public class Conexion {

    private static Conexion instancia; // la única
    private final Connection conexion;

    private Conexion() throws SQLException { 
        this.conexion = DriverManager.getConnection("jdbc:sqlite:juego.db");
    }

    public static synchronized Conexion getInstancia() throws SQLException {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection get() {
        return this.conexion;
    }
}