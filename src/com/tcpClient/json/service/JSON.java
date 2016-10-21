/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcpClient.json.service;

import java.util.List;
import java.util.Map;

/**
 *
 * @author cis
 */
public class JSON {
    public enum Instance{
        JSONObject,JSONArray,Text
    };

    private List<Object> jsonList;
    private Map<String, Object> jsonObject;
    private Instance instanceOf;
    private String text;

    /**
    * To list of objects.
    */
    public JSON(List<Object> jsonList, Instance instanceOf){
        this.jsonList = jsonList;
        this.instanceOf =instanceOf;
    }

    /**
    * To single of objects.
    */
    public JSON( Map<String, Object> jsonObject, Instance instanceOf){
        this.jsonObject = jsonObject;
        this.instanceOf = instanceOf;
    }

    /**
    * To text.
    */
    public JSON( String text, Instance instanceOf){
        this.text = text;
        this.instanceOf = instanceOf;
    }

    /**
    * To plane text.
    */
    public List<Object> getJsonList() {
        return jsonList;
    }

    public Map<String, Object> getJsonObject() {
        return jsonObject;
    }

    public Instance getInstanceOf() {
        return instanceOf;
    }

    public String getText() {
        return text;
    }

    public void addMessageInList(Object obj) {
        this.jsonList.add(obj);
    }

}