package Modelo;

import java.util.ArrayList;
import java.util.List;

public abstract class Escenario {

    protected String nombre;
    protected int anchoVentana;
    protected int altoVentana;
    protected int limitePisoY; 

    public Escenario (String nombre, int anchoVentana, int altoVentana, int limitePisoY) {
    
    if (anchoVentana <= 0 || altoVentana <= 0){
    throw new IllegalArgumentException("las dimensiones de las ventanas deben ser mayores a cero");
    }
    if (limitePisoY <= 0 || limitePisoY >= altoVentana){
        throw new IllegalArgumentException("El límite del piso debe estar dentro del rango de la ventana.");   
    }

    this.nombre = nombre;
    this.anchoVentana = anchoVentana;
    this.altoVentana = altoVentana;
    this.limitePisoY = limitePisoY;

    inicializarPlataformas();
}
  
protected abstract void inicializarPlataformas();

public boolean estaSobreSuelo (Colision entidad) {

    return entidad.y() + entidad.alto() >= limitePisoY;
}


public void delimitarMovimiento(Entidad entidad) {

    if (entidad.getPosicionX() < 0) {
        entidad.setPosicionX(0);
    }

    if (entidad.getPosicionX() + entidad.ancho() > anchoVentana) {
        entidad.setPosicionX(anchoVentana - entidad.ancho());
    }
    if (entidad.getPosicionY() + entidad.alto() > limitePisoY) {
        entidad.setPosicionY(limitePisoY - entidad.alto());
    }
}

public String getNombre() {return nombre;}
public int getAnchoVentana() {return anchoVentana;}
public int getAltoVentana() {return altoVentana;}
public int getLimitePisoY() { return limitePisoY; }

}