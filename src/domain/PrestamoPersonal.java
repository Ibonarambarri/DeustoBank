package domain;

import java.time.LocalDate;

public class PrestamoPersonal extends Prestamo {
    private String motivo;
    private boolean requiereAval;

    public PrestamoPersonal() {
        super();
    }

    public PrestamoPersonal(LocalDate fechaContratacion, boolean isActive, Cliente titular, int numPrestamo, double monto, 
                            double tasaInteres, int plazoEnMeses, String motivo, boolean requiereAval) {
        super(fechaContratacion, isActive, titular, numPrestamo, monto, tasaInteres, plazoEnMeses);
        this.motivo = motivo;
        this.requiereAval = requiereAval;
    }

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public boolean isRequiereAval() {
		return requiereAval;
	}

	public void setRequiereAval(boolean requiereAval) {
		this.requiereAval = requiereAval;
	}

	@Override
	public String toString() {
		return "PrestamoPersonal [motivo=" + motivo + ", requiereAval=" + requiereAval + "]";
	}

    
}
