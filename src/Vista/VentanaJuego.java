package Vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class VentanaJuego {
    
    private JFrame ventana;
    private JButton botonIniciar;
    private JButton botonOpciones;


    public VentanaJuego() {


        ventana = new JFrame("The Last Pounch");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(1200, 800);
        ventana.setLayout(null);

        botonIniciar = new JButton("Iniciar Juego");
        botonOpciones = new JButton("Opciones");

        botonIniciar.setBounds(300, 200, 200, 50);
        botonOpciones.setBounds(300, 300, 200, 50);    
        
        ventana.add(botonIniciar);
        ventana.add(botonOpciones);
    }

    public void mostrarVentana() {
        ventana.setVisible(true);
    }

    public JButton getBotonIniciar() {
        return botonIniciar;
    }

    public JButton getBotonOpciones() {
        return botonOpciones;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(ventana, mensaje);
    }
}
