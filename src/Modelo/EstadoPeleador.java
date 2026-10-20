package Modelo;

public interface EstadoPeleador {
    void saltar(Peleador peleador);
    void ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente);
    void actualizar(Peleador peleador);
    AccionPeleador getAnimacion();
    void caminar(Peleador peleador, int direccion); // -1 izquierda, 1 derecha

    // Comportamientos opcionales, por defecto no hacen nada o no cambian nada
    // Solo los estados que los necesitan los sobrescriben
    default void bloquear(Peleador peleador) { } //como es default no hace falta cambiar ninguno de los estados anteriores.
    default void soltarBloqueo(Peleador peleador) { }
    default int reducirDanio(int danio) { return danio; }
    default void alRecibirGolpe(Peleador peleador) {
    peleador.cambiarEstado(new EstadoHerido()); // reaccion: queda aturdido
}
}
