package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import domain.*;
import main.GestorDB;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


public class VentanaEmpleado extends JFrame {
	private static final long serialVersionUID = 1L;
	//----- Detalles del cliente -----
	private Cliente clienteActual = new Cliente();
	private Boolean miCliente;
	private Cuenta cuentaActual;
	@SuppressWarnings("unused")
	private String servicioActual;
	
	private JPanel pDetalles;
	
	private JPanel pNombreApellidos;
	private JPanel pNombre;
	private JPanel pApellido1;
	private JPanel pApellido2;
	private JLabel lblNombre;
    private JLabel lblApellido1;
    private JLabel lblApellido2;
	
    private JPanel pDniSexoNaci;
    private JPanel pDni;
    private JPanel pSexo;
    private JPanel pNaci;
    private JLabel lblDni;
    private JLabel lblSexo;
    private JLabel lblFechaNacimiento;
    
    private JPanel pEmailTlf;
    private JPanel pEmail;
    private JPanel pTlf;
    private JLabel lblEmail;
    private JLabel lblTlfn;
    
    private JPanel pDireccionTodo;
    private JPanel pDireccion;
    private JPanel pCiudad;
    private JPanel pCp;
    private JPanel pProvincia;
    private JLabel lblDireccion;
    private JLabel lblCiudad;
    private JLabel lblCp;
    private JLabel lblProvincia;
    
    private JPanel pProfesionPc;
    private JPanel pProfesion;
    private JPanel pPc;
    private JLabel lblProfesion;
    private JLabel lblPuntajeCrediticio;
    
    //----- Botones Editar cliente y contratar servicio -----
    private JPanel pEditarContratar;
    private JButton bEditarCliente;
    private JButton bContratarServicio;
    
    //----- Imagen del cliente -----
    private ImageIcon iconoCliente;
    private Image imagenEscaladaCliente;
    private JLabel imagenCliente;

    //----- Cuentas -----
    private JComboBox<Cuenta> comboBoxCuentas;
    private JTable tablaTransacciones;
    private DefaultTableModel modeloTablaTransacciones;
    
    //----- Servicios -----
    private List<Servicio> serviciosFiltrados = new ArrayList<>();
    private JComboBox<String> comboBoxServicios;
    private JTable tablaServicios;
    private DefaultTableModel modeloTablaServicios;
    
    //----- Parte DNI -----
    private ClienteTableModel modeloClientes;
    private JTextField txtBusqueda;
    private JScrollPane scrollTablaClientes;
    
    
    
    private Color colorbtn = new Color(237, 245, 255);
    private Color colorFondo = Color.white;
    
    
    private int segundos = 0; // Contador de segundos
    private boolean ejecutando = true; // Control del hilo
    
    
    

    public VentanaEmpleado(List<Cliente> clientes, Empleado empleado) {
        setTitle("Ventana Empleado - "+empleado.getNombre()+" "+empleado.getApellido1() + " - Tiempo en sesión: " + segundos + " segundos");
        setSize(1050, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //										CLIENTE														  //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        //---------------- Detalles del cliente ----------------
        miCliente = false;
        pDetalles = new JPanel(new GridLayout(5, 1, 5, 0)); //Panel que contiene todos los elementos de detalles del cliente
        pDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del cliente"));
        pDetalles.setBackground(colorFondo);
        
        //Nombre y Apellidos
        pNombreApellidos = new JPanel(new GridLayout(1, 6, 0, 5)); //Para cada Fila creamos un panel que contienen todos los elementos de la misma
        pNombreApellidos.setLayout(new BoxLayout(pNombreApellidos, BoxLayout.X_AXIS));
        pNombreApellidos.setBackground(colorFondo);
        
        pNombre = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5)); //Para cada pareja(Nombre del Dato, Valor del Dato) creamos un panel con un FlowLayout que nos permite que se ajusten automáticamente segun el tamaño del dato
        pNombre.setBorder(new LineBorder(Color.BLACK, 1));
        pNombre.setBackground(colorFondo);
        lblNombre = new JLabel("--"); lblNombre.setForeground(Color.GRAY);
        pNombre.add(new JLabel("Nombre:")).setFont(new Font("default", Font.BOLD, 12));
        pNombre.add(lblNombre);
	    
