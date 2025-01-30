package main;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Properties;
import domain.Cliente;
import domain.Empleado;
import domain.Sexo;

public class DBInicializador {

    // Se elimina el final de los atributos para poder actualizar los valores.
    protected static String DRIVER_NAME;
    protected static String DATABASE_FILE;
    protected static String CONNECTION_STRING;

    public DBInicializador() {
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
    
    
    
    
    
//------------------------------------------------------------//
    	//Crea todas las tablas de la base de datos
//------------------------------------------------------------//

 
    public void crearBBDD() {
        //CLIENTE
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
        	
            String sql = "CREATE TABLE IF NOT EXISTS CLIENTE ("
            		   + " DNI TEXT NOT NULL PRIMARY KEY,"
                       + " NOMBRE TEXT NOT NULL,"
                       + " APELLIDO1 TEXT NOT NULL,"
                       + " APELLIDO2 TEXT NOT NULL,"
                       + " DIRECCION TEXT NOT NULL,"
                       + " CP TEXT NOT NULL,"
                       + " CIUDAD TEXT NOT NULL,"
                       + " PROVINCIA TEXT NOT NULL,"
                       + " TELEFONO TEXT NOT NULL,"
                       + " EMAIL TEXT NOT NULL,"
                       + " SEXO TEXT NOT NULL,"
                       + " FECHA_NACIMIENTO TEXT NOT NULL," 
                       + " PROFESION TEXT NOT NULL,"
                       //+ " EMPLEADO_ASIGNADO INTEGER,"
                       + " PUNTAJE_CREDITICIO DOUBLE,"
                       + " OBSERVACIONES TEXT"
                       + ");";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                if (!pstmt.execute()) {
                    System.out.println("\n- Se ha creado la tabla CLIENTE");
                }
            }
        } catch (Exception ex) {
            System.err.format("\n* Error al crear la tabla CLIENTE: %s", ex.getMessage());
            ex.printStackTrace();
        }
        
        
        

