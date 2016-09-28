/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpClient.services;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tcpClient.swing.TcpClient;

/**
 *
 * @author cis
 */
public class ReceiverService extends Thread{
    private ConnectionService connectionService = new ConnectionService();
    public void run(){
        DataInputStream dis= connectionService.readFromServer();
        PopupService popupService=new PopupService();
        String responseLine;
        try {
            while(connectionService.isConnected() && (responseLine = dis.readLine()) != null){
                if(!connectionService.isConnected()){
                    break;
                }
                popupService.createPupup(responseLine);
                TcpClient.addRowInMassageTable(responseLine);
            }
        } catch(java.net.SocketException ex){
            Logger.getLogger(ReceiverService.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }catch (IOException ex) {
            Logger.getLogger(ReceiverService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}