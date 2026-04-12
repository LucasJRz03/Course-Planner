package main.Swing;

/**
 *
 * @author rodri
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import main.Clases.Alumno;
import main.Clases.Cursada;
import main.PatronState.Exception.ExceptionEstadoCursada;

public class PanelGestionCursadas extends JPanel {
    private JComboBox<Alumno> comboAlumnos;
    private JTable tablaCursadas;
    private DefaultTableModel modeloTabla;


    //Al recibir el modelo por parametro, con DefaultComboBoxModel, asegura que
    //si se registra un alumno, aparezca aca al instante (Patrón Observer). 
    public PanelGestionCursadas(DefaultComboBoxModel<Alumno> modelo) {
        this.comboAlumnos = new JComboBox<>(modelo);
        setLayout(new BorderLayout(10, 10));

        // --- PARTE SUPERIOR: SELECCIÓN DEL ALUMNO ---
        JPanel pnlNorte = new JPanel();
 
        pnlNorte.add(new JLabel("Seleccionar Alumno:"));
        pnlNorte.add(comboAlumnos);
        
        JButton btnCargar = new JButton("Cargar Historial");
        pnlNorte.add(btnCargar);
        add(pnlNorte, BorderLayout.NORTH);

        // --- PARTE CENTRAL: TABLA DEL HISTORIAL---
        String[] columnas = {"Materia", "Año", "Nota Parcial", "Estado Actual"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaCursadas = new JTable(modeloTabla);
        add(new JScrollPane(tablaCursadas), BorderLayout.CENTER);

        // --- PARTE INFERIOR: BOTONES DEL ESTADO ---
        JPanel pnlSur = new JPanel(new FlowLayout());
        JButton btnCalificar = new JButton("Calificar Parcial");
        JButton btnPromocionar = new JButton("Promocionar");
        JButton btnAprobarCur = new JButton("Aprobar Cursada");
        JButton btnDesaprobarCur = new JButton("Desaprobar Cursada");
        
        pnlSur.add(btnCalificar);
        pnlSur.add(btnPromocionar);
        pnlSur.add(btnAprobarCur);
        pnlSur.add(btnDesaprobarCur);
        add(pnlSur, BorderLayout.SOUTH);
        
        // --- LOGICA DE LOS BOTONES--
        
        //Carga el historial academico del alumno seleccionado en la tabla
        btnCargar.addActionListener(e -> actualizarTabla());

        //Transicion delegada al patrón State:
        
        //Califica el parcial y cambia su estado 
        btnCalificar.addActionListener(e -> {
            Cursada seleccionada = getCursadaSeleccionada();
            if (seleccionada != null) {
                String notaStr = JOptionPane.showInputDialog("Ingrese nota del parcial:");
                if (notaStr != null) {
                    try {
                        int nota = Integer.parseInt(notaStr);
                        seleccionada.setNotaParcial(nota);
                        // Transición: Inscripto -> ParcialAprobado/Desaprobado 
                        seleccionada.calificarParcial(nota >= 4); 
                        actualizarTabla();
                    } catch (Exception ex) {
                        mostrarError(ex.getMessage());
                    }
                }
            }
        });
        
       // Transición: ParcialAprobado -> Promocionada  (solo si cumple los requisitos) 
        btnPromocionar.addActionListener(e -> {
            Cursada seleccionada = getCursadaSeleccionada();
            if (seleccionada != null) {
                try {
                    seleccionada.promocionar();
                    actualizarTabla();
                    JOptionPane.showMessageDialog(this, "¡Materia Promocionada!");
                } catch (ExceptionEstadoCursada ex) {
                  mostrarError(ex.getMessage());
                }
            }
        });
        
        // ParcialAprobado -> CursadaAprobada
        btnAprobarCur.addActionListener(e -> {
    Cursada seleccionada = getCursadaSeleccionada();
    if (seleccionada != null) {
        try {
            seleccionada.aprobarCursada();           
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Cursada aprobada (regularizada).");
        } catch (ExceptionEstadoCursada ex) {
            // Si el alumno está 'Inscripto' o 'Promocionada', el State lanzará el error
            mostrarError(ex.getMessage());
        }
    }
});
        
        //ParcialDesaprobado -> CursadaDesaprobada
        btnDesaprobarCur.addActionListener(e -> {
            Cursada seleccionada = getCursadaSeleccionada(); 
            if(seleccionada != null){
                try{                   
                    seleccionada.reprobarCursada();
                    actualizarTabla();
                    JOptionPane.showMessageDialog(this, "Cursada desaprobada, debe recursar");
                }catch(ExceptionEstadoCursada ex){
                    mostrarError(ex.getMessage());
                }
            }
        });
    }

    //Refresca la tabla con los datos actuales del alumno
    private void actualizarTabla() {
        Alumno a = (Alumno) comboAlumnos.getSelectedItem();
        modeloTabla.setRowCount(0);
        if (a != null) {
            for (Cursada c : a.getHistorialCursadas()) {
                Object[] fila = {
                    c.getMateria().getNombre(),
                    c.getMateria().getAnio(),
                    c.getNotaParcial(),
                    c.getEstado().getClass().getSimpleName() // Muestra el nombre del estado
                };
                modeloTabla.addRow(fila);
            }
        }
    }
    
    //Obtiene la cursada que se selecciono en la tabla
    private Cursada getCursadaSeleccionada() {
        int fila = tablaCursadas.getSelectedRow();
        if (fila == -1) {
            mostrarError("Debe seleccionar una cursada de la tabla.");
            return null;
        }
        Alumno a = (Alumno) comboAlumnos.getSelectedItem();
        return a.getHistorialCursadas().get(fila);
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error de Estado", JOptionPane.ERROR_MESSAGE);
    }
    
}