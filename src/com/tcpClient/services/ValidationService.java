package com.tcpClient.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationService {

    private Matcher matcher;
    private static final String IPADDRESS_PATTERN ="[0-9]+[\\\\.]{1}[0-9]+[\\\\.]{1}[0-9]+[\\\\.]{1}[0-9]+";
    private static final String PORST_NUMBER_VALIDATION = "([0-9]{2,5})";
    private static final String NUMBER_VALIDATION = "([0-9])";
 
    public Boolean validateIp(String ip){
	 return matcher(IPADDRESS_PATTERN, ip);
    }

    public Boolean validatePort(String port){
	 return matcher(PORST_NUMBER_VALIDATION, port);
    }

    public Boolean isValidateNumber(String port){
	 return matcher(NUMBER_VALIDATION, port);
    }

    public Boolean matcher(String regex, String value){
        matcher = Pattern.compile(regex).matcher(value);
	  return matcher.matches();
    }

}