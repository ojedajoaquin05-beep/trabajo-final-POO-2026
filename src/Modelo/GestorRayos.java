package Modelo;

import java.awt.Rectangle;

// Resuelve los rayos del ataque especial en cada tick (antes estaba dentro de Juego).
// Juego coordina la partida (tiempo, KO, ganador); esta clase sabe como avanzan, chocan y golpean los rayos (SRP)
public class GestorRayos {

    private static final int PUNTOS_POR_GOLPE = 10; // puntos que suma el tirador por cada golpe del rayo

    // Un tick de los rayos: primero avanzan, despues se resuelve el choque entre ellos y por ultimo golpean al rival
    public void actualizar(Peleador jugador1, Peleador jugador2, int anchoEscenario) {
        Rayo rayo1 = jugador1.getRayo();
        Rayo rayo2 = jugador2.getRayo();
        boolean activo1 = rayo1 != null && rayo1.isActivo();
        boolean activo2 = rayo2 != null && rayo2.isActivo();
        if (activo1) rayo1.actualizar(anchoEscenario);
        if (activo2) rayo2.actualizar(anchoEscenario);

        if (activo1 && activo2) resolverChoque(rayo1, rayo2);

        if (activo1) golpearConRayo(jugador1, jugador2);
        if (activo2) golpearConRayo(jugador2, jugador1);
    }

    // Si los dos rayos se cruzan se frenan en el punto medio entre sus origenes y ninguno llega al rival
    private void resolverChoque(Rayo rayo1, Rayo rayo2) {
        if (rayo1.getDireccion() != rayo2.getDireccion() && rayo1.getHitbox().intersects(rayo2.getHitbox())) {
            int medio = (rayo1.getOrigenX() + rayo2.getOrigenX()) / 2;
            rayo1.limitarLargo(Math.abs(medio - rayo1.getOrigenX()));
            rayo2.limitarLargo(Math.abs(medio - rayo2.getOrigenX()));
            rayo1.marcarChoque();
            rayo2.marcarChoque();
        }
    }

    // Si el rayo del tirador toca al rival se frena en su cuerpo y le saca vida de a poco (dano progresivo)
    private void golpearConRayo(Peleador tirador, Peleador rival) {
        Rayo rayo = tirador.getRayo();
        if (rayo == null || !rayo.isActivo()) return;
        if (!rival.estaVivo()) {
            rayo.desactivar();
            return;
        }
        Rectangle cuerpo = rival.getCuerpo();
        if (rayo.getHitbox().intersects(cuerpo)) {
            int centroRival = cuerpo.x + cuerpo.width / 2;
            rayo.limitarLargo(Math.abs(centroRival - rayo.getOrigenX()));
            rayo.marcarImpacto();
            if (rayo.puedeGolpear()) {
                rival.recibirdanio(rayo.getDanioPorGolpe()); // el estado del rival decide cuanto le llega (si se cubre, la mitad)
                tirador.sumarPuntaje(PUNTOS_POR_GOLPE);
                rayo.registrarGolpe();
            }
        }
    }
}
