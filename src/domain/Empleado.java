package domain;

import java.time.LocalDate;
import java.util.*;

public class Empleado {
	private String nombre;
	private String id;
	private String apellido1;
	private String apellido2;
	private String dni;
	private String direccion;
	private String cp;
	private String ciudad;
	private String provincia;
	private String tlfn;
	private String email;
	private Sexo sexo;
	private LocalDate fechaNacimiento;
	private ArrayList<Cliente>listaClientes = new ArrayList<Cliente>();
	
	public Empleado() {
		super();
		
	}
	public Empleado(String nombre, String id, String apellido1, String apellido2, String dni, String direccion,
			String cp, String ciudad, String provincia, String tlfn, String email, Sexo sexo, LocalDate fechaNacimiento) {
		super();
		this.nombre = nombre;
		this.id = id;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.dni = dni;
		this.direccion = direccion;
		this.cp = cp;
		this.ciudad = ciudad;
		this.provincia = provincia;
		this.tlfn = tlfn;
		this.email = email;
		this.sexo = sexo;
		this.fechaNacimiento = fechaNacimiento;
		this.listaClientes = new ArrayList<Cliente>();
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getTlfn() {
		return tlfn;
	}
	public void setTlfn(String tlfn) {
		this.tlfn = tlfn;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public ArrayList<Cliente> getListaClientes() {
		return listaClientes;
	}
	public void setListaClientes(ArrayList<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}
	@Override
	public String toString() {
		return nombre;
	}
	
	
	

}
