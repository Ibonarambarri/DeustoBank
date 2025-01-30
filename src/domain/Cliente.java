package domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente implements Comparable<Cliente>{
	private String nombre;
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
	private String profesion;
	private Empleado empleadoAsignado;
	private double puntajeCrediticio;
	private String observaciones;
	private ArrayList<Servicio> listaServicios = new ArrayList<>();
	public Cliente() {
		super();
		
	}
	public Cliente(String nombre, String apellido1, String apellido2, String dni, String direccion, String cp,
			String ciudad, String provincia, String tlfn, String email, Sexo sexo, LocalDate fechaNacimiento,
			String profesion, Empleado empleadoAsignado, double puntajeCrediticio, String observaciones,
			ArrayList<Servicio> listaServicios) {
		super();
		this.nombre = nombre;
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
		this.profesion = profesion;
		this.empleadoAsignado = empleadoAsignado;
		this.puntajeCrediticio = puntajeCrediticio;
		this.observaciones = observaciones;
		this.listaServicios =  (listaServicios != null) ? listaServicios : new ArrayList<>();
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	public String getProfesion() {
		return profesion;
	}
	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}
	public Empleado getEmpleadoAsignado() {
		return empleadoAsignado;
	}
	public void setEmpleadoAsignado(Empleado empleadoAsignado) {
		this.empleadoAsignado = empleadoAsignado;
	}
	public double getPuntajeCrediticio() {
		return puntajeCrediticio;
	}
	public void setPuntajeCrediticio(double puntajeCrediticio) {
		this.puntajeCrediticio = puntajeCrediticio;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public ArrayList<Servicio> getListaServicios() {
		return listaServicios;
	}
	public void setListaServicios(ArrayList<Servicio> listaServicios) {
		this.listaServicios = listaServicios;
	}
	@Override
	public String toString() {
		return apellido1 + ", "+ nombre+ ": " + dni;
	}
	
	
	public int compareTo(Cliente otro) {
        int nombreComparison = this.apellido1.compareTo(otro.apellido1);
        if (nombreComparison != 0) {
            return nombreComparison;
        }
        return this.apellido1.compareTo(otro.apellido1);
    }
	//-------------------------------------------
	public void añadirServicio(Servicio servicio) {
	    if (listaServicios == null) {
	        listaServicios = new ArrayList<>();
	    }
	    listaServicios.add(servicio);
	}

}
