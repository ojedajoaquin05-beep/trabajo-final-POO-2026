package Modelo;

public interface ConfiguracionDAO {
    boolean setVolumen(int vol);
    int getVolumen();
	boolean setMusicaPelea(int musica);
	int getMusicaPelea();
	boolean setMute(boolean mute);
	boolean getMute();
}