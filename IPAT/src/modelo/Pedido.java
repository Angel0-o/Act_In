package modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

	Integer countH;
	String pedido;
	String condicionPago;//nuevo
	String rubro1;
	String rubro2;
	String rubro3;
	String rubro4;
	String rubro5;
	BigDecimal totalMercaderia;
	BigDecimal totalUnidades;
	BigDecimal totalIVAs;
	String moneda;
	List<PedidoLinea> listPedidosLinea = new ArrayList<PedidoLinea>();
	Boolean pedidoValido = true;
	String cobrador;
	String vendedor;
	String observaciones;
	String fecha;
	String notas;

	public Pedido() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pedido(String pedido, String condicionPago, String rubro1, String rubro2, String rubro3,//nuevo condicionPago
			String rubro4, String rubro5, String notas, BigDecimal totalMercaderia,
			BigDecimal totalUnidades, BigDecimal totalIVAs) {
		super();
		this.pedido = pedido;
		this.condicionPago = condicionPago;
		this.rubro1 = rubro1;
		this.rubro2 = rubro2;
		this.rubro3 = rubro3;
		this.rubro4 = rubro4;
		this.rubro5 = rubro5;
		this.notas = notas;
		this.totalMercaderia = totalMercaderia;
		this.totalUnidades = totalUnidades;
		this.totalIVAs = totalIVAs;
	}

	public Pedido(Integer countH, String pedido, String condicionPago,String rubro1, String rubro2, String rubro3,
			String rubro4, String rubro5, String moneda, String cobrador, 
			String vendedor, String observaciones, String fecha) {
		super();
		this.countH = countH;
		this.pedido = pedido;
		this.condicionPago = condicionPago;
		this.rubro1 = rubro1;
		this.rubro2 = rubro2;
		this.rubro3 = rubro3;
		this.rubro4 = rubro4;
		this.rubro5 = rubro5;
		this.moneda = moneda;
		this.cobrador = cobrador;
		this.vendedor = vendedor;
		this.observaciones = observaciones;
		this.fecha = fecha;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}
	//nuevo
	public String getCondPago() {
		return condicionPago;
	}

	public void setCondPago(String condicionPago) {
		this.condicionPago = condicionPago;
	}

	public String getRubro1() {
		return rubro1;
	}

	public void setRubro1(String rubro1) {
		this.rubro1 = rubro1;
	}

	public String getRubro2() {
		return rubro2;
	}

	public void setRubro2(String rubro2) {
		this.rubro2 = rubro2;
	}

	public String getRubro3() {
		return rubro3;
	}

	public void setRubro3(String rubro3) {
		this.rubro3 = rubro3;
	}

	public String getRubro4() {
		return rubro4;
	}

	public void setRubro4(String rubro4) {
		this.rubro4 = rubro4;
	}

	public String getRubro5() {
		return rubro5;
	}

	public void setRubro5(String rubro5) {
		this.rubro5 = rubro5;
	}

	public BigDecimal getTotalMercaderia() {
		return totalMercaderia;
	}

	public void setTotalMercaderia(BigDecimal totalMercaderia) {
		this.totalMercaderia = totalMercaderia;
	}

	public BigDecimal getTotalUnidades() {
		return totalUnidades;
	}

	public void setTotalUnidades(BigDecimal totalUnidades) {
		this.totalUnidades = totalUnidades;
	}

	public BigDecimal getTotalIVAs() {
		return totalIVAs;
	}

	public void setTotalIVAs(BigDecimal totalIVAs) {
		this.totalIVAs = totalIVAs;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Integer getCountH() {
		return countH;
	}

	public void setCountH(Integer countH) {
		this.countH = countH;
	}

	public List<PedidoLinea> getListPedidosLinea() {
		return listPedidosLinea;
	}

	public void setListPedidosLinea(List<PedidoLinea> listPedidosLinea) {
		this.listPedidosLinea = listPedidosLinea;
	}

	public Boolean getPedidoValido() {
		return pedidoValido;
	}

	public void setPedidoValido(Boolean pedidoValido) {
		this.pedidoValido = pedidoValido;
	}

	public String getCobrador() {
		return cobrador;
	}

	public void setCobrador(String cobrador) {
		this.cobrador = cobrador;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;		
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;		
	}

}
