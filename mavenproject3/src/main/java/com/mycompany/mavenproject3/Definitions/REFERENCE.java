/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Definitions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.mavenproject3.Commons.AlertException;
import com.mycompany.mavenproject3.Commons.FINALS;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author hasan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class REFERENCE extends DATATemplate{
    public String Name;
    public String Position;
    public String Company;
    public String Phone;
    public String Email;
    public String Comment; 
    public REFERENCE(){}
    public REFERENCE(int id, String name,String position,String company, String phone, String email, String comment){
        setID(id);
        Name=name;
        Position=position;
        Company=company;
        Phone=phone;
        Email=email;
        Comment=comment;
    }
    @Override
    public void insertMysql() {
        Object[] A={Name,Position,Company,Phone,Email,Comment};        
        try {
            setID(FINALS.DBC.insertSingle("REFERENCE",A));
        }catch (SQLException ex) {
            Logger.getLogger(REFERENCE.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }    }

    @Override
    public String updateMysql() {
        var A=DBC.getupdates().get("REFERENCE");
        try {
            A.setString(1, Name);
            A.setString(2, Position);
            A.setString(3, Company);
            A.setString(4, Phone);
            A.setString(5, Email);
            A.setString(6, Comment);
            A.setInt(7,getID());
            A.addBatch();            
        } catch (SQLException ex) {
            Logger.getLogger(REFERENCE.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }
        return null;
    }
    //added
    @JsonProperty("Name")
    public String getName(){ return this.Name;}
    @JsonProperty("Position")
    public String getPosition(){ return this.Position;}
    @JsonProperty("Company")
    public String getCompany(){ return this.Company;}
    @JsonProperty("Phone")
    public String getPhone(){ return this.Phone;}
    @JsonProperty("Email")
    public String getEmail(){ return this.Email;}
    @JsonProperty("Comment")
    public String getComment(){ return this.Comment;}
    @JsonProperty("Position")
    public void setPosition(String Position){ this.Position=Position;}
    @JsonProperty("Company")
    public void setCompany(String Company){ this.Company=Company;}
    @JsonProperty("Phone")
    public void setPhone(String Phone){ this.Phone=Phone;}
    @JsonProperty("Email")
    public void setEmail(String Email){ this.Email=Email;}
    @JsonProperty("Comment")
    public void setComment(String Comment){ this.Comment=Comment;}
	
}