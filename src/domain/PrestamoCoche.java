package domain;

import java.time.LocalDate;

public class PrestamoCoche extends Prestamo {
    private String modeloCoche;
    private int anioCoche;
    private boolean seguroIncluido;

    public PrestamoCoche() {
        super();
    }

    public PrestamoCoche(LocalDate fechaContratacion, boolean isActive, Cliente titular, int numPrestamo, double monto, 
                         double tasaInteres, int plazoEnMeses, String modeloCoche, int anioCoche, boolean seguroIncluido) {
        super(fechaContratacion, isActive, titular, numPrestamo, monto, tasaInteres, plazoEnMeses);
        this.modeloCoche = modeloCoche;
        this.anioCoche = anioCoche;
        this.seguroIncluido = seguroIncluido;
    }

	public String getModeloCoche() {
		return modeloCoche;
	}

	public void setModeloCoche(String modeloCoche) {
		this.modeloCoche = modeloCoche;
	}

	public int getAniooCoche() {
		return anioCoche;
	}

	public void setAnioCoche(int añoCoche) {
		this.anioCoche = añoCoche;
	}

	public boolean isSeguroIncluido() {
		return seguroIncluido;
	}

	public void setSeguroIncluido(boolean seguroIncluido) {
		this.seguroIncluido = seguroIncluido;
	}

	@Override
	public String toString() {
		return "PrestamoCoche [modeloCoche=" + modeloCoche + ", añoCoche=" + anioCoche + ", seguroIncluido="
				+ seguroIncluido + "]";
	}

    
}
