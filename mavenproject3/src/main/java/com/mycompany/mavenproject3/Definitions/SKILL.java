/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Definitions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.mavenproject3.Commons.AlertException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author hasan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SKILL extends DATATemplate{
    private String Skill;
    private int Rate; 
    public SKILL(){} 
    public SKILL(int id, String skill,int rate){
        setID(id);
        Skill=skill;
        Rate=rate;
    }
    
    @Override
    public void insertMysql() {
        Object[] A={Skill,Rate};        
        try {
            setID(DBC.insertSingle("SKILL",A));
        }catch (SQLException ex) {
            Logger.getLogger(SKILL.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
            
        }
    }

    @Override
    public String updateMysql() {
        var A=DBC.getupdates().get("SKILL");        
        try {
            A.setString(1, Skill);
            A.setInt(2, Rate);
            A.setInt(3, getID());
            A.addBatch();
        } catch (SQLException ex) {
            Logger.getLogger(SKILL.class.getName()).log(Level.SEVERE, null, ex);
             AlertException.ALERT("Error", ex);
        }
        return null;
    }
    ////added
    @JsonProperty("Skill")
    public String getSkill(){ return this.Skill;}
    @JsonProperty("Skill")
    public void setSkill(String skill){this.Skill=skill;}
    @JsonProperty("Rate")
    public int getRate(){ return this.Rate;}
    @JsonProperty("Rate")
    public void setRate(int Rate){ this.Rate=Rate;}
	
}
