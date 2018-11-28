/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Collections.list;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

/**
 *
 * @author luigui
 */
public class Cliente extends JFrame implements Runnable,ItemListener,ActionListener,MouseListener{
    static final long serialVersionUID = 1L;
    private static int Port=9090;
    private static final String direccion="localhost";
    private static String mensaje="";
    private static String cadenaEnviada="";
    private static String leer="";
    private static String cadenaRecibida="";
    private static String cartaRecibida="";
    private  boolean bandera=false;
    private int estaEditando=0;
    private int h,m,s,total,num,hp,ataque,defensa,servidores;
    private String tok="Pasa el token";
    private String carta="Dame la carta";
    private Float floatValue=1.0f;
    private boolean objetoToken=false;
    private static Connection conexion= null;
    private final String tabla = "pokemones";
    private boolean pokemon=false;
    private String nombre,tipo1,tipo2;
    private int contador=0;
    private String []valores={"0.0","0.10","0.25","0.5","1.0","1.5","2.0","3.0"};
    static Cliente c;
    public JComboBox<String> box;
    public JTextField horas,minutos,segundos;
    public Socket reloj,juego,cliente;
    public BufferedReader mServidor,leerCarta,mCoordinador;
    public PrintWriter envio,mcarta,token,msg;
    public JButton hora,minuto,segundo,peticion;
    public JList<String> cartas;
    public DefaultListModel<String> lista;
    public String []ips={"localhost"};
    public ArrayList<Socket>conexiones=new ArrayList<>();
    
    
    public Cliente(Socket c) {
        //this.sockets = new Socket[servidores];
    }

    public Cliente() {
        //this.sockets = new Socket[servidores];
    }
    
    public Connection conectar(){
        if (conexion == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexion = DriverManager.getConnection("jdbc:mysql://localhost/pokemon", "root","");
                System.out.println("Conectado");
            } catch (SQLException ex) {
                System.out.println("Error "+ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println("Error "+ex.getMessage());
            }
        }
        return conexion;   
    }

