package domain;

import java.time.LocalDate;

public abstract class Servicio {
	private LocalDate fechaContratacion;
	private boolean isActive;
	private Cliente titular;
	
	public Servicio() {
		super();
	}
	
	public Servicio(LocalDate fechaContratacion, boolean isActive, Cliente titular) {
		super();
		this.fechaContratacion = fechaContratacion;
		this.isActive = isActive;
		this.titular = titular;
	}

	public LocalDate getFechaContratacion() {
		return fechaContratacion;
	}
	public void setFechaContratacion(LocalDate fechaContratacion) {
		this.fechaContratacion = fechaContratacion;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Cliente getTitular() {
		return titular;
	}
	public void setTitular(Cliente titular) {
		this.titular = titular;
	}
	@Override
	public String toString() {
		return "Servicio [fechaContratacion=" + fechaContratacion + ", isActive=" + isActive + ", titular=" + titular
				+ "]";
	}
	
	

}
