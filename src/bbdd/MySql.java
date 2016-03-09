package bbdd;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import JanilloMySql.JanilloMySqlntb;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import proyecto2.MenuAplicacion;
import static proyecto2.MenuAplicacion.tablaListaAlumnos;

/**
 *
 * @author Jano
 */
public class MySql {

    public static JanilloMySqlntb connect = new JanilloMySqlntb();

//            PUESTO QUE HE CREADO UNA LIBRERÍA CON LAS CONEXIONES A LA BASE DE DATOS
//            ES NECESARIO INSTANCIAR UN OBJETO MEDIANTE EL CUAL LLAMAR A LAS FUNCIONES
//            ADEMÁS, PUESTO QUE EL OBJETO 'STATEMENT' LO VAMOS A USAR MUCHO, LO SACAMOS DE ESTA LIBRERIA
    public MySql() {
        try {
            //LANZAMOS EL MÉTODO QUE CREARÁ LA CONEXIÓN CON LA BASE DE DATOS GUARDÁNDOLA EN LA VARIABLE 'CONNECT'
            //ADEMÁS DE INSTANCIAR UN OBJETO STATEMENT
            JanilloMySqlntb.conectarse(JOptionPane.showInputDialog("Introduzca base de datos"), JOptionPane.showInputDialog("Introduzca usuario"), JOptionPane.showInputDialog("Introduzca contraseña"));
        } catch (Exception ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void tabla() {

        //CREAMOS USANDO UNA PREDETERMINADA UN MODELO PARA LA JTABLE
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
        String[] datos = new String[10];

        try {

            ResultSet rs = connect.st.executeQuery("select aa.id_alumno, aa.nombre, aa.apellidos,"
                    + " al.codigo_curso,al.codigo_materia, fm.docente,"
                    + " al.nota_trabajos,al.nota_teorica,al.nota_practica,al.nota_final"
                    + " from al_alumnos aa left join  al_materias al on aa.id_alumno=al.id_alumno, for_materias fm"
                    + " where fm.codigo_materia=al.codigo_materia"
                    + " and fm.codigo_curso=al.codigo_curso"
                    + " order by aa.id_alumno");
            //POR CADA LÍNEA DEVUELTA DE LA QUERY LA INTRODUCIRÁ EN EL ARRAY
            //CREAMOS UN BUCLE
            while (rs.next()) {
                //IGUALAMOS CADA CAMPO DE LA QUERY A CADA UNA DE LAS POSICIONES DEL ARRAY
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = String.valueOf(rs.getFloat(7));
                datos[7] = String.valueOf(rs.getFloat(8));
                datos[8] = String.valueOf(rs.getFloat(9));
                datos[9] = String.valueOf(rs.getFloat(10));
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

    public static void meterDatos(String nombre, String apellidos, String telefono, String direccion, String sexo, String dni, String path) {

        //NOS INTERESA QUE EN LA BASE DE DATOS
        //TODO SE GUARDE EN MAYÚSCULAS
        //DE AHÍ LA FUNCIÓN UPPER
        String Query = "INSERT INTO janillo.al_alumnos(nombre, apellidos, telefono, direccion,sexo, dni,foto) VALUES("
                + "upper(\"" + nombre + "\"), "
                + "upper(\"" + apellidos + "\"), "
                + "upper(\"" + telefono + "\"), "
                + "upper(\"" + direccion + "\"), "
                + "upper(\"" + sexo + "\"), "
                + "upper(\"" + dni + "\"), "
                +"\"" + path + "\")";
        try {

            connect.st.executeUpdate(Query);
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Alumno añadido");

    }

    public static ResultSet consultar(String ID) {

        //NECESITAMOS QUE DEVUELVA LOS DATOS Y EL RESULTSET ES LA FORMA 
        ResultSet rs = null;
        try {

            String Query = "select id_alumno, nombre, apellidos, telefono, direccion,sexo,  dni,foto from janillo.al_alumnos where "
                    + "id_alumno =\"" + ID + "\"";

            rs = connect.st.executeQuery(Query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en el almacenamiento de datos" + ex);
        }
        return rs;

    }

    public static ResultSet listarCursos() {
        ResultSet rs = null;
        try {
            //EL CODIGO ES LA PK PERO QUEREMOS MOSTRAR EL NOMBRE
            //LA PK LA NECESITAREMOS YA QUE SERÁ PARTE DE LA PK DE LA TABLA 'AL_MATERIAS'
            String Query = "select codigo_curso, nombre_curso from janillo.for_cursos";

            rs = connect.st.executeQuery(Query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error alsacar datos" + ex);
        }
        return rs;
    }

    public static ResultSet listarMaterias(String nomcurso, String anho) {
        ResultSet rs = null;
        try {
            //VERIFICAMOS EL CODIGO D ELA MATERIA Y EL AÑO PARA SABER QUÉ
            //MATERIAS PERTENECEN AL CURSO
            String Query = "select codigo_materia, nombre_materia from janillo.for_materias where"
                    + " codigo_curso in (select codigo_curso from janillo.for_cursos where"
                    + " nombre_curso='" + nomcurso + "') and"
                    + " anho='" + anho + "'";

            rs = connect.st.executeQuery(Query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error alsacar datos" + ex);
        }
        return rs;
    }

    public static void anhadirNotas(int id_alumno, String nombre_curso, String nombre_materia, float nota_teorica, float nota_practica, float nota_trabajos, float nota_final) {

        //ESTO LO PUSE PARA ACLARARME YO, CON EL NOMBRE DE LOS PARAMETROS LLEGARÍA
        //ME RESULTABA MÁS VISUAL TENERLOS AQUÍ
        int id2 = id_alumno;
        float nTra = nota_trabajos;
        float nPra = nota_practica;
        float nTeo = nota_teorica;
        float nFin = nota_final;
        String nomCurso = nombre_curso;
        String nomMateria = nombre_materia;
        String codCurso = null;
        String codMateria = null;
        ResultSet rs1 = null;

        try {

            //SACAMOS EL CÓDIGO DEL CURSO Y LO GUARDAMOS
            String Query = "select codigo_curso from janillo.for_cursos where"
                    + " nombre_curso='" + nomCurso + "'";
            rs1 = connect.st.executeQuery(Query);
            while (rs1.next()) {
                codCurso = (rs1.getString(1));
            }
            //SACAMOS EL CÓDIGO DE LA MATERIA Y LO GUARDAMOS
            String Query2 = "select codigo_materia from janillo.for_materias where"
                    + " nombre_materia='" + nomMateria + "'";
            rs1 = connect.st.executeQuery(Query2);
            while (rs1.next()) {
                codMateria = (rs1.getString(1));
            }
            //TENEMOS TODOS LOS DATOS PARA INTRODUCIRLOS EN LA TABLA

            String Query3 = "INSERT INTO janillo.al_materias(id_alumno, codigo_curso, codigo_materia, nota_teorica, nota_practica,nota_trabajos, nota_final) VALUES("
                    + "" + id2 + ", "
                    + "upper(\"" + codCurso + "\"), "
                    + "upper(\"" + codMateria + "\"), "
                    + "upper(\"" + nTeo + "\"), "
                    + "upper(\"" + nPra + "\"), "
                    + "upper(\"" + nTra + "\"), "
                    + "upper(\"" + nFin + "\"))";
            connect.st.executeUpdate(Query3);
            JOptionPane.showMessageDialog(null, "Datos almacenados de forma exitosa");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en el almacenamiento de datos" + ex);
        }

    }

    public static void eliminarAlumno(String idAlumno) {
        int idalumno = Integer.parseInt(idAlumno);
        PreparedStatement ps = null;
        try {
            //PRIMERO DEBEMOS ELIMINAR LOS REGISTROS
            //DE LA TABLA DE MATERIAS CONCERNIENTE AL ALUMNO
            //YA QUE POSEE UNA FOREIGN KEY,  SINO NO PODRÍAMOS ELIMINAR AL ALUMNO
            String Query1 = "delete from janillo.al_materias where "
                    + "id_alumno=\"" + idAlumno + "\"";
            connect.st.executeUpdate(Query1);
            JOptionPane.showMessageDialog(null, "Datos eliminados de forma exitosa");
            //LUEGO BORRAMOS AL ALUMNO
            String Query2 = "delete from janillo.al_alumnos where "
                    + "id_alumno=\"" + idAlumno + "\"";
            connect.st.executeUpdate(Query2);

            JOptionPane.showMessageDialog(null, "Datos eliminados de forma exitosa");
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void modificarAlumno(String idAlumno, String nombre, String apellidos, String telefono, String direccion, String sexo, String dni, String path) {
        int id_alumno = Integer.parseInt(idAlumno);
        try {
            //LO QUE HACEMOS CON ESTA QUERY  ES ACTUALIZAR TODOS LOS CAMPOS DE LA TABLA
            // TANTO LO QUE SE HAYA MODIFICADO COMO LO QUE NO
            //SI NO SE HA MODIFICADO EN EL FORMULARIO SE ACTUALIZARÁ CON EL MISMO VALOR
            String Query1 = "update janillo.al_alumnos set "
                    + "nombre=upper(\"" + nombre + "\"), "
                    + "apellidos=upper(\"" + apellidos + "\"), "
                    + "telefono=upper(\"" + telefono + "\"), "
                    + "direccion=upper(\"" + direccion + "\"), "
                    + "sexo=upper(\"" + sexo + "\"), "
                    + "foto=\"" + path + "\", "
                    + "dni=upper(\"" + dni + "\") where "
                    + "id_alumno=\"" + idAlumno + "\"";
            connect.st.executeUpdate(Query1);
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