        //EMPLEADO
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            String sql = "CREATE TABLE IF NOT EXISTS EMPLEADO ("
            		   + " ID TEXT PRIMARY KEY,"
                       + " NOMBRE TEXT NOT NULL,"
                       + " APELLIDO1 TEXT NOT NULL,"
                       + " APELLIDO2 TEXT NOT NULL,"
                       + " DNI TEXT NOT NULL,"//poner unique luego
                       + " DIRECCION TEXT NOT NULL,"
                       + " CP TEXT NOT NULL,"
                       + " CIUDAD TEXT NOT NULL,"
                       + " PROVINCIA TEXT NOT NULL,"
                       + " TELEFONO TEXT NOT NULL,"
                       + " EMAIL TEXT NOT NULL,"
                       + " SEXO TEXT NOT NULL,"
                       + " FECHA_NACIMIENTO TEXT NOT NULL"
                       + ");";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                if (!pstmt.execute()) {
                    System.out.println("\n- Se ha creado la tabla EMPLEADO");
                }
            }
        } catch (Exception ex) {
            System.err.format("\n* Error al crear la tabla EMPLEADO: %s", ex.getMessage());
            ex.printStackTrace();
        }
        
        
        
        //SERVICIO
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
        	String sql = "CREATE TABLE IF NOT EXISTS SERVICIO ("
                    + " ID INTEGER PRIMARY KEY AUTOINCREMENT,"
        			+ " FECHA_CONTRATACION TEXT NOT NULL,"
        			+ " ACTIVE BOOLEAN NOT NULL"
        			//-----------------------------------------------cliente titular
                    + ");";

           try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                if (!pstmt.execute()) {
                    System.out.println("\n- Se ha creado la tabla SERVICIO");
                }
            }
        } catch (Exception ex) {
            System.err.format("\n* Error al crear la tabla SERVICIO: %s", ex.getMessage());
            ex.printStackTrace();
        }
       
       
       
       
       //CUENTA
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
       	String sql = "CREATE TABLE IF NOT EXISTS CUENTA ("
       			   + " ID INTEGER PRIMARY KEY,"
                   + " IBAN INTEGER NOT NULL,"
       			   + " MANTENIMIENTO DOUBLE NOT NULL,"
                   + "FOREIGN KEY(ID) REFERENCES SERVICIO(ID)"
                   + ");";

          try (PreparedStatement pstmt = con.prepareStatement(sql)) {
               if (!pstmt.execute()) {
                   System.out.println("\n- Se ha creado la tabla CUENTA");
               }
           }
       } catch (Exception ex) {
           System.err.format("\n* Error al crear la tabla CUENTA: %s", ex.getMessage());
           ex.printStackTrace();
       }
       
       
       
       
       
       //CUENTA AHORRO
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
          	String sql = "CREATE TABLE IF NOT EXISTS CUENTA_AHORRO ("
          			  + " ID INTEGER PRIMARY KEY,"
                      + " INTERES_ANUAL DOUBLE,"
          			  + " LIMITE INT NOT NULL,"
          			  + "FOREIGN KEY(ID) REFERENCES CUENTA(ID)"
                      + ");";

             try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                  if (!pstmt.execute()) {
                      System.out.println("\n- Se ha creado la tabla CUENTA AHORRO");
                  }
              }
          } catch (Exception ex) {
              System.err.format("\n* Error al crear la tabla CUENTA AHORRO: %s", ex.getMessage());
              ex.printStackTrace();
          }
       
       
       
       
       
       //INVERSION
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
         	String sql = "CREATE TABLE IF NOT EXISTS INVERSION ("
         			 + " ID INTEGER PRIMARY KEY,"
                     + " NUMERO_CUENTA TEXT,"
         			 + " TIPO TEXT NOT NULL,"
                     + " SALDO DOUBLE NOT NULL,"
         			 + " RENDIMIENTO FLOAT NOT NULL,"
                     + " COMISION FLOAT NOT NULL,"
                     + " PERIODO_INVERSION TEXT NOT NULL,"
                     + " FOREIGN KEY(ID) REFERENCES SERVICIO(ID)"
                     + ");";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                 if (!pstmt.execute()) {
                     System.out.println("\n- Se ha creado la tabla INVERSION");
                 }
             }
         } catch (Exception ex) {
             System.err.format("\n* Error al crear la tabla INVERSION: %s", ex.getMessage());
             ex.printStackTrace();
         }
       
       
       
       
       //PRESTAMO
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
        	String sql = "CREATE TABLE IF NOT EXISTS PRESTAMO ("
        			+ " ID INTEGER PRIMARY KEY,"
                    + " NUMERO_PRESTAMO INTEGER,"
        			+ " MONTO DOUBLE NOT NULL,"
                    + " INTERES DOUBLE NOT NULL,"
        			+ " PLAZO_MESES INTEGER NOT NULL,"
        			+ " FOREIGN KEY(ID) REFERENCES SERVICIO(ID)"
                    + ");";

           try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                if (!pstmt.execute()) {
                    System.out.println("\n- Se ha creado la tabla PRESTAMO");
                }
            }
        } catch (Exception ex) {
            System.err.format("\n* Error al crear la tabla PRESTAMO: %s", ex.getMessage());
            ex.printStackTrace();
        }
       
       
       
       
       //PRESTAMO COCHE
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
       	String sql = "CREATE TABLE IF NOT EXISTS PRESTAMO_COCHE ("
       			   + " ID INTEGER PRIMARY KEY,"
                   + " MODELO TEXT NOT NULL,"
       			   + " ANIO INTEGER NOT NULL,"
                   + " SEGURO_INCLUIDO BOOLEAN NOT NULL,"
                   + " FOREIGN KEY(ID) REFERENCES PRESTAMO(ID)"
                   + ");";

          try (PreparedStatement pstmt = con.prepareStatement(sql)) {
               if (!pstmt.execute()) {
                   System.out.println("\n- Se ha creado la tabla PRESTAMO COCHE");
               }
           }
       } catch (Exception ex) {
           System.err.format("\n* Error al crear la tabla PRESTAMO COCHE: %s", ex.getMessage());
           ex.printStackTrace();
       }
       
       
       
       
       //PRESTAMO HIPOTECA
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
          	String sql = "CREATE TABLE IF NOT EXISTS PRESTAMO_HIPOTECA ("
          			  + " ID INTEGER PRIMARY KEY,"
                      + " DIRECCION TEXT NOT NULL,"
          			  + " VALOR DOUBLE NOT NULL,"
          			  + " FOREIGN KEY(ID) REFERENCES PRESTAMO(ID)"
                      + ");";

             try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                  if (!pstmt.execute()) {
                      System.out.println("\n- Se ha creado la tabla PRESTAMO HIPOTECA");
                  }
              }
          } catch (Exception ex) {
              System.err.format("\n* Error al crear la tabla PRESTAMO HIPOTECA: %s", ex.getMessage());
              ex.printStackTrace();
          }
       
       
       
       
       //PRESTAMO PERSONAL
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
         	String sql = "CREATE TABLE IF NOT EXISTS PRESTAMO_PERSONAL ("
         			 + " ID INTEGER PRIMARY KEY,"
                     + " MOTIVO TEXT NOT NULL,"
         			  + " REQUIERE_AVAL BOOLEAN NOT NULL,"
         			 + " FOREIGN KEY(ID) REFERENCES PRESTAMO(ID)"
                     + ");";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                 if (!pstmt.execute()) {
                     System.out.println("\n- Se ha creado la tabla PRESTAMO PERSONAL");
                 }
             }
         } catch (Exception ex) {
             System.err.format("\n* Error al crear la tabla PRESTAMO PERSONAL: %s", ex.getMessage());
             ex.printStackTrace();
         }
       
       
       
       
       //SEGURO
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
        	String sql = "CREATE TABLE IF NOT EXISTS SEGURO ("
        			+ " ID INTEGER PRIMARY KEY,"
                    + " NUM_POLIZA TEXT NOT NULL,"
        			+ " FECHA_VENCIMIENTO TEXT NOT NULL,"
        			+ " COBERTURA_TOTAL FLOAT NOT NULL,"
        			+ " MENSUALIDAD FLOAT NOT NULL,"
        			+ " FOREIGN KEY(ID) REFERENCES SERVICIO(ID)"
                    + ");";

           try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                if (!pstmt.execute()) {
                    System.out.println("\n- Se ha creado la tabla SEGURO");
                }
            }
        } catch (Exception ex) {
            System.err.format("\n* Error al crear la tabla SEGURO: %s", ex.getMessage());
            ex.printStackTrace();
        }
       
       
       
       
       //SEGURO CASA
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
       	String sql = "CREATE TABLE IF NOT EXISTS SEGURO_CASA ("
       			   + " ID INTEGER PRIMARY KEY,"
                   + " DIRECCION TEXT NOT NULL,"
       			   + " VALOR DOUBLE NOT NULL,"
       			   + " FECHA_CONSTRUCCION TEXT NOT NULL,"
       			   + " FOREIGN KEY(ID) REFERENCES SEGURO(ID)"
                   + ");";

          try (PreparedStatement pstmt = con.prepareStatement(sql)) {
               if (!pstmt.execute()) {
                   System.out.println("\n- Se ha creado la tabla SEGURO_CASA");
               }
           }
       } catch (Exception ex) {
           System.err.format("\n* Error al crear la tabla SEGURO_CASA: %s", ex.getMessage());
           ex.printStackTrace();
       }
       
       
       

     //SEGURO COCHE
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
       	String sql = "CREATE TABLE IF NOT EXISTS SEGURO_COCHE ("
       			   + "ID INTEGER PRIMARY KEY,"
                   + " DESCRIPCION TEXT NOT NULL,"
       			   + " MATRICULA TEXT NOT NULL,"
       			   + " ANIO_FABRICACION INTEGER NOT NULL,"
       			   + " FOREIGN KEY(ID) REFERENCES SEGURO(ID)"
                   + ");";

          try (PreparedStatement pstmt = con.prepareStatement(sql)) {
               if (!pstmt.execute()) {
                   System.out.println("\n- Se ha creado la tabla SEGURO_COCHE");
               }
           }
       } catch (Exception ex) {
           System.err.format("\n* Error al crear la tabla SEGURO_COCHE: %s", ex.getMessage());
           ex.printStackTrace();
       }
       
       
       
       
     //SEGURO VIDA
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
       	String sql = "CREATE TABLE IF NOT EXISTS SEGURO_VIDA ("
       			   + " ID INTEGER PRIMARY KEY,"
                   + " BENEFICIARIOS TEXT NOT NULL,"
                   + " FOREIGN KEY(ID) REFERENCES SEGURO(ID)"
                   + ");";

          try (PreparedStatement pstmt = con.prepareStatement(sql)) {
               if (!pstmt.execute()) {
                   System.out.println("\n- Se ha creado la tabla SEGURO_VIDA");
               }
           }
       } catch (Exception ex) {
           System.err.format("\n* Error al crear la tabla SEGURO_VIDA: %s", ex.getMessage());
           ex.printStackTrace();
       }
       
       
     //TARJETA
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
       	String sql = "CREATE TABLE IF NOT EXISTS TARJETA ("
       			   + " ID INTEGER PRIMARY KEY,"
                   + " NUMERO TEXT NOT NULL,"
       			   + " CVV TEXT NOT NULL,"
       			   + " FECHA_CADUCIDAD INTEGER NOT NULL,"
       			   + " FOREIGN KEY(ID) REFERENCES SERVICIO(ID)"
                   + ");";

          try (PreparedStatement pstmt = con.prepareStatement(sql)) {
               if (!pstmt.execute()) {
                   System.out.println("\n- Se ha creado la tabla TARJETA");
               }
           }
       } catch (Exception ex) {
           System.err.format("\n* Error al crear la tabla TARJETA: %s", ex.getMessage());
           ex.printStackTrace();
       }
       
     //TARJETA CREDITO
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
       	String sql = "CREATE TABLE IF NOT EXISTS TARJETA_CREDITO ("
       			   + " ID INTEGER PRIMARY KEY,"
                   + " LIMITE INTEGER NOT NULL,"
                   + " FOREIGN KEY(ID) REFERENCES TARJETA(ID)"
                   + ");";

          try (PreparedStatement pstmt = con.prepareStatement(sql)) {
               if (!pstmt.execute()) {
                   System.out.println("\n- Se ha creado la tabla TARJETA_CREDITO");
               }
           }
       } catch (Exception ex) {
           System.err.format("\n* Error al crear la tabla TARJETA_CREDITO: %s", ex.getMessage());
           ex.printStackTrace();
       }
       
       //TARJETA DEBITO
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
       	String sql = "CREATE TABLE IF NOT EXISTS TARJETA_DEBITO ("
       			   + " ID INTEGER PRIMARY KEY,"
                   + " LIMITE INTEGER NOT NULL,"
                   + " FOREIGN KEY(ID) REFERENCES TARJETA(ID)"
                   + ");";

          try (PreparedStatement pstmt = con.prepareStatement(sql)) {
               if (!pstmt.execute()) {
                   System.out.println("\n- Se ha creado la tabla TARJETA_DEBITO");
               }
           }
       } catch (Exception ex) {
           System.err.format("\n* Error al crear la tabla TARJETA_DEBITO: %s", ex.getMessage());
           ex.printStackTrace();
       }
       
       //TRANSACCION
       try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
       	String sql = "CREATE TABLE IF NOT EXISTS TRANSACCION ("
                   + " NOMBRE TEXT NOT NULL,"
       			   + " CANTIDAD INTEGER NOT NULL"
                   + ");";

          try (PreparedStatement pstmt = con.prepareStatement(sql)) {
               if (!pstmt.execute()) {
                   System.out.println("\n- Se ha creado la tabla TRANSACCION");
               }
           }
       } catch (Exception ex) {
           System.err.format("\n* Error al crear la tabla TRANSACCION: %s", ex.getMessage());
           ex.printStackTrace();
       }
       
        //EMPLEADO_CLIENTE
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            String sql = "CREATE TABLE IF NOT EXISTS EMPLEADO_CLIENTE ("
                       + " EMPLEADO_ID TEXT NOT NULL,"
                       + " CLIENTE_ID TEXT NOT NULL,"
                       + " FOREIGN KEY (EMPLEADO_ID) REFERENCES EMPLEADO(ID),"
                       + " FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE(DNI)"
                       + ");";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                if (!pstmt.execute()) {
                    System.out.println("\n- Se ha creado la tabla EMPLEADO_CLIENTE");
                }
            }
        } catch (Exception ex) {
            System.err.format("\n* Error al crear la tabla EMPLEADO_CLIENTE: %s", ex.getMessage());
            ex.printStackTrace();
        }
        
        //CLIENTE_SERVICIO
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            String sql = "CREATE TABLE IF NOT EXISTS CLIENTE_SERVICIO ("
                       + " CLIENTE_ID INTEGER NOT NULL,"
                       + " SERVICIO_ID INTEGER NOT NULL,"
                       + " FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE(DNI),"
                       + " FOREIGN KEY (SERVICIO_ID) REFERENCES SERVICIO(ID)"
                       + ");";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                if (!pstmt.execute()) {
                    System.out.println("\n- Se ha creado la tabla CLIENTE_SERVICIO");
                }
            }
        } catch (Exception ex) {
            System.err.format("\n* Error al crear la tabla EMPLEADO_CLIENTE: %s", ex.getMessage());
            ex.printStackTrace();
        }
        
    }

  //------------------------------------------------------------//
				// Borrar base de datos
