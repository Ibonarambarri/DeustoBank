package domain;

import java.time.LocalDate;

public class SeguroCoche extends Seguro{
	private String descripcion;
	private String matricula;
	private int anioFabricacion;

	public SeguroCoche() {
		super();

	}
	public SeguroCoche(LocalDate fechaContratacion, boolean isActive, Cliente titular) {
		super(fechaContratacion, isActive, titular);
	}
	
	public SeguroCoche(String numPoliza, LocalDate fechaVencimiento, float coberturaTotal, float mensualidad) {
		super(numPoliza, fechaVencimiento, coberturaTotal, mensualidad);
	}
	
	public SeguroCoche(String descripcion, String matricula, int anioFabricacion) {
		super();
		this.descripcion = descripcion;
		this.matricula = matricula;
		this.anioFabricacion = anioFabricacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public int getAnioFabricacion() {
		return anioFabricacion;
	}
	public void setAnioFabricacion(int anioFabricacion) {
		this.anioFabricacion = anioFabricacion;
	}
	

}
