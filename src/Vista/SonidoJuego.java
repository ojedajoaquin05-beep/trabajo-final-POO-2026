package Vista;

import Modelo.Juego;
import Modelo.Musica;
import Modelo.ObservadorJuego;

// Segundo observador. cuando termina el combate, corta la música de pelea y pone la del menu
public class SonidoJuego implements ObservadorJuego {

    private final Musica[] musicaPelea;
    private final Musica musicaMenu;
    private boolean finProcesado = false; // para reaccionar una sola vez por combate

    public SonidoJuego(Musica[] musicaPelea, Musica musicaMenu) {
        this.musicaPelea = musicaPelea;
        this.musicaMenu = musicaMenu;
    }

    @Override
    public void alCambiarEstadoJuego(Juego juego) {
        if (juego.hayGanador() && !finProcesado) {
            finProcesado = true;
            for (Musica m : musicaPelea) {
            	m.detener();
            }
            musicaMenu.reproducirLoop();
        } else if (!juego.hayGanador()) {
            finProcesado = false; // combate nuevo: vuelve a quedar listo
        }
    }
}