package domain;

import java.time.LocalDate;


public class SeguroCasa extends Seguro{
	private String direccionPropiedad;
	private double valorPropiedad;
	private LocalDate fechaConstruccion;

	public SeguroCasa() {
		super();

	}
	public SeguroCasa(LocalDate fechaContratacion, boolean isActive, Cliente titular) {
		super(fechaContratacion, isActive, titular);
		
	}
	public SeguroCasa(String numPoliza, LocalDate fechaVencimiento, float coberturaTotal, float mensualidad) {
		super(numPoliza, fechaVencimiento, coberturaTotal, mensualidad);

	}
	public SeguroCasa(String direccionPropiedad, double valorPropiedad, LocalDate fechaConstruccion) {
		super();
		this.direccionPropiedad = direccionPropiedad;
		this.valorPropiedad = valorPropiedad;
		this.fechaConstruccion = fechaConstruccion;
		
	}
	public String getDireccionPropiedad() {
		return direccionPropiedad;
	}
	public void setDireccionPropiedad(String direccionPropiedad) {
		this.direccionPropiedad = direccionPropiedad;
	}
	public double getValorPropiedad() {
		return valorPropiedad;
	}
	public void setValorPropiedad(double valorPropiedad) {
		this.valorPropiedad = valorPropiedad;
	}
	public LocalDate getFechaConstruccion() {
		return fechaConstruccion;
	}
	public void setFechaConstruccion(LocalDate fechaConstruccion) {
		this.fechaConstruccion = fechaConstruccion;
	}

	
}
