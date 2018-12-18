/* Cristopher Arellano Manjarrez 
   Marco Antonio Hernandez Gutierrez
    practica 1
*/


package transferenciaarchivos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public final class Cliente {
    
    Socket cliente;//DEFINIMOS UN SOCKET DEL CLIENTE
    
    public Cliente(){
        
        JFileChooser file_chooser = new JFileChooser();//INSTANCIAMOS UN OBJETO DE JFileChooser
        file_chooser.setMultiSelectionEnabled(true);//HABILITAMOS LA MULTISELECCIÓN DE ARCHIVOS
        int resultado = file_chooser.showOpenDialog(null);//GUARDAMOS EL RESULTADO DE EL SELECTOR DE ARCHIVOS
        if(resultado == JFileChooser.APPROVE_OPTION){
            File[] archivo = file_chooser.getSelectedFiles();//OBTENEMOS LOS ARCHIVOS SELECCIONADOS
            int numero_de_archivos = archivo.length;//GUARDAMOS EL TOTAL DE ARCHIVOS QUE SE SELECCIONARON
            //EMPEZAMOS A ENVIAR LOS ARCHIVOS
            for(int i = 0; i < numero_de_archivos; i++){
                try{
                    enviarArchivo(i, numero_de_archivos, archivo[i]);//ENVIAMOS EL ARCHIVO
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    public void enviarArchivo(int num_archivo, int numero_archivos, File archivo) throws java.io.IOException{
        
        //CREAMOS EL SOCKET DEL CLIENTE
        cliente = new Socket(InetAddress.getByName("127.0.0.1"), 4005);
        System.out.println("Conexión aceptada");
        
        BufferedOutputStream salida = new BufferedOutputStream(cliente.getOutputStream());//CREAMOS UN BUFFER DE FLUJO DE SALIDA DEL SOCKET DEL CLIENTE
        DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());//CREAMOS UN FLUJO DE SALIDA DE DATOS DEL SOCKET DEL CLIENTE
        
        String nombre_archivo;//GUARDAMOS EL NOMBRE DEL ARCHIVO
        long tamanio_archivo;//GUARDAMOS EL TAMAÑO DEL ARCHIVO
        byte[] buffer = new byte[8192];//LEEMOS BLOQUES DE 8KB
        
        //MOSTRAMOS EL NOMBRE Y EL TAMAÑO DE LOS ARCHIVOS QUE SE ESTÁN ENVIANDO
        System.out.println("Enviando archivos");
        nombre_archivo = archivo.getName();
        tamanio_archivo = archivo.length();
        
        //ESCRIBIMOS LOS DATOS DEL ARCHIVO EN EL DataOutputSream
        dos.writeUTF(nombre_archivo);
        dos.writeInt(numero_archivos);
        dos.writeLong(tamanio_archivo);
        
        System.out.println("Nombre del archivo: "+nombre_archivo+"\nTamaño: "+archivo.length()+" Bytes");//MOSTRAMOS LOS DATOS DEL ARCHIVO
        
        DataInputStream dis = new DataInputStream(cliente.getInputStream());//CREAMOS UN FLUJO DE ENTRADA DE DATOS DEL SOCKET DEL CLIENTE
        
        BufferedInputStream entrada = new BufferedInputStream(new FileInputStream(archivo));//CREAMOS UN FLUJO DE BUFFER DE ENTRADA PARA EL ARCHIVO
        String mensaje_servidor = dis.readUTF();
        System.out.println(mensaje_servidor);
        
        System.out.println("-----------------------------------------------------------------------------------------------");
        
        //CREAMOS LA PARTE GRÁFICA
        JDialog dialog = new JDialog();
        JProgressBar progressbar = new JProgressBar(0, 100);
        dialog.setSize(200, 100);
        progressbar.setStringPainted(true);
        dialog.add(progressbar);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        
        //FLUJO MIENTRAS SE TRANSFIEREN LOS ARCHIVOS
        long size = archivo.length(), c = 0;
        int l = 0;
        progressbar.setMaximum((int) size);
        int in;
        while((in = entrada.read(buffer)) != -1){
            l += in;
            salida.write(buffer, 0, in);
            progressbar.setValue(l);
        }
        entrada.close();
        salida.close();
        dos.close();
    }
    
}
