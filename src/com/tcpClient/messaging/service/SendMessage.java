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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 *
 * @author cis
 */
public class SendMessage {

    private static int INTERVAL = 1000;
    private JsonMapper jsonMapper = new JsonMapper();
    private ConnectionService connectionService = new ConnectionService();
    private PopupService popupService = new PopupService();
    private static Boolean status = false;
    /**
    * To send message from file and text field with certain time interval.
    */
    public Boolean sendMessage(Path filepath, String message, int interval){
        this.INTERVAL = interval*1000;
        JSON json = null;
        try {
            json = jsonMapper.getJson(new String(Files.readAllBytes(filepath)));
        } catch (IOException ex) {
            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(json != null && !message.isEmpty() && JSON.Instance.JSONArray.equals(json.getInstanceOf())){
            json.addMessageInList(message);
        }else if(json != null && !message.isEmpty() && JSON.Instance.Text.equals(json.getInstanceOf())){
            json = new JSON(Arrays.asList(json.getText(), message), JSON.Instance.JSONArray);
        }
        Boolean status = false ;
        if(Files.exists(filepath) && filepath != null){
            status = messageProcessing(json);
        }
        return status;
    }

    /**
    * To send message from text field with certain time interval.
    */
    public Boolean sendMessage(String message, int interval){
        this.INTERVAL = interval*1000;
        JSON json = jsonMapper.getJson(message);
        if(json != null && !message.isEmpty()){
            json.addMessageInList(message);
        }
       return messageProcessing(json);
    }

    /**
    * To send message text field.
    */
    public Boolean sendMessage(String message){
        if(!message.isEmpty()){
            return messageProcessing(jsonMapper.getJson(message));
        }
        return false;
    }

    private Boolean messageProcessing(JSON json){
        if(JSON.Instance.Text.equals(json.getInstanceOf())){
            status = connectionService.writeOnServer(json.getText());
        }if(JSON.Instance.JSONObject.equals(json.getInstanceOf())){
           status = connectionService.writeOnServer(jsonMapper.getStringToJson(json.getJsonObject()));
        }if(JSON.Instance.JSONArray.equals(json.getInstanceOf())){
            List<Object> list = json.getJsonList();
            int size = list.size();
            IntStream.range(0,size).forEach(i -> {
                if(i>0 && i<size){
                    createInterval(INTERVAL);
                }
                status = connectionService.writeOnServer(jsonMapper.getStringToJson(list.get(i)));
                
            });
        }
        if(status){
            popupService.createPupup("Message send successfully..!");
        }else{
            popupService.createPupup("Message not send..!");
        }
        return status;
    }

    private void createInterval(int interval){
        try {
            Thread.sleep(interval);
        } catch (InterruptedException ex) {
            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
