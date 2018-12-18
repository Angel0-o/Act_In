package practica_7;

import Modelo.Pool;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import practica_7.Servidor.MarcoServidor;

/**
 *
 * @author angel
 */
public class HiloJuegoServidor extends Thread
{    
    ServerSocket servidor;
    Socket tabla[] = new Socket[100];
    int conexionesActuales = 0;
    Jugador jugador;
    ObjectOutputStream j_salida;
    ObjectInputStream j_entrada;
    boolean estado;
    Pool pool;
    String query;
    ArrayList<String> fila;
    MarcoServidor marcos;
    
    public HiloJuegoServidor(MarcoServidor ms) throws IOException 
    {
        servidor = new ServerSocket(6000);
        Pool pool = new Pool();
        fila = new ArrayList<String>();
        marcos = ms;
    }
    
    public void ponerImagen(String ruta)
    {
        int ancho= 300;
    	int alto=390;
        ImageIcon muestra_img = new ImageIcon(ruta);
    	Icon muestra_icon = new ImageIcon(muestra_img.getImage().getScaledInstance(ancho, alto, java.awt.Image.SCALE_DEFAULT));
    		
    	marcos.lamina1.muestra.setIcon(muestra_icon);
    }
    
    public void run() 
    {
        System.out.println("Esperando conexiones....");
        while (true) {
            Socket s = new Socket();
            try 
            {
                s = servidor.accept();
                System.out.println("Conectado : "+s.getInetAddress());
                tabla[conexionesActuales] = s;
                j_salida = new ObjectOutputStream(tabla[conexionesActuales].getOutputStream());
                j_entrada = new ObjectInputStream(tabla[conexionesActuales].getInputStream());
                jugador = (Jugador) j_entrada.readObject();
                System.out.println("Recibiendo : "+ jugador.getNombre());
           
                query = "insert into peticiones (ip,nombre,hora) values ('"+jugador.getIp()+"','"+jugador.getNombre()+"','"+jugador.getHora()+"');";
                estado = pool.actualiza("root","root",query);
                System.out.println("Insert "+estado);
                
                //Crear HiloReplica
                
                query = "SELECT * FROM cartas ORDER BY RAND() LIMIT 1;";
                fila = pool.consulta("root","root",query);
                System.out.println(fila);
                
                jugador.setCarta(fila.get(0));
                jugador.setRuta(fila.get(1));
                ponerImagen(jugador.getRuta());
                
                j_salida.writeObject(jugador);
                System.out.println("Enviando Datos de " + jugador.getCarta());
                //System.out.println("Enviado y recibido...");
            
            
                j_salida.close();
                j_entrada.close();
                s.close();
                
                conexionesActuales++;
            } catch (IOException ex) {
                Logger.getLogger(HiloJuegoServidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HiloJuegoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
