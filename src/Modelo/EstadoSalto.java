package Modelo;

public class EstadoSalto implements EstadoPeleador {

    @Override
    public void saltar(Peleador peleador) {
        // En el aire no se permite doble salto
    }

   @Override
    public void ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente) {
         peleador.procesarGolpe(tipoAtaque, oponente);
    }

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

    @Override
public void caminar(Peleador peleador, int direccion) {
    peleador.desplazar(direccion);
}

}
    