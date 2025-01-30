package main;

import java.io.FileReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Properties;

import java.util.*;
import domain.*;


public class GestorDB {
	
	// Se elimina el final de los atributos para poder actualizar los valores.
    protected static String DRIVER_NAME;
    protected static String DATABASE_FILE;
    protected static String CONNECTION_STRING;

    public GestorDB() {
        try {
            // Se crea el Properties y se actualizan los 3 parámetros
            Properties connectionProperties = new Properties();
            connectionProperties.load(new FileReader("resources/config/parametros.properties"));

            DRIVER_NAME = connectionProperties.getProperty("DRIVER_NAME");
            DATABASE_FILE = connectionProperties.getProperty("DATABASE_FILE");
            CONNECTION_STRING = connectionProperties.getProperty("CONNECTION_STRING");

            // Cargar el driver SQLite
            Class.forName(DRIVER_NAME);
        } catch (Exception ex) {
            System.err.format("\n* Error al cargar el driver de BBDD: %s", ex.getMessage());
            ex.printStackTrace();
        }
    }

	//------------------------------------INSERTAR DATOS-------------------------------

    


	/**
	 * Recupera la lista de clientes de la base de datos
	 */
	public static List<Cliente> getListaCLientes() {
		List<Cliente> clientes = new ArrayList<>();
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM CLIENTE";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			Cliente cliente;
			
			//Se recorre el ResultSet y se crean objetos Cliente
			while (rs.next()) {
				cliente = new Cliente();
				cliente.setNombre(rs.getString("NOMBRE"));
				cliente.setApellido1(rs.getString("APELLIDO1"));
				cliente.setApellido2(rs.getString("APELLIDO2"));
				cliente.setDni(rs.getString("DNI"));
				cliente.setCp(rs.getString("CP"));
				cliente.setCiudad(rs.getString("CIUDAD"));
				cliente.setProvincia(rs.getString("PROVINCIA"));
				cliente.setTlfn(rs.getString("TELEFONO"));
				cliente.setEmail(rs.getString("EMAIL"));
				cliente.setSexo(Sexo.valueOf(rs.getString("SEXO")));
				cliente.setFechaNacimiento(LocalDate.parse(rs.getString("FECHA_NACIMIENTO")));
				cliente.setProfesion(rs.getString("PROFESION"));

//				cliente.setEmpleadoAsignado(empleado);

				cliente.setPuntajeCrediticio(rs.getInt("PUNTAJE_CREDITICIO"));
				cliente.setObservaciones(rs.getString("OBSERVACIONES"));

				//Se inserta cada nuevo cliente en la lista de clientes
				clientes.add(cliente);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			System.out.format("\n\n- Se han recuperado %d clientes...", clientes.size());			
		} catch (Exception ex) {
			System.err.format("\n\n* Error al obtener datos de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}		
		
		return clientes;
	}

	public static List<Empleado> getListaEmpleados() {
		List<Empleado> empleados = new ArrayList<>();
		
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM EMPLEADO";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			Empleado empleado;
			
			//Se recorre el ResultSet y se crean objetos Cliente
			while (rs.next()) {
				empleado = new Empleado();
				empleado.setId(rs.getString("ID"));
				empleado.setNombre(rs.getString("NOMBRE"));
				empleado.setApellido1(rs.getString("APELLIDO1"));
				empleado.setApellido2(rs.getString("APELLIDO2"));
				empleado.setDni(rs.getString("DNI"));
				empleado.setDireccion(rs.getString("DIRECCION"));
				empleado.setCp(rs.getString("CP"));
				empleado.setCiudad(rs.getString("CIUDAD"));
				empleado.setProvincia(rs.getString("PROVINCIA"));
				empleado.setTlfn(rs.getString("TELEFONO"));
				empleado.setEmail(rs.getString("EMAIL"));
				empleado.setSexo(Sexo.valueOf(rs.getString("SEXO")));
				empleado.setFechaNacimiento(LocalDate.parse(rs.getString("FECHA_NACIMIENTO")));
				
				//Se inserta cada nuevo empleado en la lista de empleados
				empleados.add(empleado);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			System.out.format("\n\n- Se han recuperado %d empleados...", empleados.size());			
		} catch (Exception ex) {
			System.err.format("\n\n* Error al obtener datos de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}		
		
		return empleados;
	}

	public static HashMap<String, ArrayList<String>> getEmpleadoClienteMap() {
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		     Statement stmt = con.createStatement()) {
			String sql = "SELECT * FROM EMPLEADO_CLIENTE";
			
			//Se ejecuta la sentencia y se obtiene el ResultSet con los resutlados
			ResultSet rs = stmt.executeQuery(sql);			
			
			
			//Se recorre el ResultSet y se crean objetos Cliente
			while (rs.next()) {
				
				map.putIfAbsent(rs.getString("EMPLEADO_ID"), new ArrayList<String>());
				map.get(rs.getString("EMPLEADO_ID")).add(rs.getString("CLIENTE_ID"));
				 
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			System.out.format("\n\n- Se han recuperado %d relaciones...", map.size());			
		} catch (Exception ex) {
			System.err.format("\n\n* Error al obtener datos de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}		
		
		return map;
	}

	/**
	 * Inserta un nuevo empleado en la tabla Empleados de la base de datos
	 * @param recibe el empleado que se desea añadir
	 */
	public static void insertarEmpleado(Empleado empleado) {
		GestorDB BD = new GestorDB();		
		 if (existeEmpleado(empleado.getId())) {
		        System.out.println("El empleado con ID " + empleado.getId() + " ya existe.");
		        return;
		    }
	    String sql = "INSERT INTO EMPLEADO (ID, NOMBRE, APELLIDO1, APELLIDO2, DNI, DIRECCION, CP, CIUDAD, PROVINCIA, TELEFONO, EMAIL, SEXO, FECHA_NACIMIENTO) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pstmt = con.prepareStatement(sql)) {
	    	pstmt.setString(1, empleado.getId());
	        pstmt.setString(2, empleado.getNombre());
	        pstmt.setString(3, empleado.getApellido1());
	        pstmt.setString(4, empleado.getApellido2());
	        pstmt.setString(5, empleado.getDni());
	        pstmt.setString(6, empleado.getDireccion());
	        pstmt.setString(7, empleado.getCp());
	        pstmt.setString(8, empleado.getCiudad());
	        pstmt.setString(9, empleado.getProvincia());
	        pstmt.setString(10, empleado.getTlfn());
	        pstmt.setString(11, empleado.getEmail());
	        pstmt.setString(12, empleado.getSexo().toString());
	        pstmt.setString(13, empleado.getFechaNacimiento().toString());

	        pstmt.executeUpdate();
	        System.out.println("\n- Empleado insertado: " + empleado.getNombre());
	    } catch (Exception ex) {
	        System.err.format("\n* Error al insertar empleado: %s", ex.getMessage());
	        ex.printStackTrace();
	    }
	}
	/**
	 * Inserta un nuevo cliente en la tabla Clientes de la base de datos
	 * @param recibe el nuevo cliente que se quiere añadir
	 */
	public static void insertarCliente(Cliente cliente) {
		GestorDB BD = new GestorDB();
		if (existeCliente(cliente.getDni())) {
	        System.out.println("El cliente con DNI " + cliente.getDni() + " ya existe.");
	        return;
	    }
		
	    String sql = "INSERT INTO CLIENTE (NOMBRE, APELLIDO1, APELLIDO2, DNI, DIRECCION, CP, CIUDAD, PROVINCIA, TELEFONO, EMAIL, SEXO, FECHA_NACIMIENTO, PROFESION, PUNTAJE_CREDITICIO, OBSERVACIONES) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pstmt = con.prepareStatement(sql)) {

	        pstmt.setString(1, cliente.getNombre());
	        pstmt.setString(2, cliente.getApellido1());
	        pstmt.setString(3, cliente.getApellido2());
	        pstmt.setString(4, cliente.getDni());
	        pstmt.setString(5, cliente.getDireccion());
	        pstmt.setString(6, cliente.getCp());
	        pstmt.setString(7, cliente.getCiudad());
	        pstmt.setString(8, cliente.getProvincia());
	        pstmt.setString(9, cliente.getTlfn());
	        pstmt.setString(10, cliente.getEmail());
	        pstmt.setString(11, cliente.getSexo().toString());
	        pstmt.setString(12, cliente.getFechaNacimiento().toString());
	        pstmt.setString(13, cliente.getProfesion());
	        GestorDB.asignarClienteAEmpleado(cliente.getEmpleadoAsignado().getId(), cliente.getDni());
	        pstmt.setDouble(14, cliente.getPuntajeCrediticio());
	        pstmt.setString(15, cliente.getObservaciones());

	        pstmt.executeUpdate();
	        System.out.println("\n- Cliente insertado: " + cliente.getNombre());
	    } catch (Exception ex) {
	        System.err.format("\n* Error al insertar cliente: %s", ex.getMessage());
	        ex.printStackTrace();
	    }
	}

	/**
	 * Se crea la relacion entre cliente y empleado, ya que los empleados tienen una cartera concreta de clientes
	 * @param string: id del empleado
	 * @param string2: id del cliente
	 */
	public static void asignarClienteAEmpleado(String string, String string2) {
		GestorDB BD = new GestorDB();
		
	    String sql = "INSERT INTO EMPLEADO_CLIENTE (EMPLEADO_ID, CLIENTE_ID) VALUES (?, ?)";
	   
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pstmt = con.prepareStatement(sql)) {

	        pstmt.setString(1, string); // ID del empleado
	        pstmt.setString(2, string2); // ID del cliente

	        pstmt.executeUpdate();
	        System.out.println("\n- Relación empleado-cliente insertada: Empleado ID = " + string + ", Cliente ID = " + string2);
	    } catch (Exception ex) {
	        System.err.format("\n* Error al insertar relación empleado-cliente: %s", ex.getMessage());
	        ex.printStackTrace();
	    }
	}
	
	
	/**
	 * Borra la relacion entre cliente y empleado, ya que los empleados tienen una cartera concreta de clientes
	 * @param string: id del empleado
	 * @param string2: id del cliente
	 */
	
	public static void eliminarClienteDeEmpleado(String empleadoId, String clienteId) {
	    GestorDB BD = new GestorDB();
	    
	    // Consulta para eliminar la relación entre un empleado y un cliente
	    String sql = "DELETE FROM EMPLEADO_CLIENTE WHERE EMPLEADO_ID = ? AND CLIENTE_ID = ?";
	    
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pstmt = con.prepareStatement(sql)) {
	         
	        pstmt.setString(1, empleadoId); // ID del empleado
	        pstmt.setString(2, clienteId); // ID del cliente

	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("\n- Relación empleado-cliente eliminada: Empleado ID = " + empleadoId + ", Cliente ID = " + clienteId);
	        } else {
	            System.out.println("\n* No se encontró ninguna relación para eliminar entre Empleado ID = " + empleadoId + " y Cliente ID = " + clienteId);
	        }
	    } catch (Exception ex) {
	        System.err.format("\n* Error al eliminar relación empleado-cliente: %s", ex.getMessage());
	        ex.printStackTrace();
	    }
	}
	/**
	 * inserta los datos generales que aparecen en todos los servicios
	 * @param nombre del servicio 
	 * @param descripcion del servicio
	 * @return devuelve el id que se genera en esta tabla
	 */
	public static int insertarServicio(String fechaContratacion, boolean isActive) {
		GestorDB BD = new GestorDB();	
        String sql= "INSERT INTO Servicios (fecha_contratacion, active) VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
        	PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, fechaContratacion);
            stmt.setBoolean(2, isActive);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                 return rs.getInt(1); // devuelve el ID generado
                
            }else {
            	return -1;//si no hay un id devuelve -1
            }
        }catch (Exception ex) {
	        System.err.format("\n* Error al insertar el servicio: %s", ex.getMessage());
	        ex.printStackTrace();
        }
        return -1;
	}
	
	/**
	 * del servicio:
	 * @param nombre 
	 * @param descripcion 
	 * de la cuenta:
	 * @param iban de la cuenta
	 * @param mantenimiento de la cuenta
	 * 
	 * @return el id que viene de servicio e hila todas las tablas
	 */
	public static int insertarCuenta(String fechaContratacion, boolean isActive, String iban, double mantenimiento) {
		GestorDB BD = new GestorDB();	
		// Inserta en Servicios y obtiene el ID
		int idServicio = insertarServicio(fechaContratacion, isActive); 
		String sql = "INSERT INTO Cuenta (id, iban, mantenimiento) VALUES (?, ?, ?)";
		 try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		         PreparedStatement stmt = con.prepareStatement(sql)) {
		            stmt.setInt(1, idServicio);
		            stmt.setString(2, iban);
		            stmt.setDouble(3, mantenimiento);
		            stmt.executeUpdate();
		           
		        } catch (SQLException ex) {
		        	System.err.format("\n* Error al insertar la cuenta ahorro: %s", ex.getMessage());
					ex.printStackTrace();
				}
		 return idServicio;
	}
	
	/**
	 * del servicio:
	 * @param nombre
	 * @param descripcion
	 * 
	 * de la cuenta:
	 * @param iban
	 * @param mantenimiento
	 * 
	 * de la cuenta ahorro:
	 * @param interes
	 * @param limite
	 */
	public static void insertarCuentaAhorro(String fechaContratacion, boolean isActive, String iban, double mantenimiento, double interes, int limite) {
		GestorDB BD = new GestorDB();	
		int idServicio = insertarCuenta(fechaContratacion, isActive, iban, mantenimiento);
		
        String sql = "INSERT INTO CuentaAhorro (id, interes_anual, limite) VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
         PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idServicio);
            stmt.setDouble(2, interes);
            stmt.setInt(3, limite);
            stmt.executeUpdate();
        } catch (SQLException ex) {
        	System.err.format("\n* Error al insertar la cuenta ahorro: %s", ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	/**
	 * del servicio:
	 * @param nombre
	 * @param descripcion
	 * 
	 * de la inversion:
	 * @param numeroCuenta
	 * @param tipo
	 * @param saldo
	 * @param rendimiento
	 * @param comision
	 * @param periodoInversion
	 */
	public static void insertarInversion(String fechaContratacion, boolean isActive,String numeroCuenta, 
			String tipo, double saldo, float rendimiento, float comision, String periodoInversion) {
		GestorDB BD = new GestorDB();	
		// Inserta en Servicios y obtiene el ID
		int idServicio = insertarServicio(fechaContratacion, isActive); 
		String sql = "INSERT INTO Inversion (id, numero_cuenta, tipo, saldo, rendimiento, "
				+ "comision, periodo_inversion) VALUES (?, ?, ?, ?, ?, ?, ?, ? )";
		 try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		         PreparedStatement stmt = con.prepareStatement(sql)) {
		            stmt.setInt(1, idServicio);
		            stmt.setString(2, numeroCuenta);
		            stmt.setString(3, tipo);
		            stmt.setDouble(4, saldo);
		            stmt.setFloat(5, rendimiento);
		            stmt.setFloat(6, comision);
		            stmt.setString(7, periodoInversion);
		            stmt.executeUpdate();
		           
		        } catch (SQLException ex) {
		        	System.err.format("\n* Error al insertar la inversion: %s", ex.getMessage());
					ex.printStackTrace();
				}
	}
	
	/**
	 * del servicio:
	 * @param nombre
	 * @param descripcion
	 * 
	 * del prestamo:
	 * @param numeroPrestamo
	 * @param monto
	 * @param interes
	 * @param plazoMeses
	 * 
	 * @return id de la tabla servicio
	 */
	public static int insertarPrestamo(String fechaContratacion, boolean isActive, int numeroPrestamo,
			double monto, double interes, int plazoMeses) {
		GestorDB BD = new GestorDB();	
		 // Inserta en Servicios y obtiene el ID
		int idServicio = insertarServicio(fechaContratacion, isActive);
		String sql = "INSERT INTO Prestamo (id, numero_prestamo, monto, interes, plazo_meses) VALUES (?, ?, ?, ?, ?)";
		 try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		         PreparedStatement stmt = con.prepareStatement(sql)) {
		            stmt.setInt(1, idServicio);
		            stmt.setInt(2, numeroPrestamo);
		            stmt.setDouble(3, monto);
		            stmt.setDouble(4, interes);
		            stmt.setInt(5, plazoMeses);
		            stmt.executeUpdate();
		           
		        } catch (SQLException ex) {
		        	System.err.format("\n* Error al insertar el prestamo: %s", ex.getMessage());
					ex.printStackTrace();
				}
		 return idServicio;
		
	}
	/**
	 * del servicio:
	 * @param nombre
	 * @param descripcion
	 * 
	 * del prestamo:
	 * @param numeroPrestamo
	 * @param monto
	 * @param interes
	 * @param plazoMeses
	 * 
	 * del prestamo de coche:
	 * @param modelo
	 * @param anio
	 * @param seguroIncluido
	 */
	public static void insertarPrestamoCoche(String fechaContratacion, boolean isActive, int numeroPrestamo,
			double monto, double interes, int plazoMeses, String modelo, int anio, boolean seguroIncluido) {
		GestorDB BD = new GestorDB();	
		// Inserta en Prestamo y obtiene el ID
		int idPrestamo = insertarPrestamo(fechaContratacion, isActive, numeroPrestamo, monto, interes, plazoMeses); 
		String sql = "INSERT INTO prestamo_coche(id, modelo, anio, seguro_incluido)"
				+ " VALUES (?, ?, ?, ?)";
		 try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		         PreparedStatement stmt = con.prepareStatement(sql)) {
		            stmt.setInt(1, idPrestamo);
		            stmt.setString(2, modelo);
		            stmt.setInt(3, anio);
		            stmt.setBoolean(4, seguroIncluido);
		            stmt.executeUpdate();
		           
		        } catch (SQLException ex) {
		        	System.err.format("\n* Error al insertar el prestamo de coche: %s", ex.getMessage());
					ex.printStackTrace();
				}
		
	}
	
	public static void insertarPrestamoHipoteca(String fechaContratacion, boolean isActive, int numeroPrestamo,
			double monto, double interes, int plazoMeses, String direccion, double valor) {
		GestorDB BD = new GestorDB();	
		// Inserta en Prestamo y obtiene el ID
		int idPrestamo = insertarPrestamo(fechaContratacion, isActive, numeroPrestamo, monto, interes, plazoMeses); 
		
		String sql = "INSERT INTO prestamo_hipoteca(id, direccion, valor)"
				+ " VALUES (?, ?, ?)";
		 try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		         PreparedStatement stmt = con.prepareStatement(sql)) {
		            stmt.setInt(1, idPrestamo);
		            stmt.setString(2, direccion);
		            stmt.setDouble(3, valor);
		            stmt.executeUpdate();
		           
		        } catch (SQLException ex) {
		        	System.err.format("\n* Error al insertar el prestamo hipotecario: %s", ex.getMessage());
					ex.printStackTrace();
				}
	}
	public static void insertarPrestamoPersonal(String fechaContratacion, boolean isActive, int numeroPrestamo,
			double monto, double interes, int plazoMeses, String motivo, boolean requiereAval) {
		GestorDB BD = new GestorDB();	
		// Inserta en Prestamo y obtiene el ID
		int idPrestamo = insertarPrestamo(fechaContratacion, isActive, numeroPrestamo, monto, interes, plazoMeses); 
		
		String sql = "INSERT INTO prestamo_personal(id, motivo, requiere_aval)"
				+ " VALUES (?, ?, ?)";
		 try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		         PreparedStatement stmt = con.prepareStatement(sql)) {
		            stmt.setInt(1, idPrestamo);
		            stmt.setString(2, motivo);
		            stmt.setBoolean(3, requiereAval);
		            stmt.executeUpdate();
		           
		        } catch (SQLException ex) {
		        	System.err.format("\n* Error al insertar el prestamo personal: %s", ex.getMessage());
					ex.printStackTrace();
				}
	}
	public static int insertarSeguro(String fechaContratacion, boolean isActive, String numPoliza, String fechaVencimiento, 
			float coberturaTotal, float mensualidad) {
		GestorDB BD = new GestorDB();	
		int idServicio = insertarServicio(fechaContratacion, isActive);
		String sql = "INSERT INTO Seguro (id, num_poliza, fecha_vencimiento, cobertura_total, mensualidad) "
				+ "VALUES (?, ?, ?, ?, ?)";
		 try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		         PreparedStatement stmt = con.prepareStatement(sql)) {
		            stmt.setInt(1, idServicio);
		            stmt.setString(2, numPoliza);
		            stmt.setString(3, fechaVencimiento);
		            stmt.setFloat(4, coberturaTotal);
		            stmt.setFloat(5, mensualidad);
		            stmt.executeUpdate();
		           
		        } catch (SQLException ex) {
		        	System.err.format("\n* Error al insertar el seguro: %s", ex.getMessage());
					ex.printStackTrace();
				}
		 return idServicio;
	}
	public static void insertarSeguroCasa(String fechaContratacion, boolean isActive, String numPoliza, String fechaVencimiento, 
			float coberturaTotal, float mensualidad, String direccion, double valor, String fechaConstruccion) {
		GestorDB BD = new GestorDB();	
		// Inserta en Seguro y obtiene el ID
		int idSeguro = insertarSeguro(fechaContratacion, isActive, numPoliza, fechaVencimiento, coberturaTotal, mensualidad); 
				
		String sql = "INSERT INTO seguro_casa(id, direccion, valor, fecha_construccion) VALUES (?, ?, ?, ?)";
			try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				   PreparedStatement stmt = con.prepareStatement(sql)) {
				        stmt.setInt(1, idSeguro);
				        stmt.setString(2, direccion);
				        stmt.setDouble(3, valor);
				        stmt.setString(4, fechaConstruccion);
				        stmt.executeUpdate();
				           
			   } catch (SQLException ex) {
				     System.err.format("\n* Error al insertar el seguro de casa: %s", ex.getMessage());
					ex.printStackTrace();
				}
		
	}
	public static void insertarSeguroCoche(String fechaContratacion, boolean isActive, String numPoliza, String fechaVencimiento, 
			float coberturaTotal, float mensualidad, String descripcionCoche, String matricula, int anioFabricacion) {
		GestorDB BD = new GestorDB();	
		// Inserta en Seguro y obtiene el ID
		int idSeguro = insertarSeguro(fechaContratacion, isActive, numPoliza, fechaVencimiento, coberturaTotal, mensualidad); 
						
	    String sql = "INSERT INTO seguro_coche(id, descripcion, matricula, anio_fabricacion) VALUES (?, ?, ?, ?)";
			try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
					PreparedStatement stmt = con.prepareStatement(sql)) {
						  stmt.setInt(1, idSeguro);
						  stmt.setString(2, descripcionCoche);
						  stmt.setString(3, matricula);
						  stmt.setInt(4, anioFabricacion);
						  stmt.executeUpdate();
						           
			} catch (SQLException ex) {
					System.err.format("\n* Error al insertar el seguro de coche: %s", ex.getMessage());
					ex.printStackTrace();
				}
	}
	public static void insertarSeguroVida(String fechaContratacion, boolean isActive, String numPoliza, String fechaVencimiento, 
			float coberturaTotal, float mensualidad, String beneficiarios) {
		GestorDB BD = new GestorDB();	
		// Inserta en Seguro y obtiene el ID
		int idSeguro = insertarSeguro(fechaContratacion, isActive, numPoliza, fechaVencimiento, coberturaTotal, mensualidad); 
								
		String sql = "INSERT INTO seguro_vida(id, beneficiarios) VALUES (?, ?)";
			try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
					PreparedStatement stmt = con.prepareStatement(sql)) {
					stmt.setInt(1, idSeguro);
					stmt.setString(2, beneficiarios);
					stmt.executeUpdate();
								           
			} catch (SQLException ex) {
					System.err.format("\n* Error al insertar el seguro de vida: %s", ex.getMessage());
					ex.printStackTrace();
			}
	}
	public static int insertarTarjeta(String fechaContratacion, boolean isActive, String numero, String cvv, int fechaCaducidad) {
		GestorDB BD = new GestorDB();	
		//Inserta en Servicio y obtiene el ID
		int idServicio = insertarServicio(fechaContratacion, isActive);
		String sql = "INSERT INTO Tarjeta (id, numero, cvv, fecha_caducidad) "
				+ "VALUES (?, ?, ?, ?)";
		 try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		         PreparedStatement stmt = con.prepareStatement(sql)) {
		            stmt.setInt(1, idServicio);
		            stmt.setString(2, numero);
		            stmt.setString(3, cvv);
		            stmt.setInt(4, fechaCaducidad);
		            stmt.executeUpdate();
		           
		        } catch (SQLException ex) {
		        	System.err.format("\n* Error al insertar la tarjeta: %s", ex.getMessage());
					ex.printStackTrace();
				}
		 return idServicio;
	}
	public static void insertarTarjetaCredito(String fechaContratacion, boolean isActive, String numero, String cvv, int fechaCaducidad, int limite) {
		GestorDB BD = new GestorDB();	
		// Inserta en Tarjeta y obtiene el ID
				int idTarjeta = insertarTarjeta(fechaContratacion, isActive, numero, cvv, fechaCaducidad); 
										
				String sql = "INSERT INTO tarjeta_credito(id, limite) VALUES (?, ?)";
					try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
							PreparedStatement stmt = con.prepareStatement(sql)) {
							stmt.setInt(1, idTarjeta);
							stmt.setInt(2, limite);
							stmt.executeUpdate();
										           
					} catch (SQLException ex) {
							System.err.format("\n* Error al insertar la tarjeta de credito: %s", ex.getMessage());
							ex.printStackTrace();
					}
		
	}
	public static void insertarTarjetaDebito(String fechaContratacion, boolean isActive, String numero, String cvv,
			int fechaCaducidad, int limite) {
		GestorDB BD = new GestorDB();	
		// Inserta en Tarjeta y obtiene el ID
		int idTarjeta = insertarTarjeta(fechaContratacion, isActive, numero, cvv, fechaCaducidad); 
								
		String sql = "INSERT INTO tarjeta_debito(id, limite) VALUES (?, ?)";
			try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
					PreparedStatement stmt = con.prepareStatement(sql)) {
					stmt.setInt(1, idTarjeta);
					stmt.setInt(2, limite);
					stmt.executeUpdate();
								           
			} catch (SQLException ex) {
					System.err.format("\n* Error al insertar la tarjeta de credito: %s", ex.getMessage());
					ex.printStackTrace();
			}
	}
	
	public static void asignaServicioACliente(String idServicio, String dni) {
		GestorDB BD = new GestorDB();
		
	    String sql = "INSERT INTO CLIENTE_SERVICIO (CLIENTE_ID, SERVICIO_ID ) VALUES (?, ?)";
	   
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pstmt = con.prepareStatement(sql)) {

	        pstmt.setString(1, dni); // ID del cliente
	        pstmt.setString(2, idServicio); // ID del cliente

	        pstmt.executeUpdate();
	        System.out.println("\n- Relación empleado-cliente insertada: Cliente ID = " + dni + ", Servicio ID = " + idServicio);
	    } catch (Exception ex) {
	        System.err.format("\n* Error al insertar relación cliente-servicio: %s", ex.getMessage());
	        ex.printStackTrace();
	    }
	}
	
	//----------------------------------------HACER REQUESTS--------------------------------------
	/**
	 * @param recibe el id de un empleado
	 * @return devuelve el empleado que este asociado a ese id
	 */
	public static Empleado getEmpleadobyID(String id) {
		Empleado empleado = null;
		String sql = "SELECT * FROM EMPLEADO WHERE ID=?";
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
			PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				empleado = new Empleado();
				empleado.setId(rs.getString("ID"));
				empleado.setNombre(rs.getString("NOMBRE"));
				empleado.setApellido1(rs.getString("APELLIDO1"));
				empleado.setApellido2(rs.getString("APELLIDO2"));
				empleado.setDni(rs.getString("DNI"));
				empleado.setDireccion(rs.getString("DIRECCION"));
				empleado.setCp(rs.getString("CP"));
				empleado.setCiudad(rs.getString("CIUDAD"));
				empleado.setProvincia(rs.getString("PROVINCIA"));
				empleado.setTlfn(rs.getString("TELEFONO"));
				empleado.setEmail(rs.getString("EMAIL"));
				empleado.setSexo(Sexo.valueOf(rs.getString("SEXO")));
				empleado.setFechaNacimiento(LocalDate.parse(rs.getString("FECHA_NACIMIENTO")));
			}
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("No existe el empleado con id: "+ id);
			e.printStackTrace();
		}		
		return empleado;
	}
	
	/**
	 * Recupera la informacion de un cliente por su dni
	 * @param recibe el dni de un cliente
	 * @return devuleve el cliente que esté asociado a ese dni
	 */
	public static Cliente getClientebyDNI(String dni) {
		Cliente cliente = null;
		String sql = "SELECT * FROM CLIENTE WHERE DNI = ?";
		try(Connection con = DriverManager.getConnection(CONNECTION_STRING);
			PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, dni);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cliente = new Cliente();
				cliente.setNombre(rs.getString("NOMBRE"));
				cliente.setApellido1(rs.getString("APELLIDO1"));
				cliente.setApellido2(rs.getString("APELLIDO2"));
				cliente.setDni(dni);
				cliente.setDireccion(rs.getString("DIRECCION"));
				cliente.setCp(rs.getString("CP"));
				cliente.setCiudad(rs.getString("CIUDAD"));
				cliente.setProvincia(rs.getString("PROVINCIA"));
				cliente.setTlfn(rs.getString("TELEFONO"));
				cliente.setEmail(rs.getString("EMAIL"));
				cliente.setSexo(Sexo.valueOf(rs.getString("SEXO")));
				cliente.setFechaNacimiento(LocalDate.parse(rs.getString("FECHA_NACIMIENTO")));
				cliente.setProfesion(rs.getString("PROFESION"));
		        cliente.setPuntajeCrediticio(rs.getDouble("PUNTAJE_CREDITICIO"));
		        cliente.setObservaciones(rs.getString("OBSERVACIONES"));
		        
				
			}
					
		} catch (SQLException e) {
					System.out.println("No existe el cliente con dni" + dni);
					e.printStackTrace();
				}		
		
		return cliente;
	}
	
	//----no esta terminado-----------------------------
	public static SeguroCasa getSeguroCasaById(int id) {
	    String sql = " SELECT s.id, s.fecha_contratacion, s.is_active, se.num_poliza, se.fecha_vencimiento, se.cobertura_total, se.mensualidad "
	    		+ "FROM Servicios s WHERE s.id = se.id WHERE s.id = ?";
	    SeguroCasa sCasa = new SeguroCasa();
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement stmt = con.prepareStatement(sql)) {
	        stmt.setInt(1, id);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	            
	                sCasa.setNumPoliza(rs.getString("num_poliza"));
	                
	            }
	        }
	    } catch (SQLException ex) {
	        System.err.format("\n* Error al obtener el seguro por ID: %s", ex.getMessage());
	        ex.printStackTrace();
	    }
	    return sCasa;
	}
	
	//-------------------------------------------------------
	/*public static List<Servicio> getServiciosDeCliente(Cliente cliente){
		String sql = "SELECT * FROM "
	}
	*/
	public static void eliminarCliente(String dni) {
		GestorDB BD = new GestorDB();
		String sql = "DELETE FROM CLIENTE WHERE DNI = ?";
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		         PreparedStatement stmt = con.prepareStatement(sql)) {
		        stmt.setString(1, dni);
		        stmt.executeUpdate();
		        System.out.println("eliminado con exito");
		    } catch (SQLException ex) {
		        System.err.format("\n* No se ha podido eliminar el cliente);: %s", ex.getMessage());
		        ex.printStackTrace();
		    }
	}
	
	public static void eliminarEmpleado(String id) {
		GestorDB BD = new GestorDB();
		String sql = "DELETE FROM EMPLEADO WHERE ID = ?";
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
		         PreparedStatement stmt = con.prepareStatement(sql)) {
		        stmt.setString(1, id);
		        stmt.executeUpdate();
		        System.out.println("eliminado con exito");
		    } catch (SQLException ex) {
		        System.err.format("\n* No se ha podido eliminar el empleado);: %s", ex.getMessage());
		        ex.printStackTrace();
		    }
	}
	public static void editarCliente(String dni, String nombre, String apellido1, String apellido2, String direccion, String cp, 
			String ciudad, String provincia, String telefono, String email, Sexo sexo, String fechaNacimiento, String profesion, 
			double puntajeCrediticio, String observaciones, String dniOriginal) {
		GestorDB BD = new GestorDB();
		 String sql = "UPDATE CLIENTE SET dni = ?, nombre = ?, apellido1 =?, apellido2 =?, direccion =?, cp=?, ciudad= ?, provincia=?, "
		 		+ "telefono= ?, email=?, sexo=?, fecha_nacimiento =?, profesion =?, puntaje_crediticio=?, observaciones=? "
		 		+ "WHERE dni = ?";

		    try (Connection conexion = DriverManager.getConnection(CONNECTION_STRING);
		         PreparedStatement stmt = conexion.prepareStatement(sql)) {

		        stmt.setString(1, dni);
		        stmt.setString(2, nombre);
		        stmt.setString(3, apellido1);
		        stmt.setString(4, apellido2);
		        stmt.setString(5, direccion);
		        stmt.setString(6, cp);
		        stmt.setString(7, ciudad);
		        stmt.setString(8, provincia);
		        stmt.setString(9, telefono);
		        stmt.setString(10, email);
		        stmt.setString(11, sexo.toString());
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        String fechaNacimientoFormateada = sdf.format(fechaNacimiento);
		        stmt.setString(12, fechaNacimientoFormateada);
		        stmt.setString(13, profesion);
		        stmt.setDouble(14, puntajeCrediticio);
		        stmt.setString(15, observaciones);
		        stmt.setString(16, dniOriginal);
		        

		        stmt.executeUpdate();
		        System.out.println("Info del cliente actualizada");

		    } catch (SQLException e) {
		    	System.out.println("Error al editar el cliente");
		        e.printStackTrace();
		    }
	}
	public static void editarEmpleado(String id, String nombre, String apellido1, String apellido2, String dni, String direccion, 
			String cp, String ciudad, String provincia, String telefono, String email, Sexo sexo, String fechaNacimiento, String dniOriginal) {
		GestorDB BD = new GestorDB();
		 String sql = "UPDATE EMPLEADO SET id = ?, nombre = ?, apellido1 =?, apellido2 =?,dni=?, direccion =?, cp=?, "
		 		+ "ciudad= ?, provincia=?, telefono= ?, email=?, sexo=?, fecha_nacimiento =? "
		 		+ "WHERE dni = ?";

		    try (Connection conexion = DriverManager.getConnection(CONNECTION_STRING);
		         PreparedStatement stmt = conexion.prepareStatement(sql)) {

		        stmt.setString(1, id);
		        stmt.setString(2, nombre);
		        stmt.setString(3, apellido1);
		        stmt.setString(4, apellido2);
		        stmt.setString(5, dni);
		        stmt.setString(6, direccion);
		        stmt.setString(7, cp);
		        stmt.setString(8, ciudad);
		        stmt.setString(9, provincia);
		        stmt.setString(10, telefono);
		        stmt.setString(10, email);
		        stmt.setString(11, sexo.toString());
		        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        //String fechaNacimientoFormateada = sdf.format(fechaNacimiento);
		        stmt.setString(12, fechaNacimiento);
		        stmt.setString(13, dniOriginal);
		        

		        stmt.executeUpdate();
		        System.out.println("Info del empleado actualizada");

		    } catch (SQLException e) {
		    	System.out.println("Error al intentar editar el empleado");
		        e.printStackTrace();
		    }
	}
    
	//----------------------------------------METODOS AUXILIARES----------------
	
	/**
	 * Comprueba si ese empleado ya esta en la base de datos
	 * @param recibe el dni del empleado
	 * @return devuleve true si el empleado ya está en la base de datos y false si no está
	 */
	public static boolean existeEmpleado(String id) {
	    String sql = "SELECT 1 FROM EMPLEADO WHERE ID = ?";
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pstmt = con.prepareStatement(sql)) {

	        pstmt.setString(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            return rs.next(); 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	/**
	 * Comprueba si el cliente ya esta en la base de datos
	 * @param recibe el dni del cliente
	 * @return devuelve true si el cliente ya está en la base de datos y false si no está
	 */
	public static boolean existeCliente(String dni) {
	    String sql = "SELECT 1 FROM CLIENTE WHERE DNI = ?";
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pstmt = con.prepareStatement(sql)) {

	        pstmt.setString(1, dni);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            return rs.next(); 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	
	
	
}