    public static void cerrar(){
        if(conexion!=null)
            try {
                conexion.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }

    public void guardar(int num,String nombre,String tipo1,String tipo2,int hp,int ataque,int defensa){
        try {
            PreparedStatement consultar;
            String query="insert into "+this.tabla+" (num,nombre,tipo1,tipo2,hp,ataque,defensa)"+" values (?,?,?,?,?,?,?)";
            consultar=conexion.prepareStatement(query);
            consultar.setInt(1,num);
            consultar.setString(2,nombre);
            consultar.setString(3,tipo1);
            consultar.setString(4,tipo2);
            consultar.setInt(5,hp);
            consultar.setInt(6,ataque);
            consultar.setInt(7,defensa);
            consultar.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
            
    public void frame(){
        JFrame frame=new JFrame("Cliente");
        frame.setPreferredSize(new Dimension(350, 150));
        frame.setLocationRelativeTo(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(3,3));
        panel.revalidate();
        panel.repaint();
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 5);

        horas=new JTextField();
        horas.setBorder(border);
        minutos=new JTextField();
        minutos.setBorder(border);
        segundos=new JTextField();
        segundos.setBorder(border);
        
        
        horas.setEditable(false);
        minutos.setEditable(false);
        segundos.setEditable(false);
        

        horas.setFocusable(false);
        minutos.setFocusable(false);
        segundos.setFocusable(false);
        
        
        hora=new JButton("HORAS");
        minuto=new JButton("MINUTOS");
        segundo=new JButton("SEGUNDOS");
        peticion=new JButton("PETICION");
        peticion.setEnabled(false);
        box= new JComboBox<>(valores);
        
        lista=new DefaultListModel<>();
        cartas=new JList<>(lista);
        cartas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        horas.setHorizontalAlignment(JTextField.CENTER);
        minutos.setHorizontalAlignment(JTextField.CENTER);
        segundos.setHorizontalAlignment(JTextField.CENTER);
        peticion.setHorizontalAlignment(JTextField.CENTER);

        hora.addMouseListener(this);
        minuto.addMouseListener(this);
        segundo.addMouseListener(this);
        peticion.addMouseListener(this);
        box.addItemListener(this);
        box.setSelectedIndex(4);
        
        panel.add(horas);
        panel.add(minutos);
        panel.add(segundos); 
        panel.add(hora);
        panel.add(minuto);
        panel.add(segundo);
        panel.add(peticion);
        panel.add(box);
        panel.add(new JScrollPane(cartas));
        
        frame.setContentPane(panel);
        frame.pack();
    }


    public static void main(String[] args) {
        
        c=new Cliente();
        c.frame();
        c.conectar();
        c.hilos();
    }
    public void hilos(){
        servidores=ips.length;
        for(int i=0;i<servidores;i++){
            c.Cartas(i);
            new Thread(c).start();
        }
    }
    
    @Override
    public void run() {
        try {
            reloj = new Socket(ips[0],Port);
            Calendar cal = Calendar.getInstance();
            h = cal.get(Calendar.HOUR_OF_DAY);
            m = cal.get(Calendar.MINUTE);
            s = cal.get(Calendar.SECOND);
            horas.setText(String.valueOf(h));
            minutos.setText(String.valueOf(m));
            segundos.setText(String.valueOf(s));
            
            while(!bandera){
                mServidor = new BufferedReader(new InputStreamReader(reloj.getInputStream()),1); 
                total=(int)(floatValue*1000);
                if(estaEditando!=0)
                    continue;
                        
                s=Integer.parseInt(segundos.getText());
                m=Integer.parseInt(minutos.getText());
                h=Integer.parseInt(horas.getText());
                
                s+=1;
                if(s==60){
                    m+=1;
                    s=0;
                } 
                if(m==60){
                    h+=1;
                    m=0;
                } 
                if(h==24){
                    h=0;
                } 
                horas.setText(String.valueOf(h));
                minutos.setText(String.valueOf(m));
                segundos.setText(String.valueOf(s));
                
                try {
                    sleep(total);
                } catch (InterruptedException ex) {
                    ex.getMessage();
                }
                if(mServidor.ready()==true){
                    if("Manda tu hora".equals(mensaje=mServidor.readLine())){
                        envio = new PrintWriter(reloj.getOutputStream());
                        cadenaEnviada = String.valueOf(h);
                        cadenaEnviada = cadenaEnviada + ":" + String.valueOf(m);
                        cadenaEnviada = cadenaEnviada + ":" + String.valueOf(s);
                        cadenaEnviada = cadenaEnviada + "," + String.valueOf(floatValue);
                        envio.println(cadenaEnviada);
                        envio.flush();
                    }
                    else{  
                        try {
                            cadenaRecibida=mensaje;
                            System.out.println(cadenaRecibida);
                            String[]cad=cadenaRecibida.split(":");
                            horas.setText(cad[0]);
                            minutos.setText(cad[1]);
                            segundos.setText(cad[2]);
                            sleep(1000);
                        } catch (InterruptedException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
                
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }finally{
            try {
                mServidor.close();
                envio.close();
                reloj.close();
            } catch (IOException ex) {
                System.out.println("El error es: "+ex.getMessage());
            }
        }
    }
    
    
            
    public void conectarServ(String []ips){
        try {
            for (String ip : ips) {
                conexiones.add(cliente = new Socket(ip, 9092));     
                //System.out.println("Conexion realizada");
            }
        } catch (IOException ex) {
            System.out.println("Error :S "+ex.getMessage());
        }  
           
    }
    public void mensajeServ(ArrayList servidores) throws InterruptedException{
        String mensaje="";
        boolean coord=false;
        int respaldo=-1;
        for(int i=0;i<conexiones.size();i++) {
            
            try {
                int contador=0;
                //iterator.next();
                
                msg=new PrintWriter(conexiones.get(i).getOutputStream());
                msg.println("Sigues vivo?");
                msg.flush();
                
                mCoordinador=new BufferedReader(new InputStreamReader(conexiones.get(i).getInputStream()));
                //mCoordinador.readLine();
                while(contador<3){
                    if((mensaje=mCoordinador.readLine())!=null )break;
                        contador++;
                        Thread.sleep(1000);
                }
                if(contador<3){
                    if("Sigo vivo".equals(mensaje)){
                        //System.out.println("Ya hay coordinador");
                        coord=true;
                    }else{
                        if(respaldo==-1){
                            respaldo=1;
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println(":S "+ex.getMessage());
            }
        }
        if(coord==false){
            try {
                msg=new PrintWriter(conexiones.get(respaldo).getOutputStream());
                msg.println("Eres el coordinador");
                msg.flush();
                Thread.sleep(5000);
            } catch (IOException ex) {
                System.out.println("Muerto");
            }
           
        }
    }
    
    public void algCoordinacion(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    conectarServ(ips);
                    while(true){
                        mensajeServ(conexiones);
                    } 
                }catch(Exception Ex){
                    System.out.println("Error :o "+Ex.getMessage());
                }
            }
        }).start();
    }

    public void Cartas(int i){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    juego=new Socket(ips[i],9091);

                    leerCarta=new BufferedReader(new InputStreamReader(juego.getInputStream()));
                    while(true) {
                        if(leerCarta.ready()==true){
                            if("Habilita boton".equals(leer=leerCarta.readLine())){
                                peticion.setEnabled(true);
                            }else if("Habilita boton".equals(leer) && pokemon==true){
                                lista.removeAllElements();
                            }else if("Ten el token".equals(leer)){
                                if(peticion.isEnabled() == false) {
                                    mcarta=new PrintWriter(juego.getOutputStream());
                                    mcarta.println(carta);
                                    mcarta.flush();
                                    try {
                                        Thread.sleep(1000);
                                    } catch(Exception e) {}
                                    cartaRecibida=leerCarta.readLine();
                                    if((cartaRecibida.charAt(0) >= '0' && cartaRecibida.charAt(0) <= '9') == false) continue;
                                    lista.addElement(cartaRecibida);
                                    contador+=1;
                                    if(contador==5)
                                        pokemon=true;
                                    String[]caracteristicas=cartaRecibida.split(" ");
                                    //System.out.println(String.join(",",caracteristicas));
                                    num=Integer.parseInt(caracteristicas[0]);
                                    nombre=caracteristicas[1];
                                    tipo1=caracteristicas[2];
                                    tipo2=caracteristicas[3];
                                    hp=Integer.parseInt(caracteristicas[4]);
                                    ataque=Integer.parseInt(caracteristicas[5]);
                                    defensa=Integer.parseInt(caracteristicas[6]);
                                    guardar(num,nombre,tipo1,tipo2,hp,ataque,defensa);

                                    token=new PrintWriter(juego.getOutputStream());
                                    token.println(tok);
                                    token.flush();
                                } else if((leer.charAt(0) >= '0' && leer.charAt(0) <= '9') == true){
                                    lista.addElement(leer);
                                    contador+=1;
                                    if(contador==5)
                                        pokemon=true;
                                    String[]caracteristicas=leer.split(" ");
                                    //System.out.println(String.join(",",caracteristicas));
                                    num=Integer.parseInt(caracteristicas[0]);
                                    nombre=caracteristicas[1];
                                    tipo1=caracteristicas[2];
                                    tipo2=caracteristicas[3];
                                    hp=Integer.parseInt(caracteristicas[4]);
                                    ataque=Integer.parseInt(caracteristicas[5]);
                                    defensa=Integer.parseInt(caracteristicas[6]);
                                    guardar(num,nombre,tipo1,tipo2,hp,ataque,defensa);

                                    token=new PrintWriter(juego.getOutputStream());
                                    token.println(tok);
                                    token.flush();
                                }
                                else {
                                    token=new PrintWriter(juego.getOutputStream());
                                    token.println(tok);
                                    token.flush();
                                }
                            } else if((leer.charAt(0) >= '0' && leer.charAt(0) <= '9') == true){
                                lista.addElement(leer);
                                contador+=1;
                                if(contador==5)
                                    pokemon=true;
                                String[]caracteristicas=leer.split(" ");
                                //System.out.println(String.join(",",caracteristicas));
                                num=Integer.parseInt(caracteristicas[0]);
                                nombre=caracteristicas[1];
                                tipo1=caracteristicas[2];
                                tipo2=caracteristicas[3];
                                hp=Integer.parseInt(caracteristicas[4]);
                                ataque=Integer.parseInt(caracteristicas[5]);
                                defensa=Integer.parseInt(caracteristicas[6]);
                                guardar(num,nombre,tipo1,tipo2,hp,ataque,defensa);

                                token=new PrintWriter(juego.getOutputStream());
                                token.println(tok);
                                token.flush();
                            }
                                System.out.println("Mensaje nuevo " + leer);
                        }

                    }
                }catch(IOException e){
                    System.out.println("Error2: "+e.getMessage());
                }
            }
        }).start();
        
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
	System.out.println("CLICK");
        if(e.getSource()==hora){
            //int val=e.getClickCount();
            if(horas.isEditable()==false){
                horas.setFocusable(true);
                horas.setEditable(true);
                estaEditando^=4;

            }else{
                horas.setFocusable(false);
                horas.setEditable(false);
                estaEditando^=4;
            }
            //System.out.println("Esta editando"+estaEditando);
        }else if(e.getSource()==minuto){
            //int val=e.getClickCount();
            if(minutos.isEditable()==false){
                minutos.setFocusable(true);
                minutos.setEditable(true);
                estaEditando^=2;
            }else{
                minutos.setFocusable(false);
                minutos.setEditable(false);
                estaEditando^=2;
            }
        }else if(e.getSource()==segundo){
            //int val=e.getClickCount();
            if(segundos.isEditable()==false){
                segundos.setFocusable(true);
                segundos.setEditable(true);
                estaEditando^=1;
            }else{
                segundos.setFocusable(false);
                segundos.setEditable(false);
                estaEditando^=1;
            }
        }else if(e.getSource()==peticion){
            System.out.println("CLICK");
            peticion.setEnabled(false);
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("");
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED){
            Object o = e.getItem();
            floatValue = Float.valueOf(o.toString());
            //System.out.println(floatValue);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        lista.addElement(cartaRecibida);
    }
}

    

