package practica_7;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author angel
 */
public class Jugador implements Serializable 
{

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
    public String getCarta() {
        return carta;
    }

    public void setCarta(String carta) {
        this.carta = carta;
    }
    
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
    private String ip;
    private String nombre;
    private String hora;
    private String carta;
    private String query;
    private String ruta;
    
    public Jugador(String i, String n, String h)
    {
        ip = i;
        nombre = n;
        hora = h;
    }
    
    public Jugador(String i, String n, String h, String c)
    {
        ip = i;
        nombre = n;
        hora = h;
        carta = c;
    }
    
    public Jugador(String i, String n, String h, String c, String q)
    {
        ip = i;
        nombre = n;
        hora = h;
        carta = c;
        query = q;
    }
    
    public Jugador(String i, String n, String h, String c, String q, String r)
    {
        ip = i;
        nombre = n;
        hora = h;
        carta = c;
        query = q;
        ruta = r;
    }
}
