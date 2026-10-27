package Modelo;

public class EstadoMuerto implements EstadoPeleador {

    @Override
    public boolean saltar(Peleador peleador) {
        return false; // un peleador derrotado no puede saltar
    }

    @Override
    public boolean caminar(Peleador peleador, int direccion) {
        return false; // esta muerto, no puede caminar
    }

    @Override
    public boolean ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente) {
        return false; // ni atacar
    }

    @Override
    public boolean cubrirse(Peleador peleador, boolean teclaApretada) {
        return false; // ni cubrirse
    }

    // Un muerto sigue muerto: la vida ya esta en 0 y no baja de ahi
    @Override
    public void recibirDanio(Peleador peleador, int danio) {
        peleador.aplicarDanio(danio);
    }

    @Override
    public void actualizar(Peleador peleador) {
        // Avanza la caida; la Vista la muestra una sola vez y se queda en el ultimo cuadro de la hoja
        peleador.actualizarAnimacion();
    }

    @Override
    public AccionPeleador getAnimacion() {
        return AccionPeleador.MUERTO;
    }
}
