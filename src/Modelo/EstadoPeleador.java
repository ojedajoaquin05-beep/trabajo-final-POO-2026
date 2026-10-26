package Modelo;

// Patron State: cada estado del peleador responde TODAS estas operaciones.
// Las acciones que devuelven boolean indican si el estado ACEPTO la orden (true) o la rechazo (false).
public interface EstadoPeleador {
    boolean saltar(Peleador peleador);
    boolean caminar(Peleador peleador, int direccion); // -1 izquierda, 1 derecha
    boolean ejecutarAtaque(Peleador peleador, ActionType tipoAtaque, Peleador oponente);
    boolean cubrirse(Peleador peleador, boolean teclaApretada); // true si queda en guardia
    void recibirDanio(Peleador peleador, int danio); // cada estado decide cuanto dano entra y como reacciona
    void actualizar(Peleador peleador); // lo que hace el estado en cada tick
    AccionPeleador getAnimacion();
}
