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

public class Envia_O
{
	public static void main(String[] args)
	{
		try
		{	
			int pto = 1234;
			String host = "127.0.0.1";
			Socket cl = new Socket(host,pto);
			System.out.println("Conexion establecida");
			Objeto o1 = new Objeto("Miguel","Morales","García",21,2014630465);
			ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
			//Enviar
			oos.writeObject(o1);
			oos.flush();
			System.out.println("Enviado:\n"+
								"Nombre: "+o1.nombre+"\n"+
								"Apellido paterno: "+o1.apaterno+"\n"+
								"Apellido materno: "+o1.amaterno+"\n"+
								"Edad: "+o1.edad+"\n"+
								"No. de Boleta: "+o1.boleta);
			oos.close();
			cl.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
