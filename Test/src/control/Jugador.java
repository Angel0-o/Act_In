package control;

public class Jugador
{
	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getDificultad() {
		return dificultad;
	}

	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}
	
	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	String IP; 
	int puerto;
	String nombre;
	int edad;
	int dificultad;
	
	public Jugador(String n, int e, int d)
	{
		nombre = n;
		edad = e;
		dificultad = d;
	}
	
	public Jugador(String i, int p)
	{
		IP = i;
		puerto = p;
	}
	
	public Jugador()
	{
		IP = null;
		puerto = 0;
		nombre = null;
		edad = 0;
		dificultad = 0;
	}
}
