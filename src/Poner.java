import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//Creacion de un JFrame
public class Poner extends javax.swing.JFrame {


    private JPanel contentPane;

    //Metodo constructor para la construccion del Pane que le pasamos el arbol
    public Poner(Tree a) {
        //Inicializamos propiedades
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(Color.CYAN);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        setBounds(0,0,800,400);
        //Creamos la instancia para el modelado del arbol
        Graficar F = new Graficar(a);
        //Agregamos el panel al Frame
        contentPane.add(F, BorderLayout.CENTER);
    }


}