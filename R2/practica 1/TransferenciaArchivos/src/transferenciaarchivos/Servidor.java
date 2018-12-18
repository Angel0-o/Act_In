/* Cristopher Arellano Manjarrez 
   Marco Antonio Hernandez Gutierrez
    practica 1
*/


package transferenciaarchivos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Servidor {

    static String ruta;
    public static void main(String[] args) throws Exception{
        
        ServerSocket ss = new ServerSocket(4005);//CREAMOS EL SOCKET DE SERVIDOR
        System.out.println("Servidor iniciado, esperando al cliente...");//NOTIFICAMOS QUE SE INICIÓ EL SERVIDOR Y ESTÁ ESPERANDO UN CLIENTE
        String ruta="C:\\Users\\Black\\Desktop\\recibido\\";
        while(true){
            
            Socket cliente;//CREAMOS EL SOCKET DEL CLIENTE
            BufferedInputStream bis;//CREAMOS EL BUFFER DE FLUJO DE ENTRADA
            BufferedOutputStream salida;//CREAMOS EL BUFFER DE FLUJO DE SALIDA
            DataInputStream dis;//CREAMOS EL FLUJO DE ENTRADA DE DATOS
            
            byte[] datosRecibidos = new byte[1024];//CREAMOS UN OBJETO DE LOS BYTES QUE SE VAN A RECIBIR EN BLOQUES DE 1024 BYTES
            
            cliente = ss.accept();//EL CLIENTE ACEPTA LA CONEXIÓN DEL SOCKET DEL SERVIDOR
            System.out.println("Cliente conectado desde: "+cliente.getInetAddress()+" Puerto: "+cliente.getPort());//MOSTRAMOS MENSAJE INDICANDO DESDE QUE DIRECCIÓN Y QUE PUERTO SE HA CONECTADO EL CLIENTE
            
            bis = new BufferedInputStream(cliente.getInputStream());//CREAMOS UN BUFFER DE FLUJO DE ENTRADA DE EL CLIENTE
            dis = new DataInputStream(cliente.getInputStream());//CREAMOS UN FLUJO DE ENTRADA DE DATOS DE EL CLIENTE
            
            String nombre_archivo = dis.readUTF();//LEEMOS EL NOMBRE DEL ARCHIVO
            int numero_archivos = dis.readInt();//LEEMOS EL NUMERO DE ARCHIVOS
            long tamanio_archivos = dis.readLong();//LEEMOS EL TAMAÑO DE LOS ARCHIVOS
            
            //MOSTRAMOS LA INFORMACIÓN DE LOS ARCHIVOS RECIBIDOS
            System.out.println("Nombre del archivo: " +nombre_archivo+ "\nTamaño: " +tamanio_archivos+ " Bytes \nTotal de archivos recibidos: " +numero_archivos);
            salida = new BufferedOutputStream(new FileOutputStream(ruta+nombre_archivo));//PASAMOS "nombre_archivo" AL BUFFER DE FLUJO DE SALIDA
            
            DataOutputStream out = new DataOutputStream(cliente.getOutputStream());//OBTENEMOS EL FLUJO DE SALIDA DEL CLIENTE
            String mensaje = "Archivo recibido";
            out.writeUTF(mensaje);
            
            System.out.println("-----------------------------------------------------------------------------------------------");
            
            //CREAMOS LA PARTE GRÁFICA DE LA TRANSFERENCIA DE LOS ARCHIVOS
            JDialog dialog = new JDialog();
            JProgressBar progressbar = new JProgressBar(0, 100);
            dialog.setSize(200, 100);
            progressbar.setStringPainted(true);
            dialog.add(progressbar);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            
            //FLUJO QUE SE HARÁ MIENTRAS SE TRANSFIEREN LOS ARCHIVOS DENTRO DE LA BARRA
            long size = tamanio_archivos, c = 0;
            int l = 0;
            progressbar.setMaximum((int) size);
            int in;
            while((in = bis.read(datosRecibidos)) != -1){
                l += in;
                salida.write(datosRecibidos, 0, in);
                progressbar.setValue(l);
            }
            bis.close();
            salida.close();
        }
        
    }
    
}
