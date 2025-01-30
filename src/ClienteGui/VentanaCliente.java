package ClienteGui;

import com.toedter.calendar.JDateChooser;

import domain.Cliente;
import domain.Cuenta;
import domain.Empleado;
import domain.Inversion;
import domain.Seguro;
import domain.Servicio;
import domain.Sexo;
import domain.Tarjeta;
import domain.TarjetaCredito;
import domain.TarjetaDebito;
import gui.VentanaInicio;
import main.GestorDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentanaCliente extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable tablaServicios;
    private JTable tablaCuentas;
    private JTable tablaTarjetas;
    private DefaultTableModel modeloTablaServicios;
    private DefaultTableModel modeloTablaCuentas;
    private DefaultTableModel modeloTablaTarjetas;
    private JComboBox<String> comboBoxServicios;
    private JPanel panelCuentas;
    private JPanel panelTarjetas;
    private JPanel panelPrincipal;
    private JPanel panelTransferencias;
    private JPanel panelPerfil;
    private JPanel panelContenido;

    private ImageIcon logoTransf;
    private Image logoTransfEsc;

    private JLabel lblLogoTransf;
    private JButton bLogout;
    
    private ImageIcon fotoPerfil;
    private Image fotoPerfilEsc;
    private JLabel lblFotoPerfil;
    
    private Cliente cliente;
    
    
    private int segundos = 0; // Contador de segundos
    private boolean ejecutando = true; // Control del hilo

    public VentanaCliente(Cliente cliente) {
    	this.cliente = cliente;
        setTitle(cliente.getNombre()+" "+cliente.getApellido1() + " - Tiempo en sesión: " + segundos + " segundos");
        setSize(500,700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panelContenido = new JPanel(new CardLayout());

        panelPrincipal = crearPanelInicio();
        panelTransferencias = crearPanelTransferencias();
        panelPerfil = crearPanelPerfil();

        panelContenido.add(panelPrincipal, "Inicio");
        panelContenido.add(panelTransferencias, "Transferencias");
        panelContenido.add(panelPerfil, "Perfil");

        JPanel panelMenuInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton botonInicio = new JButton("Inicio");
        JButton botonTransferencias = new JButton("Transferencias");
        JButton botonPerfil = new JButton("Perfil");

        botonInicio.addActionListener(e -> mostrarPanel("Inicio"));
        botonTransferencias.addActionListener(e -> mostrarPanel("Transferencias"));
        botonPerfil.addActionListener(e -> mostrarPanel("Perfil"));

        panelMenuInferior.add(botonInicio);
        panelMenuInferior.add(botonTransferencias);
        panelMenuInferior.add(botonPerfil);

        add(panelContenido, BorderLayout.CENTER);
        add(panelMenuInferior, BorderLayout.SOUTH);

        mostrarPanel("Inicio");
        setVisible(true);
        
        // -----------------------------------LISTENER ESC------------------------------------------------

        
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cerrarVentana");
		getRootPane().getActionMap().put("cerrarVentana", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				//List<Cliente> clientes = DataLoaderDemo.cargarClientes();
		        //List<Empleado> empleados = DataLoaderDemo.cargarEmpleados(clientes);
		        List<Cliente> clientes = GestorDB.getListaCLientes();
		        List<Empleado> empleados = GestorDB.getListaEmpleados();
		        
				@SuppressWarnings("unused")
				VentanaInicio ventana = new VentanaInicio(clientes,empleados);
				dispose();
			}
		});
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		//										HILO DE VENTANA CLIENTE									  //
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
        // Crear y arrancar el hilo
        Thread contadorHilo = new Thread(() -> {
            while (ejecutando) {
                try {
                    Thread.sleep(1000); // Esperar 1 segundo
                    segundos++;
                    // Actualizar el título en el hilo seguro para la GUI
                    SwingUtilities.invokeLater(() -> {
                        setTitle(cliente.getNombre()+" "+cliente.getApellido1() + " - Tiempo en sesión: " + segundos + " segundos");
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Hilo interrumpido"); 	
                }
            }
        });

        contadorHilo.start(); // Iniciar el hilo
    }
        
        

    
    // -----------------------------------PANEL DE INICIO------------------------------------------------
    private JPanel crearPanelInicio() {
        JPanel panelInicio = new JPanel();
        panelInicio.setLayout(new BoxLayout(panelInicio, BoxLayout.Y_AXIS));
        
        //-------------------------Servicios en general------------------------------
        comboBoxServicios = new JComboBox<>(new String[]{"Inversiones", "Seguros", "Prestamos"});
        comboBoxServicios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String servicioSeleccionado = (String) comboBoxServicios.getSelectedItem();
                if (servicioSeleccionado != null) {
                    actualizarTablaServicios(servicioSeleccionado, cliente.getListaServicios());
                }
            }
        });

        JPanel panelSelectorServicios = new JPanel(new BorderLayout());
        panelSelectorServicios.add(comboBoxServicios);

        modeloTablaServicios = new DefaultTableModel(new String[]{"Servicio", "Fecha Contratación", "Activo"}, 0) {
        	 private static final long serialVersionUID = 1L;

			@Override
             public boolean isCellEditable(int row, int column) {
                 return false; // Todas las celdas no serán editables
             }
        };
        
        
        tablaServicios = new JTable(modeloTablaServicios);
        tablaServicios.setRowHeight(40);
        JScrollPane scrollServicios = new JScrollPane(tablaServicios);
        scrollServicios.setBorder(BorderFactory.createTitledBorder("Servicios Seleccionados"));
        
        
      //-------------------------Cuentas------------------------------
        panelCuentas = new JPanel(new BorderLayout());
        panelCuentas.setBorder(BorderFactory.createTitledBorder("Cuentas"));

        // Modelo de tabla con celdas no editables
        modeloTablaCuentas = new DefaultTableModel(new String[]{"Cuenta", "Saldo"}, 0) {
            private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no serán editables
            }
        };

        // Configurar la tabla con el modelo personalizado
        tablaCuentas = new JTable(modeloTablaCuentas);
        tablaCuentas.setRowHeight(40);

        // Agregar la tabla al panel con un JScrollPane
        JScrollPane scrollCuentas = new JScrollPane(tablaCuentas);
        panelCuentas.add(scrollCuentas, BorderLayout.CENTER);

        
        
      //-------------------------Tarjetas------------------------------
        panelTarjetas = new JPanel(new BorderLayout());
        panelTarjetas.setBorder(BorderFactory.createTitledBorder("Tarjetas"));

        // Modelo de tabla con celdas no editables
        modeloTablaTarjetas = new DefaultTableModel(new String[]{"Tarjeta", "Límite"}, 0) {
            private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no serán editables
            }
        };

        // Configurar la tabla con el modelo personalizado
        tablaTarjetas = new JTable(modeloTablaTarjetas);
        tablaTarjetas.setRowHeight(40);

        // Agregar la tabla al panel con un JScrollPane
        JScrollPane scrollTarjetas = new JScrollPane(tablaTarjetas);
        panelTarjetas.add(scrollTarjetas, BorderLayout.CENTER);

     
        
        //-------------------------Armado del panel------------------------------
        
        actualizarTablaServicios("Inversiones", cliente.getListaServicios());
        actualizarTablaCuentas("Cuentas", cliente.getListaServicios());
        actualizarTablaTarjetas("Tarjetas", cliente.getListaServicios());
        
        panelInicio.add(panelCuentas);
        panelInicio.add(Box.createRigidArea(new Dimension(0, 10)));
        panelInicio.add(panelTarjetas);
        panelInicio.add(Box.createRigidArea(new Dimension(0, 10)));
        panelInicio.add(panelSelectorServicios);
        panelInicio.add(scrollServicios);
        
        return panelInicio;
    }

    // -----------------------------------PANEL DE TRANSFERENCIAS------------------------------------------------
    private JPanel crearPanelTransferencias() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Transferencias"));

        logoTransf = new ImageIcon("img/t_bancos.png");
        logoTransfEsc = logoTransf.getImage().getScaledInstance(90, 90, Image.SCALE_AREA_AVERAGING);
        lblLogoTransf = new JLabel(new ImageIcon(logoTransfEsc));
        lblLogoTransf.setPreferredSize(new Dimension(90, 90));

        panel.add(lblLogoTransf, BorderLayout.NORTH);

        JLabel labelTransferencia = new JLabel("Ingrese los detalles de la transferencia:");
        panel.add(labelTransferencia, BorderLayout.CENTER);

        JPanel panelFormulario = new JPanel(new GridLayout(13, 1, 5, 5));

        // ComboBox para cuenta origen
        panelFormulario.add(new JLabel("Cuenta origen:"));
        JComboBox<String> comboBoxOrigen = new JComboBox<>(new String[]{"Cuenta Corriente", "Cuenta Ahorros"});
        panelFormulario.add(comboBoxOrigen);

        // Checkbox y opciones para cuenta destino
        JCheckBox checkBoxDestinoPropio = new JCheckBox("Una de mis cuentas");
        JCheckBox checkBoxDestinoOtro = new JCheckBox("Otra cuenta");
        ButtonGroup groupDestino = new ButtonGroup();
        groupDestino.add(checkBoxDestinoPropio);
        groupDestino.add(checkBoxDestinoOtro);

        JPanel panelDestino = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDestino.add(checkBoxDestinoPropio);
        panelDestino.add(checkBoxDestinoOtro);

        panelFormulario.add(new JLabel("Cuenta destino:"));
        panelFormulario.add(panelDestino);

        // Panel de opciones de destino: comboBoxDestino y textFieldDestino
        JComboBox<String> comboBoxDestino = new JComboBox<>(new String[]{"Cuenta Corriente", "Cuenta Ahorros"});
        JTextField textFieldDestino = new JTextField("Número de cuenta");
        textFieldDestino.setForeground(Color.GRAY);
        
        textFieldDestino.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textFieldDestino.getText().equals("Número de cuenta")) {
                	textFieldDestino.setText(""); // Elimina el texto por defecto
                	textFieldDestino.setForeground(Color.BLACK); // Cambia el color a negro al escribir
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textFieldDestino.getText().isEmpty()) {
                	textFieldDestino.setText("Número de cuenta"); // Restaura el texto por defecto si está vacío
                	textFieldDestino.setForeground(Color.GRAY); // Cambia el color a gris para el texto por defecto
                }
            }
        });

        // Inicialmente ocultar el comboBoxDestino y el textFieldDestino
        comboBoxDestino.setVisible(false);
        textFieldDestino.setVisible(false);

        panelFormulario.add(comboBoxDestino);
        panelFormulario.add(textFieldDestino);

        checkBoxDestinoPropio.addActionListener(e -> {
            comboBoxDestino.setVisible(true);        // Mostrar comboBox
            textFieldDestino.setVisible(false);      // Ocultar textField
            panelFormulario.revalidate();
            panelFormulario.repaint();
        });

        checkBoxDestinoOtro.addActionListener(e -> {
            comboBoxDestino.setVisible(false);       // Ocultar comboBox
            textFieldDestino.setVisible(true);       // Mostrar textField
            panelFormulario.revalidate();
            panelFormulario.repaint();
        });

        // Monto con JSpinner
        panelFormulario.add(new JLabel("Monto:"));
        JSpinner spinnerMonto = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100000.0, 1.0));
        spinnerMonto.setEditor(new JSpinner.NumberEditor(spinnerMonto, "#,##0.00"));
        panelFormulario.add(spinnerMonto);
        
       

        // Fecha con JDateChooser
        panelFormulario.add(new JLabel("Fecha:"));
        JDateChooser calendarFecha = new JDateChooser();
        panelFormulario.add(calendarFecha);

        JButton botonRealizarTransferencia = new JButton("Realizar Transferencia");
        panel.add(panelFormulario, BorderLayout.CENTER);
        panel.add(botonRealizarTransferencia, BorderLayout.SOUTH);

        return panel;
    }
    
    
    

    // -----------------------------------PANEL DE PERFIL------------------------------------------------
    /**
     * Botones a añadir:
     * bConsultarPuntCred
     * bEditarDatos
     * bEditarFotoPerfil
     * bCambiarContra
     * 
     * */
    
    private JPanel crearPanelPerfil() {
        JPanel panel = new JPanel(new BorderLayout());
        

        //Declaramos la imagen que irá en el panel superior
	    fotoPerfil = new ImageIcon("img/imagen_defecto.jpg");
	    fotoPerfilEsc = fotoPerfil.getImage().getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING); //Escalar la imagen
	    lblFotoPerfil = new JLabel(new ImageIcon(fotoPerfilEsc));
	    lblFotoPerfil.setPreferredSize(new Dimension(90, 90));		//Escalar el JLabel donde irá la imagen
	    
	    
	    
	    //Boton para cambiar la foto de perfil
	    JButton bFotoPerfil = new JButton("Cambiar foto de perfil");
	    //bFotoPerfil.addActionListener(e -> new JOptionPane().showInputDialog("Introduce la url de la nueva foto"));
	    //NO SE SI SE PODRÁ HACER ASÍ
	    
	    JPanel pFoto = new JPanel(new BorderLayout());
	    pFoto.add(lblFotoPerfil, BorderLayout.CENTER);
	    pFoto.add(bFotoPerfil, BorderLayout.SOUTH);
	    
        panel.add(pFoto, BorderLayout.NORTH);
        
        
        JPanel panelDetalles = new JPanel(new GridLayout(11, 1, 0, 0));
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del usuario"));
        panelDetalles.add(new JLabel("Nombre:"));
        panelDetalles.add(new JTextField("Juan"));
        panelDetalles.add(new JLabel("Apellido:"));
        panelDetalles.add(new JTextField("Pérez"));
        panelDetalles.add(new JLabel("Correo electrónico:"));
        panelDetalles.add(new JTextField("juan.perez@example.com"));
        panelDetalles.add(new JLabel("Teléfono:"));
        panelDetalles.add(new JTextField("123-456-789"));
        panelDetalles.add(new JLabel("Dirección:"));
        panelDetalles.add(new JTextField("Calle Ejemplo 123"));
        
  
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 5, 5));
        
        JButton bConsultarPC = new JButton("Consultar puntaje crediticio");
        panelBotones.add(bConsultarPC);
        
        JButton bCambiarDatos = new JButton("Cambiar datos personales");
        panelBotones.add(bCambiarDatos);
        
        JButton bCambiarContra = new JButton("Cambiar contraseña");
        panelBotones.add(bCambiarContra);
        
        JButton bSoporte = new JButton("Ayuda");
        //bSoporte.setBackground(Color.BLUE);
        //bSoporte.setForeground(Color.WHITE);
        panelBotones.add(bSoporte);
        
        bSoporte.addActionListener(e-> new VentanaSoporte());
        
        bLogout = new JButton("Cerrar sesión");
        //bLogout.setBackground(Color.RED);
        //bLogout.setForeground(Color.WHITE);
        panelBotones.add(bLogout);
        bLogout.addActionListener(e->{
        	new VentanaInicioCliente();
        	dispose();
        });

        panel.add(panelDetalles, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private void mostrarPanel(String panelName) {
        CardLayout cl = (CardLayout) (panelContenido.getLayout());
        cl.show(panelContenido, panelName);
    }

    private void actualizarTablaServicios(String selected, List<Servicio> serviciosCliente) {
        modeloTablaServicios.setRowCount(0);

        for (Servicio servicio : serviciosCliente) {
            if (selected.equals("Inversiones") && servicio instanceof Inversion) {
                Inversion inversion = (Inversion) servicio;
                modeloTablaServicios.addRow(new Object[]{createHtmlCellContent(inversion.getTipo().toString(),inversion.getNumeroCuenta()), inversion.getFechaContratacion(), inversion.isActive(), "Editar"});
            } else if (selected.equals("Seguros") && servicio instanceof Seguro) {
                Seguro seguro = (Seguro) servicio;
                modeloTablaServicios.addRow(new Object[]{createHtmlCellContent(seguro.getClass().getSimpleName(),seguro.getNumPoliza()), seguro.getFechaContratacion(), seguro.isActive(), "Editar"});
            }
        }
    }
    
    private void actualizarTablaCuentas(String selected, List<Servicio> serviciosCliente) {
        modeloTablaCuentas.setRowCount(0);

        for (Servicio servicio : serviciosCliente) {
        	if (selected.equals("Cuentas") && servicio instanceof Cuenta) {
                Cuenta cuenta = (Cuenta) servicio;
                modeloTablaCuentas.addRow(new Object[]{createHtmlCellContent(cuenta.getClass().getSimpleName(),cuenta.getIban() ), cuenta.getSaldo()});
            }
        }
    }
    
    private void actualizarTablaTarjetas(String selected, List<Servicio> serviciosCliente) {
        modeloTablaTarjetas.setRowCount(0);

        for (Servicio servicio : serviciosCliente) {
        	if (selected.equals("Tarjetas") && servicio instanceof Tarjeta) {
                Tarjeta tarjeta = (Tarjeta) servicio;
                if (tarjeta instanceof TarjetaCredito) {
                	TarjetaCredito tarjeta_c = (TarjetaCredito) tarjeta;
                	modeloTablaTarjetas.addRow(new Object[]{createHtmlCellContent(tarjeta_c.getClass().getSimpleName(),tarjeta_c.getNumero()), tarjeta_c.getLimiteCreditor()});
                }else if (tarjeta instanceof TarjetaDebito) {
                	TarjetaDebito tarjeta_d = (TarjetaDebito) tarjeta;
                	modeloTablaTarjetas.addRow(new Object[]{createHtmlCellContent(tarjeta_d.getClass().getSimpleName(),tarjeta_d.getNumero()), tarjeta_d.getLimiteMensual()});
                
                	
                }
        	}
        }
    }
    private static String createHtmlCellContent(String nombre, String cuenta) {
        return String.format("<html><b>%s</b><br><font size='2' color='gray'>%s</font></html>", nombre, cuenta);
    }
    public static void main(String[] args) {
    	Empleado empleado1 = new Empleado(
    	        "Ibon", "E001", "Arambarri", "López", "87654321X", "Calle Ejemplo 2", 
    	        "48002", "Bilbao", "Vizcaya", "600654321", "ibon.garcia@example.com", 
    	        Sexo.HOMBRE, LocalDate.of(1988, 3, 12));
    	Cliente cliente = new Cliente("Elena", "Martínez", "López", "12345678Z", "Calle Ejemplo 1", 
    	        "48001", "Bilbao", "Vizcaya", "600123456", "elena.martinez@example.com", 
    	        Sexo.MUJER, LocalDate.of(1995, 5, 15), "Ingeniera", empleado1, 850.0, "Sin observaciones", new ArrayList<>());
		new VentanaCliente(cliente);
	}
}
