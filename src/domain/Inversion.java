package domain;

import java.time.LocalDate;
import java.util.Date;

public class Inversion extends Servicio{
	private String numeroCuenta;
	private TipoFondo tipo; 
	private double saldo;
	private float rendimientoAnual;
	private float comision;
	private Date periodoInversion;
	public Inversion() {
		super();
		
	}
	public Inversion(LocalDate fechaContratacion, boolean isActive, Cliente titular) {
		super(fechaContratacion, isActive, titular);
		
	}
	public Inversion(String numeroCuenta, TipoFondo tipo, double saldo, float rendimientoAnual, float comision,
			Date periodoInversion) {
		super();
		this.numeroCuenta = numeroCuenta;
		this.tipo = tipo;
		this.saldo = saldo;
		this.rendimientoAnual = rendimientoAnual;
		this.comision = comision;
		this.periodoInversion = periodoInversion;
	}
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public TipoFondo getTipo() {
		return tipo;
	}
	public void setTipo(TipoFondo tipo) {
		this.tipo = tipo;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public float getRendimientoAnual() {
		return rendimientoAnual;
	}
	public void setRendimientoAnual(float rendimientoAnual) {
		this.rendimientoAnual = rendimientoAnual;
	}
	public float getComision() {
		return comision;
	}
	public void setComision(float comision) {
		this.comision = comision;
	}
	public Date getPeriodoInversion() {
		return periodoInversion;
	}
	public void setPeriodoInversion(Date periodoInversion) {
		this.periodoInversion = periodoInversion;
	}

}
