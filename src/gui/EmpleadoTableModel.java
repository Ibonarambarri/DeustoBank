package gui;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import domain.Empleado;

// Modelo personalizado para la tabla que muestra el DNI y el nombre en una celda
class EmpleadoTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private List<Empleado> empleados;
	private List<Empleado> empleadosFiltrados;
	private String[] columnNames = { "empleados" }; // Cambiamos el nombre a "empleado" para incluir DNI y nombre

	public EmpleadoTableModel(List<Empleado> empleados) {
		this.empleados = empleados;
		this.empleadosFiltrados = new ArrayList<>(empleados); // Inicia con la lista completa
	}

	@Override
	public int getRowCount() {
		return empleadosFiltrados.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Empleado empleado = empleadosFiltrados.get(rowIndex);
		// Formato HTML: DNI en la primera línea, nombre en la segunda en gris
		return String.format("<html><b>%s</b><br><font color='gray'>%s %s</font></html>", 
				empleado.getDni(), empleado.getNombre(), empleado.getApellido1());
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	// Método para aplicar el filtro de búsqueda en la lista de empleados
	public void filtrarPorTitulo(String filtro) {
		if (filtro == null || filtro.isEmpty()) {
			empleadosFiltrados = new ArrayList<>(empleados); // Restablece la lista completa si no hay filtro
		} else {
			// Filtrar según coincidencia en nombre, primer apellido o DNI
			empleadosFiltrados = empleados.stream()
					.filter(c -> c.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
							c.getApellido1().toLowerCase().contains(filtro.toLowerCase()) ||
							c.getDni().toLowerCase().contains(filtro.toLowerCase()))
					.collect(Collectors.toList());
		}
		fireTableDataChanged(); // Notifica cambios en la tabla
	}

	// Método para obtener el empleado en una fila específica de la lista filtrada
	public Empleado getEmpleadoAt(int rowIndex) {
		return empleadosFiltrados.get(rowIndex); // Obtiene empleado según el índice filtrado
	}
	
 
}

