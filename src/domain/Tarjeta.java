package domain;

import java.time.LocalDate;
import java.util.*;

public abstract class Tarjeta extends Servicio{
	private String numero;
	private int cvv;
	private Date fechaCaducidad;
	private Cuenta cuentaAsignada;

	public Tarjeta() {
		super();
		
	}
	public Tarjeta(LocalDate fechaContratacion, boolean isActive, Cliente titular) {
		super(fechaContratacion, isActive, titular);

	}
	public Tarjeta(String numero, int cvv, Date fechaCaducidad,Cuenta cuentaAsignada) {
		super();
		this.numero = numero;
		this.cvv = cvv;
		this.fechaCaducidad = fechaCaducidad;
		this.cuentaAsignada = cuentaAsignada;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	public Cuenta getCuentaAsignada() {
		return cuentaAsignada;
	}
	public void setCuentaAsignada(Cuenta cuentaAsignada) {
		this.cuentaAsignada = cuentaAsignada;
	}
	@Override
	public String toString() {
		return "Tarjeta [numero=" + numero + ", cvv=" + cvv + ", fechaCaducidad=" + fechaCaducidad + ", cuentaAsignada="
				+ cuentaAsignada + "]";
	}
}
