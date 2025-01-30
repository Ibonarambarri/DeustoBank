package gui;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import domain.TipoFondo;

import java.awt.*;
import java.text.ParseException;

public class VentanaEditarServicio extends JFrame {

  
	private static final long serialVersionUID = 1L;
	
	private JPanel pSeleccion;
    private JPanel pTipo;
    private JPanel pInfo;
    private JPanel pSuperior;
    private JPanel pInferior;
    
    private JButton bGuardar;
    private JButton bCancelar;
    private JButton bEliminar;
    
    private JRadioButton rbCuenta;
    private JRadioButton rbInversion;
    private JRadioButton rbPrestamo;
    private JRadioButton rbSeguro;
    private JRadioButton rbTarjeta;
    private ButtonGroup bGrupoEleccion;
    
    //-------------cambiar nombre a este button group
    private JRadioButton rbSi;
    private JRadioButton rbNo;
    private ButtonGroup bGrupoSiNo;
    
    private JRadioButton rbSiAval;
    private JRadioButton rbNoAval;
    private ButtonGroup bGrupoAval;
    
    private JRadioButton rbSiSeguro;
    private JRadioButton rbNoSeguro;
    private ButtonGroup bGrupoSeguro;
    
    private JDateChooser dcFechaContratacion;
    private JDateChooser dcFechaVencimiento;
    private JDateChooser dcFechaConstruccion;
    private JDateChooser dcFechaFabricacion;
    private JDateChooser dcFechaCaducidad;
    
   
    private JSlider sliderInteres;
    private JSlider sliderLimiteMensual;
    
    private SpinnerNumberModel modeloSpinnerLimite;
    private JSpinner spinnerLimite;
    
   
    

