package Persistencia;

import Modelo.Puntaje;
import Modelo.PuntajeDAO;
import java.util.ArrayList;
import java.util.List;

// Implementación con una lista: no importa java.sql, sirve para probar y para
// que el juego funcione aunque la base falle.
public class PuntajeDAOMemoria implements PuntajeDAO {

    private final List<Puntaje> puntajes = new ArrayList<>();

    @Override
    public boolean guardar(Puntaje puntaje) {
        if (puntaje == null) {
            return false;
        }
        this.puntajes.add(puntaje);
        return true;
    }

    @Override
    public List<Puntaje> mejores(int cantidad) {
        List<Puntaje> ordenados = new ArrayList<>(this.puntajes);
        ordenados.sort((a, b) -> b.getPuntos() - a.getPuntos());
        return ordenados.subList(0, Math.min(cantidad, ordenados.size()));
    }
}   // esto existe por si falla la base SQLite