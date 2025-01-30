package ClienteGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaSoporte extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JPanel pDatos;
	private JPanel pBoton;
	private JPanel pDescrip;
	
	private JLabel lblDescrip1;
	private JLabel lblDescrip2;
	private JLabel lblDescrip3;
	private JLabel lblMail;
	private JLabel lblMailValor;
	private JLabel lblTlf;
	private JLabel lblTlfValor;
	
	private JButton bAceptar;

	public VentanaSoporte() {
		
		setTitle("Soporte DeustoBank");
		setSize(400, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		mainPanel = new JPanel(new BorderLayout());
		pDatos = new JPanel(new GridLayout(5, 1));
		pDatos.setBorder(BorderFactory.createTitledBorder("Datos de contacto"));
		
		pDescrip = new JPanel(new GridLayout(4,1));
		lblDescrip1 = new JLabel(" La ventana de soporte ofrece contacto directo para resolver tus");
		lblDescrip2 = new JLabel(" dudas o incidencias. Escríbenos a nuestro correo o llámanos al");
		lblDescrip3 = new JLabel(" número disponible para recibir ayuda personalizada.");
		
		lblDescrip1.setForeground(Color.GRAY);
		lblDescrip2.setForeground(Color.GRAY);
		lblDescrip3.setForeground(Color.GRAY);
		
		pDescrip.add(lblDescrip1);
		pDescrip.add(lblDescrip2);
		pDescrip.add(lblDescrip3);
		
		mainPanel.add(pDescrip, BorderLayout.NORTH);
		
		lblMail = new JLabel("   Mail:");
		lblMailValor = new JLabel("   deustobank@deusto.es");
		lblMailValor.setForeground(Color.BLUE);
		
		lblTlf = new JLabel("   Número de contacto:");
		lblTlfValor = new JLabel("   609308615");
		lblTlfValor.setForeground(Color.BLUE);

		pDatos.add(lblMail);
		pDatos.add(lblMailValor);
		pDatos.add(lblTlf);
		pDatos.add(lblTlfValor);
		
		mainPanel.add(pDatos, BorderLayout.CENTER);
		
		pBoton = new JPanel(new FlowLayout());
		bAceptar = new JButton("Aceptar");
		bAceptar.addActionListener(e-> dispose());
		pBoton.add(bAceptar);
		
		mainPanel.add(pBoton, BorderLayout.SOUTH);

		this.add(mainPanel);
		setVisible(true);
	}

}
