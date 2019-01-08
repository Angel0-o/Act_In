package control;

import java.io.*;

public class test {

	public static void main(String[] args) throws IOException 
	{
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		String entrada = null;
		
		Jugador jugador1 = new Jugador();
		System.out.println("Escribe la IP a la que deseas conectarte");
		entrada = buffer.readLine();
		System.out.println("La IP es "+entrada);
		jugador1.IP = entrada;
		
		System.out.println("Escribe el puerto al que deseas conectarte");
		entrada = buffer.readLine();
		System.out.println("El puerto es "+entrada);
		jugador1.puerto = Integer.parseInt(entrada);
		
		System.out.println("Escribe la dificultad");
		entrada = buffer.readLine();
		System.out.println("La dificultad que escogiste es "+entrada);
		jugador1.IP = entrada;
	}

}
