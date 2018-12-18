/*
* INSTITUTO POLITECNICO NACIONAL
* ESCUELA SUPERIOR DE COMPUTO
* APLICACIONES PARA COMUNICACIONES EN RED
* 
* Bautista Rosales Sandra Ivette
*
* Arellano Cristopher
* Hernandez Marco
*/

package com.servidor;

import com.controlador.Compra;
import com.modelo.Producto;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Servidor {

    private static ArrayList<Producto> reportesProductos = null;
    private static ArrayList<Integer> idRecibidos = null;
    static BufferedOutputStream bos = null;
    static ObjectInputStream ois = null;
    static ObjectOutputStream oos = null;
    private static Producto[] p = new Producto[4];

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket cl = null;
        ServerSocket s = new ServerSocket(3080);
        System.out.println("Servidor en espera de compras... ");
        p[0] = new Producto("Tennis Adidas", "/src/com/ico/tennisAdidas.png", 1000.00, 100, 01);
        p[1] = new Producto("Tennis Jordan", "/src/com/ico/tennisJordan.png", 3500.00, 100, 02);
        p[2] = new Producto("Balon Champions League", "/src/com/ico/balonAdidas.png", 700.00, 100, 03);
        p[3] = new Producto("Tacos Nike Mercurial", "/src/com/ico/tennisNMercurial.png", 3000, 100, 04);
        for (;;) {
            cl = s.accept();
            System.out.println("Cliente conectado desde : " + cl.getInetAddress() + ":" + cl.getPort());
            ois = new ObjectInputStream(cl.getInputStream());
            idRecibidos = (ArrayList<Integer>) ois.readObject();
            //id de los elementos comprados
            for (int i = 0; i < idRecibidos.size(); i++) {
                System.out.println("Id articulo vendido: " + idRecibidos.get(i));
            }
            //Modifica El Stock por cada conexión al servidor
            p = modificaExistencia(idRecibidos, p);
            //ArrayList de objetos de productos a generar en el reporte
            reportesProductos = new ArrayList<Producto>(idRecibidos.size());
            reportesProductos = generaReporte(idRecibidos, p);
            avisoStock(p);
            oos = new ObjectOutputStream(cl.getOutputStream());
            oos.writeObject(reportesProductos);
            oos.reset();
            oos.flush();
            
        }
    }

    /*
    @params Recibe nuestros id de productos y el arreglo de productos en venta
    con el stock actualizado
    */
    public static ArrayList<Producto> generaReporte(ArrayList<Integer> ai, Producto[] p) {
        ArrayList<Producto> alp = new ArrayList<Producto>(ai.size());
        for (int i = 0; i < ai.size(); i++) {
            if (ai.get(i) == 01) {
                alp.add(p[0]);
            } else if (ai.get(i) == 02) {
                alp.add(p[1]);
            } else if (ai.get(i) == 03) {
                alp.add(p[2]);
            } else if (ai.get(i) == 04) {
                alp.add(p[3]);
            }
        }
        return alp;
    }

    public static void avisoStock(Producto[] p) {
        int tam = p.length;
        for (int i = 0; i < tam; i++) {
            System.out.println("Cantidad en stock de "+ p[i].getNombre() + ": " + p[i].getExistencia() );
        }
    }

    public static Producto[] modificaExistencia(ArrayList<Integer> p, Producto[] pe) {
        int i = 0;
        int aux = 0;
        do {
            if (null != p.get(i)) switch (p.get(i)) {
                case 01:
                    aux = pe[0].getExistencia();
                    aux -= 1;
                    pe[0].setExistencia(aux);
                    break;
                case 02:
                    aux = pe[1].getExistencia();
                    aux -= 1;
                    pe[1].setExistencia(aux);
                    break;
                case 03:
                    aux = pe[2].getExistencia();
                    
                    aux -= 1;
                    pe[2].setExistencia(aux);
                    break;
                case 04:
                    aux = pe[3].getExistencia();
                    //if (aux>0){
                    aux -= 1;
                    pe[3].setExistencia(aux);
                    //}
                  
                        
                    break;
                default:
                    break;
            }
            i++;
        } while (i < p.size());
        return pe;
    }

}
