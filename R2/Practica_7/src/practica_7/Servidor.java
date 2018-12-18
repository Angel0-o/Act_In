/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_7;

import java.io.IOException;
import static java.lang.Thread.sleep;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import Control.*;

/**
 *
 * @author alumno
 */
public class Servidor extends javax.swing.JFrame{

   
    MarcoServidor marcos;

    public Servidor() {
        
        marcos = new MarcoServidor();
		marcos.setVisible(true);
    }
    
    public class MarcoServidor extends JFrame
    {
    	Lamina lamina1;
    	public MarcoServidor() 
    	{
    		Toolkit mipantalla = Toolkit.getDefaultToolkit();
    		Dimension tamano = mipantalla.getScreenSize();
    		setSize(tamano.width/3,tamano.height/2);
    		setLocation(tamano.width/4,tamano.height/4);
    		
    		setTitle("Server");
    		Image icono = mipantalla.getImage(Constantes.RUTA_IMAGENES+"Crash_Icon.png");
    		setIconImage(icono);
    		
    		setResizable(true);
    		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		
    		lamina1 = new Lamina();
    		add(lamina1);
    	}
    }

    class Lamina extends JPanel implements ActionListener 
    {
    	JTextField clk;
    	marcaTemporal conta;
    	JLabel muestra;
    	JButton boton;
    	Box cajav;
    	public Lamina() 
    	{
    		int ancho= 300;
    		int alto=390;
    		ImageIcon muestra_img = new ImageIcon(Constantes.RUTA_IMAGENES+"tapa.jpg");
    		Icon muestra_icon = new ImageIcon(muestra_img.getImage().getScaledInstance(ancho, alto, java.awt.Image.SCALE_DEFAULT));
    		
    		muestra = new JLabel();
    		muestra.setIcon(muestra_icon);
    		
    		clk = new JTextField(1);
            clk.setFont(new java.awt.Font("Verdana", 0, 28));
            clk.setText("0000");
            conta = new marcaTemporal(clk);
    		
    		boton = new JButton("Reiniciar");
    		boton.setFont(new java.awt.Font("Verdana", 0, 28));
    		
    		cajav = Box.createVerticalBox();
    		cajav.add(muestra);
    		cajav.add(clk);
    		cajav.add(boton);
    		add(cajav,BorderLayout.CENTER);
    	}
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
		}
    	
    }
    
    public static void main(String args[]) throws IOException, ClassNotFoundException 
    {
    	Servidor s1 = new Servidor();
        HiloJuegoServidor hjs = new HiloJuegoServidor(s1.marcos);
        hjs.start();
    }
}
