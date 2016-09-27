/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpClient.services;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.Timer;
import tcpClient.swing.TcpClient;

/**
 *
 * @author cis
 */
public class PopupService {
    /**
    * This method used to create a popup over the frame for 3 sec.
    */
    public void createPupup(String message){
     PopupFactory factory = PopupFactory.getSharedInstance();
     //create message as label
     Component component = TcpClient.frame;
     JLabel messageLabel = new JLabel(message);
        final Popup popup = factory.getPopup(component, messageLabel, component.getX(), component.getY()+30);
        popup.show();
        ActionListener hider = new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            popup.hide();
          }
        };
      // Hide popup in 3 seconds
      Timer timer = new Timer(1000, hider);
      timer.start();
    }
}
