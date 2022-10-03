/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Commons;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 * This class contains String processing methods.
 * @author Hasan Barzegaravval
 * @version 1.0
 * @since   1.0
 */
public class STRINGS {
    /**
     * A method to convert a list of string to a single string with special separator character.
     * @param list list of strings 
     * @param Sep  special character as a separator
     * @return  a single string which is the serialized form of list by special separator.
     */
    public static String ListToString(List<String> list,char Sep){
       try{
           StringBuilder strb=new StringBuilder();
           for(String t:list){
               if (t.charAt(t.length()-1)==Sep){
                strb.append(t);
               }else{
                strb.append(t).append(Sep);   
               }
           }
           return strb.toString();
       }catch(java.lang.StringIndexOutOfBoundsException ex){
           return null;
       }catch(Exception ex){
           AlertException.ALERT("Error", ex);
           return "";
       }
    }
    /**
     * read a string and convert it to a list of strings using a separator
     * @param str input string
     * @param sep separator string.
     * @return a list of strings.
     */
    public static List<String>StringToList(String str,String sep){
        try{
            return (Arrays.asList(str.split(sep))).stream().map((e)->e.trim()).collect(Collectors.toList());
        }catch(NullPointerException ex){
            return null;
        }
    }
    /**
     * converts a string form of a list to integer list. 
     * @param A input string
     * @return list of integers
     */
    public static List<Integer> StringtoIntList(String A){
        //converting '[]' to list of ints
        try{
            return Arrays.asList(A.replace("[","").replace("]","").trim().split(",")).stream().map((e)->(Integer.valueOf(e.trim()))).collect(Collectors.toList());
        }catch(java.lang.NumberFormatException ex){
            return null;
        }catch(NullPointerException ex){
            return null;
        }
    }
    
}
