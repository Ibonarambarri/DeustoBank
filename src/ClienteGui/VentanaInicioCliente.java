package ClienteGui;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import domain.Cliente;
import main.GestorDB;

public class VentanaInicioCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
    private JPanel pLogo;
    private JPanel pCentral;
    private JPanel pBotones;

    private JTextField txtUsuario;
    private JPasswordField txtPassw;

    private JLabel lblLogo;
    private JCheckBox cbRememberMe;
    private JLabel lblForgotPassword;
    private JButton bIniciarSesion;

    private ImageIcon logo;
    private Image logoEsc;

    public VentanaInicioCliente() {
    	//List<Cliente> clientes = DataLoaderDemo.cargarClientes();
    	List<Cliente> clientes = GestorDB.getListaCLientes();
    	
    	setTitle("Inicio de sesión.");
        setSize(420, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        //---------------------------- LOGO -----------------------------
        pLogo = new JPanel();
        pLogo.setBackground(Color.WHITE);
        pLogo.setBorder(new EmptyBorder(20, 0, 100, 0)); // Espacio adicional arriba

        // Imagen del usuario como logo
        logo = new ImageIcon("img/usuario.png");
        logoEsc = logo.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
        lblLogo = new JLabel(new ImageIcon(logoEsc));
        lblLogo.setPreferredSize(new Dimension(130, 130));

        pLogo.add(lblLogo);
        mainPanel.add(pLogo, BorderLayout.NORTH);

        //---------------------------- PANEL CENTRAL ---------------------
        pCentral = new JPanel(new GridLayout(5, 1, 5, 5)); // Reducido el espaciado vertical
        pCentral.setBackground(Color.WHITE);

        // Panel de usuario
        JPanel pUsuario = new JPanel(new BorderLayout());
        pUsuario.setBackground(new Color(34, 66, 90));
        
        JLabel lblLogoU = new JLabel(new ImageIcon(new ImageIcon("img/tarjeta-de-identificacion.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        lblLogoU.setPreferredSize(new Dimension(45, 45));
        txtUsuario = new JTextField();
        txtUsuario.setPreferredSize(new Dimension(200, 40));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(42, 112, 165), 2), 
                new EmptyBorder(5, 10, 5, 10)));
        txtUsuario.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtUsuario.setBackground(new Color(42, 112, 165));
        txtUsuario.setForeground(Color.WHITE);

        pUsuario.add(lblLogoU, BorderLayout.WEST);
        pUsuario.add(txtUsuario, BorderLayout.CENTER);

        // Panel de contraseña
        JPanel pPassword = new JPanel(new BorderLayout());
        pPassword.setBackground(new Color(34, 66, 90));
        
        JLabel lblLogoC = new JLabel(new ImageIcon(new ImageIcon("img/candado.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        lblLogoC.setPreferredSize(new Dimension(45, 45));
        txtPassw = new JPasswordField();
        txtPassw.setPreferredSize(new Dimension(200, 40));
        txtPassw.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(42, 112, 165), 2), 
                new EmptyBorder(5, 10, 5, 10)));
        txtPassw.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtPassw.setBackground(new Color(42, 112, 165));
        txtPassw.setForeground(Color.WHITE);

        pPassword.add(lblLogoC, BorderLayout.WEST);
        pPassword.add(txtPassw, BorderLayout.CENTER);
        
        // Añadir los JTextField al panel central
        pCentral.add(pUsuario);
        pCentral.add(pPassword);

        // Checkbox de "Remember me" y opción de "Forgot Password?"
        cbRememberMe = new JCheckBox("Remember me");
        cbRememberMe.setBackground(Color.WHITE);
        cbRememberMe.setForeground(Color.GRAY);
        lblForgotPassword = new JLabel("Forgot Password?");
        lblForgotPassword.setForeground(new Color(100, 149, 237));
        lblForgotPassword.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        optionsPanel.setBackground(Color.WHITE);
        optionsPanel.add(cbRememberMe);
        optionsPanel.add(lblForgotPassword);

        pCentral.add(optionsPanel);
        mainPanel.add(pCentral, BorderLayout.CENTER);

        //---------------------------- BOTÓN DE INICIAR SESIÓN ---------------------
        pBotones = new JPanel();
        pBotones.setBackground(Color.WHITE);

        // Configuración del botón de inicio de sesión
        bIniciarSesion = new JButton("LOGIN");
        bIniciarSesion.setPreferredSize(new Dimension(300, 50));
        bIniciarSesion.setBackground(new Color(34, 66, 90));
        bIniciarSesion.setForeground(Color.WHITE);
        bIniciarSesion.setOpaque(true); // Forzar el color de fondo
        bIniciarSesion.setBorderPainted(false); // Remover el borde
        bIniciarSesion.setFont(new Font("SansSerif", Font.BOLD, 18));
        bIniciarSesion.addActionListener(e -> {
            new VentanaCliente(clientes.get(0));
            dispose();
        });

        pBotones.add(bIniciarSesion);
        mainPanel.add(pBotones, BorderLayout.SOUTH);

        this.add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        VentanaInicioCliente v = new VentanaInicioCliente();
        v.setVisible(true);
    }
}