//------------------------------------------------------------//
    
    
    
    
    
    
    
    
    
    
//------------------------------------------------------------//
		//Cargar datos en la Base de datos
//------------------------------------------------------------//
    
    
    
    
    public void cargarDatos() {
    	
    	//IAG - Lista de empleados y clientes generadas con ChatGPT
    	
    	Empleado empleado1 = new Empleado(
    	        "Ibon", "E001", "Arambarri", "López", "87654321X", "Calle Ejemplo 2", 
    	        "48002", "Bilbao", "Vizcaya", "600654321", "ibon.garcia@example.com", 
    	        Sexo.HOMBRE, LocalDate.of(1988, 3, 12));
    	GestorDB.insertarEmpleado(empleado1);

    	Empleado empleado2 = new Empleado(
    	        "Leire", "E002", "Larrauri", "González", "12345678Y", "Calle Ejemplo 3", 
    	        "48003", "Bilbao", "Vizcaya", "600654322", "ane.martinez@example.com", 
    	        Sexo.MUJER, LocalDate.of(1990, 7, 25));
    	GestorDB.insertarEmpleado(empleado2);

    	Empleado empleado3 = new Empleado(
    	        "Aitor", "E003", "Ubieta", "López", "45678912Z", "Calle Ejemplo 4", 
    	        "48004", "Bilbao", "Vizcaya", "600654323", "jon.fernandez@example.com", 
    	        Sexo.HOMBRE, LocalDate.of(1985, 1, 17));
    	GestorDB.insertarEmpleado(empleado3);

    	Empleado empleado4 = new Empleado(
    	        "Maialen", "E004", "Blanco", "Martínez", "78945612X", "Calle Ejemplo 5", 
    	        "48005", "Bilbao", "Vizcaya", "600654324", "nerea.sanchez@example.com", 
    	        Sexo.MUJER, LocalDate.of(1992, 11, 8));
    	GestorDB.insertarEmpleado(empleado4);

    	Empleado empleado5 = new Empleado(
    	        "Roberto", "E005", "Carballedo", "Hernández", "14785236Y", "Calle Ejemplo 6", 
    	        "48006", "Bilbao", "Vizcaya", "600654325", "mikel.gomez@example.com", 
    	        Sexo.HOMBRE, LocalDate.of(1987, 5, 20));
    	GestorDB.insertarEmpleado(empleado5);

    	Cliente cliente1 = new Cliente("Elena", "Martínez", "López", "12345678Z", "Calle Ejemplo 1", 
    	        "48001", "Bilbao", "Vizcaya", "600123456", "elena.martinez@example.com", 
    	        Sexo.MUJER, LocalDate.of(1995, 5, 15), "Ingeniera", empleado1, 850.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente1);

    	Cliente cliente2 = new Cliente("Luis", "Gómez", "Fernández", "98765432Y", "Calle Ejemplo 7", 
    	        "48007", "Bilbao", "Vizcaya", "600987654", "luis.gomez@example.com", 
    	        Sexo.HOMBRE, LocalDate.of(1990, 3, 14), "Abogado", empleado2, 700.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente2);

    	Cliente cliente3 = new Cliente("Marta", "López", "Martínez", "65432198X", "Calle Ejemplo 8", 
    	        "48008", "Bilbao", "Vizcaya", "600654321", "marta.lopez@example.com", 
    	        Sexo.MUJER, LocalDate.of(1988, 9, 10), "Doctora", empleado3, 900.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente3);

    	Cliente cliente4 = new Cliente("Juan", "Sánchez", "Hernández", "32165498Y", "Calle Ejemplo 9", 
    	        "48009", "Bilbao", "Vizcaya", "600321654", "juan.sanchez@example.com", 
    	        Sexo.HOMBRE, LocalDate.of(1982, 2, 5), "Arquitecto", empleado4, 750.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente4);

    	Cliente cliente5 = new Cliente("Ana", "García", "Gómez", "85236941X", "Calle Ejemplo 10", 
    	        "48010", "Bilbao", "Vizcaya", "600852369", "ana.garcia@example.com", 
    	        Sexo.MUJER, LocalDate.of(1997, 7, 22), "Diseñadora", empleado2, 810.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente5);

    	Cliente cliente6 = new Cliente("Pablo", "Hernández", "Fernández", "96325874Z", "Calle Ejemplo 11", 
    	        "48011", "Bilbao", "Vizcaya", "600963258", "pablo.hernandez@example.com", 
    	        Sexo.HOMBRE, LocalDate.of(1989, 12, 12), "Programador", empleado1, 780.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente6);

    	Cliente cliente7 = new Cliente("Laura", "Martínez", "Sánchez", "14785263Y", "Calle Ejemplo 12", 
    	        "48012", "Bilbao", "Vizcaya", "600147852", "laura.martinez@example.com", 
    	        Sexo.MUJER, LocalDate.of(1994, 6, 18), "Psicóloga", empleado2, 920.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente7);

    	Cliente cliente8 = new Cliente("Carlos", "López", "García", "75395146X", "Calle Ejemplo 13", 
    	        "48013", "Bilbao", "Vizcaya", "600753951", "carlos.lopez@example.com", 
    	        Sexo.HOMBRE, LocalDate.of(1986, 11, 3), "Empresario", empleado3, 860.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente8);

    	Cliente cliente9 = new Cliente("Sara", "González", "López", "45612378Z", "Calle Ejemplo 14", 
    	        "48014", "Bilbao", "Vizcaya", "600456123", "sara.gonzalez@example.com", 
    	        Sexo.MUJER, LocalDate.of(1991, 8, 29), "Profesora", empleado2, 780.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente9);

    	Cliente cliente10 = new Cliente("Pedro", "Fernández", "Martínez", "95175384Y", "Calle Ejemplo 15", 
    	        "48015", "Bilbao", "Vizcaya", "600951753", "pedro.fernandez@example.com", 
    	        Sexo.HOMBRE, LocalDate.of(1983, 1, 15), "Contable", empleado5, 710.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente10);

    	Cliente cliente11 = new Cliente("Clara", "Hernández", "Gómez", "15975384X", "Calle Ejemplo 16", 
    	        "48016", "Bilbao", "Vizcaya", "600159753", "clara.hernandez@example.com", 
    	        Sexo.MUJER, LocalDate.of(1992, 4, 12), "Enfermera", empleado1, 900.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente11);

    	Cliente cliente12 = new Cliente("Roberto", "Sánchez", "Fernández", "75315984Z", "Calle Ejemplo 17", 
    	        "48017", "Bilbao", "Vizcaya", "600753159", "roberto.sanchez@example.com", 
    	        Sexo.HOMBRE, LocalDate.of(1987, 9, 5), "Ingeniero", empleado2, 830.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente12);

    	Cliente cliente13 = new Cliente("Sofía", "López", "Martínez", "12398765Y", "Calle Ejemplo 18", 
    	        "48018", "Bilbao", "Vizcaya", "600123987", "sofia.lopez@example.com", 
    	        Sexo.MUJER, LocalDate.of(1990, 5, 20), "Bióloga", empleado3, 880.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente13);

    	Cliente cliente14 = new Cliente("David", "García", "Sánchez", "78965412X", "Calle Ejemplo 19", 
    	        "48019", "Bilbao", "Vizcaya", "600789654", "david.garcia@example.com", 
    	        Sexo.HOMBRE, LocalDate.of(1984, 10, 7), "Piloto", empleado3, 870.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente14);

    	Cliente cliente15 = new Cliente("Irene", "Martínez", "González", "95148732Z", "Calle Ejemplo 20", 
    	        "48020", "Bilbao", "Vizcaya", "600951487", "irene.martinez@example.com", 
    	        Sexo.MUJER, LocalDate.of(1993, 2, 26), "Administrativa", empleado3, 760.0, "Sin observaciones", null);
    	GestorDB.insertarCliente(cliente15);

    	
        System.out.println("\n- Todos los datos han sido cargados en la base de datos.");
    }

    
    
  /*  public static void main(String[] args) {
    	DBInicializador BD = new DBInicializador();		
		
		//CREATE DATABASE: Se crea la BBDD
    	BD.borrarBBDD();
    	
    	System.out.println("................");
    	
    	
    	BD.crearBBDD();
    	
    	
    	System.out.println("................");
    	
    	BD.cargarDatos();
    	
    	
    }*/
}

