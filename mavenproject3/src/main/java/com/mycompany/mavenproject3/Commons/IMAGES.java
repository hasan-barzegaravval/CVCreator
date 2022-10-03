/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Commons;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
/**
 * This class contains static methods for image handling.
 * @author Hasan Barzegaravval
 * @version 1.0
 */
public class IMAGES {
    /**
     * A method to convert images to base 64 strings. 
     * @param File the absolute path of the image file
     * @return  string which is base64 encoded version of the image. 
     * @throws IOException on file opening or processing Exceptions.
     */
    public static String BitMapToString(String File) throws IOException{
        byte[] BA=Files.readAllBytes(Paths.get(File));
        return Base64.getEncoder().encodeToString(BA); 
    }
   
}
