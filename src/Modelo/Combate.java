package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Combate {
    
    // Clase interna que reemplaza a combateResultado.java 
    // Agrupa la información de un golpe exitoso sin necesidad de otro archivo
    public static class Resultado {
        private final boolean impactoExitoso;
        private final String nombreCombo;

        public Resultado(boolean impactoExitoso, String nombreCombo) {
            this.impactoExitoso = impactoExitoso;
            this.nombreCombo = nombreCombo;
        }

        public boolean isImpactoExitoso() { return impactoExitoso; }
        public String getNombreCombo() { return nombreCombo; }
    }

    private final List<ActionType> inputBuffer;
    private long lastInputTime;
    private final long COMBO_TIMEOUT = 1000; // 1 segundo máximo entre teclas porque sino se rompe el combo

    public Combate() {
        this.inputBuffer = new ArrayList<>();
        this.lastInputTime = 0;
    }

    // Aca es lo que estaba en combateResultado.java pero lo puse aca para que sea mas facil de manejar y no tener que crear otra clase
    // Lre pasamos directamente las Entidades (los personajes) para que les reste vida
    public Resultado registrarInput(ActionType accion, long timestamp, Entidad atacante, Entidad victima) {
        
        // Si pasó mucho tiempo desde la última tecla, limpiamos el historial de combos
        if (timestamp - lastInputTime > COMBO_TIMEOUT) {
            inputBuffer.clear(); 
        }
        
        inputBuffer.add(accion);
        lastInputTime = timestamp;
        
        return evaluarCombos(atacante, victima);
    }

    private Resultado evaluarCombos(Entidad atacante, Entidad victima) {
        int size = inputBuffer.size();

        // Combo Especial (Requiere 3 inputs: PUNCH + KICK + SPECIAL)
        if (size >= 3 && 
            inputBuffer.get(size - 3) == ActionType.PUNCH &&
            inputBuffer.get(size - 2) == ActionType.KICK &&
            inputBuffer.get(size - 1) == ActionType.SPECIAL) {
                
            // Atacamos a la víctima usando recibirdanio de Entidad
            victima.recibirdanio(30); 
            inputBuffer.clear();
            return new Resultado(true, "Dragon Fury");       
        }

        // Combo Intermedio (Requiere 2 inputs: KICK + PUNCH)
        if (size >= 2 && 
            inputBuffer.get(size - 2) == ActionType.KICK &&
            inputBuffer.get(size - 1) == ActionType.PUNCH) {
                
            victima.recibirdanio(20);
            inputBuffer.clear();
            return new Resultado(true, "Uppercut Smash");       
        }

        // Golpe Básico (1 solo input)
        ActionType last = inputBuffer.get(size - 1);
        int danioBasico = (last == ActionType.PUNCH) ? 5 : 8; // Punch saca 5, Kick saca 8 
        victima.recibirdanio(danioBasico);
        
        return new Resultado(true, "Golpe Básico");
    }

    public void limpiarBuffer() {
        inputBuffer.clear();
    }
}