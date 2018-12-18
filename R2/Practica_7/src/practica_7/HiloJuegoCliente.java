package practica_7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import practica_7.Cliente.MarcoCliente;

/**
 *
 * @author angel
 */
public class HiloJuegoCliente extends Thread
{
    Socket socket = null;
    Jugador jugador;
    ObjectOutputStream j_salida;
    ObjectInputStream j_entrada;
    MarcoCliente marcoc;

    public HiloJuegoCliente(String nm, String host, MarcoCliente mc) throws IOException 
    {
        socket = new Socket(host,6000);
        jugador = new Jugador(socket.getInetAddress().getHostAddress(),nm,"12:00");
        marcoc = mc;
    }
    
    public void run() 
    {
        try {
            j_salida = new ObjectOutputStream(socket.getOutputStream());
            j_entrada = new ObjectInputStream(socket.getInputStream());
            j_salida.writeObject(jugador);
            System.out.println("Enviando Datos de " + jugador.getNombre());
            //System.out.println("Enviado y recibido...");
            jugador = (Jugador) j_entrada.readObject();
            System.out.println("Recibiendo carta: "+jugador.getCarta());
            marcoc.lamina1.muestra_carta.setText(jugador.getCarta());
            j_salida.close();
            j_entrada.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(HiloJuegoCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HiloJuegoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
