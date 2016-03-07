/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Jano
 */
public class MenuAplicacion extends javax.swing.JFrame {

    String vacio = "";

    /**
     * Creates new form MenuAplicacion
     */
    public MenuAplicacion() {
        initComponents();
        vaciar();
        scrollCursos();
        scrollMaterias();
    }

    public void scrollCursos() {

        ResultSet rs = bbdd.MySql.listarCursos();
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();

        modelo.addElement("------");
        try {
            while (rs.next()) {
                String stringTemporal = (rs.getString(2));
                modelo.addElement(stringTemporal);
            }
            comboCursos.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(MenuAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void scrollMaterias() {

        //CAPTURAMOS EL VALOR DEL DESPLEGABLE
        //EN LOS DOS PRIMEROS COMBOBOX
        String vnomCurso = comboCursos.getSelectedItem().toString();
        String vAnho = comboAnho.getSelectedItem().toString();

        //LOS PASAMOS COMO PARÁMETROS DE LA CONSULTA
        ResultSet rs = bbdd.MySql.listarMaterias(vnomCurso, vAnho);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();

        //COMO ELEMENTO POR DEFECTO PONEMOS ALGO QUE NO DE 
        //RESULTADO EN EL QUERY
        modelo.addElement("------");
        try {
            //NOS DEVUELVE LOS CAMPOS DE LA BBD
            while (rs.next()) {
                String stringTemporal = (rs.getString(2));
                //LO AÑADIMOS AL COMBOBOX
                modelo.addElement(stringTemporal);
            }
            comboMateria.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(MenuAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void vaciar() {
        //VACIAMOS TODOS LOS CAMPOS
        fieldApellidos.setText("");
        fieldDni.setText("");
        fieldDireccion.setText("");
        fieldId.setText("");
        fieldNombre.setText("");
        fieldTelefono.setText("");
        fieldSexo.setText("");
        fieldApellidos2.setText("");
        fieldId2.setText("");
        fieldNombre2.setText("");

    }

    public void consultarAlum() {

        //COMPROBAMOS QUE LOS CAMPOS DE BÚSQUEDA ESTÉN A NULL
        //PUESTO QUE HAY DOS CAMPOS DE ID SI UNO ESTA A NULL
        // IGUALAMOS UNA VARIABLE AUXILIAR CON EL VALOR DE LA OTRA
        //Y VICEVERSA
        String IDAL = "";
        if (fieldId.getText() == null || fieldId.getText().equalsIgnoreCase("")) {
            IDAL = fieldId2.getText();
        } else if (fieldId2.getText() == null || fieldId2.getText().equalsIgnoreCase("")) {
            IDAL = fieldId.getText();
        } else if ((fieldId.getText() == null || fieldId.getText().equalsIgnoreCase("") && (fieldId2.getText() == null) || fieldId2.getText().equalsIgnoreCase(""))) {
            JOptionPane.showMessageDialog(null, "No has introducido un ID de búsqueda");
        }
        //CON EL VALOR ID EN LA VARIABLE LO PASAMOS COMO PARAMETRO PARA LA CONSULTA
        try {

            ResultSet rs = bbdd.MySql.consultar(IDAL);

            String[] datos = new String[7];
            //RELLENAMOS TODOS LOS CAMPOS DE LA APLICACION
            //CON LOS DATOS OBTENIDOS DE LA QUERY
            while (rs.next()) {
                datos[0] = rs.getString(1);
                fieldId2.setText(datos[0]);
                fieldId.setText(datos[0]);
                datos[1] = rs.getString(2);
                fieldNombre.setText(datos[1]);
                fieldNombre2.setText(datos[1]);
                datos[2] = rs.getString(3);
                fieldApellidos.setText(datos[2]);
                fieldApellidos2.setText(datos[2]);
                datos[3] = rs.getString(4);
                fieldTelefono.setText(datos[3]);
                datos[4] = rs.getString(5);
                fieldDireccion.setText(datos[4]);
                datos[5] = rs.getString(6);
                fieldSexo.setText(datos[5]);
                datos[6] = rs.getString(7);
                fieldDni.setText(datos[6]);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuAplicacion.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void tabla() {

        //CREAMOS USANDO UNA PREDETERMINADA UN MODELO PARA LA JTABLE
        DefaultTableModel modelo = new DefaultTableModel();
        //AÑADIMOS LA CABECERA DE LA TABLA
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Curso");
        modelo.addColumn("Materia");
        modelo.addColumn("Nota Trabajos");
        modelo.addColumn("Nota Teórica");
        modelo.addColumn("Nota Práctica");
        modelo.addColumn("Nota Final");
        //APLICAMOS EL MODELO A LA JTABLE QUE TENEMOS EN EL JFRAME
        tablaListaAlumnos.setModel(modelo);

        //CREAMOS UN ARRAY PARA INTRODUCIR LOS DATOS QUE NOS DEVUELVA LA QUERY
        //ESTE ARRAY SERÁ LA FUTURA LÍNEA (ROW) DE LA TABLA
        String[] datos = new String[9];

        try {
            com.mysql.jdbc.Statement st = (com.mysql.jdbc.Statement) bbdd.MySql.conexion.createStatement();
            ResultSet rs = st.executeQuery("select aa.id_alumno, aa.nombre, aa.apellidos,"
                    + " al.codigo_curso,al.codigo_materia,"
                    + " al.nota_trabajos,al.nota_teorica,al.nota_practica,al.nota_final"
                    + " from al_alumnos aa left join  al_materias al on aa.id_alumno=al.id_alumno order by aa.id_alumno");
            //POR CADA LÍNEA DEVUELTA DE LA QUERY LA INTRODUCIRÁ EN EL ARRAY
            //CREAMOS UN BUCLE
            while (rs.next()) {
                //IGUALAMOS CADA CAMPO DE LA QUERY A CADA UNA DE LAS POSICIONES DEL ARRAY
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = String.valueOf(rs.getFloat(6));
                datos[6] = String.valueOf(rs.getFloat(7));
                datos[7] = String.valueOf(rs.getFloat(8));
                datos[8] = String.valueOf(rs.getFloat(9));
                //AÑADIMOS LA LÍNEA A LA TABLA
                modelo.addRow(datos);
            }
            //PUESTO QUE EL CAMPO ID ES MUY PEQUEÑO NO NECESITAMOS UNA COLUMNA MUY ANCHA
            //MODIFICAMOS EL ANCHO DE LA MISMA
            TableColumn columna1 = tablaListaAlumnos.getColumnModel().getColumn(0);
            columna1.setPreferredWidth(35);
            columna1.setMaxWidth(35);
            columna1.setMinWidth(35);
            //VOLVEMOS A APLICAR EL MODELO PARA QUE SE APLIQUEN LOS CAMBIOS CON LAS NUEVAS LÍNEAS
            tablaListaAlumnos.setModel(modelo);

        } catch (SQLException ex) {
            Logger.getLogger(MenuAplicacion.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaListaAlumnos = new javax.swing.JTable();
        buttonActualizar = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        buttonExportar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fieldId = new javax.swing.JTextField();
        fieldNombre = new javax.swing.JTextField();
        fieldApellidos = new javax.swing.JTextField();
        fieldDni = new javax.swing.JTextField();
        fieldTelefono = new javax.swing.JTextField();
        fieldDireccion = new javax.swing.JTextField();
        buttonCargarFoto = new javax.swing.JButton();
        buttonLimpiar = new javax.swing.JButton();
        buttonAnhadir = new javax.swing.JButton();
        buttonEliminar = new javax.swing.JButton();
        fieldSexo = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        buttonConsultar = new javax.swing.JButton();
        buttonModificar = new javax.swing.JButton();
        lblFoto = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        buttonConsultaDatAca = new javax.swing.JButton();
        fieldId2 = new javax.swing.JTextField();
        fieldNombre2 = new javax.swing.JTextField();
        fieldApellidos2 = new javax.swing.JTextField();
        comboCursos = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        comboAnho = new javax.swing.JComboBox<>();
        comboMateria = new javax.swing.JComboBox<>();
        buttonCalcMaterias = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        fieldNotaTra = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        fieldNotaTeo = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        fieldNotaPrac = new javax.swing.JTextField();
        buttonCalcMedia = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        fieldNotaFinal = new javax.swing.JTextField();
        buttonNotasBase = new javax.swing.JButton();
        buttonLimpiar3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaListaAlumnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaListaAlumnos);

        buttonActualizar.setText("Actualizar");
        buttonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActualizarActionPerformed(evt);
            }
        });

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("LISTADO RESUMEN");
        jLabel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        buttonExportar.setText("Exportar");
        buttonExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(310, 310, 310)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(117, 315, Short.MAX_VALUE)
                .addComponent(buttonActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(180, 180, 180)
                .addComponent(buttonExportar)
                .addGap(52, 52, 52))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonExportar))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Listado", jPanel1);

        jLabel1.setText("ID:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Apellidos:");

        jLabel4.setText("DNI:");

        jLabel5.setText("Teléfono:");

        jLabel6.setText("Dirección:");

        jLabel7.setText("Sexo:");

        buttonCargarFoto.setText("Cargar Foto");
        buttonCargarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCargarFotoActionPerformed(evt);
            }
        });

        buttonLimpiar.setText("Limpiar");
        buttonLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLimpiarActionPerformed(evt);
            }
        });

