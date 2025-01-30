package domain;

import java.time.LocalDate;

public abstract class Seguro extends Servicio{
	private String numPoliza;
	private LocalDate fechaVencimiento;
	private float coberturaTotal;
	private float mensualidad;

	public Seguro() {
		super();
		
	}
	public Seguro(LocalDate fechaContratacion, boolean isActive, Cliente titular) {
		super(fechaContratacion, isActive, titular);

	}
	public Seguro(String numPoliza, LocalDate fechaVencimiento, float coberturaTotal, float mensualidad) {
		super();
		this.numPoliza = numPoliza;
		this.fechaVencimiento = fechaVencimiento;
		this.coberturaTotal = coberturaTotal;
		this.mensualidad = mensualidad;
	}
	public String getNumPoliza() {
		return numPoliza;
	}
	public void setNumPoliza(String numPoliza) {
		this.numPoliza = numPoliza;
	}
	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public float getCoberturaTotal() {
		return coberturaTotal;
	}
	public void setCoberturaTotal(float coberturaTotal) {
		this.coberturaTotal = coberturaTotal;
	}
	public float getMensualidad() {
		return mensualidad;
	}
	public void setMensualidad(float mensualidad) {
		this.mensualidad = mensualidad;
	}

}