        pApellido1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pApellido1.setBorder(new LineBorder(Color.BLACK, 1));
        pApellido1.setBackground(colorFondo);
	    lblApellido1 = new JLabel("--"); lblApellido1.setForeground(Color.GRAY);
	    pApellido1.add(new JLabel("Primer Apellido:")).setFont(new Font("default", Font.BOLD, 12));
	    pApellido1.add(lblApellido1);
        
	    pApellido2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
	    pApellido2.setBorder(new LineBorder(Color.BLACK, 1));
        pApellido2.setBackground(colorFondo);
        lblApellido2 = new JLabel("--"); lblApellido2.setForeground(Color.GRAY);
        pApellido2.add(new JLabel("Segundo Apellido:")).setFont(new Font("default", Font.BOLD, 12));
        pApellido2.add(lblApellido2);
        
        pNombreApellidos.add(pNombre); pNombreApellidos.add(pApellido1); pNombreApellidos.add(pApellido2);
        
        pDetalles.add(pNombreApellidos);
        
        //DNI, Sexo y Fecha de nacimiento
        pDniSexoNaci = new JPanel();
        pDniSexoNaci.setLayout(new BoxLayout(pDniSexoNaci, BoxLayout.X_AXIS));
        pDniSexoNaci.setBackground(colorFondo);
        
        pDni = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pDni.setBorder(new LineBorder(Color.BLACK, 1));
        pDni.setBackground(colorFondo);
        lblDni = new JLabel("--"); lblDni.setForeground(Color.GRAY);
        pDni.add(new JLabel("DNI:")).setFont(new Font("default", Font.BOLD, 12));
        pDni.add(lblDni);
        
        pSexo = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pSexo.setBorder(new LineBorder(Color.BLACK, 1));
        pSexo.setBackground(colorFondo);
        lblSexo = new JLabel("--"); lblSexo.setForeground(Color.GRAY);
        pSexo.add(new JLabel("Sexo:")).setFont(new Font("default", Font.BOLD, 12));
        pSexo.add(lblSexo);
        
        pNaci = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pNaci.setBorder(new LineBorder(Color.BLACK, 1));
        pNaci.setBackground(colorFondo);
        lblFechaNacimiento = new JLabel("--"); lblFechaNacimiento.setForeground(Color.GRAY);
        pNaci.add(new JLabel("Fecha de Nacimiento:")).setFont(new Font("default", Font.BOLD, 12));
        pNaci.add(lblFechaNacimiento);
        
        pDniSexoNaci.add(pDni); pDniSexoNaci.add(pSexo); pDniSexoNaci.add(pNaci);
        
        pDetalles.add(pDniSexoNaci);
        
        //Email y Tlf
        pEmailTlf = new JPanel();
        pEmailTlf.setLayout(new BoxLayout(pEmailTlf, BoxLayout.X_AXIS));
        pEmailTlf.setBackground(colorFondo);

        pTlf = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pTlf.setBorder(new LineBorder(Color.BLACK, 1));
        pTlf.setBackground(colorFondo);
        lblTlfn = new JLabel("--"); lblTlfn.setForeground(Color.GRAY);
        pTlf.add(new JLabel("Teléfono:")).setFont(new Font("default", Font.BOLD, 12));
        pTlf.add(lblTlfn); 

        pEmail = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pEmail.setBorder(new LineBorder(Color.BLACK, 1));
        pEmail.setBackground(colorFondo);
        lblEmail = new JLabel("--"); lblEmail.setForeground(Color.GRAY);
        pEmail.add(new JLabel("Email:")).setFont(new Font("default", Font.BOLD, 12));
        pEmail.add(lblEmail); 

        pEmailTlf.add(pTlf); pEmailTlf.add(pEmail);

        pDetalles.add(pEmailTlf);

        //Todo sobre direccion
        pDireccionTodo = new JPanel();
        pDireccionTodo.setLayout(new BoxLayout(pDireccionTodo, BoxLayout.X_AXIS));
        pDireccionTodo.setBackground(colorFondo);
        
        pDireccion = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pDireccion.setBorder(new LineBorder(Color.BLACK, 1));
        pDireccion.setBackground(colorFondo);
        lblDireccion = new JLabel("--"); lblDireccion.setForeground(Color.GRAY);
        pDireccion.add(new JLabel("Dirección:")).setFont(new Font("default", Font.BOLD, 12));
        pDireccion.add(lblDireccion);
        
        pCp = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pCp.setBorder(new LineBorder(Color.BLACK, 1));
        pCp.setBackground(colorFondo);
        lblCp = new JLabel("--"); lblCp.setForeground(Color.GRAY);
        pCp.add(new JLabel("Código Postal:")).setFont(new Font("default", Font.BOLD, 12));
        pCp.add(lblCp);

