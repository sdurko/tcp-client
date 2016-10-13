/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcpClient.messaging.service;

import com.tcpClient.connection.ConnectionService;
import com.tcpClient.json.service.JSON;
import com.tcpClient.json.service.JsonMapper;
import com.tcpClient.services.PopupService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cis
 */
public class SendMessage {

    private static int INTERVAL = 1000;
    private static JsonMapper jsonMapper = new JsonMapper();
    private static ConnectionService connectionService = new ConnectionService();
    private static PopupService popupService = new PopupService();
    private static Boolean status = false;

    /**
    * To send message from file and text field with certain time interval.
    */
    public void sendMessage(Path filepath, String message, int interval){
        this.INTERVAL = interval*1000;
        if(Files.exists(filepath) && filepath != null){
            try {
                messageProcessing(jsonMapper.getJson(new String(Files.readAllBytes(filepath))));
            } catch (IOException ex) {
                Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(!message.isEmpty()){
            connectionService.writeOnServer(message);
        }
    }

    /**
    * To send message text field.
    */
    public void sendMessage(String message){
        if(!message.isEmpty()){
            messageProcessing(jsonMapper.getJson(message));
        }
    }

    private void messageProcessing(JSON json){
        if(JSON.Instance.Text.equals(json.getInstanceOf())){
            status = connectionService.writeOnServer(json.getText());
        }if(JSON.Instance.JSONObject.equals(json.getInstanceOf())){
           status = connectionService.writeOnServer(jsonMapper.getStringToJson(json.getJsonObject()));
        }if(JSON.Instance.JSONArray.equals(json.getInstanceOf())){
            json.getJsonList().forEach(object -> {
               status = connectionService.writeOnServer(jsonMapper.getStringToJson(object));
                try {
                    Thread.sleep(INTERVAL);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        if(status){
            popupService.createPupup("Message send successfully..!");
        }else{
            popupService.createPupup("Message not send..!");
        }
    }
}