        buttonAnhadir.setText("Añadir");
        buttonAnhadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAnhadirActionPerformed(evt);
            }
        });

        buttonEliminar.setText("Eliminar");
        buttonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEliminarActionPerformed(evt);
            }
        });

        buttonConsultar.setText("Consultar");
        buttonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConsultarActionPerformed(evt);
            }
        });

        buttonModificar.setText("Modificar");
        buttonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonModificarActionPerformed(evt);
            }
        });

        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoto.setText("Foto");
        lblFoto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(fieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fieldApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldDni, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fieldDireccion)
                                    .addComponent(fieldSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(buttonLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(buttonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(buttonAnhadir, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(buttonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(buttonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buttonCargarFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(lblFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(82, 82, 82))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 34, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(fieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(fieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(fieldApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(fieldDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(fieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(fieldDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCargarFoto)
                        .addGap(25, 25, 25)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(fieldSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAnhadir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos Personales", jPanel2);

        jLabel8.setText("ID:");

        jLabel9.setText("Apellidos:");

        jLabel10.setText("Nombre:");

        jLabel11.setText("Curso:");

        jLabel12.setText("Materias:");

        buttonConsultaDatAca.setText("Consultar");
        buttonConsultaDatAca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConsultaDatAcaActionPerformed(evt);
            }
        });

        jLabel13.setText("Curso:");

        comboAnho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "1", "2" }));

        buttonCalcMaterias.setText("Calcular Materias");
        buttonCalcMaterias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCalcMateriasActionPerformed(evt);
            }
        });

        jLabel14.setText("Nota Trabajos");

        jLabel15.setText("Nota Teórica");

        jLabel16.setText("Nota Práctica");

        fieldNotaPrac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldNotaPracActionPerformed(evt);
            }
        });

        buttonCalcMedia.setText("Calcular Media");
        buttonCalcMedia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCalcMediaActionPerformed(evt);
            }
        });

        jLabel17.setText("Nota Final");

        buttonNotasBase.setText("Añadir a la Base de Datos");
        buttonNotasBase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNotasBaseActionPerformed(evt);
            }
        });

        buttonLimpiar3.setText("Limpiar");
        buttonLimpiar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLimpiar3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(jLabel10))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel8)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(fieldNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(58, 58, 58)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(fieldApellidos2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(fieldId2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(270, 270, 270)
                                .addComponent(buttonCalcMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(comboMateria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(26, 26, 26)
                                        .addComponent(comboCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13)
                                .addGap(44, 44, 44)
                                .addComponent(comboAnho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(fieldNotaTra))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(fieldNotaTeo))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(fieldNotaPrac))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(145, 145, 145)
                                        .addComponent(jLabel17))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(buttonCalcMedia)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(fieldNotaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(30, 30, 30)
                                .addComponent(buttonNotasBase, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 22, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(buttonLimpiar3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonConsultaDatAca, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(211, 211, 211))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(fieldId2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(fieldNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldApellidos2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonLimpiar3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(buttonConsultaDatAca, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboCursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(comboAnho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(buttonCalcMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(32, 32, 32)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fieldNotaTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldNotaTeo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldNotaPrac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonCalcMedia)
                            .addComponent(fieldNotaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel17)
                    .addComponent(buttonNotasBase, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos Académicos", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAnhadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAnhadirActionPerformed
        //INVOCAMOS EL MÉTODO DE INSERCIÓN DE DATOS DEL PAQUETE BBDD
        bbdd.MySql.meterDatos(fieldNombre.getText(), fieldApellidos.getText(), fieldTelefono.getText(), fieldDireccion.getText(), fieldSexo.getText(), fieldDni.getText());
    }//GEN-LAST:event_buttonAnhadirActionPerformed

    private void buttonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActualizarActionPerformed
        tabla();
    }//GEN-LAST:event_buttonActualizarActionPerformed

    private void buttonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLimpiarActionPerformed
        vaciar();
    }//GEN-LAST:event_buttonLimpiarActionPerformed

    private void buttonConsultaDatAcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConsultaDatAcaActionPerformed
        consultarAlum();

    }//GEN-LAST:event_buttonConsultaDatAcaActionPerformed

    private void buttonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConsultarActionPerformed
        consultarAlum();

    }//GEN-LAST:event_buttonConsultarActionPerformed

    private void buttonCalcMateriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCalcMateriasActionPerformed
        scrollMaterias();
    }//GEN-LAST:event_buttonCalcMateriasActionPerformed

    private void fieldNotaPracActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNotaPracActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldNotaPracActionPerformed

    private void buttonNotasBaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNotasBaseActionPerformed

        //VARIABLES PARA QUE YO ME ENTERE
        //ADEMÁS NECESITAMOS CONVERTIRLO A LOS
        //TIPOS QUE VA A USAR MYSQL
        String id_alum = fieldId2.getText();
        String nom_curso = comboCursos.getSelectedItem().toString();
        String nom_materia = comboMateria.getSelectedItem().toString();
        float nota_pra = Float.parseFloat(fieldNotaPrac.getText());
        float nota_teo = Float.parseFloat(fieldNotaTeo.getText());
        float nota_tra = Float.parseFloat(fieldNotaTra.getText());
        float nota_fin = Float.parseFloat(fieldNotaFinal.getText());

        //LLAMAMOS AL MÉTODO QUE INTRODUCIRÁ LOS DATOS
        bbdd.MySql.anhadirNotas(id_alum, nom_curso, nom_materia, nota_teo, nota_pra, nota_tra, nota_fin);

    }//GEN-LAST:event_buttonNotasBaseActionPerformed

    private void buttonCalcMediaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCalcMediaActionPerformed

        //CONVERTIMOS TODO A FLOAT
        float nota_pra = Float.parseFloat(fieldNotaPrac.getText());
        float nota_teo = Float.parseFloat(fieldNotaTeo.getText());
        float nota_tra = Float.parseFloat(fieldNotaTra.getText());
        //REALIZAMOS EL CÁLCULO DE LA MEDIA
        float nota_fin = (float) (Math.round(((nota_pra * 0.4) + (nota_teo * 0.4) + (nota_tra * 0.2)) * 100.0) / 100.0);
        //LO PASAMOS AL CAMPO DE LA INTERFAZ GRÁFICA
        fieldNotaFinal.setText(String.valueOf(nota_fin));
    }//GEN-LAST:event_buttonCalcMediaActionPerformed

    private void buttonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEliminarActionPerformed
        String idAlumno = fieldId2.getText();
        bbdd.MySql.eliminarAlumno(idAlumno);
    }//GEN-LAST:event_buttonEliminarActionPerformed

    private void buttonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonModificarActionPerformed
        //ESTABLECEMOS LAS VARIABLES QUE PASAREMOS POR PARÁMETRO
        String apellidos = fieldApellidos.getText();
        String dni = fieldDni.getText();
        String direccion = fieldDireccion.getText();
        String id = fieldId.getText();
        String nombre = fieldNombre.getText();
        String telefono = fieldTelefono.getText();
        String sexo = fieldSexo.getText();

        //INVOCAMOS AL MÉTODO DE MODIFICACION
        bbdd.MySql.modificarAlumno(id, nombre, apellidos, telefono, direccion, sexo, dni);

    }//GEN-LAST:event_buttonModificarActionPerformed

    private void buttonLimpiar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLimpiar3ActionPerformed
        vaciar();
    }//GEN-LAST:event_buttonLimpiar3ActionPerformed

    //PARA PODER CARGAR LA FOTO NECESITAOS UNA VARIABLE FILE
    File fotillo;

    private void buttonCargarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCargarFotoActionPerformed

        int resultado;
        CargarFoto foto1=new CargarFoto();

        resultado = foto1.jfchCargarfoto.showOpenDialog(null);
            fotillo = foto1.jfchCargarfoto.getSelectedFile();
            try {
                ImageIcon icon = new ImageIcon(fotillo.toString());
                Icon icono = new ImageIcon(icon.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_DEFAULT));
                lblFoto.setText(null);
                lblFoto.setIcon(icono);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error abriendo la imagen " + ex);
            }

    }//GEN-LAST:event_buttonCargarFotoActionPerformed

    private void buttonExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExportarActionPerformed
       //VERIFICAMOS QUE HAY DATOS EN LA TABLA
         if (this.tablaListaAlumnos.getRowCount()==0) {
            JOptionPane.showMessageDialog (null, "No hay datos en la tabla para exportar.","BCO",
                JOptionPane.INFORMATION_MESSAGE);
        }
         else{JOptionPane.showMessageDialog(null, "Hay datos");}
      
    String archivo = "C:\\Users\\Jano\\Desktop\\listado.txt";
        File salidaTXT = new File(archivo);
        PrintWriter ps = null;
        try {
            ps = new PrintWriter(archivo);
            
           int numFilas=tablaListaAlumnos.getRowCount();
           int numColumn=tablaListaAlumnos.getColumnCount();
           
           for(int i=0;i<numFilas;i++ ){
               for (int j=0;j<numColumn;j++){
                   
            ps.print(tablaListaAlumnos.getModel().getValueAt(i, j)+", ");
            
           }
               ps.println("\n");
           }
            System.out.println("Se ha generado un fichero");
            ps.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el archivo " + archivo);
        }
       
        
    }//GEN-LAST:event_buttonExportarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonActualizar;
    private javax.swing.JButton buttonAnhadir;
    private javax.swing.JButton buttonCalcMaterias;
    private javax.swing.JButton buttonCalcMedia;
    private javax.swing.JButton buttonCargarFoto;
    private javax.swing.JButton buttonConsultaDatAca;
    private javax.swing.JButton buttonConsultar;
    private javax.swing.JButton buttonEliminar;
    private javax.swing.JButton buttonExportar;
    private javax.swing.JButton buttonLimpiar;
    private javax.swing.JButton buttonLimpiar3;
    private javax.swing.JButton buttonModificar;
    private javax.swing.JButton buttonNotasBase;
    private javax.swing.JComboBox<String> comboAnho;
    private javax.swing.JComboBox<String> comboCursos;
    private javax.swing.JComboBox<String> comboMateria;
    private javax.swing.JTextField fieldApellidos;
    private javax.swing.JTextField fieldApellidos2;
    private javax.swing.JTextField fieldDireccion;
    private javax.swing.JTextField fieldDni;
    private javax.swing.JTextField fieldId;
    private javax.swing.JTextField fieldId2;
    private javax.swing.JTextField fieldNombre;
    private javax.swing.JTextField fieldNombre2;
    private javax.swing.JTextField fieldNotaFinal;
    private javax.swing.JTextField fieldNotaPrac;
    private javax.swing.JTextField fieldNotaTeo;
    private javax.swing.JTextField fieldNotaTra;
    private javax.swing.JTextField fieldSexo;
    private javax.swing.JTextField fieldTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JTable tablaListaAlumnos;
    // End of variables declaration//GEN-END:variables
}
