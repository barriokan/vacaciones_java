package model;

import java.util.Date;

public class Movimiento {

	private int cuenta;
	private Date fecha;
	private double cantidad;
	private String operacion;
	
	public Movimiento(int cuenta, Date fecha, double cantidad, String operacion) {
		super();
		this.cuenta = cuenta;
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.operacion = operacion;
	}

	public int getCuenta() {
		return cuenta;
	}

	public void setCuenta(int cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	
}
