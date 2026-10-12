package Modelo;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.File;

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

    public void reanudar() {
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}