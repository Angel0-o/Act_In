package practica_7;

import Modelo.Pool;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author angel
 */
public class HiloReplica extends Thread
{
    ServerSocket servidor;
    Socket tabla[] = new Socket[10];
    Socket s;
    int conexionesActuales = 0;
    Jugador jugador;
    ObjectOutputStream r_salida;
    ObjectInputStream r_entrada;
    boolean estado;
    Pool pool;
    String query;
    ArrayList<String> fila;
    boolean banderaMaestro;
    Servidor.MarcoServidor marcos;
    
    public HiloReplica(String q, Servidor.MarcoServidor ms) throws IOException
    {
        Pool pool = new Pool();
        fila = new ArrayList<String>();
        query = q;
        banderaMaestro = true;
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
        if (banderaMaestro)
        {
            try {
                servidor = new ServerSocket(6001);
                System.out.println("Maestro esperando conexiones....");
                while (true) 
                {
                    Socket s = new Socket();
                    try
                    {
                        s = servidor.accept();
                        System.out.println("Esclavo conectado : "+s.getInetAddress());
                        tabla[conexionesActuales] = s;
                        r_salida = new ObjectOutputStream(tabla[conexionesActuales].getOutputStream());
                        r_entrada = new ObjectInputStream(tabla[conexionesActuales].getInputStream());
                        
                        jugador = (Jugador) r_entrada.readObject();
                        
                        jugador.setQuery(query);
                        r_salida.writeObject(jugador);
                        System.out.println("Enviando Replica " + query);
                        
                        r_entrada.close();
                        r_salida.close();
                        s.close();
                        
                        conexionesActuales++;
                    } catch (IOException ex) {
                        Logger.getLogger(HiloJuegoServidor.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(HiloReplica.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(HiloReplica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            try 
            {
                s = new Socket("localhost",6001);
                System.out.println("Esclavo Conectado....");
                r_entrada = new ObjectInputStream(s.getInputStream());
                r_salida = new ObjectOutputStream(s.getOutputStream());
                
                r_salida.writeObject(jugador);
                System.out.println("Enviando Datos de " + jugador.getNombre());
                
                jugador = (Jugador) r_entrada.readObject();
                System.out.println("Recibiendo Replica : "+ jugador.getQuery());
                
                estado = pool.actualiza("root","root",jugador.getQuery());
                System.out.println("Insert "+estado);
                
                ponerImagen(jugador.getRuta());
                
                r_entrada.close();
                r_salida.close();
                s.close();
            } catch (IOException ex) {
                Logger.getLogger(HiloReplica.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HiloReplica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
