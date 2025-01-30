package main;


import java.util.*;
import domain.*;
import gui.*;

public class Main {

	public static void main(String[] args) {
		

		DBInicializador BD = new DBInicializador();

		BD.crearBBDD();


		System.out.println("................");

		BD.cargarDatos();

		List<Cliente> clientes = GestorDB.getListaCLientes();
		List<Empleado> empleados = GestorDB.getListaEmpleados();

//		HashMap<String, ArrayList<String>> map = GestorDB.getEmpleadoClienteMap();
//		System.out.println(map);


		// Crear y mostrar la ventana
		VentanaInicio ventana = new VentanaInicio(clientes,empleados);
		ventana.setVisible(true);
	}
}
