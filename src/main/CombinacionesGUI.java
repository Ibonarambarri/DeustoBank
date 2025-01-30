package main;

import javax.swing.*;
import domain.Cliente;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class CombinacionesGUI extends JDialog {
    private static final long serialVersionUID = 1L;
	private List<List<Cliente>> todasLasCombinaciones;
    private JList<String> listaCombi;
    private DefaultListModel<String> modeloLista;
    private JButton btnAceptar;
    private List<Cliente> combinacionSeleccionada;

    public CombinacionesGUI(List<Cliente> clientes, int maxClientes) {
        // Configurar como diálogo modal
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        // Inicializar variables
        todasLasCombinaciones = new ArrayList<>();
        combinacionSeleccionada = null;
        
        // Generar las combinaciones
        generarTodasLasCombinaciones(clientes, maxClientes);
        
        // Inicializar la interfaz
        inicializarGUI();
        
        // Configurar el manejador de cierre de ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opcion = JOptionPane.showConfirmDialog(
                    CombinacionesGUI.this,
                    "¿Desea cancelar la selección de combinación?",
                    "Confirmar cancelación",
                    JOptionPane.YES_NO_OPTION
                );
                if (opcion == JOptionPane.YES_OPTION) {
                    combinacionSeleccionada = null;
                    dispose();
                }
            }
        });
    }

    private void inicializarGUI() {
        setTitle("Combinaciones de Clientes");
        setLayout(new BorderLayout(10, 10));
        
        // Panel principal con padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Crear el modelo de lista y añadir las combinaciones
        modeloLista = new DefaultListModel<>();
        for (int i = 0; i < todasLasCombinaciones.size(); i++) {
            List<Cliente> combinacion = todasLasCombinaciones.get(i);
            StringBuilder sb = new StringBuilder();
            sb.append("Combinación ").append(i + 1).append(": ");
            
            for (int j = 0; j < combinacion.size(); j++) {
                Cliente cliente = combinacion.get(j);
                sb.append(cliente.getNombre());
                if (j < combinacion.size() - 1) {
                    sb.append(", ");
                }
            }
            
            modeloLista.addElement(sb.toString());
        }

        // Configurar la lista de combinaciones
        listaCombi = new JList<>(modeloLista);
        listaCombi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCombi.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Scroll pane para la lista
        JScrollPane scrollPane = new JScrollPane(listaCombi);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Combinaciones disponibles"));
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        // Botón aceptar
        btnAceptar = new JButton("Aceptar Combinación");
        btnAceptar.addActionListener(e -> {
            int selectedIndex = listaCombi.getSelectedIndex();
            if (selectedIndex != -1) {
                combinacionSeleccionada = todasLasCombinaciones.get(selectedIndex);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Por favor, seleccione una combinación",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        });
        
        // Botón cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> {
            combinacionSeleccionada = null;
            dispose();
        });
        
        // Añadir botones al panel
        buttonPanel.add(btnAceptar);
        buttonPanel.add(btnCancelar);
        
        // Añadir componentes al panel principal
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Añadir panel principal a la ventana
        add(mainPanel);
        
        // Configurar el tamaño y posición
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    private void generarTodasLasCombinaciones(List<Cliente> clientes, int maxClientes) {
        generarCombinacionesRecursivo(new ArrayList<>(), clientes, maxClientes, 0);
    }

    private void generarCombinacionesRecursivo(List<Cliente> combinacionActual,
                                             List<Cliente> clientesDisponibles,
                                             int maxClientes,
                                             int startIndex) {
        // Si la combinación actual alcanza el tamaño máximo, la agregamos
        if (combinacionActual.size() == maxClientes) {
            todasLasCombinaciones.add(new ArrayList<>(combinacionActual));
            return;
        }

        // Si no hay más clientes disponibles
        if (startIndex >= clientesDisponibles.size()) {
            if (!combinacionActual.isEmpty()) {
                todasLasCombinaciones.add(new ArrayList<>(combinacionActual));
            }
            return;
        }

        // Para cada cliente disponible desde startIndex
        for (int i = startIndex; i < clientesDisponibles.size(); i++) {
            // Agregar el cliente actual a la combinación
            combinacionActual.add(clientesDisponibles.get(i));
            
            // Llamada recursiva con el siguiente índice
            generarCombinacionesRecursivo(combinacionActual, clientesDisponibles,
                                        maxClientes, i + 1);
            
            // Remover el último cliente agregado (backtracking)
            combinacionActual.remove(combinacionActual.size() - 1);
        }

        // Si la combinación actual no está vacía y es menor que maxClientes
        if (!combinacionActual.isEmpty() && !todasLasCombinaciones.contains(combinacionActual)) {
            todasLasCombinaciones.add(new ArrayList<>(combinacionActual));
        }
    }

    // Método para obtener la combinación seleccionada
    public List<Cliente> getCombinacionSeleccionada() {
        return combinacionSeleccionada;
    }

    // Método estático para mostrar el diálogo y obtener la selección
    public static List<Cliente> mostrarYObtenerSeleccion(List<Cliente> clientes, int maxClientes) {
        CombinacionesGUI gui = new CombinacionesGUI(clientes, maxClientes);
        gui.setVisible(true);
        return gui.getCombinacionSeleccionada();
    }
}