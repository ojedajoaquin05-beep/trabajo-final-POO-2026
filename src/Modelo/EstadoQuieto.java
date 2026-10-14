package Modelo;

public class EstadoQuieto implements EstadoPeleador {

    @Override
    public void saltar(Peleador peleador){
        if (peleador.isEnElSuelo()){
            peleador.setVelocidadY(peleador.getFuerzaSalto());
            peleador.setEnElSuelo(false);
            peleador.cambiarEstado(new EstadoSalto());
        }
    }

    @Override 
     public void ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente) {
         peleador.procesarGolpe(tipoAtaque, oponente);
     }

    @Override
    public void actualizar(Peleador peleador) {
        // en reposo no requiere actualización de animación
    }

   @Override
    public String getNombre() {
        return "QUIETO";
    }
}
