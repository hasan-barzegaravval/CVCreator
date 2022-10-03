/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Definitions;

/**
 *
 * @author hasan
 */
public class Item {
    private String VAL;
    private int ID;
    public Item(){}
    public Item(String Value,Integer id){
        this.VAL=Value;this.ID=id;
    }
    public void setVAL(String value){this.VAL=value;}
    public void setID(Integer ID){this.ID=ID;}
    public String getVAL(){return this.VAL;}
    public Integer getID(){return this.ID;} 
}
