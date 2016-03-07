/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

/**
 *
 * @author Jano
 */
public class Proyecto2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        try {
            bbdd.MySql.conectarse();
        } catch (Exception ex) {
            Logger.getLogger(Proyecto2.class.getName()).log(Level.SEVERE, null, ex);
        }
//File archivo= new File("C:\\listado.txt");
//MenuAplicacion.leerFichero(archivo);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MenuAplicacion().setVisible(true);
           
        });
       
    }
    
}
