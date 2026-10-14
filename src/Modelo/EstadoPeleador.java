package Modelo;

public interface EstadoPeleador {
    void saltar(Peleador peleador);
    void ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente);
    void actualizar(Peleador peleador);
    AccionPeleador getAnimacion();
    void caminar(Peleador peleador, int direccion); // -1 izquierda, 1 derecha
}
