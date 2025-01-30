package domain;

import java.time.LocalDate;

public class SeguroVida extends Seguro{
	private String beneficiarios;


	public SeguroVida() {
		super();

	}

	public SeguroVida(LocalDate fechaContratacion, boolean isActive, Cliente titular) {
		super(fechaContratacion, isActive, titular);
		
	}

	public SeguroVida(String numPoliza, LocalDate fechaVencimiento, float coberturaTotal, float mensualidad) {
		super(numPoliza, fechaVencimiento, coberturaTotal, mensualidad);

	}

	public SeguroVida(String beneficiarios) {
		super();
		this.beneficiarios = beneficiarios;
	}

	public String getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(String beneficiarios) {
		this.beneficiarios = beneficiarios;
	}
	

}
