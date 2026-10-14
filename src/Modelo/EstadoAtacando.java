package Modelo;

public class EstadoAtacando implements EstadoPeleador {

    @Override
    public void saltar(Peleador peleador) {
        // Bloqueado durante la animación del golpe
    }

    @Override
    public void ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente) {
        // Ya está atacando
    }

    @Override
    public void actualizar(Peleador peleador) {
        if (peleador.getTicksAtaque() <= 0) {
            if (peleador.isEnElSuelo()) {
                peleador.cambiarEstado(new EstadoQuieto());
            } else {
                peleador.cambiarEstado(new EstadoSalto());
            }
        }
    }

   @Override
    public AccionPeleador getAnimacion() {
        return AccionPeleador.ATACANDO;
    }

    @Override
    public void caminar(Peleador peleador, int direccion) {
      peleador.desplazar(direccion); 
    }
}