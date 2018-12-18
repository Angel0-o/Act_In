/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_7;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import Control.Constantes;
import Control.marcaTemporal;


public class Cliente extends javax.swing.JFrame {
    
    String nombreMaquina = "maquina1";
    MarcoCliente marcoc;

    public Cliente() {
    	marcoc = new MarcoCliente();
		marcoc.setVisible(true);
    }

    public static void main(String args[]) throws IOException {
        Cliente c1 = new Cliente();
        
    }
    
    public class MarcoCliente extends JFrame
    {
    	Laminac lamina1;
    	public MarcoCliente() 
    	{
    		Toolkit mipantalla = Toolkit.getDefaultToolkit();
    		Dimension tamano = mipantalla.getScreenSize();
    		setSize(tamano.width/3,tamano.height/3);
    		setLocation(tamano.width/4,tamano.height/4);
    		
    		setTitle("Jugador");
    		Image icono = mipantalla.getImage(Constantes.RUTA_IMAGENES+"Crash_Icon.png");
    		setIconImage(icono);
    		
    		setResizable(true);
    		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		
    		lamina1 = new Laminac();
    		add(lamina1);
    	}
    }

    class Laminac extends JPanel implements ActionListener 
    {
    	JTextField clk;
    	marcaTemporal conta;
    	JTextArea muestra_carta;
    	JButton boton;
    	Box cajav;
    	public Laminac() 
    	{
    		muestra_carta = new JTextArea();
    		muestra_carta.setText("Mesaje de Prueba");
    		muestra_carta.setFont(new java.awt.Font("Verdana", 0, 28));
    		
    		clk = new JTextField(1);
            clk.setFont(new java.awt.Font("Verdana", 0, 28));
            clk.setText("0000");
            conta = new marcaTemporal(clk);
    		
    		boton = new JButton("Pedir Carta");
    		boton.setFont(new java.awt.Font("Verdana", 0, 28));
    		boton.addActionListener(this);
    		
    		
    	    cajav = Box.createVerticalBox();
    		cajav.add(Box.createVerticalStrut(30));
    		cajav.add(muestra_carta);
    		cajav.add(Box.createVerticalStrut(30));
    		cajav.add(clk);
    		cajav.add(Box.createVerticalStrut(30));
    		cajav.add(boton);
    		add(cajav,BorderLayout.CENTER);
    	}
    	
    	public void actionPerformed(ActionEvent e) 
    	{
                try {
                    HiloJuegoCliente hjc = new HiloJuegoCliente(nombreMaquina, "localhost", marcoc);
                    hjc.start();
                } catch (IOException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
    	}
    	
    }
}
