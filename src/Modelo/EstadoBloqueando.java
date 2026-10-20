package Modelo;

public class EstadoBloqueando implements EstadoPeleador {

    @Override
    public void saltar(Peleador peleador) {
        // En guardia no salta
    }

    @Override
    public void ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente) {
        // En guardia no ataca
    }

    @Override
    public void actualizar(Peleador peleador) {
        // Se mantiene en guardia hasta que suelte la tecla
    }

    @Override
    public AccionPeleador getAnimacion() {
        return AccionPeleador.BLOQUEANDO;
    }

    @Override
    public void caminar(Peleador peleador, int direccion) {
        // En guardia no camina
    }

    // Al soltar la tecla vuelve a quieto
    @Override
    public void soltarBloqueo(Peleador peleador) {
        peleador.cambiarEstado(new EstadoQuieto());
    }

    // Bloquear reduce el danio a la mitad 
    @Override
    public int reducirDanio(int danio) {
        return Math.max(1, danio / 2);
    }

    // Bloqueando no queda aturdido 
    @Override
    public void alRecibirGolpe(Peleador peleador) {
    }  
}