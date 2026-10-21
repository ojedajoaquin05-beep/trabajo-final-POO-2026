package Modelo;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

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

    public void reanudar() {
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}