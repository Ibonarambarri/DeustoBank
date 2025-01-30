package domain;

import java.time.LocalDate;

public class PrestamoHipoteca extends Prestamo {
    private String direccionPropiedad;
    private double valorPropiedad;

    public PrestamoHipoteca() {
        super();
    }

    public PrestamoHipoteca(LocalDate fechaContratacion, boolean isActive, Cliente titular, int numPrestamo, double monto, 
                            double tasaInteres, int plazoEnMeses, String direccionPropiedad, double valorPropiedad) {
        super(fechaContratacion, isActive, titular, numPrestamo, monto, tasaInteres, plazoEnMeses);
        this.direccionPropiedad = direccionPropiedad;
        this.valorPropiedad = valorPropiedad;
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

	@Override
	public String toString() {
		return "PrestamoHipoteca [direccionPropiedad=" + direccionPropiedad + ", valorPropiedad=" + valorPropiedad
				+ "]";
	}

    
}
