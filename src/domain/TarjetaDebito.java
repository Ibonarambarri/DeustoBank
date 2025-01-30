package domain;

import java.time.LocalDate;
import java.util.Date;

public class TarjetaDebito extends Tarjeta{
	private int limiteMensual;
	
	public TarjetaDebito() {
		super();

	}
	public TarjetaDebito(LocalDate fechaContratacion, boolean isActive, Cliente titular) {
		super(fechaContratacion, isActive, titular);
		
	}
	public TarjetaDebito(String numero, int cvv, Date fechaCaducidad,Cuenta cuentaAsignada,int limiteMensual) {
		// Llamada al constructor de la clase base (Cuenta)
		super(numero, cvv, fechaCaducidad, cuentaAsignada);
		this.limiteMensual = limiteMensual;
	}
	public int getLimiteMensual() {
		return limiteMensual;
	}
	public void setLimiteMensual(int limiteMensual) {
		this.limiteMensual = limiteMensual;
	}
	
}
