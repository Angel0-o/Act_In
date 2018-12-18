import java.io.Serializable;
import java.net.*;
import java.io.*;

class Objeto implements Serializable
{
	
	String nombre;
	String apaterno;
	String amaterno;
	int edad;
	int boleta;

	public Objeto(String n, String p, String m, int e, int b)
	{
		this.nombre=n;
		this.apaterno=p;
		this.amaterno=m;
		this.edad=e;
		this.boleta=b;
	}
}

public class Recibe_O
{
	public static void main(String[] args)
	{
		try
		{	
			int pto = 1234;
			ServerSocket s = new ServerSocket(pto);
			System.out.println("Esperando conexion");
			for(;;)
			{
				Socket cl = s.accept();
				System.out.println("Cliente conectado: "+cl.getInetAddress()+"-"+cl.getPort());
				//Recibir
				ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());
				Objeto o2 = (Objeto)ois.readObject();
				//ois.flush();

				System.out.println("Recibido:\n"+
									"Nombre: "+o2.nombre+"\n"+
									"Apellido paterno: "+o2.apaterno+"\n"+
									"Apellido materno: "+o2.amaterno+"\n"+
									"Edad: "+o2.edad+"\n"+
									"No. de Boleta: "+o2.boleta);
				cl.close();
				s.close();
				ois.close();
				break;
			}	

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}