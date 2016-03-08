
package proyecto2;

import bbdd.MySql;
/**
 *
 * @author Jano
 */
public class Proyecto2 {
    public static void main(String[] args){
      
        //CREAMOS UN OBBJETO MYSQL, QUE GENERARÁ LA CONEXIÓN CON LA BASE DE DATOS
        MySql base=new MySql();

      
        java.awt.EventQueue.invokeLater(() -> {
           //ARRANCAMOS EL JFRAME
           MenuAplicacion menu= new MenuAplicacion();
           
           menu.setVisible(true);
        
           
        });
       
    }
    
}
