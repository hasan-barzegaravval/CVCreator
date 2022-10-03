/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Definitions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.mavenproject3.Commons.AlertException;
import com.mycompany.mavenproject3.Commons.FINALS;
import com.mycompany.mavenproject3.UIElements.WorkExperiencePane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author hasan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WORKEXPERIENCE extends DATATemplate {
    private String Position;
    private List<COMPANY> Companies;
    public WORKEXPERIENCE(){}
    public WORKEXPERIENCE(WORKEXPERIENCE WK){
        Position=WK.Position;
        Companies=new ArrayList(WK.Companies);
    }
    public WORKEXPERIENCE(int id,String position,List<COMPANY> com){
        setID(id);
        Position=position;
        Companies=com;
    }
    public WORKEXPERIENCE(int id,String position){
        setID(id);
        Position=position;        
    }
    @Override
    public void insertMysql() {
        try {
            setID(FINALS.DBC.insertSingleUnique(Position,"positions","Position"));
        }catch (SQLException ex) {            
            Logger.getLogger(WorkExperiencePane.class.getName()).log(Level.SEVERE, null, ex);
             AlertException.ALERT("Error", ex);
        }
        
    }
    @Override
    public String updateMysql() {       
        var A=DBC.getupdates().get("WORKEXPERIENCE");
        try {
            A.setString(1,Position);
            A.setInt(2,getID());
            A.addBatch();
        } catch (SQLException ex) {
            Logger.getLogger(WORKEXPERIENCE.class.getName()).log(Level.SEVERE, null, ex);
             AlertException.ALERT("Error", ex);
        }    
//        String St="UPDATE positions SET Position='"+Position+"'WHERE id="+String.valueOf(getID())+";";
//        for (COMPANY c:Companies) St+=c.updateMysql();
//        return St;
        Companies.forEach((e)->e.updateMysql());
        return null;
    }
    @JsonProperty("Position")
    public void setPosition(String position){this.Position=position;}
    @JsonProperty("Companies")
    public void setCompanies(List<COMPANY> comps){this.Companies=comps;}
    @JsonProperty("Companies")
    public List<COMPANY> getCompanies(){return this.Companies;}
    @JsonProperty("Position")
    public String getPosition(){return this.Position;}
    
}
