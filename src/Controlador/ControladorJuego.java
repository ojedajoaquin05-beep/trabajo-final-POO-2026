package Controlador;

import Vista.VentanaJuego;
import main.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControladorJuego {

    private final VentanaJuego ventana;
    private final Main main;

    public controladorJuego(VentanaJuego ventana, Main main) {
        this.ventana = ventana;
        this.main = main;

        ventana.getBotonIniciar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                iniciarJuego();
            }
        });

        ventana.getBotonOpciones().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarOpciones();
            }
        });
    }


    public void iniciarJuego() {
        // Lógica para iniciar el juego
        System.out.println("Iniciando el juego...");
    }
    
}
