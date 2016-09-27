/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpClient.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.logging.Logger;
import tcpClient.connection.TcpConnection;
import tcpClient.swing.TcpClient;

/**
 *
 * @author cis
 */
public class ConnectionService {

   private TcpConnection tcpConnection = new TcpConnection();
   private static TcpClient tcpClient;
   private static final Logger LOGGER = Logger.getLogger(ConnectionService.class.getName());
   private static ReceiverService receiverService;

    /**
     * This method used to disconnect from server.
    */
   public void disconect(){
       tcpConnection.doDisconnect();
   }

    /**
     * This method used to check connection to server if it is not connected then will change status of
     * connection detail section and open the connection window.
    */
   public boolean isConnected(){
       boolean status = tcpConnection.isConncted();
       if(!status){
           setConnetionDetails();
           openConnectionWindow();
       }
       return status;
   }

   /**
     * This method used to set current connection details(ip,port,status) in connection details section of window.
    */
   public void setConnetionDetails(){
       tcpClient.setConectionDetail(tcpConnection.getHost(),tcpConnection.getPort(),tcpConnection.isConncted());
   }

   /**
    * This method used to open connection window over the application if its not connected.
   */
   public void openConnectionWindow(){
       tcpClient.openConnectionWindow();
   }

   /**
    * This method used to write stuff on server.
   */
   public void writeOnServer(String message){
        DataOutputStream output;
        try {
           output = new DataOutputStream(tcpConnection.getOutputStream());
           output.writeBytes(message);
        }
        catch (Exception e) {
            e.printStackTrace();
           LOGGER.getLogger("OutputStream failed to write"+e);
        }
   }

   /**
    * This method used to read stuff from server.
   */
   public DataInputStream readFromServer(){
        try {
           return new DataInputStream(tcpConnection.getInputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
           LOGGER.getLogger("OutputStream failed to write"+e);
        }
        return null;
   }

    /**
    * This method used to start read server.
   */
   public void startReciver(){
       receiverService = new ReceiverService();
       receiverService.start();
   }

   /**
    * This method used to stop read server.
   */
   public void stoprReciver(){
       receiverService.terminate();
   }
}