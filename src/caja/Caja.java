/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caja;

import IGU.Principal;
import javax.swing.JFrame;

/**
 *
 * @author giaan
 */
public class Caja {


    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.setVisible(true);
        principal.setLocationRelativeTo(null);
       principal.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }
    
}
