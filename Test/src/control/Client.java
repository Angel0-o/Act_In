package control;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client 
{
	Socket cliente;
	ObjectOutputStream salida;
	ObjectInputStream entrada;
	
	public Client(String ip, int puerto) throws UnknownHostException, IOException 
	{
		cliente = new Socket(ip,puerto);
		salida = new ObjectOutputStream(cliente.getOutputStream());
		entrada = new ObjectInputStream(cliente.getInputStream());
	}
	
	public void escribe(Jugador j) throws IOException
	{
		salida.writeObject(j);
	}
	
	public Jugador lee() throws ClassNotFoundException, IOException
	{
		Jugador j = new Jugador();
		j = (Jugador) entrada.readObject();
		return j;
	}

}
