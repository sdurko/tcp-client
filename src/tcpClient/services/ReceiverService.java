/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpClient.services;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cis
 */
public class ReceiverService extends Thread{
    private ConnectionService connectionService = new ConnectionService();
    private static boolean stopRunning = false;
    public void run(){
        DataInputStream dis= connectionService.readFromServer();
        PopupService popupService=new PopupService();
        String responseLine;
        try {
            while((responseLine = dis.readUTF()) != null){
                if(stopRunning){
                    break;
                }
                popupService.createPupup(responseLine);
            }
        } catch (IOException ex) {
            Logger.getLogger(ReceiverService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
    *This method used to terminate the thread.
    */
    public void terminate(){
        stopRunning = true;
        connectionService.writeOnServer("");
    }

}