import java.net.*;
import java.io.*;

public class Recibe
{
	public static void main(String[] args)
	{
		try
		{
			int pto = 5000;
			ServerSocket s = new ServerSocket(pto);
			System.out.println("Esperando conexion\n");
			for(;;)
			{
				Socket cl = s.accept();
				System.out.println("Cliente conectado: "+cl.getInetAddress()+"-"+cl.getPort());
				DataInputStream dis = new DataInputStream(cl.getInputStream());
				for(;;)
				{
					String nom = dis.readUTF();
					long tam = dis.readLong();
					System.out.println("Recibiendo archivo: "+nom+" Size of: "+tam+" bytes");
					DataOutputStream dos = new DataOutputStream(new FileOutputStream("C:/Recibe/"+nom));
					int n=0,i=0,porcentaje=0;
					while(n<tam)
					{
						byte[] b = new byte[2000];
						i=dis.read(b);
						dos.write(b);
						dos.flush();
						n+=i;
						porcentaje=(int)((n*100)/tam);
						System.out.println("Recibiendo: "+porcentaje+"%");
						dos.flush();
					}
					System.out.println("Archivo recibido");
					dis.close();
					dos.close();
					cl.close();
					s.close();
					break;
				}
				break;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}