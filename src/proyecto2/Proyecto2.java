/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import bbdd.MySql;

/**
 *
 * @author Jano
 */
public class Proyecto2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
      MySql base=new MySql();
//File archivo= new File("C:\\listado.txt");
//MenuAplicacion.leerFichero(archivo);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
           MenuAplicacion menu= new MenuAplicacion();
           
           menu.setVisible(true);
        
           
        });
       
    }
    
}
