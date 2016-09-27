package tcpClient.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            LOGGER.getLogger("Connection failed"+ ex);
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
            LOGGER.getLogger("Disconnected failed"+ex);
        }
 }
 
     /**
     * This method used to check connection it will return true if socket is connected.
     */
    public Boolean isConncted(){
        return socket != null && socket.isConnected();
    }

    /**
     * This method used to write something on server.
     */
    public OutputStream getOutputStream(){
        try {
            return socket.getOutputStream();
        } catch (IOException ex) {
            LOGGER.getLogger("OutputStream failed",TcpConnection.class.getName()).log(Level.SEVERE, null, ex);
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
           LOGGER.getLogger("InsputStream failed"+ex);
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