        pCiudad = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pCiudad.setBorder(new LineBorder(Color.BLACK, 1));
        pCiudad.setBackground(colorFondo);
        lblCiudad = new JLabel("--"); lblCiudad.setForeground(Color.GRAY);
        pCiudad.add(new JLabel("Ciudad:")).setFont(new Font("default", Font.BOLD, 12));
        pCiudad.add(lblCiudad);
        
        pProvincia = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pProvincia.setBorder(new LineBorder(Color.BLACK, 1));
        pProvincia.setBackground(colorFondo);
        lblProvincia = new JLabel("--"); lblProvincia.setForeground(Color.GRAY);
        pProvincia.add(new JLabel("Provincia:")).setFont(new Font("default", Font.BOLD, 12));
        pProvincia.add(lblProvincia);
        
        pDireccionTodo.add(pDireccion); pDireccionTodo.add(pCp); pDireccionTodo.add(pCiudad); pDireccionTodo.add(pProvincia);
        
        pDetalles.add(pDireccionTodo);
        
        //Profesion y Puntaje crediticio
	    pProfesionPc = new JPanel();
	    pProfesionPc.setLayout(new BoxLayout(pProfesionPc, BoxLayout.X_AXIS));
	    pProfesionPc.setBackground(colorFondo);
	    
	    pProfesion = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
	    pProfesion.setBorder(new LineBorder(Color.BLACK, 1));
	    pProfesion.setBackground(colorFondo);
	    lblProfesion = new JLabel("--"); lblProfesion.setForeground(Color.GRAY);
	    pProfesion.add(new JLabel("Profesión:")).setFont(new Font("default", Font.BOLD, 12));
        pProfesion.add(lblProfesion);
        
        pPc = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pPc.setBorder(new LineBorder(Color.BLACK, 1));
        pPc.setBackground(colorFondo);
        lblPuntajeCrediticio = new JLabel("--"); lblPuntajeCrediticio.setForeground(Color.GRAY);
        pPc.add(new JLabel("Puntaje Crediticio:")).setFont(new Font("default", Font.BOLD, 12));
        pPc.add(lblPuntajeCrediticio);
        
        pProfesionPc.add(pProfesion); pProfesionPc.add(pPc);
        
        pDetalles.add(pProfesionPc);

        
        //---------------- Botones Editar cliente y contratar servicio ----------------
        pEditarContratar = new JPanel();
        pEditarContratar.setLayout(new BoxLayout(pEditarContratar, BoxLayout.X_AXIS));
        
        bEditarCliente = new JButton("Editar");
        bEditarCliente.setMaximumSize(new Dimension(Integer.MAX_VALUE, bEditarCliente.getPreferredSize().height));
        bEditarCliente.setBackground(colorbtn);
        bEditarCliente.addActionListener(e -> {
        	if(miCliente) {
        		new VentanaEditarCliente(clienteActual);
        	}
        });
        
        bContratarServicio = new JButton("Contratar Servicio");
        bContratarServicio.setMaximumSize(new Dimension(Integer.MAX_VALUE, bContratarServicio.getPreferredSize().height));
        bContratarServicio.setBackground(colorbtn);
        bContratarServicio.addActionListener(e -> {
        	if(miCliente) {
            	new VentanaEditarServicio(null);
        	}else {
        		//---------------------poner bonito
        		JOptionPane.showMessageDialog(null, "Primero debes seleccionar un cliente propio", "ERROR!", JOptionPane.ERROR_MESSAGE);
        		
        	}
        });
        
        pEditarContratar.add(bEditarCliente); 
        pEditarContratar.add(bContratarServicio);
        
        //---------------- Imagen del cliente ----------------
        iconoCliente = new ImageIcon("img/imagen_defecto.jpg");
        imagenEscaladaCliente = iconoCliente.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        imagenCliente = new JLabel(new ImageIcon(imagenEscaladaCliente));
        imagenCliente.setPreferredSize(new Dimension(250, 250));
        
        //---------------- Organizar Imagen, Detalles y Botones ----------------
        JPanel pImagenDetallesBotones = new JPanel(new BorderLayout());
        pImagenDetallesBotones.setBackground(colorFondo);
        
