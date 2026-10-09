package Modelo;

public class EscenarioItalia extends Escenario {

    public EscenarioItalia(int anchoVentana, int altoVentana) {
        // Por ejemplo: pantalla completa y el piso a la altura de 650px
        super("EscenarioItalia", anchoVentana, altoVentana,(int)(altoVentana * 0.85)); // Ajusta el límite del piso a un porcentaje del alto de la ventana. en este caso 85%
    }

    @Override
    protected void inicializarPlataformas() {
        // Si tiene plataformas flotantes intermedias, se agregan acá:
        // this.plataforma.add(new Suelo(300, 450, 200, 20));
    }
}