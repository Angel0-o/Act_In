package bs;

public class Struct {

	private int fila;
	private int columna;
	private Boolean rojo;
	
	public Struct(int fila, int columna, Boolean rojo) {
		super();
		this.fila = fila;
		this.columna = columna;
		this.rojo = rojo;
	}
	
	public Struct() {
		// TODO Auto-generated constructor stub
	}
	
	
	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public int getColumna() {
		return columna;
	}
	public void setColumna(int columna) {
		this.columna = columna;
	}
	public Boolean getRojo() {
		return rojo;
	}
	public void setRojo(Boolean rojo) {
		this.rojo = rojo;
	}
	
	
	
}
