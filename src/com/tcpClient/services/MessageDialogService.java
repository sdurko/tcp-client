package com.tcpClient.services;

import java.awt.Component;
import javax.swing.JOptionPane;

public class MessageDialogService {

    public void showError(Component component, String message){
        JOptionPane.showMessageDialog(component, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showInformation(Component component, String message){
        JOptionPane.showMessageDialog(component, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

}