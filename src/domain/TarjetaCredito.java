package domain;

import java.time.LocalDate;
import java.util.*;

public class TarjetaCredito extends Tarjeta{
	private int limiteCreditor;
	
	public TarjetaCredito() {
		super();
	}
	public TarjetaCredito(LocalDate fechaContratacion, boolean isActive, Cliente titular) {
		super(fechaContratacion, isActive, titular);
		
	}
	public TarjetaCredito(String numero, int cvv, Date fechaCaducidad,Cuenta cuentaAsignada,int limiteCreditor) {
		// Llamada al constructor de la clase base (Cuenta)
		super(numero, cvv, fechaCaducidad, cuentaAsignada);
		this.limiteCreditor = limiteCreditor;
	}
	public int getLimiteCreditor() {
		return limiteCreditor;
	}
	public void setLimiteCreditor(int limiteCreditor) {
		this.limiteCreditor = limiteCreditor;
	}
	
}
