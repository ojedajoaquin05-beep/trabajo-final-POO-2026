package Modelo;

public class EstadoHerido implements EstadoPeleador {

    private static final int DURACION_ATURDIMIENTO = 15; // ticks (~0,25 s)
    private int ticksRestantes = DURACION_ATURDIMIENTO;

    @Override
    public boolean saltar(Peleador peleador) {
        return false; // aturdido: no puede saltar
    }

    @Override
    public boolean caminar(Peleador peleador, int direccion) {
        return false; // aturdido: no puede caminar
    }

    @Override
    public boolean ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente) {
        return false; // ni atacar
    }

    @Override
    public boolean cubrirse(Peleador peleador, boolean teclaApretada) {
        return false; // aturdido: no puede cubrirse
    }

    // Ya esta aturdido: recibe el dano completo y solo cambia de estado si muere
    @Override
    public void recibirDanio(Peleador peleador, int danio) {
        peleador.aplicarDanio(danio);
        if (!peleador.estaVivo()) {
            peleador.cambiarEstado(new EstadoMuerto());
        }
    }

    @Override
    public void actualizar(Peleador peleador) {
        peleador.actualizarAnimacion(2); // alterna los 2 primeros frames
        ticksRestantes--;
        if (ticksRestantes <= 0) {
            peleador.cambiarEstado(peleador.isEnElSuelo() ? new EstadoQuieto() : new EstadoSalto());
        }
    }

    @Override
    public AccionPeleador getAnimacion() {
        return AccionPeleador.HERIDO;
    }
}
