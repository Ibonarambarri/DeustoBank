package domain;
import java.time.LocalDate;
import java.util.*;

public class Cuenta extends Servicio{
	private String iban;
	private double tasaMantenimiento;
	private ArrayList<Transaccion>listaTransacciones;
	public Cuenta() {
		super();
		
	}
	public Cuenta(LocalDate fechaContratacion, boolean isActive, Cliente titular) {
		super(fechaContratacion, isActive, titular);

	}
	public Cuenta(String iban, double tasaMantenimiento, ArrayList<Transaccion> listaTransacciones) {
		super();
		this.iban = iban;
		this.tasaMantenimiento = tasaMantenimiento;
		this.listaTransacciones = listaTransacciones;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	// Método para calcular el saldo basado en las transacciones
    public double getSaldo() {
        double saldo = 0;
        if (listaTransacciones != null) {
            for (Transaccion transaccion : listaTransacciones) {
                saldo += transaccion.getCantidad(); // Asumiendo que getCantidad retorna un int o double
            }
        }
        return saldo;
    }
	public double getTasaMantenimiento() {
		return tasaMantenimiento;
	}
	public void setTasaMantenimiento(double tasaMantenimiento) {
		this.tasaMantenimiento = tasaMantenimiento;
	}
	public ArrayList<Transaccion> getListaTransacciones() {
		return listaTransacciones;
	}
	public void setListaTransacciones(ArrayList<Transaccion> listaTransacciones) {
		this.listaTransacciones = listaTransacciones;
	}
	@Override
    public String toString() {
        // Devuelve una representación amigable de la cuenta
        return getClass().getSimpleName()+" - " + iban + " - Saldo: " + getSaldo();
    }


}
