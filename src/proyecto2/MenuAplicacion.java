package proyecto2;

import JanilloMySql.JanilloMySqlntb;
import bbdd.MySql;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Jano
 */
public class MenuAplicacion extends javax.swing.JFrame {

    /**
     * Declaramos las variables que se emplearán a lo largo de esta clase
     */
    String vacio = "";
    private String pathFoto;
    File fileImportacion;
    File fileFoto;
    static JanilloMySqlntb connect = new JanilloMySqlntb();

    /**
     * Creates new form MenuAplicacion inicializando lo campos que nos interesan.
     */
    public MenuAplicacion() {
        initComponents();
        vaciar();
        scrollCursos();
        scrollMaterias();
    }

    /**
     * Rescata la consulta a la bbd para introducir el resultado en el combobox
     */
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
/**
     * Rescata la consulta a la bbd para introducir el resultado en el combobox
     */
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
/**
 * Deja en blanco todos los inputtext de la aplicación
 */
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
        lblFoto.setIcon(null);
        lblFoto.setText("FOTO");
        fieldNotaFinal.setText("");
        fieldNotaPrac.setText("");
        fieldNotaTeo.setText("");
        fieldNotaTra.setText("");

    }
/**
 * Recoge los datos de la bbdd y los muestra en cada uno de los campos de la aplicación
 */
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
        } else {
            JOptionPane.showMessageDialog(null, "No has introducido un ID de búsqueda");
        }
        //CON EL VALOR ID EN LA VARIABLE LO PASAMOS COMO PARAMETRO PARA LA CONSULTA
        try {

            ResultSet rs = bbdd.MySql.consultar(IDAL);

            String[] datos = new String[8];
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
                datos[7] = rs.getString(8);
                String rutaImagen = datos[7];
                meterFoto(rutaImagen);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuAplicacion.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }
