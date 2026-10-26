package Modelo;

// Estado del ataque especial: el peleador queda haciendo la pose y no puede hacer otra cosa.
// Si lo golpean, se interrumpe (queda herido o muerto) y el rayo se corta.
public class EstadoEspecial implements EstadoPeleador {

    @Override
    public boolean saltar(Peleador peleador) {
        return false; // concentrado en el especial
    }

    @Override
    public boolean caminar(Peleador peleador, int direccion) {
        return false;
    }

    @Override
    public boolean ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente) {
        return false; // ya esta lanzando el especial
    }

    @Override
    public boolean cubrirse(Peleador peleador, boolean teclaApretada) {
        return false;
    }

    // Un golpe interrumpe el especial
    @Override
    public void recibirDanio(Peleador peleador, int danio) {
        peleador.aplicarDanio(danio);
        peleador.cambiarEstado(peleador.estaVivo() ? new EstadoHerido() : new EstadoMuerto());
    }

    // Al terminar la pose sale el rayo (una sola vez) y el peleador espera quieto hasta que se apague
    @Override
    public void actualizar(Peleador peleador) {
        if (peleador.getTicksAtaque() <= 0) {
            peleador.lanzarRayo();
            if (!peleador.isRayoActivo()) {
                peleador.cambiarEstado(new EstadoQuieto());
            }
        }
    }

    // Por ahora usa la animacion de ataque (despues puede tener la suya)
    @Override
    public AccionPeleador getAnimacion() {
        return AccionPeleador.ATACANDO;
    }
}
