package domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class CuentaAhorro extends Cuenta{
	private double interesAnual;
	private int limite;
	
	public CuentaAhorro() {
		super();
	}
	public CuentaAhorro(LocalDate fechaContratacion, boolean isActive, Cliente titular) {
		super(fechaContratacion, isActive, titular);
		
	}
	public CuentaAhorro(String iban, double tasaMantenimiento, ArrayList<Transaccion> listaTransacciones,double interesAnual, int limite) {
		// Llamada al constructor de la clase base (Cuenta)
		super(iban, tasaMantenimiento, listaTransacciones);
		this.interesAnual = interesAnual;
		this.limite = limite;
	}
	public double getInteresAnual() {
		return interesAnual;
	}
	public void setInteresAnual(float interesAnual) {
		this.interesAnual = interesAnual;
	}
	public int getLimite() {
		return limite;
	}
	public void setLimite(int limite) {
		this.limite = limite;
	}
	

}
