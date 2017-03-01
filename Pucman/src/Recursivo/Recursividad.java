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
public class Recursividad {
       public int recursivo(int num){
            int x=num;

            if( num== 0){
            return 0;
            }else{
               if  ( x>3){
              while (x >3){
                x -= 3;
                    }
               }
              if ( x%3 == 0){
                  return 5+ recursivo(num-1) ;
              }else if (x%2 == 0){
                  return 3+ recursivo(num-1);
              }else
                  return 4 + recursivo (num-1);

                }
            }
    public void mostrarRecursivo(int size){
            String texto1= "",texto2 =" ";
            for (int i = 0; i <= size; i++) {
                int h = recursivo(i);
                texto1 += "  " + h;
                
            }
            JOptionPane.showMessageDialog(null,texto2 + texto1+ texto2);
        }
}
