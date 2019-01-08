package modelo;

import java.math.BigDecimal;

public class PedidoLinea {

	String pedido;
	Integer pedido_linea;
	String articulo;
	BigDecimal precio_unitario;
	BigDecimal cantidadL;
	String descripcion;
	String notas;
	Boolean pedidoLineaValido = true;
	
	
	public PedidoLinea() {
		super();
	}


	public PedidoLinea(String pedido, Integer pedido_linea, String articulo,
			BigDecimal precio_unitario, BigDecimal cantidadL,
			String descripcion, String notas) {
		super();
		this.pedido = pedido;
		this.pedido_linea = pedido_linea;
		this.articulo = articulo;
		this.precio_unitario = precio_unitario;
		this.cantidadL = cantidadL;
		this.descripcion = descripcion;
		this.notas = notas;
	}


	public String getPedido() {
		return pedido;
	}


	public void setPedido(String pedido) {
		this.pedido = pedido;
	}


	public Integer getPedido_linea() {
		return pedido_linea;
	}


	public void setPedido_linea(Integer pedido_linea) {
		this.pedido_linea = pedido_linea;
	}


	public String getArticulo() {
		return articulo;
	}


	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}


	public BigDecimal getPrecio_unitario() {
		return precio_unitario;
	}


	public void setPrecio_unitario(BigDecimal precio_unitario) {
		this.precio_unitario = precio_unitario;
	}

	public BigDecimal getCantidadL() {
		return cantidadL;
	}


	public void setCantidadL(BigDecimal cantidadL) {
		this.cantidadL = cantidadL;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Boolean getPedidoLineaValido() {
		return pedidoLineaValido;
	}


	public void setPedidoLineaValido(Boolean pedidoLineaValido) {
		this.pedidoLineaValido = pedidoLineaValido;
	}
	
	
	public String getNotas() {
		return notas;
	}


	public void setNotas(String notas) {
		this.notas = notas;
	}
	
	
	
}
