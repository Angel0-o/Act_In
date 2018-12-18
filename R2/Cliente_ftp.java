import java.net.*;
import java.io.*;
public class Cliente_ftp{
	public static void main(String[] args) throws Exception{
	Socket cl = new Socket(InetAddress.getByName("127.0.0.1"),4000);
	System.out.println("Cliente conectado..\n transfiriendo archivo..");
	BufferedInputStream bis = new BufferedInputStream(cl.getInputStream());
	//BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("cancion2.mp3"));
	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("duke1_1.png"));
	cl.setSoTimeout(3000);
	byte[] buf = new byte[1024];
	int leidos;
	while((leidos=bis.read(buf,0,buf.length))!=-1){
		bos.write(buf,0,leidos);
		bos.flush();
		}//while
	bis.close();
	bos.close();
	System.out.println("Archivo copiado....");
		
	}//main
	
}//class