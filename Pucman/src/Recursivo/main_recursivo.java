/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recursivo;

import javax.swing.JOptionPane;

/**
 *
 * @author Nozaki
 */
public class main_recursivo {
    public void main( ) {
         Recursividad rec = new Recursividad();
        int num = 0;
       String texto = JOptionPane.showInputDialog("Ingrese el numero de veces que desea realizar el proceso: ");
                        num = Integer.parseInt(texto);
                        rec.mostrarRecursivo(num);
    }
}
