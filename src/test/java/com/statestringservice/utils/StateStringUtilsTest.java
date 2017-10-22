package com.statestringservice.utils;

import com.statestringservice.exception.StateStringException;
import org.junit.Assert;
import org.junit.Test;

public class StateStringUtilsTest {

    @Test
    public void testAddNumbersInString(){
        Assert.assertEquals("0", StateStringUtils.addNumbersInString("abcdef"));
        Assert.assertEquals("4", StateStringUtils.addNumbersInString("ab1cde3f"));
        Assert.assertEquals("114", StateStringUtils.addNumbersInString("ab111cde3f"));
    }

    @Test
    public void testGetCharsInString(){
        Assert.assertEquals("", StateStringUtils.getCharsInString(""));
        Assert.assertEquals("", StateStringUtils.getCharsInString(null));
        Assert.assertEquals("abcd", StateStringUtils.getCharsInString("a1b2c3d4"));
        Assert.assertEquals("abcd", StateStringUtils.getCharsInString("abcd"));
        Assert.assertEquals("", StateStringUtils.getCharsInString("1234"));
        Assert.assertEquals("aBcD", StateStringUtils.getCharsInString("a1B2c3D4"));
    }

    @Test
    public void testAddCharInString(){
        Assert.assertEquals("aaaa", StateStringUtils.addCharInString('a',4,null));
        Assert.assertEquals("aaaa", StateStringUtils.addCharInString('a',4,""));
        Assert.assertEquals("abcdaaaa", StateStringUtils.addCharInString('a',4,"abcd"));
    }
    @Test
    public void testAddCharInStringException(){
        boolean thrown = false;
        try{
            StateStringUtils.addCharInString('a',14,"abcd");

        }catch (StateStringException exception){
            thrown = true;
        }
        Assert.assertEquals(true,thrown);
        thrown = false;
        try{
            StateStringUtils.addCharInString('a',0,"abcd");

        }catch (StateStringException exception){
            thrown = true;
        }
        Assert.assertEquals(true,thrown);
        thrown = false;
        try{
            StateStringUtils.addCharInString('!',0,"abcd");

        }catch (StateStringException exception){
            thrown = true;
        }
        Assert.assertEquals(true,thrown);
    }

    @Test
    public void testDeleteCharsInString(){
        Assert.assertEquals("", StateStringUtils.deleteCharInString('a',""));
        Assert.assertEquals(null, StateStringUtils.deleteCharInString('a',null));
        Assert.assertEquals("bcd", StateStringUtils.deleteCharInString('a',"abcd"));
        Assert.assertEquals("abcd", StateStringUtils.deleteCharInString('a',"abcda"));
        Assert.assertEquals("abcd", StateStringUtils.deleteCharInString('A',"abcdA"));
        Assert.assertEquals("abcda", StateStringUtils.deleteCharInString('j',"abcda"));
        Assert.assertEquals("abcda123", StateStringUtils.deleteCharInString('4',"abcda1234"));
    }

}
