/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import dominio.Contacto;
import dao.CrudDao;
import dao.ContactoDao;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Anguar Alberto Rodriguez Fonseca
 */
public class AgendaGUI extends JFrame {
    private CrudDao contactoDAO;
    private JList<Contacto> listaContactos;
    private DefaultListModel<Contacto> modeloLista;
    
    // Componentes para mostrar detalles
    private JLabel lblNombreDetalle;
    private JLabel lblTelefonoDetalle;
    private JLabel lblEmailDetalle;
    
    // Colores para el tema
    private final Color COLOR_PRIMARIO = new Color(41, 128, 185);
    private final Color COLOR_SECUNDARIO = new Color(52, 152, 219);
    private final Color COLOR_FONDO = new Color(245, 245, 245);
    private final Color COLOR_BLANCO = Color.WHITE;

    public AgendaGUI() {
        contactoDAO = new ContactoDao();
        initComponents();
        cargarContactos();
    }
    
    private void initComponents() {
        setTitle("📋 Agenda de Contactos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        
        // Panel principal con BorderLayout
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior - Título
        JPanel panelTitulo = crearPanelTitulo();
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central - Lista y detalles
        JPanel panelCentral = crearPanelCentral();
        add(panelCentral, BorderLayout.CENTER);
        
        // Panel inferior - Botones
        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);
        
        // Agregar margen general
        ((JComponent) getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }
    
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel();
        panel.setBackground(COLOR_PRIMARIO);
        panel.setPreferredSize(new Dimension(800, 60));
        
        JLabel titulo = new JLabel("AGENDA DE CONTACTOS");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(COLOR_BLANCO);
        panel.add(titulo);
        
        return panel;
    }
    
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setBackground(COLOR_FONDO);
        
        // Panel izquierdo - Lista de contactos
        JPanel panelLista = crearPanelLista();
        panel.add(panelLista);
        
        // Panel derecho - Detalles del contacto
        JPanel panelDetalles = crearPanelDetalles();
        panel.add(panelDetalles);
        
        return panel;
    }
    
    private JPanel crearPanelLista() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BLANCO);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_PRIMARIO, 2),
            "Lista de Contactos",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            COLOR_PRIMARIO
        ));
        
        // Configurar la lista
        modeloLista = new DefaultListModel<>();
        listaContactos = new JList<>(modeloLista);
        listaContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaContactos.setFont(new Font("Arial", Font.PLAIN, 14));
        listaContactos.setFixedCellHeight(30);
        
        // Personalizar el renderizado de la lista
        listaContactos.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                if (value instanceof Contacto) {
                    Contacto c = (Contacto) value;
                    setText("  👤 " + c.getNombre());
                }
                
                if (isSelected) {
                    setBackground(COLOR_SECUNDARIO);
                    setForeground(COLOR_BLANCO);
                } else {
                    setBackground(COLOR_BLANCO);
                    setForeground(Color.BLACK);
                }
                
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return this;
            }
        });
        
        // Agregar listener para mostrar detalles al hacer clic
        listaContactos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarDetallesContacto();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(listaContactos);
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelDetalles() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_BLANCO);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_PRIMARIO, 2),
            "Detalles del Contacto",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            COLOR_PRIMARIO
        ));
        
        // Espaciador superior
        panel.add(Box.createVerticalStrut(30));
        
        // Panel para cada detalle
        JPanel panelNombre = crearPanelDetalle("👤 Nombre:", "");
        lblNombreDetalle = (JLabel) panelNombre.getComponent(1);
        panel.add(panelNombre);
        panel.add(Box.createVerticalStrut(20));
        
        JPanel panelTelefono = crearPanelDetalle("📞 Teléfono:", "");
        lblTelefonoDetalle = (JLabel) panelTelefono.getComponent(1);
        panel.add(panelTelefono);
        panel.add(Box.createVerticalStrut(20));
        
        JPanel panelEmail = crearPanelDetalle("✉️ Email:", "");
        lblEmailDetalle = (JLabel) panelEmail.getComponent(1);
        panel.add(panelEmail);
        
        // Espaciador para empujar todo hacia arriba
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JPanel crearPanelDetalle(String etiqueta, String valor) {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 5));
        panel.setBackground(COLOR_BLANCO);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        
        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("Arial", Font.BOLD, 12));
        lblEtiqueta.setForeground(Color.GRAY);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.PLAIN, 16));
        
        panel.add(lblEtiqueta);
        panel.add(lblValor);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(COLOR_FONDO);
        
        // Botón Agregar
        JButton btnAgregar = crearBoton("➕ Agregar", COLOR_PRIMARIO);
        btnAgregar.addActionListener(this::agregarContacto);
        panel.add(btnAgregar);
        
        // Botón Eliminar
        JButton btnEliminar = crearBoton("🗑️ Eliminar", new Color(231, 76, 60));
        btnEliminar.addActionListener(this::eliminarContacto);
        panel.add(btnEliminar);
        
        // Botón Actualizar
        JButton btnActualizar = crearBoton("🔄 Actualizar", new Color(46, 204, 113));
        btnActualizar.addActionListener(e -> cargarContactos());
        panel.add(btnActualizar);
        
        return panel;
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(COLOR_BLANCO);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(130, 40));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(color.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }
    
    private void mostrarDetallesContacto() {
        Contacto seleccionado = listaContactos.getSelectedValue();
        if (seleccionado != null) {
            lblNombreDetalle.setText(seleccionado.getNombre());
            lblTelefonoDetalle.setText(seleccionado.getTelefono());
            lblEmailDetalle.setText(seleccionado.getEmail());
        }
    }
    
    private void cargarContactos() {
        try {
            modeloLista.clear();
            List<Contacto> contactos = contactoDAO.obtenerTodos();
            for (Contacto c : contactos) {
                modeloLista.addElement(c);
            }
            
            // Limpiar detalles
            lblNombreDetalle.setText("");
            lblTelefonoDetalle.setText("");
            lblEmailDetalle.setText("");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar contactos: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregarContacto(ActionEvent e) {
        // Crear panel personalizado para el diálogo
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField nombreField = new JTextField();
        JTextField telefonoField = new JTextField();
        JTextField emailField = new JTextField();
        
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefonoField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        
        int option = JOptionPane.showConfirmDialog(
            this, 
            panel, 
            "Nuevo Contacto", 
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (option == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText().trim();
            String telefono = telefonoField.getText().trim();
            String email = emailField.getText().trim();
            
            // Validación básica
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "El nombre es obligatorio", 
                    "Validación", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                Contacto nuevo = new Contacto(nombre, telefono, email);
                contactoDAO.agregarContacto(nuevo);
                cargarContactos();
                JOptionPane.showMessageDialog(this, 
                    "Contacto agregado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al agregar contacto: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarContacto(ActionEvent e) {
        Contacto seleccionado = listaContactos.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un contacto para eliminar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar a " + seleccionado.getNombre() + "?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                contactoDAO.eliminarContacto(seleccionado.getId());
                cargarContactos();
                JOptionPane.showMessageDialog(this, 
                    "Contacto eliminado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar contacto: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            // Configurar Look and Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new AgendaGUI().setVisible(true);
        });
    }
}
