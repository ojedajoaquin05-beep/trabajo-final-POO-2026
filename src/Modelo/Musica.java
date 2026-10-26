package Modelo;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

import Vista.PanelOpciones;

public class Musica {

    private Clip clip;

    public Musica(String resourcePath) {
        try {
        	File archivoMusica = new File(resourcePath);
        	AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoMusica);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public boolean isReproduciendo() {
        return clip != null && clip.isRunning();
    }
    
    public void reproducirLoop() {
    	if (!isReproduciendo()) {
    		clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
    }

    public void pausar() {
        clip.stop();
    }

    public void detener() {
    if (clip != null) {
        clip.stop();
        clip.setFramePosition(0);
    }
}

    
    public void setVolumen(int vol) {
    	// MASTER_GAIN recibe un parámetro expresado en decibeles donde 0 es el volumen máximo
    	// y se utilizan valores negativos para especificar un volumen menor
    	FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    	
    	// Función matemática para aproximar una respuesta lineal del control de volumen
    	// Extraída de "General MIDI System Level 1 Developer Guidelines", MIDI Manufacturers Association
    	float db = 40.0f * (float)Math.log10(vol / 127.0);
    	gainControl.setValue(db);
    	
    	// Se debe enviar otra vez el comando de reproducir para que el ajuste haga efecto, pero
    	// no queremos que se haga para la música que no esté reproduciéndose en ese momento ya
    	// que sino comenzarían a sonar todos los temas a la vez
    	if (this.isReproduciendo()) clip.start();
    }

}