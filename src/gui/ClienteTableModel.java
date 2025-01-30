package gui;

import javax.swing.table.AbstractTableModel;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import domain.Cliente;



////////////////////////////////////////////////////////////////////////////////////////////////////////
//										IAG															  //
////////////////////////////////////////////////////////////////////////////////////////////////////////

class ClienteTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Cliente> clientes;
    private List<Cliente> clientesFiltrados;
    private List<Cliente> clientesS;
    private String[] columnNames = { "Clientes" }; // Cambiamos el nombre a "Cliente" para incluir DNI y nombre

    public ClienteTableModel(List<Cliente> clientes,List<Cliente> clientesS) {
        this.clientes = clientes;
        this.clientesFiltrados = new ArrayList<>(clientes); // Inicia con la lista completa
        this.clientesS = clientesS;
    }

    @Override
    public int getRowCount() {
        return clientesFiltrados.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = clientesFiltrados.get(rowIndex);
        if (clientesS.contains(cliente)) {
        	// Formato HTML: DNI en la primera línea, nombre en la segunda en gris
            return String.format("<html><b>%s</b><br><font color='gray'>%s %s</font></html>", 
                                 cliente.getDni(), cliente.getNombre(), cliente.getApellido1());
        }else {
        	return String.format("<html><b color='gray' >%s</b><br><font color='gray'>%s %s</font></html>", 
                    cliente.getDni(), cliente.getNombre(), cliente.getApellido1());
        }
        
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // Método para aplicar el filtro de búsqueda en la lista de clientes
    public void filtrarPorTitulo(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            clientesFiltrados = new ArrayList<>(clientes); // Restablece la lista completa si no hay filtro
        } else {
            // Filtrar según coincidencia en nombre, primer apellido o DNI
            clientesFiltrados = clientes.stream()
                    .filter(c -> c.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
                                 c.getApellido1().toLowerCase().contains(filtro.toLowerCase()) ||
                                 c.getDni().toLowerCase().contains(filtro.toLowerCase()))
                    .collect(Collectors.toList());
        }
        fireTableDataChanged(); // Notifica cambios en la tabla
    }

    // Método para obtener el cliente en una fila específica de la lista filtrada
    public Cliente getClienteAt(int rowIndex) {
        return clientesFiltrados.get(rowIndex); // Obtiene cliente según el índice filtrado
    }
}

// Renderer personalizado para interpretar HTML en las celdas
class HtmlTableCellRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	public HtmlTableCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value != null ? value.toString() : "");
        setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
        return this;
    }
}
