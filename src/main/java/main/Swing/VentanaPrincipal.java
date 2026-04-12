package main.Swing;

import main.Util.ConfiguradorDemo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import main.Clases.Alumno;
import main.Clases.Carrera;
import main.Clases.Cursada;
import main.Clases.Materia;
import main.PatronBuilder.PlanDeEstudios;

import main.PatronStrategy.CondicionInscripcionA;
import main.PatronStrategy.CondicionInscripcionB;
import main.PatronStrategy.CondicionInscripcionC;
import main.PatronStrategy.CondicionInscripcionD;
import main.PatronStrategy.CondicionInscripcionE;
import main.PatronStrategy.ICondicionInscripcion;

/**
 *
 * @author rodri
 */
public class VentanaPrincipal extends JFrame {
    private JTabbedPane pestanias;
    private List<Alumno> listaAlumnos = new ArrayList<>(); 
    private List<Carrera> listaCarreras = new ArrayList<>();
    
    //DefaultComboBoxModel me permite que una misma carrera sea seleccionada para diferentes alumnos.
    //Ya que implementa el patron Observer
    private DefaultComboBoxModel<Alumno> modeloComboAlumnos = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel<Carrera> modeloComboCarreras = new DefaultComboBoxModel<>();
    
    public VentanaPrincipal(){
        setTitle("Sistema de Gestión Académico");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //Centra la ventana
        
        //Carga los datos de prueba 
        cargarDatosPrueba();
        
        pestanias = new JTabbedPane();
        
        // 1. Pestania para Alta de Alumnos
        pestanias.addTab("Alumnos", crearPanelAlumnos());
        
        // 2. Pestania para Alta de Carreras y Planes de estudio
        pestanias.addTab("Carreras", crearPanelCarreras());
        
        // 3. Pestania para Inscripciones(Carreras y Materias)
        pestanias.addTab("Inscripciones", crearPanelInscripciones());
        
        // 4. Pestania para verificar graduacion
        pestanias.addTab("Estado Academico", crearPanelEstadoAcademico());
        
        //5. Pestania de gestion de notas de los alumnos
        pestanias.addTab("Gestion de Notas", new PanelGestionCursadas(modeloComboAlumnos));
        
        
        add(pestanias, BorderLayout.CENTER);
        
        //Boton para la configuracion estática de la demo
        JButton btnDemo = new JButton("Cargar datos (DEMO)");
        
        btnDemo.addActionListener(e-> cargarDatosPrueba());
        add(btnDemo, BorderLayout.SOUTH);  
    }
   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    private JPanel crearPanelAlumnos() {
        JPanel panel = new JPanel(new GridBagLayout()); //Para que quede centrado
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        
        //Campos de entrada
        JTextField txtNombre = new JTextField(25);
        JTextField txtLegajo = new JTextField(25);
        JButton btnGuardar = new JButton("Registrar Alumno"); 
        
        //Armado del formulario
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre: "), gbc);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; 
        panel.add(new JLabel("Legajo: "), gbc);
        gbc.gridx = 1; 
        panel.add(txtLegajo, gbc);
        
        gbc.gridx = 1; gbc.gridy = 2; 
        panel.add(btnGuardar, gbc);
        
        btnGuardar.addActionListener(e -> {
            try{
                String nombre = txtNombre.getText();
                int legajo = Integer.parseInt(txtLegajo.getText());
                
                Alumno nuevo = new Alumno(legajo, nombre);
                listaAlumnos.add(nuevo);
                modeloComboAlumnos.addElement(nuevo);
                
                JOptionPane.showMessageDialog(this, "Alumno registrado: "+ nombre);
                txtNombre.setText(" ");
                txtLegajo.setText(" ");
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "El legajo debe ser un número.");
            }
        });
                return panel;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private ICondicionInscripcion obtenerEstrategia(int index){
        switch(index){
            case 0: return new CondicionInscripcionA(); 
            case 1: return new CondicionInscripcionB(); 
            case 2: return new CondicionInscripcionC(); 
            case 3: return new CondicionInscripcionD();
            case 4: return new CondicionInscripcionE(); 
            default: return new CondicionInscripcionA();
        }
    }
   private JPanel crearPanelCarreras() {
    JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
    
    // 1. LISTA TEMPORAL (El "Borrador" del Plan)
    DefaultListModel<Materia> modeloMateriasPlan = new DefaultListModel<>();
    JList<Materia> listaMateriasPlan = new JList<>(modeloMateriasPlan);
    
    // 2. PANEL DE CARGA DE MATERIAS
    JPanel pnlNuevaMateria = new JPanel(new GridBagLayout());
    pnlNuevaMateria.setBorder(BorderFactory.createTitledBorder("Agregar Materia al Plan"));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new java.awt.Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Componentes de entrada
    JTextField txtCodMat = new JTextField(15);
    JTextField txtNomMat = new JTextField(20);
    JSpinner spnAnioMat = new JSpinner(new SpinnerNumberModel(1, 1, 6, 1));
    JSpinner spnCuatrim = new JSpinner(new SpinnerNumberModel(1, 1, 6, 1));
    JCheckBox chkEsObligatoria = new JCheckBox("Es Obligatoria", true);
    JCheckBox chkPromocion = new JCheckBox("Permite Promoción", true);
    JButton btnAgregarAlPlan = new JButton("Añadir Materia al Listado");
    JButton btnEliminarMat = new JButton("Eliminar Materia Seleccionada");
    btnEliminarMat.setBackground(new Color(255, 200, 200));
    
    // Selector de correlativas
    JList<Materia> listaSelectorCorrelativas = new JList<>(modeloMateriasPlan);
    listaSelectorCorrelativas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    JScrollPane scrollCorrelativas = new JScrollPane(listaSelectorCorrelativas);
    scrollCorrelativas.setPreferredSize(new Dimension(150, 100));

    // --- POSICIONAMIENTO EN EL PANEL (Izquierda: Datos) ---
    gbc.gridx = 0; gbc.gridy = 0; pnlNuevaMateria.add(new JLabel("Código:"), gbc);
    gbc.gridx = 1; pnlNuevaMateria.add(txtCodMat, gbc);

    gbc.gridx = 0; gbc.gridy = 1; pnlNuevaMateria.add(new JLabel("Nombre:"), gbc);
    gbc.gridx = 1; pnlNuevaMateria.add(txtNomMat, gbc);

    gbc.gridx = 0; gbc.gridy = 2; pnlNuevaMateria.add(new JLabel("Año:"), gbc);
    gbc.gridx = 1; pnlNuevaMateria.add(spnAnioMat, gbc);
    
    gbc.gridx= 0; gbc.gridy = 3; pnlNuevaMateria.add(new JLabel("Cuatrim:"), gbc); 
    gbc.gridx= 1; pnlNuevaMateria.add(spnCuatrim, gbc);

    gbc.gridx = 0; gbc.gridy = 4; pnlNuevaMateria.add(chkEsObligatoria, gbc);
    gbc.gridx = 1; pnlNuevaMateria.add(chkPromocion, gbc);

    // --- POSICIONAMIENTO (Derecha: Correlativas) ---
    gbc.gridx = 2; 
    gbc.gridy = 0; 
    gbc.gridheight = 1;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    pnlNuevaMateria.add(new JLabel("Seleccionar Correlativas:"), gbc);
    
    gbc.gridx = 2; 
    gbc.gridy = 1; 
    gbc.gridheight = 3; //La lista ocupa 3 filas
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.BOTH; //Hace que se estire tanto como a lo ancho y largo
    pnlNuevaMateria.add(scrollCorrelativas, gbc);

    // Botón abajo de todo
    gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3; gbc.gridheight = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    pnlNuevaMateria.add(btnAgregarAlPlan, gbc);
    
    gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 3;
    pnlNuevaMateria.add(btnEliminarMat, gbc);

    // 3. PANEL DE CONFIGURACIÓN DE CARRERA
    JPanel pnlConfigCarrera = new JPanel(new GridLayout(4, 2, 5, 5));
    pnlConfigCarrera.setBorder(BorderFactory.createTitledBorder("Finalizar Carrera"));
    
    JTextField txtNomCarrera = new JTextField();
    String[] estrategias = {"Condición A", "Condición B", "Condición C", "Condición D", "Condición E"};
    JComboBox<String> comboEstrategias = new JComboBox<>(estrategias);
    JSpinner spnOptativasReq = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1));
    JButton btnCrearTodo = new JButton("GUARDAR CARRERA Y PLAN");

    pnlConfigCarrera.add(new JLabel("Nombre Carrera:")); pnlConfigCarrera.add(txtNomCarrera);
    pnlConfigCarrera.add(new JLabel("Estrategia:")); pnlConfigCarrera.add(comboEstrategias);
    pnlConfigCarrera.add(new JLabel("Optativas Req:")); pnlConfigCarrera.add(spnOptativasReq);
    pnlConfigCarrera.add(new JLabel("")); pnlConfigCarrera.add(btnCrearTodo);

    // --- LÓGICA DE BOTONES ---

    btnAgregarAlPlan.addActionListener(e -> {
        if (!txtCodMat.getText().isEmpty() && !txtNomMat.getText().isEmpty()) {
            List<Materia> seleccionadas = listaSelectorCorrelativas.getSelectedValuesList();
            List<Materia> correlativasParaNueva = new ArrayList<>(seleccionadas);
            
            // Se asignan las correlativas seleccionadas al constructor de la Materia
            Materia m = new Materia(
                txtCodMat.getText(),
                txtNomMat.getText(),
                (int) spnAnioMat.getValue(),
                (int) spnCuatrim.getValue(),
                chkEsObligatoria.isSelected(),
                correlativasParaNueva, 
                chkPromocion.isSelected()
            );
            
            modeloMateriasPlan.addElement(m);
            listaSelectorCorrelativas.clearSelection();
            txtCodMat.setText(""); txtNomMat.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Completá los datos de la materia.");
        }
    });
    
    btnEliminarMat.addActionListener(e -> {
        //Busca el indice de la materia seleccionada en el JList
    int selectedIndex = listaMateriasPlan.getSelectedIndex();
    
    if (selectedIndex != -1) {
        // La borramos del modelo de la lista
        modeloMateriasPlan.remove(selectedIndex); //
        JOptionPane.showMessageDialog(this, "Materia eliminada  del borrador.");
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, seleccioná una materia de la lista de arriba.");
        }
    });

    btnCrearTodo.addActionListener(e -> {
        if (txtNomCarrera.getText().isEmpty() || modeloMateriasPlan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La carrera necesita un nombre y al menos una materia.");
            return;
        }

        PlanDeEstudios.Builder builder = new PlanDeEstudios.Builder();
        builder.setCondicion(obtenerEstrategia(comboEstrategias.getSelectedIndex()));
        builder.agregarOptativasRequeridas((int) spnOptativasReq.getValue());

        for (int i = 0; i < modeloMateriasPlan.size(); i++) {
            Materia m = modeloMateriasPlan.getElementAt(i);
            if (m.isEsObligatorio()) {
                builder.agregarMObligatorias(m);
            } else {
                builder.agregarMOptativas(m);
            }
        }

        PlanDeEstudios planFinal = builder.build();
        
        for (int i = 0; i < modeloMateriasPlan.size(); i++) {
            modeloMateriasPlan.getElementAt(i).setPlan(planFinal);
        }

        Carrera nuevaCarrera = new Carrera(txtNomCarrera.getText(), planFinal);
        listaCarreras.add(nuevaCarrera);
        modeloComboCarreras.addElement(nuevaCarrera);

        JOptionPane.showMessageDialog(this, "Carrera '" + nuevaCarrera.getNombre() + "' creada.");
        modeloMateriasPlan.clear();
        txtNomCarrera.setText("");
    });

    JPanel pnlIzquierdo = new JPanel(new BorderLayout());
    pnlIzquierdo.add(pnlNuevaMateria, BorderLayout.NORTH);
    pnlIzquierdo.add(new JScrollPane(listaMateriasPlan), BorderLayout.CENTER);
    
    panelPrincipal.add(pnlIzquierdo, BorderLayout.WEST);
    panelPrincipal.add(pnlConfigCarrera, BorderLayout.SOUTH);

    return panelPrincipal;
}
    
    private JPanel crearPanelInscripciones(){
       JPanel panel = new JPanel(new BorderLayout());
       
       //Panel Superior: Seleccion de Alumno y Carrera
       JPanel pnlSeleccion = new JPanel(new GridLayout(2,2,5,5));
       
       //comboAlumnos y ComboCarreras serian los Observadores de ambos modeloComboX 
       JComboBox<Alumno> comboAlumnos = new JComboBox<>(modeloComboAlumnos); 
       JComboBox<Carrera> comboCarreras = new JComboBox<>(modeloComboCarreras);

       pnlSeleccion.add(new JLabel("Seleccion de Alumno:"));
       pnlSeleccion.add(comboAlumnos);
       pnlSeleccion.add(new JLabel("Seleccion de Carrera:"));
       pnlSeleccion.add(comboCarreras);
       
       //Panel central: lista de materias disponibles.
       DefaultListModel<Materia> modeloLista = new DefaultListModel<>(); 
       JList<Materia> listaMateriasDisponibles = new JList<>(modeloLista);
       panel.add(new JScrollPane(listaMateriasDisponibles), BorderLayout.CENTER);
       
       //Panel inferior: Botones de acción
       JPanel pnlBotones = new JPanel();
       JButton btnInscribirCarrera = new JButton("Inscribir a Carrera");
       JButton btnConsultar = new JButton("Ver Materias disponibles");
       JButton btnInscripcionMateria = new JButton("Inscribir a Materia Seleccionada");
       
       pnlBotones.add(btnInscribirCarrera); 
       pnlBotones.add(btnConsultar);
       pnlBotones.add(btnInscripcionMateria);
       
       panel.add(pnlSeleccion, BorderLayout.NORTH);
       panel.add(pnlBotones, BorderLayout.SOUTH);
       
       //Logica del boton para inscribir alumnos a una carrera
       btnInscribirCarrera.addActionListener(e -> {
        Alumno a = (Alumno) comboAlumnos.getSelectedItem();
        Carrera c = (Carrera) comboCarreras.getSelectedItem();
        
        if (a != null && c != null) {
            // Verificamos si ya está anotado para no duplicar
            if (!a.getCarrerasInscriptas().contains(c)) {
                a.getCarrerasInscriptas().add(c); 
                JOptionPane.showMessageDialog(this, a.getNombre() + " ahora es alumno de: " + c.getNombre());
            } else {
                JOptionPane.showMessageDialog(this, "El alumno ya pertenece a esta carrera.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione alumno y carrera primero.");
        }
    });
       
       
       // Logica de filtrado 
       btnConsultar.addActionListener(e -> {
           Alumno a = (Alumno) comboAlumnos.getSelectedItem();
           Carrera c = (Carrera) comboCarreras.getSelectedItem();
           
           if(a != null && c != null){
               if(a.getCarrerasInscriptas().contains(c)) {
               
               modeloLista.clear();
               PlanDeEstudios plan = c.getPlanDeEstudios();
               //Obtiene la estrategia definida en el plan (A,B,C,D,E) 
               ICondicionInscripcion estrategia = plan.getCondicionInscripcion();
               
               //Recorrer todas las materias obligatorias
               for(Materia m : plan.getMateriasObligatorias()){
                   if(estrategia.puedeInscribirse(a, m) && a.puedeInscribirsePorEstado(m)){
                       modeloLista.addElement(m);
                   }
               }
               for(Materia o : plan.getMateriasOptativas()){
                   if(estrategia.puedeInscribirse(a, o) && a.puedeInscribirsePorEstado(o)){
                       modeloLista.addElement(o);
                   }
               }
               
               } else {
                 JOptionPane.showMessageDialog(this, 
                    "Acceso Denegado: El alumno debe inscribirse a la carrera '" + c.getNombre() + "' primero.", 
                    "Error de Inscripción", 
                    JOptionPane.WARNING_MESSAGE);
                 modeloLista.clear(); // Limpiamos por las dudas
                }
               }
       });
       
       // Logica de Inscripcion
       btnInscripcionMateria.addActionListener(e -> {
           Materia seleccionada = listaMateriasDisponibles.getSelectedValue();
           Alumno alumno = (Alumno) comboAlumnos.getSelectedItem();
           
           if( seleccionada != null && alumno != null ){
               //Crea la cursada e inica en estado Inscripto (del patron state)
               Cursada nuevaCursada = new Cursada(seleccionada);
               alumno.getHistorialCursadas().add(nuevaCursada);
               
               JOptionPane.showMessageDialog(this, "Inscripcion exitosa a: "+ seleccionada);
               modeloLista.removeElement(seleccionada); //La sacamos de la lista.
           }
       });
      
       return panel;
    }
    
    private JPanel crearPanelEstadoAcademico(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        //Componentes de seleccion
       JComboBox<Alumno> comboAlumnos = new JComboBox<>(modeloComboAlumnos); 
       JComboBox<Carrera> comboCarreras = new JComboBox<>(modeloComboCarreras);
        JButton btnVerificar = new JButton("Verificar Graduacion");
        
        //Etiqueta de resultado 
        JLabel lblResultado = new JLabel("Seleccione un alumno para verificar su estado.", JLabel.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Armado del Panel
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Alumno:"), gbc);
        gbc.gridx = 1; panel.add(comboAlumnos, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Carrera:"), gbc);
        gbc.gridx = 1; panel.add(comboCarreras, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(btnVerificar, gbc);

        gbc.gridy = 3;
        panel.add(lblResultado, gbc);
        
        //Logica de verificacion
        btnVerificar.addActionListener(e -> {
            Alumno alumno = (Alumno) comboAlumnos.getSelectedItem();
            Carrera carrera = (Carrera) comboCarreras.getSelectedItem();

            if (alumno != null && carrera != null) {
                PlanDeEstudios plan = carrera.getPlanDeEstudios(); 
             
                String reporte = plan.obtenerReporteGraduacion(alumno);
                
                JOptionPane.showMessageDialog(this, reporte, "Estado Académico de " + alumno.getNombre(), JOptionPane.INFORMATION_MESSAGE);
                  
                boolean seGraduo = plan.verificarGraduacion(alumno);
                if(seGraduo){
                    lblResultado.setText("ESTADO: Graduado.");
                    lblResultado.setForeground(new Color(0, 128, 0)); /*e da un color verde*/
                }else {
                    lblResultado.setText("ESTADO: En curso (ver detalles en reporte)");
                    lblResultado.setForeground(Color.RED);
                }
            }
        });
       
        return panel; 
    }
    
    private void cargarDatosPrueba() {
        ConfiguradorDemo.cargarDatos(modeloComboAlumnos, modeloComboCarreras);
    }
}