    public VentanaEditarServicio(String servicio) {
        setTitle("Edición de servicios");
        setSize(600, 400);
        setBounds(200, 100, 800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        
        //crear y colocar paneles y crear radioButtons
        pSuperior = new JPanel(new BorderLayout());
        pSeleccion = new JPanel();
        pTipo = new JPanel();
        
        rbCuenta = new JRadioButton("Cuenta");
        rbInversion = new JRadioButton("Inversion");
        rbPrestamo = new JRadioButton("Prestamo");
        rbSeguro = new JRadioButton("Seguro");
        rbTarjeta = new JRadioButton("Tarjeta");
        

        bGrupoEleccion = new ButtonGroup();
        bGrupoEleccion.add(rbCuenta);
        bGrupoEleccion.add(rbInversion);
        bGrupoEleccion.add(rbPrestamo);
        bGrupoEleccion.add(rbSeguro);
        bGrupoEleccion.add(rbTarjeta);

        pSeleccion.add(rbCuenta);
        pSeleccion.add(rbInversion);
        pSeleccion.add(rbPrestamo);
        pSeleccion.add(rbSeguro);
        pSeleccion.add(rbTarjeta);
        
        pSuperior.add(pSeleccion, BorderLayout.NORTH);

        pTipo = new JPanel();
        pInfo = new JPanel();
        pInfo.setLayout(new GridLayout(0, 2, 10, 5));
        
        pSuperior.add(pTipo, BorderLayout.CENTER);
        add(pInfo, BorderLayout.CENTER);
        add(pSuperior, BorderLayout.NORTH);
        
        if(servicio!=null) {
        	if(servicio.equals("cuenta")) {
        		rbCuenta.setSelected(true);
        		mostrarOpciones("cuentas");
        	}else if(servicio.equals("inversión")) {
        		rbInversion.setSelected(true);
        		mostrarOpciones("inversiones");
        	}else if(servicio.equals("prestamo")) {
        		rbPrestamo.setSelected(true);
        		mostrarOpciones("prestamos");
        	}else if(servicio.equals("seguro")) {
        		rbSeguro.setSelected(true);
        		mostrarOpciones("seguros");
        	}else {
        		rbTarjeta.setSelected(true);
        		mostrarOpciones("tarjetas");
        	}
       }
        rbCuenta.addActionListener(e -> mostrarOpciones("cuentas"));
        rbInversion.addActionListener(e -> mostrarOpciones("inversiones"));
        rbPrestamo.addActionListener(e -> mostrarOpciones("prestamos"));
        rbSeguro.addActionListener(e -> mostrarOpciones("seguros"));
        rbTarjeta.addActionListener(e -> mostrarOpciones("tarjetas"));
        setVisible(true);
    }
    

    private void mostrarOpciones(String tipo) {
    	pTipo.removeAll();
    	pInfo.removeAll();

        switch (tipo) {
            case "cuentas":
                JComboBox<String> cuentasBox = new JComboBox<>(new String[]{"Cuenta", "Cuenta de Ahorro"});
                pTipo.add(new JLabel("Selecciona tipo de cuenta:"));
                pTipo.add(cuentasBox);

                cuentasBox.addActionListener(e -> mostrarFormulario("cuenta", (String) cuentasBox.getSelectedItem()));
                break;
            case "inversiones":
                mostrarFormulario("inversion", null);
                break;
            case "prestamos":
                JComboBox<String> prestamosBox = new JComboBox<>(new String[]{"Prestamo de Coche", "Prestamo Hipotecario", "Prestamo Personal"});
                pTipo.add(new JLabel("Selecciona tipo de prestamo:"));
                pTipo.add(prestamosBox);

                prestamosBox.addActionListener(e -> mostrarFormulario("prestamo", (String) prestamosBox.getSelectedItem()));
                break;
            case "seguros":
                JComboBox<String> segurosBox = new JComboBox<>(new String[]{"Seguro de Hogar", "Seguro de Coche", "Seguro de Vida"});
                pTipo.add(new JLabel("Selecciona tipo de seguro:"));
                pTipo.add(segurosBox);

                segurosBox.addActionListener(e -> mostrarFormulario("seguro", (String) segurosBox.getSelectedItem()));
                break;
            case "tarjetas":
                JComboBox<String> tarjetasBox = new JComboBox<>(new String[]{"Tarjeta de crédito", "Tarjeta de débito"});
                pTipo.add(new JLabel("Selecciona tipo de tarjeta:"));
                pTipo.add(tarjetasBox);
                tarjetasBox.addActionListener(e -> mostrarFormulario("tarjeta", (String) tarjetasBox.getSelectedItem()));
                break;
            
        }

        pTipo.revalidate();
        pTipo.repaint();
    }
    
    
    
    private void mostrarFormulario(String categoria, String tipoEspecifico) {
    	
        JPanel pFechaContratacion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblFechaContratacion = new JLabel("Fecha de contratación:");
        lblFechaContratacion.setPreferredSize(new Dimension(150, 20));
        pFechaContratacion.add(lblFechaContratacion);

        dcFechaContratacion = new JDateChooser();
        dcFechaContratacion.setDateFormatString("dd-MM-yyyy");
        dcFechaContratacion.setPreferredSize(new Dimension(150, 20)); // Ajusta la altura
        pFechaContratacion.add(dcFechaContratacion);
        pInfo.add(pFechaContratacion);
    	
        JPanel pActivo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblActivo = new JLabel("Activo:");
        lblActivo.setPreferredSize(new Dimension(150, 20));
        pActivo.add(lblActivo);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rbSi = new JRadioButton("Sí");
        rbNo = new JRadioButton("No");
        bGrupoSiNo = new ButtonGroup();
        bGrupoSiNo.add(rbSi);
        bGrupoSiNo.add(rbNo);
        radioPanel.add(rbSi);
        radioPanel.add(rbNo);
        pActivo.add(radioPanel);
        pInfo.add(pActivo);
    	
        JPanel pTitular = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTitular = new JLabel("Fecha de contratación:");
        lblTitular.setPreferredSize(new Dimension(150, 20));
        pTitular.add(lblTitular);
        
        JTextField txtTitular = new JTextField();
        txtTitular.setPreferredSize(new Dimension(150, 20));
        pTitular.add(txtTitular);
        pInfo.add(pTitular);
        
        switch (categoria) {
            case "cuenta":
			MaskFormatter ibanFormateado = null;
			try {
				ibanFormateado = new MaskFormatter("UU#######################");
			} catch (ParseException e) {
				e.printStackTrace();
			}
                ibanFormateado.setPlaceholderCharacter('*');
                JFormattedTextField ibanField = new JFormattedTextField(ibanFormateado);
                ibanField.setColumns(26); 
                
                JPanel pIban = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblIban = new JLabel("IBAN:");
                lblIban.setPreferredSize(new Dimension(150, 20));
                pIban.add(lblIban);
                pIban.add(ibanField);
                pInfo.add(pIban);
                
                JPanel pTasaMantenimiento = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblTasaMantenimiento = new JLabel("Tasa de mantenimiento:");
                lblTasaMantenimiento.setPreferredSize(new Dimension(200, 20));
                pTasaMantenimiento.add(lblTasaMantenimiento);

            	sliderInteres = new JSlider(1, 100);
            	sliderInteres.setPaintTicks(true);
            	sliderInteres.setPaintLabels(true);
            	sliderInteres.setMajorTickSpacing(10);
            	sliderInteres.setMinorTickSpacing(1);
            	pTasaMantenimiento.add(sliderInteres);
            	pInfo.add(pTasaMantenimiento);
            	
            	JPanel pTransacciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblTransacciones = new JLabel("Transacciones:");
                lblTransacciones .setPreferredSize(new Dimension(200, 20));
                pTransacciones.add(lblTransacciones);
                 
            	String[] transacciones = {"Transaccion 1", "transaccion 2", "transaccion 3", "transaccion 4"};
                JList<String> lista = new JList<>(transacciones);
                JScrollPane scrollPane = new JScrollPane(lista);
                pTransacciones.add(scrollPane);
                pInfo.add(pTransacciones);
                if ("Cuenta".equals(tipoEspecifico)) {
                } else {
                	JPanel pInteres = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblInteres = new JLabel("Interés anual:");
                    lblInteres.setPreferredSize(new Dimension(200, 20));
                    pInteres.add(lblInteres);
                    pInteres.add(new JTextField(4));
                	pInfo.add(pInteres);
                	
                	
                	JPanel pLimite = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblLimite = new JLabel("Límite:");
                    lblLimite.setPreferredSize(new Dimension(100, 20));
                    pLimite.add(lblLimite);
                	pLimite.add(new JTextField(4));
                	pInfo.add(pLimite);
                }
                break;
            case "inversion":
            	JPanel pNumCuenta = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblNumCuenta = new JLabel("Número de cuenta:");
                lblNumCuenta.setPreferredSize(new Dimension(100, 20));
                pNumCuenta.add(lblNumCuenta);
                pNumCuenta.add(new JTextField(4));
            	pInfo.add(pNumCuenta);
            	
            	JPanel pTipoFondo = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblTipoFondo  = new JLabel("Tipo de fondo:");
                lblTipoFondo.setPreferredSize(new Dimension(100, 20));
                pTipoFondo.add(lblTipoFondo);
                
            	JComboBox<String> tipoFondoBox = new JComboBox<>(new String[]{TipoFondo.ALTO.toString(), TipoFondo.MEDIO.toString(), TipoFondo.BAJO.toString()});
            	pTipoFondo.add(tipoFondoBox);
            	pInfo.add(pTipoFondo);
            	
            	JPanel pSaldo = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblSaldo  = new JLabel("Saldo:");
                lblSaldo.setPreferredSize(new Dimension(100, 20));
                pSaldo.add(lblSaldo);
            	MaskFormatter saldoFormateado = null;
    			try {
    				saldoFormateado = new MaskFormatter("######");
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    			saldoFormateado.setPlaceholderCharacter('-');
                JFormattedTextField txtIban = new JFormattedTextField(saldoFormateado);
                txtIban.setColumns(10); 
                    
                pSaldo.add(txtIban);
                pInfo.add(pSaldo);
                
                JPanel pRendimiento = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblRendimiento  = new JLabel("Rendimiento:");
                lblRendimiento.setPreferredSize(new Dimension(100, 20));
                pRendimiento.add(lblRendimiento);
                
                SpinnerNumberModel modeloSpinnerRendimiento = new SpinnerNumberModel(2.0, 0.1, 10.0, 0.1);
                JSpinner spinnerRendimiento = new JSpinner(modeloSpinnerRendimiento);
                
                pRendimiento.add(spinnerRendimiento);
            	pInfo.add(pRendimiento);
            	
            	JPanel pComision = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblComision  = new JLabel("Comisión:");
                lblComision.setPreferredSize(new Dimension(100, 20));
                pComision.add(lblComision);
                
            	SpinnerNumberModel modeloSpinnerComision = new SpinnerNumberModel(5, 3, 30, 1);
                JSpinner spinnerComision = new JSpinner(modeloSpinnerComision);
                pComision.add(spinnerComision);
                pInfo.add(pComision);
            	
            	//pInfo.add(new JLabel("Periódo de inversión:"));
            	//----------------------------
                break;
            case "prestamo":
            	JPanel pNumPrestamo = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblNumPrestamo  = new JLabel("Número de préstamo:");
                lblNumPrestamo.setPreferredSize(new Dimension(100, 20));
                pNumPrestamo.add(lblNumPrestamo);
                pNumPrestamo.add(new JTextField(10));
            	pInfo.add(pNumPrestamo);
            	
            	JPanel pMonto = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblMonto  = new JLabel("Monto:");
                lblMonto.setPreferredSize(new Dimension(100, 20));
                pMonto.add(lblMonto);
                pMonto.add(new JTextField(10));
            	pInfo.add(pMonto);
            	
            	JPanel pTasaInteres = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lbTasaInteres  = new JLabel("Tasa de Interés:");
                lbTasaInteres.setPreferredSize(new Dimension(100, 20));
                pTasaInteres.add(lbTasaInteres);
                pTasaInteres.add(new JTextField(10));
            	pInfo.add(pTasaInteres);
            	
            	JPanel pPlazo = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lbPlazo  = new JLabel("Plazo en meses:");
                lbPlazo.setPreferredSize(new Dimension(100, 20));
                pPlazo.add(lbPlazo);
            	MaskFormatter plazoFormateado = null;
    			try {
    				plazoFormateado = new MaskFormatter("###");
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    			plazoFormateado.setPlaceholderCharacter('-');
                    JFormattedTextField txtPlazo = new JFormattedTextField(plazoFormateado);
                    txtPlazo.setColumns(10); 
                pPlazo.add(txtPlazo);
                pInfo.add(pPlazo);
                
                if ("Prestamo de Coche".equals(tipoEspecifico)) {
                	JPanel pModelo = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblModelo  = new JLabel("Modelo:");
                    lblModelo.setPreferredSize(new Dimension(100, 20));
                    pModelo.add(lblModelo);
                    pModelo.add(new JTextField(10));
                	pInfo.add(pModelo);
                	
                	JPanel pAnio = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblAnio  = new JLabel("Año del coche:");
                    lblAnio.setPreferredSize(new Dimension(100, 20));
                    pAnio.add(lblAnio);
                    pAnio.add(new JTextField(10));
                	pInfo.add(pAnio);
                	
                	JPanel pSeguro = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblSeguro  = new JLabel("Seguro Incluido:");
                    lblSeguro.setPreferredSize(new Dimension(100, 20));
                    pSeguro.add(lblSeguro);
                	rbSiSeguro = new JRadioButton("Si");
                	rbNoSeguro = new JRadioButton("No");
                	bGrupoSeguro = new ButtonGroup();
                	bGrupoSeguro.add(rbSiSeguro);
                	bGrupoSeguro.add(rbNoSeguro);
                	pSeguro.add(rbSiSeguro);
                	pSeguro.add(rbNoSeguro);
                	pInfo.add(pSeguro);
                } else if("Prestamo Hipotecario".equals(tipoEspecifico)) {
                	JPanel pDireccion = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblDireccion  = new JLabel("Dirección:");
                    lblDireccion.setPreferredSize(new Dimension(100, 20));
                    pDireccion.add(lblDireccion);
                    pDireccion.add(new JTextField(10));
                	pInfo.add(pDireccion);
                	
                	JPanel pValor = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblValor  = new JLabel("Valor:");
                    lblValor.setPreferredSize(new Dimension(100, 20));
                    pValor.add(lblValor);
                    pValor.add(new JTextField(10));
                	pInfo.add(pValor);
                }else {
                	JPanel pMotivo = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblMotivo  = new JLabel("Motivo:");
                    lblMotivo.setPreferredSize(new Dimension(100, 20));
                    pMotivo.add(lblMotivo);
                    pMotivo.add(new JTextArea());
                	pInfo.add(pMotivo);
                	pInfo.add(new JTextArea());
                	
                	JPanel pAval = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblAval  = new JLabel("Aval:");
                    lblAval.setPreferredSize(new Dimension(100, 20));
                    pAval.add(lblAval);
                	rbSiAval = new JRadioButton("Si");
                	rbNoAval = new JRadioButton("No");
                	bGrupoAval = new ButtonGroup();
                	bGrupoAval.add(rbSiAval);
                	bGrupoAval.add(rbNoAval);
                	pAval.add(rbSiAval);
                	pAval.add(rbNoAval);
                	pInfo.add(pAval);
                }
                break;    
            case "seguro":
            	JPanel pNumPoliza = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblNumPoliza  = new JLabel("Número de poliza:");
                lblNumPoliza.setPreferredSize(new Dimension(100, 20));
                pNumPoliza.add(lblNumPoliza);
                pNumPoliza.add(new JTextField(10));
            	pInfo.add(pNumPoliza);
            	
            	JPanel pFechaVencimiento = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblFechaVencimiento  = new JLabel("Fecha de Vencimiento:");
                lblFechaVencimiento.setPreferredSize(new Dimension(100, 20));
                pFechaVencimiento.add(lblFechaVencimiento);
                
            	dcFechaVencimiento = new JDateChooser();
            	dcFechaVencimiento.setDateFormatString("dd-MM-yyyy");
            	pFechaVencimiento.add(dcFechaVencimiento);
            	pInfo.add(pFechaVencimiento);
            	
            	JPanel pCoberturaTotal = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblCoberturaTotal  = new JLabel("Cobertura total:");
                lblCoberturaTotal.setPreferredSize(new Dimension(100, 20));
                pCoberturaTotal.add(lblCoberturaTotal);
                pCoberturaTotal.add(new JTextField(10));
            	pInfo.add(pCoberturaTotal);
            	
            	JPanel pMensualidad = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblMensualidad  = new JLabel("Mensualidad:");
                lblMensualidad.setPreferredSize(new Dimension(100, 20));
                pMensualidad.add(lblMensualidad);
                pMensualidad.add(new JTextField(10));
            	pInfo.add(pMensualidad);
            	
                if ("Seguro de Hogar".equals(tipoEspecifico)) {
                	JPanel pDireccion = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblDireccion  = new JLabel("Dirección:");
                    lblDireccion.setPreferredSize(new Dimension(100, 20));
                    pDireccion.add(lblDireccion);
                    pDireccion.add(new JTextField(15));
                	pInfo.add(pDireccion);
                	
                	JPanel pValor = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblValor  = new JLabel("Valor de propiedad:");
                    lblValor.setPreferredSize(new Dimension(100, 20));
                    pValor.add(lblValor);
                    pValor.add(new JTextField(10));
                	pInfo.add(pValor);
                	
                	JPanel pFechaConstruccion = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblFechaConstruccion  = new JLabel("Fecha de construcción:");
                    lblFechaConstruccion.setPreferredSize(new Dimension(100, 20));
                    pFechaConstruccion.add(lblFechaConstruccion);
                    
                	dcFechaConstruccion = new JDateChooser();
                	dcFechaConstruccion.setDateFormatString("dd-MM-yyyy");
                	pFechaConstruccion.add(dcFechaConstruccion);
                	pInfo.add(pFechaConstruccion);
                	
                } else if("Seguro de Coche".equals(tipoEspecifico)) {
                	JPanel pDescripcion = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblDescripcion  = new JLabel("Descripción:");
                    lblDescripcion.setPreferredSize(new Dimension(100, 20));
                    pDescripcion.add(lblDescripcion);
                    pDescripcion.add(new JTextArea());
                	pInfo.add(pDescripcion);
                	
                	JPanel pMatricula = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblMatricula  = new JLabel("Matrícula:");
                    lblMatricula.setPreferredSize(new Dimension(100, 20));
                    pMatricula.add(lblMatricula);
                    pMatricula.add(new JTextField(10));
                	pInfo.add(pMatricula);
                	
                	JPanel pFechaFabricacion = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblFechaFabricacion  = new JLabel("Fecha de fabricación:");
                    lblFechaFabricacion.setPreferredSize(new Dimension(100, 20));
                    pFechaFabricacion.add(lblFechaFabricacion);
                    
                	dcFechaFabricacion = new JDateChooser();
                	dcFechaFabricacion.setDateFormatString("dd-MM-yyyy");
                	pFechaFabricacion.add(dcFechaFabricacion);
                	pInfo.add(pFechaFabricacion);
                	
                }else {
                	JPanel pBeneficiarios = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblBeneficiarios  = new JLabel("Beneficiarios:");
                    lblBeneficiarios.setPreferredSize(new Dimension(100, 20));
                    pBeneficiarios.add(lblBeneficiarios);
                    pBeneficiarios.add(new JTextField(10));
                	pInfo.add(pBeneficiarios);
                }
                break;
            case "tarjeta":
            	JPanel pNumTarjeta = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblNumTarjeta  = new JLabel("Numero de tarjeta:");
                lblNumTarjeta.setPreferredSize(new Dimension(100, 20));
                pNumTarjeta.add(lblNumTarjeta);
            	MaskFormatter tarjetaFormateado = null;
    			try {
    				tarjetaFormateado = new MaskFormatter("######################");
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    			tarjetaFormateado.setPlaceholderCharacter('-');
                    JFormattedTextField txtTarjeta = new JFormattedTextField(tarjetaFormateado);
                    txtTarjeta.setColumns(22); 
                pNumTarjeta.add(txtTarjeta);
                pInfo.add(pNumTarjeta);
                
                JPanel pCvv = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblCvv  = new JLabel("CVV:");
                lblCvv.setPreferredSize(new Dimension(100, 20));
                pCvv.add(lblCvv);
                pCvv.add(new JTextField(10));
            	pInfo.add(pCvv);
            	
            	JPanel pFechaCaducidad = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lblFechaCaducidad  = new JLabel("Fecha de caducidad:");
                lblFechaCaducidad.setPreferredSize(new Dimension(100, 20));
                pFechaCaducidad.add(lblFechaCaducidad);
            	dcFechaCaducidad = new JDateChooser();
            	
            	dcFechaCaducidad.setDateFormatString("dd-MM-yyyy");
            	pFechaCaducidad.add(dcFechaCaducidad);
            	pInfo.add(pFechaCaducidad);
            	
            	//pInfo.add(new JLabel("Cuenta asignada:"));
            	//-----------------------------------------
                if ("Tarjeta de crédito".equals(tipoEspecifico)) {
                	JPanel pLimCreditor = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblLimCreditor  = new JLabel("Límite creditor:");
                    lblLimCreditor.setPreferredSize(new Dimension(100, 20));
                    pLimCreditor.add(lblLimCreditor);
                	modeloSpinnerLimite = new SpinnerNumberModel(2.0, 0.1, 10.0, 0.1);
                	spinnerLimite = new JSpinner(modeloSpinnerLimite);
                	pLimCreditor.add(spinnerLimite);
                	pInfo.add(pLimCreditor);
                } else {
                	JPanel pLimMensual = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel lblLimMensual  = new JLabel("Límite mensual:");
                    lblLimMensual.setPreferredSize(new Dimension(100, 20));
                    pLimMensual.add(lblLimMensual);
                	sliderLimiteMensual = new JSlider(1000, 10000, 5000);
                	sliderLimiteMensual.setPaintTicks(true);
                	sliderLimiteMensual.setPaintLabels(true);
                	sliderLimiteMensual.setMajorTickSpacing(4000);
                	sliderLimiteMensual.setMinorTickSpacing(1000);
                	pLimMensual.add(sliderLimiteMensual);
                	pInfo.add(pLimMensual);
                }
                break;
        }
        

        //Botones inferiores
        pInferior = new JPanel();
        
        bGuardar = new JButton("Guardar");
        bGuardar.addActionListener(e -> {
        	guardarInformacion(categoria, tipoEspecifico);
        });
        
        
        bCancelar = new JButton("Cancelar");
        bCancelar.addActionListener(e -> dispose());
        
        bEliminar = new JButton("Eliminar");
        bEliminar.addActionListener(e -> {
	         JOptionPane.showMessageDialog(this, "Servicio eliminado.");
	         dispose();
	     });
	     
        bEliminar.setBackground(Color.red);
        bEliminar.setForeground(Color.WHITE);
        
        pInferior.add(bGuardar);
        pInferior.add(bCancelar);
        pInferior.add(bEliminar);
        add(pInferior, BorderLayout.SOUTH);

        pInfo.revalidate();
        pInfo.repaint();
        setVisible(true);
    }

    private void guardarInformacion(String categoria, String tipoEspecifico) {
       
    	JOptionPane.showConfirmDialog(null, "Información guardada para " + categoria + " de tipo " + tipoEspecifico, "Guardado", JOptionPane.OK_OPTION); 
    }
    


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaEditarServicio ventana = new VentanaEditarServicio(null);
            ventana.setVisible(true);
        });
    }
}
