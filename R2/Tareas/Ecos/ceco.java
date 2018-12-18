import java.net.*;
import java.io.*;

public class ceco
{
	public static void main(String[] args)
	{
		try
		{
			InetAddress host=null;
			BufferedReader br= new BufferedReader(new InputStreamReader(System.in));//Creamos nuestro buffer
			int pto=5000;//Puerto al cual nos conectaremos
			String dir="";
			for(;;)
			{
				System.out.println("Escribe la direccion del servidor");
				dir=br.readLine();
				try
				{
					host=InetAddress.getByName(dir);
				}
				catch(UnknownHostException e)
				{
					System.err.println("direccion no valida");
					continue;
				}
				Socket cl=new Socket(host,pto);//Creamos el socketcliente
				System.err.println("\nConexion establecida \n Escribe mensaje <Enter> para enviar\n /salir/ para terminar");
				PrintWriter pw=new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));//Creamos nuestro escritor de flujo
				BufferedReader entrada = new BufferedReader(new InputStreamReader(cl.getInputStream()));//Creamos nuestro buffer
				for(;;)
				{
					String msj= br.readLine();//string que leera desde el teclado
					pw.println(msj);
					pw.flush();
					if(msj.compareToIgnoreCase("salir")==0)
					{
						System.out.println("Termina conexion");
						br.close();//Cerramos todo
						pw.close();
						cl.close();
						System.exit(0);
					}
					else
					{
						String eco=entrada.readLine();//Eco
						System.out.println("Eco recicbido "+eco);
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