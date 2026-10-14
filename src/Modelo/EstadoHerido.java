package Modelo;

public class EstadoHerido implements EstadoPeleador {

    private static final int DURACION_ATURDIMIENTO = 15; // ticks (~0,25 s)
    private int ticksRestantes = DURACION_ATURDIMIENTO;

    @Override
    public void saltar(Peleador peleador) {
        // Aturdido: no puede saltar
    }

    @Override
    public void ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente) {
        // Ni atacar
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

    @Override
     public void caminar(Peleador peleador, int direccion) {
    // esta herido, no puede caminar
    }
}