import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;

public class Envia
{
	public static void main(String[] args)
	{
		try
		{
			String host="127.0.0.1";
			int pto=5000;
			JFileChooser jf = new JFileChooser();
			int r = jf.showOpenDialog(null);
			if(r==JFileChooser.APPROVE_OPTION){
				File f = jf.getSelectedFile();
				String nombre = f.getName();
				String path = f.getAbsolutePath();
				long tam = f.length();
				Socket cl = new Socket(host,pto);
				System.out.println("Conexion establecida\nSe enviara el archivo: "+nombre+" Size of: "+tam+" bytes");
				DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
				DataInputStream dis = new DataInputStream(new FileInputStream(path));
				dos.writeUTF(nombre);
				dos.flush();
				dos.writeLong(tam);
				dos.flush();
				long n=0;//Acumulador
				int i = 0, porcentaje=0;
				while(n<tam)
				{
					byte[] b = new byte[2000];//Buffer - paquetes de 2000 bytes
					i=dis.read(b);
					n=n+i;
					porcentaje=(int)((n*100)/tam);
					dos.write(b,0,i);
					dos.flush();
					System.out.println("Enviado el "+porcentaje+"%");
				}
				System.out.println("Archivo enviado");
				dis.close();
				dos.close();
				cl.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}