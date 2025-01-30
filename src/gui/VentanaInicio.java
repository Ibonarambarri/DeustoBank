
package gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import domain.Cliente;
import domain.Empleado;
import main.GestorDB;
import ClienteGui.VentanaCliente;


public class VentanaInicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JPanel cardPanel;
	private CardLayout cardLayout;
	private List<Cliente> clientes;
	private List<Empleado> empleados;

	public VentanaInicio(List<Cliente> clientes, List<Empleado> empleados) {


		this.clientes = clientes;
		this.empleados = empleados;



		setTitle("Inicio de sesión - Múltiples Logins");
		setSize(450, 600); // Tamaño ajustado para una vista más compacta
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));



		// Crear un JComboBox para seleccionar el tipo de login
		String[] opciones = {"Empleado", "Administrador", "Cliente"};
		JComboBox<String> comboBox = new JComboBox<>(opciones);
		comboBox.setFont(new Font("SansSerif", Font.PLAIN, 16));
		comboBox.setBackground(Color.WHITE);
		comboBox.setSelectedIndex(0); // Selección inicial
		comboBox.setMaximumSize(new Dimension(50,30));



		// Crear el CardLayout y el panel que contendrá los distintos logins
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		// Añadir los paneles de login al CardLayout
		cardPanel.add(crearPanelLogin(new Color(71, 40, 112), new Color(100, 80, 130), 2), "Empleado");
		cardPanel.add(crearPanelLogin(new Color(121, 30, 52), new Color(160, 60, 80), 1), "Administrador");
		cardPanel.add(crearPanelLogin(new Color(63, 73, 112),new Color(95, 113, 199), 3), "Cliente");

		// Añadir el listener para cambiar de panel cuando se selecciona una opción en el ComboBox
		comboBox.addActionListener(e -> {
			String seleccion = (String) comboBox.getSelectedItem();
			cardLayout.show(cardPanel, seleccion);
		});

		mainPanel.add(comboBox, BorderLayout.SOUTH);
		mainPanel.add(cardPanel, BorderLayout.CENTER);

		this.add(mainPanel);
		setVisible(true);
	}

	// Método que crea un panel de inicio de sesión con un esquema de colores personalizado
	private JPanel crearPanelLogin(Color colorFondo, Color colorBorde, int tipoVentana) {
		JPanel panelLogin = new JPanel(new BorderLayout());
		panelLogin.setBackground(Color.WHITE);

		//---------------------------- LOGO -----------------------------
		JPanel pLogo = new JPanel();
		pLogo.setBackground(Color.WHITE);
		pLogo.setBorder(new EmptyBorder(20, 0, 50, 0));

		// Crear un JPanel para envolver la imagen y aplicar el fondo rojo
		JPanel imageContainer = new JPanel();
		imageContainer.setBackground(colorFondo); // Fondo rojo
		imageContainer.setPreferredSize(new Dimension(100, 100)); // Tamaño del contenedor
		imageContainer.setLayout(new BorderLayout());

		// Crear el JLabel con la imagen
		ImageIcon logo = new ImageIcon("img/usuario.png");
		Image logoEsc = logo.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		JLabel lblLogo = new JLabel(new ImageIcon(logoEsc));

		// No es necesario poner el fondo del JLabel, solo del contenedor
		imageContainer.add(lblLogo, BorderLayout.CENTER);

		// Añadir el contenedor con la imagen al panel pLogo
		pLogo.add(imageContainer);
		panelLogin.add(pLogo, BorderLayout.NORTH);

		//---------------------------- PANEL CENTRAL ---------------------
		JPanel pCentral = new JPanel(new GridLayout(5, 1, 5, 5));
		pCentral.setBackground(Color.WHITE);

		// Panel de usuario
		JPanel pUsuario = new JPanel(new BorderLayout());
		pUsuario.setBackground(colorFondo);

		JLabel lblLogoU = new JLabel(new ImageIcon(new ImageIcon("img/tarjeta-de-identificacion.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		lblLogoU.setPreferredSize(new Dimension(45, 45));
		JTextField txtUsuario = new JTextField();
		txtUsuario.setPreferredSize(new Dimension(200, 40));
		txtUsuario.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(colorBorde, 2),
				new EmptyBorder(5, 10, 5, 10)));
		txtUsuario.setFont(new Font("SansSerif", Font.PLAIN, 16));
		txtUsuario.setBackground(colorBorde);
		txtUsuario.setForeground(Color.WHITE);

		pUsuario.add(lblLogoU, BorderLayout.WEST);
		pUsuario.add(txtUsuario, BorderLayout.CENTER);

		// Panel de contraseña
		JPanel pPassword = new JPanel(new BorderLayout());
		pPassword.setBackground(colorFondo);

		JLabel lblLogoC = new JLabel(new ImageIcon(new ImageIcon("img/candado.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		lblLogoC.setPreferredSize(new Dimension(45, 45));
		JPasswordField txtPassw = new JPasswordField();
		txtPassw.setPreferredSize(new Dimension(200, 40));
		txtPassw.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(colorBorde, 2),
				new EmptyBorder(5, 10, 5, 10)));
		txtPassw.setFont(new Font("SansSerif", Font.PLAIN, 16));
		txtPassw.setBackground(colorBorde);
		txtPassw.setForeground(Color.WHITE);

		pPassword.add(lblLogoC, BorderLayout.WEST);
		pPassword.add(txtPassw, BorderLayout.CENTER);

		// Añadir los JTextField al panel central
		pCentral.add(pUsuario);
		pCentral.add(pPassword);

		// Checkbox de "Remember me" y opción de "Forgot Password?"
		JCheckBox cbRememberMe = new JCheckBox("Remember me");
		cbRememberMe.setBackground(Color.WHITE);
		cbRememberMe.setForeground(Color.GRAY);
		JLabel lblForgotPassword = new JLabel("Forgot Password?");
		lblForgotPassword.setForeground(new Color(100, 149, 237));
		lblForgotPassword.setFont(new Font("SansSerif", Font.BOLD, 12));

		JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		optionsPanel.setBackground(Color.WHITE);
		optionsPanel.add(cbRememberMe);
		optionsPanel.add(lblForgotPassword);

		pCentral.add(optionsPanel);
		panelLogin.add(pCentral, BorderLayout.CENTER);

		//---------------------------- BOTÓN DE INICIAR SESIÓN ---------------------
		JPanel pBotones = new JPanel();
		pBotones.setBackground(Color.WHITE);

		JButton bIniciarSesion = new JButton("LOGIN");
		bIniciarSesion.setPreferredSize(new Dimension(300, 50));
		bIniciarSesion.setBackground(colorFondo);
		bIniciarSesion.setForeground(Color.WHITE);
		bIniciarSesion.setOpaque(true);
		bIniciarSesion.setBorderPainted(false);
		bIniciarSesion.setFont(new Font("SansSerif", Font.BOLD, 18));
		bIniciarSesion.addActionListener(e -> {
			//Recoge lo que haya escrito en los txtFields de usuario y contraseña
			String usuario = txtUsuario.getText();
			@SuppressWarnings("deprecation")
			String passw = txtPassw.getText();
			if (tipoVentana == 1) {
				if(usuario.equals("admin") && passw.equals("admin")) {
					new VentanaAdmin(clientes, empleados);
				} else {
					JOptionPane.showMessageDialog(null, "El usuario y contraseña no son correctos", "Inicio Sesión", JOptionPane.ERROR_MESSAGE);
				}

			} else if (tipoVentana == 2) {
				if(GestorDB.existeEmpleado(usuario)) {
					JOptionPane.showMessageDialog(null, "Bienvenido a DeustoBank", "Inicio Sesión", JOptionPane.INFORMATION_MESSAGE);
					new VentanaEmpleado(clientes, GestorDB.getEmpleadobyID(usuario));
				}else {
					JOptionPane.showMessageDialog(null, "El id de empleado no es correcto", "Inicio Sesión", JOptionPane.ERROR_MESSAGE);
				}

				txtUsuario.setText("");

			} else if (tipoVentana == 3) {

				if (GestorDB.existeCliente(usuario)) {
					JOptionPane.showMessageDialog(null, "Bienvenido a DeustoBank", "Inicio Sesión", JOptionPane.INFORMATION_MESSAGE);
					//Se abre la ventana
					new VentanaCliente(GestorDB.getClientebyDNI(usuario));
				} else {
					JOptionPane.showMessageDialog(null, "El id de cliente no es correcto", "Inicio Sesión", JOptionPane.ERROR_MESSAGE);

				}
				txtUsuario.setText("");
			}
		 
		});
		
		
		
		///////////////////// CODIGO PROVISIONAL PARA PRUEBAS ////////////////////////////////////////

		// INICIO DE SESION CLIENTE AL PULSAR C

	/*	getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("C"), "acceder_cliente");
=======
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("1"), "acceder_cliente");
>>>>>>> branch 'master' of git@github.com:leirelarrauri/DeustoBank.git
		getRootPane().getActionMap().put("acceder_cliente", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				 
				new VentanaCliente(GestorDB.getClientebyDNI("12345678Z"));  
				dispose();
				

			}
		});

		
		
		// INICIO DE SESION ADMIN AL PULSAR A
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("2"), "acceder_admin");
		getRootPane().getActionMap().put("acceder_admin", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				 
				new VentanaAdmin(clientes, empleados);
				dispose();

			}
		});

		
		
		// INICIO DE SESION EMPLEADO AL PULSAR E
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("3"), "acceder_empleado");
		getRootPane().getActionMap().put("acceder_empleado", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				 
				new VentanaEmpleado(clientes, GestorDB.getEmpleadobyID("E003"));
				

			}
		});
*/
		
	///////////////////////////////////////////////////////////////////////////////////////////
		
		pBotones.add(bIniciarSesion);
		panelLogin.add(pBotones, BorderLayout.SOUTH);

		return panelLogin;
	}

}