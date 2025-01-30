package gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


import domain.*;
import main.CombinacionesGUI;
import main.GestorDB;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VentanaAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	// Etiquetas de detalles del empleado
	private JLabel lblNombreValor;
	private JLabel lblIDValor;
	private JLabel lblApellido1Valor;
	private JLabel lblApellido2Valor;
	private JLabel lblDniValor;
	private JLabel lblDireccionValor;
	private JLabel lblTlfnValor;
	private JLabel lblEmailValor;
	private JLabel lblSexoValor;
	private JLabel lblFechaNacimientoValor;
	private JLabel lblNumeroClientes;
	private JLabel lblCpValor;
	private JLabel lblCiudadValor;
	private JLabel lblProvinciaValor;
	private Empleado empleadoActual = new Empleado(); // Empleado actualmente seleccionado
	private JTextField txtBusqueda; // Campo de texto para búsqueda de empleados
	private EmpleadoTableModel modeloEmpleados; // Modelo de tabla para empleados
	private JScrollPane scrollTablaClientes; // Desplazamiento de la tabla de clientes	
	private int selectedRow;
	private JList<Cliente> listaClientesTodos; 
	private JScrollPane scrollPaneTodos;
	private JList<Cliente> listaClientesEmpleado;
	private JScrollPane scrollPaneEmpleado;
	private JButton btnAdd = new JButton("Añadir >");
	private JButton btnRemove = new JButton("< Eliminar");
	private int segundos = 0; // Contador de segundos
	private boolean ejecutando = true; // Control del hilo

	// Constructor de la ventana de administración
	public VentanaAdmin(List<Cliente> clientes, List<Empleado> empleados) {

		// Configuración de la tabla de empleados
		modeloEmpleados = new EmpleadoTableModel(empleados);
		JTable tablaEmpleados = new JTable(modeloEmpleados);
		scrollTablaClientes = new JScrollPane(tablaEmpleados);
		tablaEmpleados.setRowHeight(40);
		tablaEmpleados.setSelectionBackground(Color.WHITE);
		tablaEmpleados.setSelectionForeground(Color.RED);

		// Panel de detalles del empleado seleccionado
		JPanel panelDetalles = new JPanel(new GridLayout(7, 2, 10, 10));
		panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del empleado"));

		// Inicialización de etiquetas de detalles
		lblNombreValor = new JLabel("--");
		lblIDValor = new JLabel("--");
		lblApellido1Valor = new JLabel("--");
		lblApellido2Valor = new JLabel("--");
		lblDniValor = new JLabel("--");
		lblDireccionValor = new JLabel("--");
		lblCpValor = new JLabel("--");         
		lblCiudadValor = new JLabel("--");     
		lblProvinciaValor = new JLabel("--");  
		lblTlfnValor = new JLabel("--");
		lblEmailValor = new JLabel("--");
		lblSexoValor = new JLabel("--");
		lblFechaNacimientoValor = new JLabel("--");
		lblNumeroClientes = new JLabel("--");

		// Configuración de colores en los valores de los JLabels
		lblNombreValor.setForeground(Color.GRAY);
		lblIDValor.setForeground(Color.GRAY);
		lblApellido1Valor.setForeground(Color.GRAY);
		lblApellido2Valor.setForeground(Color.GRAY);
		lblDniValor.setForeground(Color.GRAY);
		lblDireccionValor.setForeground(Color.GRAY);
		lblCpValor.setForeground(Color.GRAY);
		lblCiudadValor.setForeground(Color.GRAY);
		lblProvinciaValor.setForeground(Color.GRAY);
		lblTlfnValor.setForeground(Color.GRAY);
		lblEmailValor.setForeground(Color.GRAY);
		lblSexoValor.setForeground(Color.GRAY);
		lblFechaNacimientoValor.setForeground(Color.GRAY);
		lblNumeroClientes.setForeground(Color.GRAY);

		// Añadir etiquetas al panel de detalles
		panelDetalles.add(new JLabel("Nombre:"));
		panelDetalles.add(lblNombreValor);
		panelDetalles.add(new JLabel("Núm. identificación:"));
		panelDetalles.add(lblIDValor);
		panelDetalles.add(new JLabel("Primer Apellido:"));
		panelDetalles.add(lblApellido1Valor);
		panelDetalles.add(new JLabel("Segundo Apellido:"));
		panelDetalles.add(lblApellido2Valor);
		panelDetalles.add(new JLabel("DNI:"));
		panelDetalles.add(lblDniValor);
		panelDetalles.add(new JLabel("Dirección:"));
		panelDetalles.add(lblDireccionValor);
		panelDetalles.add(new JLabel("Código postal:"));
		panelDetalles.add(lblCpValor);
		panelDetalles.add(new JLabel("Ciudad:"));
		panelDetalles.add(lblCiudadValor);
		panelDetalles.add(new JLabel("Provincia:"));
		panelDetalles.add(lblProvinciaValor);
		panelDetalles.add(new JLabel("Teléfono:"));
		panelDetalles.add(lblTlfnValor);
		panelDetalles.add(new JLabel("Email:"));
		panelDetalles.add(lblEmailValor);
		panelDetalles.add(new JLabel("Sexo:"));
		panelDetalles.add(lblSexoValor);
		panelDetalles.add(new JLabel("Fecha de Nacimiento:"));
		panelDetalles.add(lblFechaNacimientoValor);
		panelDetalles.add(new JLabel("Numero de clientes:"));
		panelDetalles.add(lblNumeroClientes);

		// Configuración del botón de edición
		ImageIcon icono_e = new ImageIcon("img/editar.jpg");
		Image imagenEscalada_e = icono_e.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		JButton botonEditar = new JButton(new ImageIcon(imagenEscalada_e));
		botonEditar.setPreferredSize(new Dimension(100, 100));

		botonEditar.setBorderPainted(false);
		botonEditar.setFocusPainted(false);
		botonEditar.setContentAreaFilled(false);

		// Añadir ActionListener al botón de edición (comentado)
		botonEditar.addActionListener(e -> {
			new VentanaEditarEmpleado(empleadoActual,this,empleados,tablaEmpleados);
		});

		// Panel contenedor con imagen, detalles y botón de edición
		JPanel panelContenedor = new JPanel(new BorderLayout());
		panelContenedor.add(panelDetalles, BorderLayout.CENTER);
		panelContenedor.add(botonEditar, BorderLayout.EAST);

		// Panel derecho principal, que incluye panel de detalles e imagen
		JPanel panelDerechoPrincipal = new JPanel(new GridLayout(2,1,0,25));
		panelDerechoPrincipal.add(panelContenedor, BorderLayout.NORTH); 

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// TABLAS INTERCAMBIO DE CLIENTES
		// FUENTE-EXTERNA
		// URL: https://alud.deusto.es/mod/resource/view.php?id=800467
		// Adaptado para funcionar dentro de otra ventana, pero la idea ha sido tomada de este ejemplo en clase
		///////////////////////////////////////////////////	/////////////////////////////////////////////////////////////////////////////////////////////////////

		// Panel de tablas y su configuración
		JPanel paneltablas = new JPanel(new GridLayout(1, 3));
		panelDerechoPrincipal.add(paneltablas, BorderLayout.NORTH);
		panelDerechoPrincipal.setBackground(Color.white);

		// Crear el modelo para todos los clientes
		DefaultListModel<Cliente> clientesTotalModel = new DefaultListModel<>();
		Collections.sort(clientes);
		clientes.forEach(cliente -> clientesTotalModel.addElement(cliente));

		// Crear el modelo para clientes del empleado
		DefaultListModel<Cliente> clientesEmpleadoModel = new DefaultListModel<>();
		List<Cliente> clientesEmpleado = empleadoActual.getListaClientes();
		Collections.sort(clientesEmpleado);
		clientesEmpleado.forEach(cliente -> clientesEmpleadoModel.addElement(cliente));

		// Crear JList para todos los clientes y añadirlo al JScrollPane
		this.listaClientesTodos = new JList<>(clientesTotalModel);

		// Crear un panel para el JList y establecer el padding
		JPanel panelTodos = new JPanel(new BorderLayout());
		panelTodos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20)); // Padding de 10 píxeles en todos los lados
		panelTodos.add(this.listaClientesTodos, BorderLayout.CENTER);
		panelTodos.setBackground(Color.white);

		// Crear el JScrollPane y establecer la política para la barra de desplazamiento horizontal
		scrollPaneTodos = new JScrollPane(panelTodos);
		scrollPaneTodos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Oculta la barra de desplazamiento horizontal
		scrollPaneTodos.setBorder(BorderFactory.createTitledBorder("Todos los Clientes"));
		scrollPaneTodos.setBackground(Color.white);

		// Crear JList para clientes del empleado y añadirlo al JScrollPane
		this.listaClientesEmpleado = new JList<>(clientesEmpleadoModel);

		// Crear un panel para el JList y establecer el padding
		JPanel panelEmpleado = new JPanel(new BorderLayout());
		panelEmpleado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20)); // Padding de 10 píxeles en todos los lados
		panelEmpleado.add(this.listaClientesEmpleado, BorderLayout.CENTER);
		panelEmpleado.setBackground(Color.white);

		// Crear el JScrollPane y establecer la política para la barra de desplazamiento horizontal
		scrollPaneEmpleado = new JScrollPane(panelEmpleado);
		scrollPaneEmpleado.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Oculta la barra de desplazamiento horizontal
		scrollPaneEmpleado.setBorder(BorderFactory.createTitledBorder("Selecciona un empleado en el buscador"));
		scrollPaneEmpleado.setBackground(Color.white);

		/////////////////////////////////////////////////////////////////// 
		//IAG
		// El centrado de los botones ha sido solucionado por chatGPT
		///////////////////////////////////////////////////////////////////
		GridBagConstraints gbc = new GridBagConstraints();													

		// Configuración de GridBagConstraints para centrar los botones										

		// Panel Central para los botones de añadir y eliminar                                  
		JPanel panelBotones = new JPanel(new GridBagLayout());											  	

		// Configuración de GridBagConstraints para centrar los botones										
		gbc.gridx = 0; // Columna
		gbc.gridy = 0; // Fila
		gbc.anchor = GridBagConstraints.CENTER; // Centra los botones
		gbc.insets = new Insets(10, 10, 10, 10); // Espaciado alrededor de los botones
		panelBotones.add(btnAdd, gbc); // Añadir botón "Añadir"
		gbc.gridy = 1; // Mover a la siguiente fila
		panelBotones.add(btnRemove, gbc); // Añadir botón "Eliminar"
		panelBotones.setBackground(Color.white);

		// Configurar acciones de los botones
		btnAdd.addActionListener(e -> {
			Cliente clienteSeleccionado = listaClientesTodos.getSelectedValue();
			if (clienteSeleccionado != null && !clientesEmpleadoModel.contains(clienteSeleccionado) && tablaEmpleados.getSelectedRow() != -1) {
				GestorDB.asignarClienteAEmpleado(empleadoActual.getId(),clienteSeleccionado.getDni());
				lblNumeroClientes.setText(String.valueOf(GestorDB.getEmpleadoClienteMap().get(empleadoActual.getId()).size()));
				actualizarListaClientesEmpleado(clientes);
			}
		});

		btnRemove.addActionListener(e -> {
			Cliente clienteSeleccionado = listaClientesEmpleado.getSelectedValue();
			if (clienteSeleccionado != null && (clientesEmpleadoModel.size() > 1)) {
				GestorDB.eliminarClienteDeEmpleado(empleadoActual.getId(),clienteSeleccionado.getDni());
				lblNumeroClientes.setText(String.valueOf(GestorDB.getEmpleadoClienteMap().get(empleadoActual.getId()).size()));
				actualizarListaClientesEmpleado(clientes);
			}else {
				JOptionPane.showMessageDialog(null, "Cada empleado debe tener al menos un cliente a su cargo", "No se ha podido eliminar cliente de la lista", JOptionPane.ERROR_MESSAGE);

			}
		});

		// Configurar el layout de la ventana
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());

		paneltablas.add(scrollPaneTodos, BorderLayout.EAST);
		paneltablas.add(panelBotones, BorderLayout.CENTER);
		paneltablas.add(scrollPaneEmpleado, BorderLayout.WEST);

		// Panel de lista de clientes y buscador
		JPanel panelListaEmpleados = new JPanel(new BorderLayout());
		panelListaEmpleados.add(scrollTablaClientes);
		
		JButton bontonCombi = new JButton("Generar COMBI");
		JTextField txtMaxClientes = new JTextField("3", 5);
		
		bontonCombi.addActionListener(e -> {
		    try {
		        // Validate that an employee is selected
		        if (empleadoActual == null || empleadoActual.getId() == null) {
		            JOptionPane.showMessageDialog(null, 
		                "Por favor, seleccione un empleado primero",
		                "Error", 
		                JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        // Get max clients value
		        int maxClientes = Integer.parseInt(txtMaxClientes.getText());
		        
		        // Create combinations window
		        CombinacionesGUI ventanaCombinaciones = new CombinacionesGUI(clientes, maxClientes);
		        ventanaCombinaciones.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		        
		        // Add window listener to prevent closing without selection
		        ventanaCombinaciones.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		                int option = JOptionPane.showConfirmDialog(ventanaCombinaciones,
		                    "¿Desea cancelar la selección de combinación?",
		                    "Confirmar cancelación",
		                    JOptionPane.YES_NO_OPTION);
		                if (option == JOptionPane.YES_OPTION) {
		                    ventanaCombinaciones.dispose();
		                }
		            }
		        });
		        
		        // Show window and wait for result
		        ventanaCombinaciones.setVisible(true);
		        
		        // Get selected combination
		        List<Cliente> combinacionSeleccionada = ventanaCombinaciones.getCombinacionSeleccionada();
		        if (combinacionSeleccionada != null) {
		            // Clear existing clients for the employee
		            GestorDB.getEmpleadoClienteMap().get(empleadoActual.getId()).clear();
		            
		            // Add new clients from selected combination
		            for (Cliente cliente : combinacionSeleccionada) {
		                GestorDB.asignarClienteAEmpleado(empleadoActual.getId(), cliente.getDni());
		            }
		            
		            // Update the number of clients label
		            lblNumeroClientes.setText(String.valueOf(
		                GestorDB.getEmpleadoClienteMap().get(empleadoActual.getId()).size()
		            ));
		            
		            // Update client lists
		            actualizarListaClientesEmpleado(clientes);
		            
		            // Show success message
		            JOptionPane.showMessageDialog(null,
		                "Combinación asignada correctamente",
		                "Éxito",
		                JOptionPane.INFORMATION_MESSAGE);
		        }else {
		        	System.out.println("esta fuckn bacion");
		        }
		    } catch (NumberFormatException ex) {
		        JOptionPane.showMessageDialog(null,
		            "Por favor, ingrese un número válido para el máximo de clientes",
		            "Error",
		            JOptionPane.ERROR_MESSAGE);
		    }
		});
		
		JPanel panelCombi = new JPanel(new BorderLayout());
		
		panelCombi.add(bontonCombi,BorderLayout.SOUTH);
		panelCombi.add(txtMaxClientes,BorderLayout.NORTH);
		
		panelListaEmpleados.add(panelCombi, BorderLayout.SOUTH);


		ImageIcon icono_busqueda = new ImageIcon("img/buscar.jpg");
		Image imagenEscalada_busqueda = icono_busqueda.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		JLabel imagenBusqueda = new JLabel(new ImageIcon(imagenEscalada_busqueda));
		imagenBusqueda.setPreferredSize(new Dimension(17, 17));

		JPanel panelBuscador = new JPanel();
		txtBusqueda = new JTextField(10);
		txtBusqueda.setToolTipText("Buscar por DNI/Nombre");
		panelBuscador.add(imagenBusqueda);
		panelBuscador.add(txtBusqueda);

		panelListaEmpleados.add(panelBuscador, BorderLayout.NORTH);

		// Configuración de SplitPane para dividir entre el buscador y el panelDerecho
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelListaEmpleados, panelDerechoPrincipal);
		splitPane.setDividerLocation(250);


		
		panelListaEmpleados.setMinimumSize(new Dimension(200, 250));
		panelListaEmpleados.setSize(new Dimension(250, 250));
		add(splitPane, BorderLayout.CENTER);

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// FILTRO DE EMPLEADOS

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// Filtrar empleados en función de la entrada de búsqueda
		txtBusqueda.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtrarPorTitulo();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				filtrarPorTitulo();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				filtrarPorTitulo();
			}
		});

		// Escuchar cambios de selección en la tabla de empleados
		tablaEmpleados.getSelectionModel().addListSelectionListener(event -> {
			if (!event.getValueIsAdjusting() && tablaEmpleados.getSelectedRow() != -1) {
				selectedRow = tablaEmpleados.getSelectedRow();
				Empleado empleadoSeleccionado = modeloEmpleados.getEmpleadoAt(selectedRow);
				mostrarDetallesEmpleado(empleadoSeleccionado);
				actualizarListaClientesEmpleado(clientes);
			}
		});

		setLocationRelativeTo(null);
		setVisible(true);

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// CONFIGURACION FINAL DE LA VENTANA

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		setTitle("Ventana Admin"+ "- Tiempo en sesión: " + segundos + " segundos");
		setSize(1200, 700);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//IAG
		// A partir del ejemplo inicial se han modificado las acciones especificas para cada ventana

		// Configuración de Key Binding para la tecla ESC
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cerrarVentana");
		getRootPane().getActionMap().put("cerrarVentana", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); // Cerrar la ventana

				List<Cliente> clientes = GestorDB.getListaCLientes();
				List<Empleado> empleados = GestorDB.getListaEmpleados();

				// Crear y mostrar la ventana
				VentanaInicio ventana = new VentanaInicio(clientes,empleados);
				ventana.setVisible(true);
			}

		});

		////////////////////////////////////////////////////////////////////////////////////////////////////////
		//										HILO DE VENTANA EMPLEADO									  //
		////////////////////////////////////////////////////////////////////////////////////////////////////////

		// Crear y arrancar el hilo
		Thread contadorHilo = new Thread(() -> {
			while (ejecutando) {
				try {
					Thread.sleep(1000); // Esperar 1 segundo
					segundos++;
					// Actualizar el título en el hilo seguro para la GUI
					SwingUtilities.invokeLater(() -> {
						setTitle("Ventana Admin"+ "- Tiempo en sesión: " + segundos + " segundos");
					});
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					System.out.println("Hilo interrumpido"); 	
				}
			}
		});
		contadorHilo.start(); // Iniciar el hilo
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// METODOS DE LA CLASE

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// --> Método para eliminar el empleado actual
	public void eliminarEmpleado(List<Empleado> empleados, JTable tablaEmpleados) {
		empleados.remove(empleadoActual);
		modeloEmpleados = new EmpleadoTableModel(empleados);
		tablaEmpleados.setModel(modeloEmpleados);
	}

	// --> Método para mostrar detalles del empleado seleccionado
	private void mostrarDetallesEmpleado(Empleado empleado) {
		empleadoActual = empleado;
		lblNombreValor.setText(empleado.getNombre());
		lblIDValor.setText(empleado.getId());
		lblApellido1Valor.setText(empleado.getApellido1());
		lblApellido2Valor.setText(empleado.getApellido2());
		lblDniValor.setText(empleado.getDni());
		lblDireccionValor.setText(empleado.getDireccion());
		lblCpValor.setText(empleado.getCp());
		lblCiudadValor.setText(empleado.getCiudad());
		lblProvinciaValor.setText(empleado.getProvincia());
		lblTlfnValor.setText(empleado.getTlfn());
		lblEmailValor.setText(empleado.getEmail());
		lblSexoValor.setText(empleado.getSexo().toString());
		lblFechaNacimientoValor.setText(empleado.getFechaNacimiento().toString());
		lblNumeroClientes.setText(String.valueOf(GestorDB.getEmpleadoClienteMap().get(empleadoActual.getId()).size()));
	}

	// -->  Método para filtrar empleados en la tabla según el texto de búsqueda
	private void filtrarPorTitulo() {
		String filtro = txtBusqueda.getText();
		modeloEmpleados.filtrarPorTitulo(filtro);
	}

	private void actualizarListaClientesEmpleado(List<Cliente> clientes) {
		// Actualizar la lista del empleado
		DefaultListModel<Cliente> clientesEmpleadoModel = (DefaultListModel<Cliente>) listaClientesEmpleado.getModel();
		clientesEmpleadoModel.clear(); // Limpiar el modelo existente

		Map<String, ArrayList<String>> mapEmpleadoCliente = GestorDB.getEmpleadoClienteMap();
		System.out.println(mapEmpleadoCliente);

		for (Cliente cliente : clientes) {
			if(mapEmpleadoCliente.get(empleadoActual.getId()).contains(cliente.getDni())) {	
				clientesEmpleadoModel.addElement(cliente); // Agregar clientes del empleado seleccionado
			}
		}
		
		listaClientesEmpleado.setModel(clientesEmpleadoModel);
		
		// Quitar los clientes del empleado del general
		DefaultListModel<Cliente> clientestotalModel = new DefaultListModel<Cliente>();

		for (Cliente cliente : clientes) {
			if(!clientesEmpleadoModel.contains(cliente)) {
				clientestotalModel.addElement(cliente);
			}
		}
		listaClientesTodos.setModel(clientestotalModel);

		// Actualizar el título del JScrollPane 
		scrollPaneEmpleado.setBorder(BorderFactory.createTitledBorder("Clientes de " + empleadoActual.getNombre()));
	}
	
	/*private void limpiarDetallesEmpleado() {
		lblNombreValor.setText("--");
		lblIDValor.setText("--");
		lblApellido1Valor.setText("--");
		lblApellido2Valor.setText("--");
		lblDniValor.setText("--");
		lblDireccionValor.setText("--");
		lblCpValor.setText("--");
		lblCiudadValor.setText("--");
		lblProvinciaValor.setText("--");
		lblTlfnValor.setText("--");
		lblEmailValor.setText("--");
		lblSexoValor.setText("--");
		lblFechaNacimientoValor.setText("--");
		lblNumeroClientes.setText("--");
	}*/
}