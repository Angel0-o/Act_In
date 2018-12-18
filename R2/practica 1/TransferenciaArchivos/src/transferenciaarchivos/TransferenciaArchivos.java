/* Cristopher Arellano Manjarrez 
   Marco Antonio Hernandez Gutierrez
    practica 1
*/

package transferenciaarchivos;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;

public class TransferenciaArchivos extends JFrame{
    
    private final JButton boton_adjuntar_archivos;
    private final JButton boton_enviar_archivos;
    private final JPanel panel_chooser;
    private final JPanel panel_metadatos_archivos;
    private final JLabel etiqueta_metadatos;
    private final JTextArea area_metadatos;
    private final JFileChooser file_chooser;
    
    public TransferenciaArchivos(){
        
        super("Transferencia de Archivos");
        
        boton_adjuntar_archivos = new JButton();
        boton_enviar_archivos = new JButton();
        panel_chooser = new JPanel();
        panel_metadatos_archivos = new JPanel();
        etiqueta_metadatos = new JLabel();
        area_metadatos = new JTextArea();
        file_chooser = new JFileChooser();
        panel_chooser.setLayout(new GridLayout(1, 1));
        setLayout(new FlowLayout());
        
        ImageIcon imagen = new ImageIcon("filetransfer.png");
        
        boton_adjuntar_archivos.setIcon(imagen);
        panel_chooser.add(boton_adjuntar_archivos);
        boton_adjuntar_archivos.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Cliente cliente = new Cliente();
            }
        });
        add(panel_chooser);
    }

   
    public static void main(String[] args) {
        
        TransferenciaArchivos interfaz = new TransferenciaArchivos();
        interfaz.setBounds(300, 300, 500, 300);
        interfaz.setVisible(true);
        interfaz.setDefaultCloseOperation(interfaz.EXIT_ON_CLOSE);
        interfaz.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        
    }
    
}
