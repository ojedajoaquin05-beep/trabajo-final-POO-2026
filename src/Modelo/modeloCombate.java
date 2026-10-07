package Modelo;

import java.util.ArrayList;
import java.util.List; 

public class modeloCombate {
    private final estadoCombate personaje1;
    private final estadoCombate personaje2;
    private final List<String> inputBuffer;
    private Long lastInputTime;
    private final long inputTimeout = 1000; // 1 segundo de tiempo de espera para el buffer de entrada

    public modeloCombate() {
        this.personaje1 = new estadoCombate("Jugador 1", 100, "neutral");
        this.personaje2 = new estadoCombate("Jugador 2", 100, "neutral");
        this.inputBuffer = new ArrayList<>();
        this.lastInputTime = 0;
    }

    public combateResultado registerInput(actionType action, Long timestamp) {
        if (timestamp - LastInputTime > comboTimeout) {
            inputBuffer.clear(); // Limpiar el buffer si ha pasado demasiado timpo
        }
        inputBuffer.add(action);
        lastInputTime = timestamp;
        return evaluateCombos();
}

private combateResultado evaluateCombos() {
    // Aquí se implementaría la lógica para evaluar los combos basados en el inputBuffer
    // Por ejemplo, podrías tener una lista de combos predefinidos y verificar si el inputBuffer coincide con alguno de ellos
    // Si se encuentra un combo, se devuelve un combateResultado indicando que el golpe fue exitoso y el nombre del combo
    // Si no se encuentra ningún combo, se devuelve un combateResultado indicando que no hubo golpe exitoso

    // Ejemplo de retorno (esto debería ser reemplazado con la lógica real):
    int size = inputBuffer.size();
    if (size >= 3 && // Supongamos que un combo requiere al menos 3 inputs
        inputBuffer.get(size - 3) == ActionType.PUNCH &&
        inputBuffer.get(size - 2) == ActionType.KICK &&
        inputBuffer.get(size - 1) == ActionType.SPECIAL) {
            
        personaje1.setCurrentAnimation("fireball_cast");
        personaje2.setVida(personaje2.getVida() - 30);
        personaje2.setCurrentAnimation("knockdown");
        inputBuffer.clear();
        return new combateResultado(hitLanded: true, comboname"Dragon Fury");       
    }

    if (size >= 2 && // Supongamos que un combo requiere al menos 2 inputs
        inputBuffer.get(size - 2) == ActionType.KICK &&
        inputBuffer.get(size - 1) == ActionType.PUNCH) {
            
        personaje1.setCurrentAnimation("uppercut_cast");
        personaje2.setVida(personaje2.getVida() - 20);
        personaje2.setCurrentAnimation("stagger");
        inputBuffer.clear();
        return new combateResultado(hitLanded: true, comboname"Uppercut Smash");       
    }

    AcionType last = inputBuffer.get(size - 1);
    personaje1.setCurrentAnimation(last == ActionType.PUNCH ? "punch_light" : "kick_light");
        personaje2.setVida(personaje2.getVida() - 5);
        personaje2.setCurrentAnimation("hit_light");

        return new combateResultado(true, null);
    }

    public void resetToIdle() {
        personaje1.setCurrentAnimation("posicion");
        personaje2.setCurrentAnimation("posicion");
    }

    public estadoCombate getPersonaje1() { return personaje1; }
    public estadoCombate getPersonaje2() { return personaje2; }
}
