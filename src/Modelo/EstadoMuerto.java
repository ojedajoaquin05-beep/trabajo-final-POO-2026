package Modelo;

public class EstadoMuerto implements EstadoPeleador {

    @Override
    public void saltar(Peleador peleador) {
        // Un peleador derrotado no puede saltar
    }

    @Override
    public void ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente) {
        // Ni atacar
    }

    @Override
    public void actualizar(Peleador peleador) {
        // Reproduce la caída una sola vez y queda en el último frame (4 frames: 0 a 3)
        if (peleador.getFrameActual() < 3) {
            peleador.actualizarAnimacion(4);
        }
    }

    @Override
    public AccionPeleador getAnimacion() {
        return AccionPeleador.MUERTO;
    }

    @Override
    public void caminar(Peleador peleador, int direccion) {
     //esta muerto, no puede caminar
    }
}