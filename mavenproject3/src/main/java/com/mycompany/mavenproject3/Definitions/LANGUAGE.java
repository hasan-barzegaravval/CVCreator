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
public class LANGUAGE extends DATATemplate{
    private String Language;
    private String Fluency;
    private String Score;
    private String Test;
    private String Comment;
    public LANGUAGE(){
        
    }
    public LANGUAGE(int id, String lang,String fluency, String score, String test, String comment){
        setID(id);
        Language=lang;
        Fluency=fluency;
        Score=score;
        Test=test;
        Comment=comment;
    }
    
    @Override
    public void insertMysql() {
        Object[] A={Language,Fluency,Score,Test,Comment};
        
        try {
            setID(FINALS.DBC.insertSingle("LANGUAGE",A));
        }catch (SQLException ex) {
            Logger.getLogger(LANGUAGE.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }
    }

    @Override
    public String updateMysql() {
        var A=DBC.getupdates().get("LANGUAGE");
        try {
            A.setString(1, Language);
            A.setString(2, Fluency);
            A.setString(3, Score);
            A.setString(4, Test);
            A.setString(5, Comment);
            A.setInt(6, getID());
            A.addBatch();
        } catch (SQLException ex) {
            Logger.getLogger(LANGUAGE.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }  
        return null;

    }
    
    ///added
    @JsonProperty("Language")
    public String getLanguage(){ return this.Language;}
    @JsonProperty("Fluency")
    public String getFluency(){ return this.Fluency;}
    @JsonProperty("Score")
    public String getScore(){ return this.Score;}
    @JsonProperty("Test")
    public String getTest(){ return this.Test;}
    @JsonProperty("Comment")
    public String getComment(){ return this.Comment;}	
    @JsonProperty("Fluency")
    public void setFluency(String Fluency){ this.Fluency=Fluency;}
    @JsonProperty("Score")
    public void setScore(String Score){ this.Score=Score;}
    @JsonProperty("Test")
    public void setTest(String Test){ this.Test=Test;}
    @JsonProperty("Comment")
    public void setComment(String Comment){ this.Comment=Comment;}
    @JsonProperty("Language")
    public void setLanguage(String Language){ this.Language=Language;}
	
}