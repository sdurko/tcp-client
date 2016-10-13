package com.tcpClient.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;
import com.tcpClient.swing.TcpClient;

public class TcpConnection {
    private static Socket socket;
    private static final Logger LOGGER = Logger.getLogger(TcpConnection.class.getName());

    /**
    * This method used to create connection from server.
    */
    public Boolean doConnection(String ip, int port){
        try {
           socket = new Socket(ip, port);
           if(isConncted()){
            LOGGER.info("Connection success");
            return true;
           }
        }
        catch (IOException ex) {
            ex.printStackTrace();
            TcpClient.addRowInErrorTable("Connetion faild due to : ".concat(ex.toString()));
        }
        return false;
    }

    /**
     * This method used to check socket disconnect the socket.
     */
 public void doDisconnect(){
        try {
            if(isConncted()){
                socket.close();
                socket = null;
                LOGGER.info("Disconnected success");
            }else{
                LOGGER.info("Already Disconnected");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            TcpClient.addRowInErrorTable("Disconnection failed due to : ".concat(ex.toString()));
        }
 }
 
     /**
     * This method used to check connection it will return true if socket is connected.
     */
    public Boolean isConncted(){
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    /**
     * This method used to write something on server.
     */
    public OutputStream getOutputStream(){
        try {
            return socket.getOutputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
            TcpClient.addRowInErrorTable("Server writing failed due to : ".concat(ex.toString()));
        }
        return null;
    }

    /**
     * This method used to read something from server.
     */
    public InputStream getInputStream(){
        try {
            return socket.getInputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
           TcpClient.addRowInErrorTable("Server reading failed due to : ".concat(ex.toString()));
        }
        return null;
    }

    /**
     * This method used to get connected host name if connected otherwise will return null.
    */
    public String getHost(){
        if(isConncted()){
            return socket.getInetAddress().getHostName();
        }
        return null;
    }

    /**
     * This method used to get connected port number if connected otherwise will return null.
    */
    public Integer getPort(){
        if(isConncted()){
            return socket.getPort();
        }
        return null;
    }
}