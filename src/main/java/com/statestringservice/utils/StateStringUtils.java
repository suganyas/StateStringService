package com.statestringservice.utils;

import com.statestringservice.exception.StateStringException;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateStringUtils {

    private static final Logger logger = LoggerFactory.getLogger("StateStringUtils");

    public static String  addNumbersInString(String stateString){
        logger.trace("Entered addNumbersInString of Utils {}", stateString);
        int sum = 0;
        if(stateString == null || "".equals(stateString)){
            return Integer.toString(sum);
        }
        Matcher m = Pattern.compile("\\d+").matcher(stateString);
        while (m.find()) {
            sum = sum + new Integer(m.group(0));
        }
        return Integer.toString(sum);
    }

    public static String getCharsInString(String stateString){
        logger.trace("Entered getCharsInString of Utils {}", stateString);
        if(stateString == null || "".equals(stateString)){
            return "";
        }
        return stateString.replaceAll("\\d","");
    }

    public static String addCharInString(char character, int amount, String stateString){
        logger.trace("Entered addCharInString of Utils {}", stateString);
        if(stateString == null){
            stateString = "";
        }
        if( (!Character.isLetter(character) && !Character.isDigit(character)) || amount < 1 || amount > 10  ){
            logger.error("Exception addCharInString of Utils {} {}", character,amount);
            throw  new StateStringException("The character and amount specification is wrong: "
                    + amount + " " + character);
        }
        if((stateString.length() + amount) > 200){
            logger.error("Exception addCharInString of Utils {} {}", stateString,amount);
            throw  new StateStringException("The stateString exceeded length 200 while appending character:"
                    + stateString + " " + amount);
        }
        return  stateString + StringUtils.repeat(character, amount);
    }

    public static String deleteCharInString(char character, String stateString){
        logger.trace("Entered deleteCharInString of Utils {}", stateString);
        if( (!Character.isLetter(character) && !Character.isDigit(character))|| stateString == null || "".equals(stateString)){
            return stateString;
        }
        int index = stateString.lastIndexOf(character);
        if(index > -1){
            logger.info("Last occurrence of Character found {} {}", character, stateString);
            return stateString.substring(0, index) + stateString.substring(index+1);

        }
        return stateString;
    }

    public static String getUUID(){
        logger.trace("Generated Universal Unique Identifier in Utils {}");
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
