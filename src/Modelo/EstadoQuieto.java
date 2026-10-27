package Modelo;

public class EstadoQuieto implements EstadoPeleador {

    private boolean caminando = false; // si camino en el ultimo tick: la Vista muestra la caminata o la respiracion

    @Override
    public boolean saltar(Peleador peleador) {
        if (!peleador.isEnElSuelo()) {
            return false;
        }
        peleador.setVelocidadY(peleador.getFuerzaSalto());
        peleador.setEnElSuelo(false);
        peleador.cambiarEstado(new EstadoSalto());
        return true;
    }

    @Override
    public boolean caminar(Peleador peleador, int direccion) {
        peleador.desplazar(direccion);
        return true;
    }

    @Override
    public boolean ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente) {
        if (tipoAtaque == ActionType.SPECIAL) {
            return peleador.iniciarEspecial(); // el especial es un estado propio
        }
        return peleador.procesarGolpe(tipoAtaque, oponente);
    }

    // Solo se puede cubrir estando quieto en el suelo
    @Override
    public boolean cubrirse(Peleador peleador, boolean teclaApretada) {
        if (teclaApretada && peleador.isEnElSuelo()) {
            peleador.cambiarEstado(new EstadoBloqueando());
            return true;
        }
        return false;
    }

    // Un golpe lo deja aturdido (o lo mata)
    @Override
    public void recibirDanio(Peleador peleador, int danio) {
        peleador.aplicarDanio(danio);
        peleador.cambiarEstado(peleador.estaVivo() ? new EstadoHerido() : new EstadoMuerto());
    }

    // Al pasar de caminar a quedarse parado (o al reves) la animacion arranca del primer cuadro.
    // Parado, la animacion sigue avanzando: el personaje respira
    @Override
    public void actualizar(Peleador peleador) {
        boolean camino = peleador.seDesplazoEnTick();
        if (camino != caminando) {
            peleador.reiniciarAnimacion();
            caminando = camino;
        }
        if (!caminando) {
            peleador.actualizarAnimacion();
        }
    }

    @Override
    public AccionPeleador getAnimacion() {
        return caminando ? AccionPeleador.CAMINANDO : AccionPeleador.QUIETO;
    }
}
