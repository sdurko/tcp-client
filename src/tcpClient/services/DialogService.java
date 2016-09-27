/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpClient.services;

import java.awt.Component;
import java.awt.Frame;
import javax.swing.JDialog;

/**
 *
 * @author cis
 */
public class DialogService {

    private static JDialog dialog;

    /**
    * This method used to create a child window with Component
   */    
    public void createDilog(Frame owner, String Title,  Component comp){
        dialog = new JDialog(owner, Title, true);
        dialog.getContentPane().add(comp);
        dialog.pack();
    }

    /**
    * This method used to make visible the dialog
   */
    public void setVisible(Boolean flag){
        dialog.setVisible(flag);
    }

    /**
    * This method used to close dialog
   */
    public static void close(){
        dialog.dispose();
    }

}