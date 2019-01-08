package modelo;

public class Cliente {

	String pedido;
	String id;
	String nombre;
	String direccion;
	String pais;
	//String condicionPago;

	public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cliente(String pedido, String id, String nombre, String direccion, String pais/*, String condicionPago*/) {
		super();
		this.pedido = pedido;
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.pais = pais;
		//this.condicionPago = condicionPago;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	/*
	public String getCondicionPago() {
		return condicionPago;
	}

	public void setCondicionPago(String condicionPago) {
		this.condicionPago = condicionPago;
	}
	*/

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}
	
	

}
