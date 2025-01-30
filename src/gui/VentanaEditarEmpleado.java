package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.toedter.calendar.JDateChooser;

import domain.Empleado;
import domain.Sexo;
import main.GestorDB;

public class VentanaEditarEmpleado extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel lblNombre;
	private JLabel lblID;
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
	private JLabel lblFotoPerfil;

	private JTextField txtNombre;
	private JTextField txtID;
	private JTextField txtApellido1;
	private JTextField txtApellido2;
	private JTextField txtDni;
	private JTextField txtDireccion;
	private JTextField txtCiudad;
	private JTextField txtCP;
	private JTextField txtProvincia;
	private JTextField txtTlfn;
	private JTextField txtEmail;

	private JTextArea txtaObservaciones;

	private JPanel mainPanel;
	private JPanel pFoto;
	private JPanel pDatos;
	private JPanel pBotones;
	private JPanel pNombreID;
	private JPanel pApellidos;
	private JPanel pDniSexo;
	private JPanel pEmailTlf;
	private JPanel pDireccion;
	private JPanel pBotEditarPerf;
	private JPanel pObservaciones;
	private JPanel pCentral;

	private JButton bGuardarCambios;
	private JButton bCancelar;
	private JButton bEditarFotoPerfil;
	private JButton bEliminarEmpleado;

	private JComboBox<String> cbSexo;
	private ImageIcon fotoPerfil;
	private Image fotoPerfilEsc;

	private JDateChooser dcFechaNacimiento;

	@SuppressWarnings("unused")
	private VentanaAdmin ventanaAdmin;

	public VentanaEditarEmpleado(Empleado empleado, VentanaAdmin ventanaAdmin, List<Empleado> empleados, JTable tablaEmpleados) {

		this.ventanaAdmin = ventanaAdmin;

		setTitle("Editar Empleado");
		setSize(420, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Para que no cierre toda la aplicación
		setLocationRelativeTo(null);

		//Declaramos el panel principal
		mainPanel = new JPanel(new BorderLayout());

		//Declaramos los paneles que irán dentro del panel principal (mainPanel)
		pFoto = new JPanel(new BorderLayout());
		pCentral = new JPanel(new BorderLayout());
		pDatos = new JPanel(new GridLayout(5, 1, 5, 5));  
		pDatos.setBorder(BorderFactory.createTitledBorder("Datos personales del empleado:"));
		pObservaciones = new JPanel(new GridLayout(1, 1, 10, 10));
		pObservaciones.setBorder(BorderFactory.createTitledBorder("Observaciones del empleado:"));
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
		pNombreID = new JPanel(new GridLayout(2, 1, 5, 5));
		pApellidos = new JPanel(new GridLayout(2, 2, 5, 5));
		pDniSexo = new JPanel(new GridLayout(2, 3, 5, 5));
		pEmailTlf = new JPanel(new GridLayout(2, 2, 5, 5));
		pDireccion = new JPanel(new GridLayout(2, 4, 5, 5));

		//Labels de datos del cliente
		lblNombre = new JLabel("Nombre");
		lblID = new JLabel("Núm. identificación");
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

		// ---------------------------- CONFIGURACIÓN DE CAMPOS ----------------------------
		txtNombre = new JTextField(empleado.getNombre());
		txtID = new JTextField(empleado.getId());
		txtApellido1 = new JTextField(empleado.getApellido1());
		txtApellido2 = new JTextField(empleado.getApellido2());
		txtDni = new JTextField(empleado.getDni());
		txtDireccion = new JTextField(empleado.getDireccion());
		txtCP = new JTextField();
		txtCiudad = new JTextField();
		txtProvincia = new JTextField();
		txtCP = new JTextField(empleado.getCp());
		txtCiudad = new JTextField(empleado.getCiudad());
		txtProvincia = new JTextField(empleado.getProvincia());
		txtTlfn = new JTextField(empleado.getTlfn());
		txtEmail = new JTextField(empleado.getEmail());

		// ComboBox para el sexo
		String[] opcionesSexo = {"Masculino", "Femenino", "Otro"};
		cbSexo = new JComboBox<>(opcionesSexo);
		if (empleado.getSexo() != null) {
			switch (empleado.getSexo()) {
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
		pNombreID.add(lblNombre);
		pNombreID.add(lblID);
		pNombreID.add(txtNombre);
		pNombreID.add(txtID);

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

		//Añadir los paneles con los datos al panel de datos (pDatos)
		pDatos.add(pNombreID);
		pDatos.add(pApellidos);
		pDatos.add(pDniSexo);
		pDatos.add(pEmailTlf);
		pDatos.add(pDireccion);

		pCentral.add(pDatos, BorderLayout.CENTER);



		//---------------------------Observaciones del cliente----------------------
		txtaObservaciones = new JTextArea(6, 30);
		pObservaciones.add(txtaObservaciones);
		pCentral.add(pObservaciones, BorderLayout.SOUTH);

		mainPanel.add(pCentral);


		//----------------------------BOTONES----------------------------

		//Declaramos los botones para aceptar/cancelar los cambios
		bGuardarCambios = new JButton("Guardar cambios");
		bCancelar = new JButton("Cancelar");
		bEliminarEmpleado = new JButton("Eliminar empleado");

		//ActionListeners para los botones
		//TODO date no puede ser null, controlar eso
		bGuardarCambios.addActionListener(e -> {
			//TODO Hay que programar que si se detectan cambios el boton se enablee, y sino que este unabled
			String textoSexo = (String) cbSexo.getSelectedItem();
	        Sexo sexoSeleccionado = convertirTextoASexo(textoSexo);
			GestorDB.editarEmpleado(txtID.getText(), txtNombre.getText(), txtApellido1.getText(),txtApellido2.getText(), 
					txtDni.getText(), txtDireccion.getText(), txtCP.getText() ,txtCiudad.getText() , txtProvincia.getText(), txtTlfn.getText(), 
	    			 txtEmail.getText(), sexoSeleccionado , dcFechaNacimiento.getDateFormatString(), txtaObservaciones.getText());
	         JOptionPane.showMessageDialog(this, "Cambios guardados correctamente.");
	         dispose();
			JOptionPane.showMessageDialog(this, "Cambios guardados correctamente.");
			dispose();
		});
		

		bCancelar.addActionListener(e -> dispose());

		bEliminarEmpleado.addActionListener(e -> {
			//TODO Hay que programar que si se detectan cambios el boton se enablee, y sino que este unabled
			JOptionPane.showMessageDialog(this, "Empleado eliminado exitosamente.");
			ventanaAdmin.eliminarEmpleado(empleados, tablaEmpleados);
			 
			dispose();
		});


		bEliminarEmpleado.setBackground(Color.red);
		bEliminarEmpleado.setForeground(Color.WHITE);

		//Añadimos los botones al panel de los botones
		pBotones.add(bGuardarCambios, BorderLayout.SOUTH);
		pBotones.add(bCancelar, BorderLayout.SOUTH);
		pBotones.add(bEliminarEmpleado, BorderLayout.SOUTH);

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
	

}
