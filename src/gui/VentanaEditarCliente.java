package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import domain.Cliente;
import domain.Empleado;
import domain.Sexo;
import main.GestorDB;

import com.toedter.calendar.JDateChooser;


//Nueva clase para la ventana de edición del cliente
public class VentanaEditarCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lblNombre;
    private JLabel lblApellido1;
    private JLabel lblApellido2;
    private JLabel lblDni;
    private JLabel lblDireccion;
    private JLabel lblCiudad;
    private JLabel lblCP;
    private JLabel lblProvincia;
    private JLabel lblTlfn;
    private JLabel lblEmail;
    private JLabel lblSexo;
    private JLabel lblFechaNacimiento;
    private JLabel lblProfesion;
    private JLabel lblPuntajeCrediticio;
    private JLabel lblFotoPerfil;
    private JTextField txtNombre;
    private JTextField txtApellido1;
    private JTextField txtApellido2;
    private JTextField txtDni;
    private JTextField txtDireccion;
    private JTextField txtCiudad;
    private JTextField txtCP;
    private JTextField txtProvincia;
    private JTextField txtTlfn;
    private JTextField txtEmail;
    private JTextField txtProfesion;
    private JTextField txtPuntajeCrediticio;
    
    private JTextArea txtaObservaciones;
    
    private JPanel mainPanel;
    private JPanel pFoto;
    private JPanel pDatos;
    private JPanel pBotones;
    private JPanel pNombre;
    private JPanel pApellidos;
    private JPanel pDniSexo;
    private JPanel pEmailTlf;
    private JPanel pDireccion;
    private JPanel pProfesionPC;
    private JPanel pBotEditarPerf;
    private JPanel pObservaciones;
    private JPanel pCentral;
    
    private JButton bGuardarCambios;
    private JButton bCancelar;
    private JButton bEditarFotoPerfil;
    private JButton bEliminarCliente;
    
    private JComboBox<String> cbSexo;
    private ImageIcon fotoPerfil;
    private Image fotoPerfilEsc;
    
    private JDateChooser dcFechaNacimiento;
    
    public VentanaEditarCliente(Cliente cliente) {
    	setTitle("Editar Cliente");
	     setSize(420, 650);
	     setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Para que no cierre toda la aplicación
	     setLocationRelativeTo(null);
	    
	     //Declaramos el panel principal
	     mainPanel = new JPanel(new BorderLayout());
	     
	     //Declaramos los paneles que irán dentro del panel principal (mainPanel)
	     pFoto = new JPanel(new BorderLayout());
	     pCentral = new JPanel(new BorderLayout());
	     pDatos = new JPanel(new GridLayout(6, 1, 5, 5));  
	     pDatos.setBorder(BorderFactory.createTitledBorder("Datos personales del cliente:"));
	     pObservaciones = new JPanel(new GridLayout(1, 1, 10, 10));
	     pObservaciones.setBorder(BorderFactory.createTitledBorder("Observaciones del cliente:"));
	     pBotones = new JPanel(new FlowLayout());
	     
	     new JPanel(new BorderLayout());
	     pBotEditarPerf = new JPanel(new FlowLayout());
	
	     
	     //-----------------------------------FOTO------------------------------------
	     //Declaramos la imagen y el texto que irán en el panel superior
	     fotoPerfil = new ImageIcon("img/imagen_defecto.jpg");
	     fotoPerfilEsc = fotoPerfil.getImage().getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING); //Escalar la imagen
	     lblFotoPerfil = new JLabel(new ImageIcon(fotoPerfilEsc));
	     lblFotoPerfil.setPreferredSize(new Dimension(90, 90));		//Escalar el JLabel donde irá la imagen
	     
	     pFoto.add(lblFotoPerfil, BorderLayout.WEST);
	     
	     //Botones para editar la foto de perfil
	     bEditarFotoPerfil = new JButton("Editar foto de perfil");
	     pBotEditarPerf.add(bEditarFotoPerfil);
	     pFoto.add(pBotEditarPerf, BorderLayout.CENTER);     
	     
	     //Añadimos el panel con la foto al panel principal (mainPanel)
	     mainPanel.add(pFoto, BorderLayout.NORTH);
	     
	     
	     //------------------------------------DATOS------------------------------------
	     //---------------------------Datos personales del cliente----------------------
	     
	     //Declaramos los paneles que irán dentro del panel de datos (pDatos)
	     pNombre = new JPanel(new GridLayout(2, 1, 5, 5));
	     pApellidos = new JPanel(new GridLayout(2, 2, 5, 5));
	     pDniSexo = new JPanel(new GridLayout(2, 3, 5, 5));
	     pEmailTlf = new JPanel(new GridLayout(2, 2, 5, 5));
	     pDireccion = new JPanel(new GridLayout(2, 4, 5, 5));
	     pProfesionPC = new JPanel(new GridLayout(2, 2, 5, 5));
	     
	     //Labels de datos del cliente
	     lblNombre = new JLabel("Nombre");
	     lblApellido1 = new JLabel("1er Apellido");
	     lblApellido2 = new JLabel("2do Apellido");
	     lblDni = new JLabel("DNI");
	     lblDireccion = new JLabel("Dirección");
	     lblCP = new JLabel("Código Postal");
	     lblCiudad = new JLabel("Ciudad");
	     lblProvincia = new JLabel("Provincia");
	     lblTlfn = new JLabel("Número de teléfono");
	     lblEmail = new JLabel("Email");
	     lblSexo = new JLabel("Sexo");
	     lblFechaNacimiento = new JLabel("Fecha de nacimiento");
	     lblProfesion = new JLabel("Profesión");
	     lblPuntajeCrediticio = new JLabel("Puntaje crediticio");
	     
	  // ---------------------------- CONFIGURACIÓN DE CAMPOS ----------------------------
	     txtNombre = new JTextField(cliente.getNombre());
	     txtApellido1 = new JTextField(cliente.getApellido1());
	     txtApellido2 = new JTextField(cliente.getApellido2());
	     txtDni = new JTextField(cliente.getDni());
	     txtDireccion = new JTextField(cliente.getDireccion());
	     txtCP = new JTextField();
	     txtCiudad = new JTextField();
	     txtProvincia = new JTextField();
	     txtCP = new JTextField(cliente.getCp());
	     txtCiudad = new JTextField(cliente.getCiudad());
	     txtProvincia = new JTextField(cliente.getProvincia());
	     txtTlfn = new JTextField(cliente.getTlfn());
	     txtEmail = new JTextField(cliente.getEmail());
	     txtProfesion = new JTextField(cliente.getProfesion());
	     txtPuntajeCrediticio = new JTextField(String.valueOf(cliente.getPuntajeCrediticio()));

	     // ComboBox para el sexo
	     String[] opcionesSexo = {"Masculino", "Femenino", "Otro"};
	     cbSexo = new JComboBox<>(opcionesSexo);
	     if (cliente.getSexo() != null) {
	         switch (cliente.getSexo()) {
	             case HOMBRE -> cbSexo.setSelectedIndex(0);
	             case MUJER -> cbSexo.setSelectedIndex(1);
	             case OTRO -> cbSexo.setSelectedIndex(2);
	         }
	     } else {
	         cbSexo.setSelectedIndex(-1); // Ninguna opción seleccionada por defecto
	     }

	     // Fecha de nacimiento
	     dcFechaNacimiento = new JDateChooser();
	     //dcFechaNacimiento.setDate(cliente.getFechaNacimiento());
	     dcFechaNacimiento.setDateFormatString("dd-MM-yyyy"); // Formato de fecha
	     
	     //Añadimos los JLabels y JTextFields a los paneles de datos
	     pNombre.add(lblNombre);
	     pNombre.add(txtNombre);
	     
	     pApellidos.add(lblApellido1);
	     pApellidos.add(lblApellido2);
	     pApellidos.add(txtApellido1);
	     pApellidos.add(txtApellido2);
	     
	     pDniSexo.add(lblDni);
	     pDniSexo.add(lblFechaNacimiento);   
	     pDniSexo.add(lblSexo); 
	     pDniSexo.add(txtDni);   
	     pDniSexo.add(dcFechaNacimiento);
	     pDniSexo.add(cbSexo); 
	     
	     pEmailTlf.add(lblEmail);
	     pEmailTlf.add(lblTlfn);
	     pEmailTlf.add(txtEmail);
	     pEmailTlf.add(txtTlfn);
	     
	     pDireccion.add(lblDireccion);
	     pDireccion.add(lblCP);
	     pDireccion.add(lblCiudad);
	     pDireccion.add(lblProvincia);
	     pDireccion.add(txtDireccion);
	     pDireccion.add(txtCP);
	     pDireccion.add(txtCiudad);
	     pDireccion.add(txtProvincia);
	     
	     pProfesionPC.add(lblProfesion);
	     pProfesionPC.add(lblPuntajeCrediticio);
	     pProfesionPC.add(txtProfesion);
	     pProfesionPC.add(txtPuntajeCrediticio);
	     
	     //Añadir los paneles con los datos al panel de datos (pDatos)
	     pDatos.add(pNombre);
	     pDatos.add(pApellidos);
	     pDatos.add(pDniSexo);
	     pDatos.add(pEmailTlf);
	     pDatos.add(pDireccion);
	     pDatos.add(pProfesionPC);
	     
	     pCentral.add(pDatos, BorderLayout.CENTER);
	
	     
	     /**---------------------------Datos bancarios del cliente----------------------
	      // COMENTADO POR SI SE PUDIESE REUTILIZAR EN OTRA VENTANA
	     
	     //Declaramos el panel que contendrá los datos bancarios
	     pDatosBancarios = new JPanel(new GridLayout(6, 1, 5, 5));
	     pDatosBancarios.setBorder(BorderFactory.createTitledBorder("Datos bancarios del cliente:"));
	     
	     //Declaramos los los JLabels, JCombobox y JTextFields para contener los datos bancarios
	     lblCuentasBancarias = new JLabel("Cuentas bancarias");
	     lblServicios = new JLabel("Servicios");
	     
	     cbCuentasBancarias = new JComboBox<String>();
	     String [] lServicios = {"Cuentas", "Inversiones", "Seguros", "Prestamos", "Tarjetas"};
	     cbServicios = new JComboBox<String>(lServicios);
	     
	     txtCuentasBancarias = new JTextArea();
	     txtServicios = new JTextArea();
	     
	     //Añadimos los datos al panel
	     pDatosBancarios.add(lblCuentasBancarias);
	     pDatosBancarios.add(cbCuentasBancarias);
	     pDatosBancarios.add(txtCuentasBancarias);
	     pDatosBancarios.add(lblServicios);
	     pDatosBancarios.add(cbServicios);
	     pDatosBancarios.add(txtServicios);
	     
	     pDatosPersBanc.add(pDatosBancarios, BorderLayout.SOUTH);     
	     
	     
	     //Añadimos todos los paneles al panel principal (mainPanel)
	     mainPanel.add(pDatosPersBanc);
	     */
	     
	     //---------------------------Observaciones del cliente----------------------
	     txtaObservaciones = new JTextArea(6, 30);
	     pObservaciones.add(txtaObservaciones);
	     pCentral.add(pObservaciones, BorderLayout.SOUTH);
	     
	     mainPanel.add(pCentral);
	     
	     
	     //----------------------------BOTONES----------------------------
	     
	     //Declaramos los botones para aceptar/cancelar los cambios
	     bGuardarCambios = new JButton("Guardar cambios");
	     bCancelar = new JButton("Cancelar");
	     bEliminarCliente = new JButton("Eliminar cliente");
	     
	     //ActionListeners para los botones

	     bGuardarCambios.addActionListener(e -> {
	    	 String textoSexo = (String) cbSexo.getSelectedItem();
		        Sexo sexoSeleccionado = convertirTextoASexo(textoSexo);

	    	 GestorDB.editarCliente(txtDni.getText(), txtNombre.getText(), txtApellido1.getText(),txtApellido2.getText(), 
	    			 txtDireccion.getText(), txtCP.getText(), txtCiudad.getText(), txtProvincia.getText(), txtTlfn.getText(), 
	    			 txtEmail.getText(),  sexoSeleccionado, dcFechaNacimiento.getDateFormatString(), txtProfesion.getText(), 
	    			 Double.parseDouble(txtPuntajeCrediticio.getText()), txtaObservaciones.getText(), cliente.getDni());
	         JOptionPane.showMessageDialog(this, "Cambios guardados correctamente.");
	         dispose();
	     });
	     
	     bCancelar.addActionListener(e -> dispose());
	     
	     bEliminarCliente.addActionListener(e -> {
	    	 GestorDB.eliminarCliente(cliente.getDni());
	         JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente.");
	         dispose();
	     });
	     
	     
	     bEliminarCliente.setBackground(Color.red);
	     bEliminarCliente.setForeground(Color.WHITE);
	     
	     //Añadimos los botones al panel de los botones
	     pBotones.add(bGuardarCambios, BorderLayout.SOUTH);
	     pBotones.add(bCancelar, BorderLayout.SOUTH);
	     pBotones.add(bEliminarCliente, BorderLayout.SOUTH);
	     
	     //Añadimos el panel de los botones al panel principal
	     mainPanel.add(pBotones, BorderLayout.SOUTH);
	         
	
	     this.add(mainPanel);
	     setVisible(true);
	     
	     
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			// LISTENER PARA ESC

			//IAG
			// A partir del ejemplo inicial se han modificado las acciones especificas para cada ventana

			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			// Configuración de Key Binding para la tecla ESC
			getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cerrarVentana");
			getRootPane().getActionMap().put("cerrarVentana", new AbstractAction() {

				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose(); // Cerrar la ventana
					
				}
				
			});
	 }
	private Sexo convertirTextoASexo(String texto) {
		if (texto == null) {
            return Sexo.OTRO; // Valor por defecto en caso de que el texto sea nulo
        }
        switch (texto) {
            case "Masculino":
                return Sexo.HOMBRE;
            case "Femenino":
                return Sexo.MUJER;
            case "Otro":
                return Sexo.OTRO;
            default:
                throw new IllegalArgumentException("Sexo desconocido: " + texto);
        }
	}
    public static void main(String[] args) {
    	Empleado empleado1 = new Empleado(
    	        "Ibon", "E001", "Arambarri", "López", "87654321X", "Calle Ejemplo 2", 
    	        "48002", "Bilbao", "Vizcaya", "600654321", "ibon.garcia@example.com", 
    	        Sexo.HOMBRE, LocalDate.of(1988, 3, 12));
    	Cliente cliente = new Cliente("Elena", "Martínez", "López", "12345678Z", "Calle Ejemplo 1", 
    	        "48001", "Bilbao", "Vizcaya", "600123456", "elena.martinez@example.com", 
    	        Sexo.MUJER, LocalDate.of(1995, 5, 15), "Ingeniera", empleado1, 850.0, "Sin observaciones", new ArrayList<>());
		new VentanaEditarCliente(cliente);
	}
    
}
