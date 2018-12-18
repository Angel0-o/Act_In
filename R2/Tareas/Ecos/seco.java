import java.io.*;
import java.net.*;

public class seco
{
	public static void main(String[] args)
	{
		try
		{
			int pto=5000;//Puerto al cual nos conectaremos
			ServerSocket s=new ServerSocket(pto);//Creamos el socketServer
			Socket cl=new Socket();//Creamos el socketCliente
			System.out.println("Servicio ECO inicaido, Esperando cliente");
			for(;;)
			{
				cl=s.accept();//Aceptamos la conexion del cliente
				System.out.println("Cliente conectado desde la direccion: "+cl.getInetAddress()+" Puerto: "+cl.getPort());
				BufferedReader entrada = new BufferedReader(new InputStreamReader(cl.getInputStream()));//Creamos nuestro buffer
				PrintWriter pw=new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));//Creamos nuestro escritor de flujo
				for(;;)
				{
					String msj= entrada.readLine();//String que almacenara la entrada del cliente
					if(msj.compareToIgnoreCase("salir")==0)
					{
						System.out.println("Cliente termina conexion");
						entrada.close();//Cerramos buffer
						pw.close();//Cerramos escritor
						cl.close();//Cerramos cone con Cliente
						break;
					}
					else
					{
						System.out.println("Mensaje recicbido: "+msj);
						String eco="ECO: "+msj;//Eco
						pw.println(eco);//Escribimos eco
						pw.flush();//Limpiamos 
						continue;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}