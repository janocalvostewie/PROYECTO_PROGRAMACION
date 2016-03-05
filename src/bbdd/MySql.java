package bbdd;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jano
 */
public class MySql {

    public static Connection conexion;
    private static int id;
    private static int idBis;

    //Método para abrir la conexión con la base de datos
    //Debe ser static parapoder invocarla
    public static void conectarse() throws Exception {
        String db_name = "janillo";
        String user = "jano";
        String pass = "Cthulhu+22";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name, user, pass);
            JOptionPane.showMessageDialog(null, "Se ha iniciado la conexión con el servidor de forma exitosa");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Método para cerrar la conexión con la base de datos
    public static void cerrarConexion() {
        try {
            conexion.close();
            JOptionPane.showMessageDialog(null, "Se ha finalizado la conexión con el servidor");
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void meterDatos(String nombre, String apellidos, String telefono, String direccion, String sexo, String dni) {

        try {
            Statement st1 = (com.mysql.jdbc.Statement) bbdd.MySql.conexion.createStatement();

            ResultSet rs = st1.executeQuery("select count(id_alumno) from janillo.al_alumnos");
            while (rs.next()) {
                id = rs.getInt(1) + 1;
            }
            //NOS INTERESA QUE EN LA BASE DE DATOS
            //TODO SE GUARDE EN MAYÚSCULAS
            //DE AHÍ LA FUNCIÓN UPPER
            String Query = "INSERT INTO janillo.al_alumnos(id_alumno, nombre, apellidos, telefono, direccion,sexo, dni) VALUES("
                    + "upper(\"" + id + "\"), "
                    + "upper(\"" + nombre + "\"), "
                    + "upper(\"" + apellidos + "\"), "
                    + "upper(\"" + telefono + "\"), "
                    + "upper(\"" + direccion + "\"), "
                    + "upper(\"" + sexo + "\"), "
                    + "upper(\"" + dni + "\"))";
            Statement st = (Statement) conexion.createStatement();
            st.executeUpdate(Query);
            JOptionPane.showMessageDialog(null, "Datos almacenados de forma exitosa");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en el almacenamiento de datos" + ex);
        }
    }

    public static ResultSet consultar(int ID) {
        ResultSet rs = null;
        try {

            String Query = "select id_alumno, nombre, apellidos, telefono, direccion,sexo,  dni from janillo.al_alumnos where "
                    + "id_alumno like \"%" + ID + "%\"";

            Statement st = (Statement) conexion.createStatement();
            rs = st.executeQuery(Query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en el almacenamiento de datos" + ex);
        }
        return rs;

    }

    public static ResultSet listarCursos() {
        ResultSet rs = null;
        try {

            String Query = "select codigo_curso, nombre_curso from janillo.for_cursos";

            Statement st = (Statement) conexion.createStatement();
            rs = st.executeQuery(Query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error alsacar datos" + ex);
        }
        return rs;
    }

    public static ResultSet listarMaterias(String nomcurso, String anho) {
        ResultSet rs = null;
        try {

            String Query = "select codigo_materia, nombre_materia from janillo.for_materias where"
                    + " codigo_curso in (select codigo_curso from janillo.for_cursos where"
                    + " nombre_curso='" + nomcurso + "') and"
                    + " anho='" + anho + "'";

            Statement st = (Statement) conexion.createStatement();
            rs = st.executeQuery(Query);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error alsacar datos" + ex);
        }
        return rs;
    }

    public static void anhadirNotas(int id_alumno, String nombre_curso, String nombre_materia, float nota_teorica, float nota_practica, float nota_trabajos, float nota_final) {

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
            Statement st1 = (com.mysql.jdbc.Statement) bbdd.MySql.conexion.createStatement();

            String Query = "select codigo_curso from janillo.for_cursos where"
                    + " nombre_curso='" + nomCurso + "'";
            Statement st = (Statement) conexion.createStatement();
            rs1 = st.executeQuery(Query);
            while (rs1.next()) {
                codCurso = (rs1.getString(1));
            }
            //SACAMOS EL CÓDIGO DE LA MATERIA Y LO GUARDAMOS
            String Query2 = "select codigo_materia from janillo.for_materias where"
                    + " nombre_materia='" + nomMateria + "'";
             st = (Statement) conexion.createStatement();
             rs1 = st.executeQuery(Query2);
            while (rs1.next()) {
                codMateria = (rs1.getString(1));
            }
            //TENEMOS TODOS LOS DATOS PARA INTRODUCIRLOS EN LA TABLA
            st1 = (com.mysql.jdbc.Statement) bbdd.MySql.conexion.createStatement();

            String Query3 = "INSERT INTO janillo.al_materias(id_alumno, codigo_curso, codigo_materia, nota_teorica, nota_practica,nota_trabajos, nota_final) VALUES("
                    + "upper(\"" + id2 + "\"), "
                    + "upper(\"" + codCurso + "\"), "
                    + "upper(\"" + codMateria + "\"), "
                    + "upper(\"" + nTeo + "\"), "
                    + "upper(\"" + nPra + "\"), "
                    + "upper(\"" + nTra + "\"), "
                    + "upper(\"" + nFin + "\"))";
            st = (Statement) conexion.createStatement();
            st.executeUpdate(Query3);
            JOptionPane.showMessageDialog(null, "Datos almacenados de forma exitosa");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en el almacenamiento de datos" + ex);
        }

    }
    public static void eliminarAlumno(int idAlumno){
    
        try {
            Statement st = (com.mysql.jdbc.Statement) bbdd.MySql.conexion.createStatement();
            //PRIMERO DEBEMOS ELIMINAR LOS REGISTROS
            //DE LA TABLA DE MATERIAS CONCERNIENTE AL ALUMNO
            String Query1 = "delete from janillo.al_materias where "
                    +"id_alumno=\""+idAlumno+"\"";
            st = (Statement) conexion.createStatement();
            st.executeUpdate(Query1);
            //LUEGO BORRAMOS AL ALUMNO
            String Query2 = "delete from janillo.al_alumnos where "
                    +"id_alumno=\""+idAlumno+"\"";
            st = (Statement) conexion.createStatement();
            st.executeUpdate(Query2);
            JOptionPane.showMessageDialog(null, "Datos eliminados de forma exitosa");
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
