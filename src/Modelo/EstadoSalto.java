package Modelo;

public class EstadoSalto implements EstadoPeleador {

    @Override
    public boolean saltar(Peleador peleador) {
        return false; // en el aire no se permite doble salto
    }

    @Override
    public boolean caminar(Peleador peleador, int direccion) {
        peleador.desplazar(direccion); // se puede mover en el aire
        return true;
    }

    @Override
    public boolean ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente) {
        if (tipoAtaque == ActionType.SPECIAL) {
            return false; // el especial solo se lanza desde el suelo
        }
        return peleador.procesarGolpe(tipoAtaque, oponente);
    }

    @Override
    public boolean cubrirse(Peleador peleador, boolean teclaApretada) {
        return false; // en el aire no se puede cubrir
    }

    @Override
    public void recibirDanio(Peleador peleador, int danio) {
        peleador.aplicarDanio(danio);
        peleador.cambiarEstado(peleador.estaVivo() ? new EstadoHerido() : new EstadoMuerto());
    }

    // Al tocar el suelo vuelve a quieto
    @Override
    public void actualizar(Peleador peleador) {
        if (peleador.isEnElSuelo()) {
            peleador.setVelocidadY(0);
            peleador.cambiarEstado(new EstadoQuieto());
        }
    }

    @Override
    public AccionPeleador getAnimacion() {
        return AccionPeleador.SALTO;
    }
}