        JPanel pDetallesBotones = new JPanel(new BorderLayout());
        pDetallesBotones.setLayout(new BoxLayout(pDetallesBotones, BoxLayout.Y_AXIS));
        pDetallesBotones.add(pDetalles, BorderLayout.NORTH);
        pDetallesBotones.add(pEditarContratar, BorderLayout.SOUTH);
        
        pImagenDetallesBotones.add(imagenCliente, BorderLayout.WEST);
        pImagenDetallesBotones.add(pDetallesBotones, BorderLayout.CENTER);
        
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //										CUENTAS	Y SERVICIOS											  //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        //---------------- ComboBox Cuentas ----------------
        comboBoxCuentas = new JComboBox<>();
        comboBoxCuentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	cuentaActual = (Cuenta) comboBoxCuentas.getSelectedItem();
            }
        });
        
        //---------------- Tabla Cuentas ----------------
        modeloTablaTransacciones = new DefaultTableModel(new String[]{"Transacción", "Cantidad"}, 0) {

			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaTransacciones = new JTable(modeloTablaTransacciones);
        tablaTransacciones.setCellSelectionEnabled(false);
        tablaTransacciones.setRowSelectionAllowed(false);
        tablaTransacciones.setColumnSelectionAllowed(false);
        
        //IAG
        tablaTransacciones.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 5916547316623832133L;

			@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                Color darkGreen = new Color(0, 128, 0);

                if (value != null && value instanceof String) {
                    String cantidadStr = (String) value;
                    if (cantidadStr.startsWith("+")) {
                        cell.setForeground(darkGreen);
                    } else if (cantidadStr.startsWith("-")) {
                        cell.setForeground(Color.RED);
                    } else {
                        cell.setForeground(Color.BLACK);
                    }
                }
                return cell;
            }
        });
        
        tablaTransacciones.getColumnModel().getColumn(0).setPreferredWidth(200);
        tablaTransacciones.setRowHeight(40);
        
        //---------------- ComboBox Servicios ----------------
        comboBoxServicios = new JComboBox<>(new String[]{"Cuentas", "Inversiones", "Seguros", "Prestamos", "Tarjetas"});
        comboBoxServicios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	servicioActual = (String) comboBoxServicios.getSelectedItem();
            }
        });
        
        //---------------- Tabla Servicios ----------------
        modeloTablaServicios = new DefaultTableModel(new String[]{"Servicio", "Fecha Contratación", "Activo", "Editar"}, 0) {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
            	return column == 3;
            }
        };
        
        tablaServicios = new JTable(modeloTablaServicios);
        tablaServicios.setCellSelectionEnabled(false);
        tablaServicios.setRowSelectionAllowed(true);
        tablaServicios.setColumnSelectionAllowed(false);
        
        //IAG
        tablaServicios.getColumnModel().getColumn(3).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JButton editButton = new JButton("Editar");
                editButton.setBackground(colorbtn);
                return editButton;
            }
        });

        tablaServicios.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JCheckBox()) {
			private static final long serialVersionUID = 1L;
			private JButton button = new JButton("Editar");{
				
                button.addActionListener(new ActionListener() {
                    @Override
                   public void actionPerformed(ActionEvent e) {
                      int selectedRow = tablaServicios.getSelectedRow();
                      if(miCliente) {
                  		selectedRow = tablaServicios.getSelectedRow();
                          if (selectedRow != -1) {
                              Servicio servicio = serviciosFiltrados.get(selectedRow);
                          	  String s = servicio.getClass().getName();
                              int pos = s.lastIndexOf(".");
                              if(pos!=-1) {
                              	String ser = s.substring(pos+1).toLowerCase();
                              	new VentanaEditarServicio(ser);
                           }else {
                          	 new VentanaEditarServicio(null);
                          	 }
                           fireEditingStopped();
                          }
                  	}
                    }
                    
                });
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return button;
            }
            @Override
            public Object getCellEditorValue() {
                return null;
            }
        });
        
        tablaServicios.getColumnModel().getColumn(0).setPreferredWidth(200);
        tablaServicios.setRowHeight(40);
        
        //---------------- Organizar Cuentas y Servicios ----------------
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.add(comboBoxCuentas, BorderLayout.NORTH);
        panelIzquierdo.add(new JScrollPane(tablaTransacciones), BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.add(comboBoxServicios, BorderLayout.NORTH);
        panelDerecho.add(new JScrollPane(tablaServicios), BorderLayout.CENTER);
        
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //										LISTADO DNIS												  //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        //---------------- Lista de clientes ----------------
        modeloClientes = new ClienteTableModel(clientes,empleado.getListaClientes());
            
        JTable tablaClientes = new JTable(modeloClientes);
        scrollTablaClientes = new JScrollPane(tablaClientes);
     // Ocultar los deslizadores
        scrollTablaClientes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTablaClientes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        
        tablaClientes.setRowHeight(40);
        tablaClientes.setSelectionBackground(Color.WHITE);
        tablaClientes.setSelectionForeground(Color.RED);
        
        tablaClientes.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tablaClientes.getSelectedRow() != -1) {
                int selectedRow = tablaClientes.getSelectedRow();
                Cliente clienteSeleccionado = modeloClientes.getClienteAt(selectedRow);
                ActualizarDetallesCliente(clienteSeleccionado);
                if(empleado.getListaClientes().contains(clienteSeleccionado)) {
                	miCliente = true;
                }else {
                	miCliente = false;
                }
            }
        });

        //---------------- Buscar ----------------
        ImageIcon icono_busqueda = new ImageIcon("img/buscar.jpg");
        Image imagenEscalada_busqueda = icono_busqueda.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        JLabel imagenBusqueda = new JLabel(new ImageIcon(imagenEscalada_busqueda));
        imagenBusqueda.setPreferredSize(new Dimension(17, 17));
        imagenBusqueda.setToolTipText("Busqua por nombre o DNI");
        
        txtBusqueda = new JTextField(10);
        txtBusqueda.setToolTipText("Buscar por DNI/Nombre");
        
        txtBusqueda.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {filtrarPorTitulo();}

            @Override
            public void removeUpdate(DocumentEvent e) {filtrarPorTitulo();}

            @Override
            public void changedUpdate(DocumentEvent e) {filtrarPorTitulo();}
        });
        
        JPanel panelBuscador = new JPanel();
        panelBuscador.add(imagenBusqueda);
        panelBuscador.add(txtBusqueda);

        //---------------- Añadir Cliente ----------------
        JPanel panelAniadirCliente = new JPanel();
        JButton botonAniadirCliente = new JButton("Nuevo cliente");
        botonAniadirCliente.setBackground(colorbtn);
        botonAniadirCliente.addActionListener(e -> {
            VentanaEditarCliente ventanaEditarCliente = new VentanaEditarCliente(new Cliente());
            ventanaEditarCliente.setVisible(true);
        });
        
        panelAniadirCliente.add(botonAniadirCliente);
        
        //---------------- Organizar Buscar, Clientes y Boton para añadir CLientes ----------------
        JPanel panelListaClientes = new JPanel(new BorderLayout());
        panelListaClientes.add(scrollTablaClientes);
        panelListaClientes.add(panelAniadirCliente, BorderLayout.SOUTH);
        panelListaClientes.add(panelBuscador, BorderLayout.NORTH);
        
        
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		//										ESC                          								  //
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
        //IAG
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cerrarVentana");
		getRootPane().getActionMap().put("cerrarVentana", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Cliente> clientes = GestorDB.getListaCLientes();
				List<Empleado> empleados = GestorDB.getListaEmpleados();
				@SuppressWarnings("unused")
				VentanaInicio ventana = new VentanaInicio(clientes,empleados);
				dispose();
			}
		});


		////////////////////////////////////////////////////////////////////////////////////////////////////////
		//										ORGANIZAR COMPLETAMENTE										  //
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        JPanel panelTablas = new JPanel(new GridLayout(1, 2));

        panelTablas.add(panelIzquierdo);
        panelTablas.add(panelDerecho);

        JPanel panelDerechoPrincipal = new JPanel(new BorderLayout());
        panelDerechoPrincipal.add(pImagenDetallesBotones, BorderLayout.NORTH);
        panelDerechoPrincipal.add(panelTablas, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelListaClientes, panelDerechoPrincipal);
        splitPane.setDividerLocation(150);

        panelListaClientes.setMinimumSize(new Dimension(150,200));
        add(splitPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
        
        
        
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
                        setTitle("Ventana Empleado - "+empleado.getNombre()+" "+empleado.getApellido1() + " - Tiempo en sesión: " + segundos + " segundos");
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Hilo interrumpido");
                }
            }
        });

        contadorHilo.start(); // Iniciar el hilo
    }
    
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	//										METODOS														  //
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	    
    private void ActualizarDetallesCliente(Cliente cliente) {
        clienteActual = cliente;
        

        lblNombre.setText(cliente.getNombre());
        lblApellido1.setText(cliente.getApellido1());
        lblApellido2.setText(cliente.getApellido2());
        lblDni.setText(cliente.getDni());
        lblDireccion.setText(cliente.getDireccion());
        lblCp.setText(cliente.getCp());
        lblCiudad.setText(cliente.getCiudad());
        lblProvincia.setText(cliente.getProvincia());
        lblTlfn.setText(cliente.getTlfn());
        lblEmail.setText(cliente.getEmail());
        lblSexo.setText(cliente.getSexo().toString());
        lblFechaNacimiento.setText(cliente.getFechaNacimiento().toString());
        lblProfesion.setText(cliente.getProfesion());
        lblPuntajeCrediticio.setText(Double.toString(cliente.getPuntajeCrediticio()));
        
        comboBoxCuentas.removeAllItems();
        modeloTablaTransacciones.setRowCount(0);
        modeloTablaServicios.setRowCount(0);
        
        
        
   

        for (Servicio servicio : cliente.getListaServicios()) {
            if (servicio instanceof Cuenta) {
            	Cuenta cuenta = (Cuenta) servicio;
                comboBoxCuentas.addItem(cuenta);
            }
        }
        if (cuentaActual != null) {
        	actualizarTablaTransacciones(cuentaActual);
        }

        String servicioSeleccionado = (String) comboBoxServicios.getSelectedItem();
        if (servicioSeleccionado != null) {
            actualizarTablaServicios(servicioSeleccionado, cliente.getListaServicios());
        }
    }
    
    private void actualizarTablaTransacciones(final Cuenta cuenta) {
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	modeloTablaTransacciones.setRowCount(0);
                for (Transaccion transaccion : cuenta.getListaTransacciones()) {
                    String nombre = transaccion.getNombre();
                    int cantidad = transaccion.getCantidad();
                    String cantidadFormateada = (cantidad >= 0 ? "+" : "-") + Math.abs(cantidad);
                    modeloTablaTransacciones.addRow(new Object[]{nombre, cantidadFormateada});
                }
            }
    	});
    }

    private void actualizarTablaServicios(final String selected,final List<Servicio> serviciosCliente) {
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
		        modeloTablaServicios.setRowCount(0);
		        serviciosFiltrados.clear();
		
		        for (Servicio servicio : serviciosCliente) {
		            if (selected.equals("Cuentas") && servicio instanceof Cuenta) {
		                Cuenta cuenta = (Cuenta) servicio;
		                modeloTablaServicios.addRow(new Object[]{createHtmlCellContent(cuenta.getClass().getSimpleName(), cuenta.getIban()), cuenta.getFechaContratacion(), cuenta.isActive(), "Editar"});
		                serviciosFiltrados.add(cuenta);
		            } else if (selected.equals("Inversiones") && servicio instanceof Inversion) {
		                Inversion inversion = (Inversion) servicio;
		                modeloTablaServicios.addRow(new Object[]{createHtmlCellContent(inversion.getTipo().toString(), inversion.getNumeroCuenta()), inversion.getFechaContratacion(), inversion.isActive(), "Editar"});
		                serviciosFiltrados.add(inversion);
		            } else if (selected.equals("Seguros") && servicio instanceof Seguro) {
		                Seguro seguro = (Seguro) servicio;
		                modeloTablaServicios.addRow(new Object[]{createHtmlCellContent(seguro.getClass().getSimpleName(), seguro.getNumPoliza()), seguro.getFechaContratacion(), seguro.isActive(), "Editar"});
		                serviciosFiltrados.add(seguro);
		            } else if (selected.equals("Tarjetas") && servicio instanceof Tarjeta) {
		                Tarjeta tarjeta = (Tarjeta) servicio;
		                modeloTablaServicios.addRow(new Object[]{createHtmlCellContent(tarjeta.getClass().getSimpleName(), tarjeta.getNumero()), tarjeta.getFechaContratacion(), tarjeta.isActive(), "Editar"});
		                serviciosFiltrados.add(tarjeta);
		            }
		        }
            }
    	});
    }

    private static String createHtmlCellContent(String campo1, String campo2) {
        return String.format("<html><b>%s</b><br><font size='2' color='gray'>%s</font></html>", campo1, campo2);
    }
    
    private void filtrarPorTitulo() {
        String filtro = txtBusqueda.getText();
        modeloClientes.filtrarPorTitulo(filtro);
    }

    
}
