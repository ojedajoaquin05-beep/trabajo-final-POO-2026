package Modelo;

public class EstadoAtacando implements EstadoPeleador {

    private final ActionType tipoAtaque; // golpe o patada: cambia solo la animacion

    public EstadoAtacando(ActionType tipoAtaque) {
        this.tipoAtaque = tipoAtaque;
    }

    @Override
    public boolean saltar(Peleador peleador) {
        return false; // bloqueado durante la animacion del golpe
    }

    @Override
    public boolean caminar(Peleador peleador, int direccion) {
        peleador.desplazar(direccion);
        return true;
    }

    @Override
    public boolean ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente) {
        return false; // ya esta atacando
    }

    @Override
    public boolean cubrirse(Peleador peleador, boolean teclaApretada) {
        return false; // no se puede cubrir en medio de un ataque
    }

    // Si lo golpean mientras ataca, se interrumpe el ataque
    @Override
    public void recibirDanio(Peleador peleador, int danio) {
        peleador.aplicarDanio(danio);
        peleador.cambiarEstado(peleador.estaVivo() ? new EstadoHerido() : new EstadoMuerto());
    }

    // Cuando termina la animacion del golpe, vuelve a quieto o a salto
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
        return tipoAtaque == ActionType.KICK ? AccionPeleador.PATADA : AccionPeleador.ATACANDO;
    }
}
