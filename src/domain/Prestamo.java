package domain;

import java.time.LocalDate;

public abstract class Prestamo extends Servicio {
    protected int numPrestamo;
    protected double monto;
    protected double tasaInteres;
    protected int plazoEnMeses;

    public Prestamo() {
        super();
    }

    public Prestamo(LocalDate fechaContratacion, boolean isActive, Cliente titular, int numPrestamo, double monto, 
                    double tasaInteres, int plazoEnMeses) {
        super(fechaContratacion, isActive, titular);
        this.numPrestamo = numPrestamo;
        this.monto = monto;
        this.tasaInteres = tasaInteres;
        this.plazoEnMeses = plazoEnMeses;
    }

	public int getNumPrestamo() {
		return numPrestamo;
	}

	public void setNumPrestamo(int numPrestamo) {
		this.numPrestamo = numPrestamo;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public double getTasaInteres() {
		return tasaInteres;
	}

	public void setTasaInteres(double tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	public int getPlazoEnMeses() {
		return plazoEnMeses;
	}

	public void setPlazoEnMeses(int plazoEnMeses) {
		this.plazoEnMeses = plazoEnMeses;
	}

	@Override
	public String toString() {
		return "Prestamo [numPrestamo=" + numPrestamo + ", monto=" + monto + ", tasaInteres=" + tasaInteres
				+ ", plazoEnMeses=" + plazoEnMeses + "]";
	}

    
}
