package Modelo;

public class EstadoBloqueando implements EstadoPeleador {

    @Override
    public boolean saltar(Peleador peleador) {
        return false; // en guardia no salta
    }

    @Override
    public boolean caminar(Peleador peleador, int direccion) {
        return false; // en guardia no camina
    }

    @Override
    public boolean ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente) {
        return false; // en guardia no ataca
    }

    // Si suelta la tecla vuelve a quieto; si la mantiene, sigue en guardia
    @Override
    public boolean cubrirse(Peleador peleador, boolean teclaApretada) {
        if (!teclaApretada) {
            peleador.cambiarEstado(new EstadoQuieto());
            return false;
        }
        return true;
    }

    // Bloquear reduce el dano a la mitad (como minimo 1) y no lo deja aturdido
    @Override
    public void recibirDanio(Peleador peleador, int danio) {
        peleador.aplicarDanio(Math.max(1, danio / 2));
        if (!peleador.estaVivo()) {
            peleador.cambiarEstado(new EstadoMuerto());
        }
    }

    // Mantiene fija la pose de guardia
    @Override
    public void actualizar(Peleador peleador) {
        peleador.reiniciarAnimacion();
    }

    @Override
    public AccionPeleador getAnimacion() {
        return AccionPeleador.BLOQUEANDO;
    }
}
