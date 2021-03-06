/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcpClient.json.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcpClient.services.ValidationService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author cis
 */
public class JsonMapper {

    private static final Logger LOGGER = Logger.getLogger(JsonMapper.class.getName());
    private static ObjectMapper mapper = new ObjectMapper();
    private ValidationService validationService = new ValidationService();
    /**
    * to get JSON according to message.
    */     
    public JSON getJson(String message){        
        message = message.trim();
        try {
            if(validationService.matcher("\\[(?:,|\\{)?([^$]*)\\]", message)){//to list of json
                List<Object> list = mapper.readValue(message, new TypeReference<List<Object>>(){});
                return new JSON(list,JSON.Instance.JSONArray);
            }else if(validationService.matcher("\\{(?:,|\\{)?([^$]*)\\}", message)){ //to object of json
                Map<String, Object> map = mapper.readValue(message, new TypeReference<Map<String, Object>>(){});
                return new JSON(map,JSON.Instance.JSONObject);
            }else{//to text of json
                return new JSON(message,JSON.Instance.Text);
            }
        }catch (IOException ex) {
            LOGGER.severe("Json mapping failed due to wrong format: ".concat(ex.toString()));
            return new JSON(message,JSON.Instance.Text);
        }
    }

    public String getStringToJson(Object obj){
        try {
            if (obj instanceof String) {
                return obj.toString();
            }
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(JsonMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}