/**
 * Pone un ICON en la etiqueta específica a través d euna ruta
 * @param ruta lugar donde se encuentra la imagen
 */
    public static void meterFoto(String ruta) {
        Icon fotillo = new ImageIcon(ruta);
        lblFoto.setText("");
        lblFoto.setIcon(fotillo);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSeparator5 = new javax.swing.JSeparator();
        panelPrincipal = new javax.swing.JTabbedPane();
        panelDatosPersonales = new javax.swing.JPanel();
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
        panelDatosAcademicos = new javax.swing.JPanel();
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
        panelListado = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaListaAlumnos = new javax.swing.JTable();
        buttonActualizar = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        buttonExportar = new javax.swing.JButton();
        buttonImportar = new javax.swing.JToggleButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        javax.swing.GroupLayout panelDatosPersonalesLayout = new javax.swing.GroupLayout(panelDatosPersonales);
        panelDatosPersonales.setLayout(panelDatosPersonalesLayout);
        panelDatosPersonalesLayout.setHorizontalGroup(
            panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(fieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2))
                                        .addGap(24, 24, 24)
                                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(fieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4))
                                        .addGap(18, 18, 18)
                                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(fieldApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fieldDni, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7))
                                        .addGap(18, 18, 18)
                                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(fieldDireccion)
                                            .addComponent(fieldSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(buttonLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(buttonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(buttonAnhadir, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(buttonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(buttonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(buttonCargarFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                    .addComponent(lblFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(82, 82, 82))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDatosPersonalesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 54, Short.MAX_VALUE))
        );
        panelDatosPersonalesLayout.setVerticalGroup(
            panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(fieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(fieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(fieldApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(fieldDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(fieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosPersonalesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(fieldDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosPersonalesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCargarFoto)
                        .addGap(25, 25, 25)))
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(fieldSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAnhadir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        panelPrincipal.addTab("Datos Personales", panelDatosPersonales);

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

        javax.swing.GroupLayout panelDatosAcademicosLayout = new javax.swing.GroupLayout(panelDatosAcademicos);
        panelDatosAcademicos.setLayout(panelDatosAcademicosLayout);
        panelDatosAcademicosLayout.setHorizontalGroup(
            panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(fieldId2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(fieldNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel9)
                        .addGap(38, 38, 38)
                        .addComponent(fieldApellidos2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                        .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                                .addGap(270, 270, 270)
                                .addComponent(buttonCalcMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(26, 26, 26)
                                        .addComponent(comboCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel13)
                                        .addGap(44, 44, 44)
                                        .addComponent(comboAnho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                                        .addGap(87, 87, 87)
                                        .addComponent(jLabel12)
                                        .addGap(26, 26, 26)
                                        .addComponent(comboMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(fieldNotaTra))
                                .addGap(30, 30, 30)
                                .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(fieldNotaTeo))
                                .addGap(28, 28, 28)
                                .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(fieldNotaPrac))
                                .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                                        .addGap(145, 145, 145)
                                        .addComponent(jLabel17))
                                    .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(buttonCalcMedia)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(fieldNotaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(30, 30, 30)
                                .addComponent(buttonNotasBase, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosAcademicosLayout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(buttonLimpiar3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonConsultaDatAca, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(211, 211, 211))
        );
        panelDatosAcademicosLayout.setVerticalGroup(
            panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldId2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(fieldNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(fieldApellidos2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(comboCursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(comboAnho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(buttonCalcMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(32, 32, 32)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                                .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(fieldNotaTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldNotaTeo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldNotaPrac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonCalcMedia)
                                    .addComponent(fieldNotaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel17)
                            .addComponent(buttonNotasBase, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelDatosAcademicosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelDatosAcademicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonLimpiar3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonConsultaDatAca, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        panelPrincipal.addTab("Datos Académicos", panelDatosAcademicos);

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

        buttonImportar.setText("Importar");
        buttonImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonImportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelListadoLayout = new javax.swing.GroupLayout(panelListado);
        panelListado.setLayout(panelListadoLayout);
        panelListadoLayout.setHorizontalGroup(
            panelListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListadoLayout.createSequentialGroup()
                .addGroup(panelListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelListadoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE))
                    .addGroup(panelListadoLayout.createSequentialGroup()
                        .addGap(310, 310, 310)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(161, 161, 161)
                .addComponent(buttonImportar)
                .addGap(18, 18, 18)
                .addComponent(buttonExportar)
                .addGap(52, 52, 52))
        );
        panelListadoLayout.setVerticalGroup(
            panelListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListadoLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonExportar)
                    .addComponent(buttonImportar))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        panelPrincipal.addTab("Listado", panelListado);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**
 * Acción específica al pulsar el botón
 * @param evt 
 */
    private void buttonAnhadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAnhadirActionPerformed

        //INVOCAMOS EL MÉTODO DE INSERCIÓN DE DATOS DEL PAQUETE BBDD
        bbdd.MySql.meterDatos(fieldNombre.getText(), fieldApellidos.getText(), fieldTelefono.getText(), fieldDireccion.getText(), fieldSexo.getText(), fieldDni.getText(), pathFoto);
    }//GEN-LAST:event_buttonAnhadirActionPerformed
/**
 * Acción específica al pulsar el botón
 * @param evt 
 */
    private void buttonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActualizarActionPerformed
        MySql.tabla();
    }//GEN-LAST:event_buttonActualizarActionPerformed
/**
 * Acción específica al pulsar el botón
 * @param evt 
 */
    private void buttonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLimpiarActionPerformed
        vaciar();
    }//GEN-LAST:event_buttonLimpiarActionPerformed
/**
 * Acción específica al pulsar el botón
 * @param evt 
 */
    private void buttonConsultaDatAcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConsultaDatAcaActionPerformed
        consultarAlum();


    }//GEN-LAST:event_buttonConsultaDatAcaActionPerformed
/**
 * Acción específica al pulsar el botón
 * @param evt 
 */
    private void buttonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConsultarActionPerformed
        consultarAlum();

    }//GEN-LAST:event_buttonConsultarActionPerformed
/**
 * Acción específica al pulsar el botón
 * @param evt 
 */
    private void buttonCalcMateriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCalcMateriasActionPerformed
        scrollMaterias();
    }//GEN-LAST:event_buttonCalcMateriasActionPerformed

    private void fieldNotaPracActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNotaPracActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldNotaPracActionPerformed
/**
 * Acción específica al pulsar el botón
 * @param evt 
 */
    private void buttonNotasBaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNotasBaseActionPerformed

        //VARIABLES PARA QUE YO ME ENTERE
        //ADEMÁS NECESITAMOS CONVERTIRLO A LOS
        //TIPOS QUE VA A USAR MYSQL
        int id_alum = Integer.parseInt(fieldId2.getText());
        String nom_curso = comboCursos.getSelectedItem().toString();
        String nom_materia = comboMateria.getSelectedItem().toString();
        float nota_pra = Float.parseFloat(fieldNotaPrac.getText());
        float nota_teo = Float.parseFloat(fieldNotaTeo.getText());
        float nota_tra = Float.parseFloat(fieldNotaTra.getText());
        float nota_fin = Float.parseFloat(fieldNotaFinal.getText());

        //LLAMAMOS AL MÉTODO QUE INTRODUCIRÁ LOS DATOS
        bbdd.MySql.anhadirNotas(id_alum, nom_curso, nom_materia, nota_teo, nota_pra, nota_tra, nota_fin);

    }//GEN-LAST:event_buttonNotasBaseActionPerformed
/**
 * Acción específica al pulsar el botón
 * @param evt 
 */
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
/**
 * Acción específica al pulsar el botón
 * @param evt 
 */
    private void buttonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEliminarActionPerformed
        String idAlumno = fieldId2.getText();
        bbdd.MySql.eliminarAlumno(idAlumno);
    }//GEN-LAST:event_buttonEliminarActionPerformed
/**
 * Acción específica al pulsar el botón
 * @param evt 
 */
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
        bbdd.MySql.modificarAlumno(id, nombre, apellidos, telefono, direccion, sexo, dni, pathFoto);

    }//GEN-LAST:event_buttonModificarActionPerformed
/**
 * Acción específica al pulsar el botón
 * @param evt 
 */
    private void buttonLimpiar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLimpiar3ActionPerformed
        vaciar();
    }//GEN-LAST:event_buttonLimpiar3ActionPerformed

    //PARA PODER CARGAR LA FOTO NECESITAOS UNA VARIABLE FILE
/**
 * Acción específica al pulsar el botón
 * @param evt 
 */
    private void buttonCargarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCargarFotoActionPerformed

        int resultado;
        //INSTANCIAMOS UN OBJETO DE LA CLASE CARGARFOTO
        CargarFoto foto1 = new CargarFoto();

        //CON ESTO NOS ASEGURAMOS QUE AL DARLE AL BOTÓN NOS APAREZCA 
        //EL EXPLORADOR DE ARCHIVOS Y QUE PODAMOS SELECCIONAR UN FICHERO
        resultado = foto1.jfchCargarfoto.showOpenDialog(null);
        fileFoto = foto1.jfchCargarfoto.getSelectedFile();
        try {
            //CREAMOS UN OBJETO ICON
            ImageIcon icon = new ImageIcon(fileFoto.toString());
            pathFoto = (icon.getDescription());
            //Modificamos el path ya que suelen dar problemas
            String contraBarraSimple = "\\";
            pathFoto = pathFoto.replace(contraBarraSimple, "/");
            //FORMATEAMOS EL OBJETO ICON
            Icon icono = new ImageIcon(icon.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_DEFAULT));
            //QUITAMOS EL TEXTO Y PONEMOS LA IMAGEN SELECCIONADA
            lblFoto.setText(null);
            lblFoto.setIcon(icono);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error abriendo la imagen " + ex);
        }


    }//GEN-LAST:event_buttonCargarFotoActionPerformed
/**
 * Acción específica al pulsar el botón
 * @param evt 
 */
    private void buttonExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExportarActionPerformed
        //VERIFICAMOS QUE HAY DATOS EN LA TABLA
        if (this.tablaListaAlumnos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay datos en la tabla para exportar.", "BCO",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Hay datos");
        }

        String archivo = "C:\\Users\\Jano\\Desktop\\listado.txt";
        File salidaTXT = new File(archivo);
        PrintWriter ps = null;
        try {
            ps = new PrintWriter(archivo);

            int numFilas = tablaListaAlumnos.getRowCount();
            int numColumn = tablaListaAlumnos.getColumnCount();

            for (int i = 0; i < numFilas; i++) {
                for (int j = 0; j < numColumn; j++) {

                    ps.print(tablaListaAlumnos.getModel().getValueAt(i, j) + ", ");

                }
                ps.println("\n");
            }
            System.out.println("Se ha generado un fichero");
            ps.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el archivo " + archivo);
        }


    }//GEN-LAST:event_buttonExportarActionPerformed
/**
 * Acción específica al pulsar el botón
 * @param evt 
 */
    private void buttonImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonImportarActionPerformed

        int resultado;
        //INSTANCIAMOS UN OBJETO DE LA CLASE CARGARFOTO
        CargaArchivos archivo1 = new CargaArchivos();

        //CON ESTO NOS ASEGURAMOS QUE AL DARLE AL BOTÓN NOS APAREZCA 
        //EL EXPLORADOR DE ARCHIVOS Y QUE PODAMOS SELECCIONAR UN FICHERO
        resultado = archivo1.jfchCargaArchivos.showOpenDialog(null);
        fileImportacion = archivo1.jfchCargaArchivos.getSelectedFile();
        DefaultTableModel modelo = new DefaultTableModel();
        //AÑADIMOS LA CABECERA DE LA TABLA
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Curso");
        modelo.addColumn("Materia");
        modelo.addColumn("Docente");
        modelo.addColumn("Nota Trabajos");
        modelo.addColumn("Nota Teórica");
        modelo.addColumn("Nota Práctica");
        modelo.addColumn("Nota Final");
        //APLICAMOS EL MODELO A LA JTABLE QUE TENEMOS EN EL JFRAME
        MenuAplicacion.tablaListaAlumnos.setModel(modelo);

        //CREAMOS UN ARRAY PARA INTRODUCIR LOS DATOS QUE NOS DEVUELVA LA QUERY
        //ESTE ARRAY SERÁ LA FUTURA LÍNEA (ROW) DE LA TABLA
        try {
            Scanner sc = new Scanner(fileImportacion);
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] datos = new String[10];
                datos = linea.split(",");
                System.out.println(datos[1]);
                modelo.addRow(datos);

            }
            sc.close();

            //VOLVEMOS A APLICAR EL MODELO PARA QUE SE APLIQUEN LOS CAMBIOS CON LAS NUEVAS LÍNEAS
            tablaListaAlumnos.setModel(modelo);

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

    }//GEN-LAST:event_buttonImportarActionPerformed


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
    private javax.swing.JToggleButton buttonImportar;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private static javax.swing.JLabel lblFoto;
    private javax.swing.JPanel panelDatosAcademicos;
    private javax.swing.JPanel panelDatosPersonales;
    private javax.swing.JPanel panelListado;
    private javax.swing.JTabbedPane panelPrincipal;
    public static javax.swing.JTable tablaListaAlumnos;
    // End of variables declaration//GEN-END:variables
}
