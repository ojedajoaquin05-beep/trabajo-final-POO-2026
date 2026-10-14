package Modelo;

public interface EstadoPeleador {
    void saltar(Peleador peleador);
    void ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente);
    void actualizar(Peleador peleador);
    String getNombre();
}
