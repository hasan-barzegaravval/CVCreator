/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Definitions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.mavenproject3.Commons.AlertException;
import com.mycompany.mavenproject3.Commons.STRINGS;
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
public class PUBLICATION extends DATATemplate {
    private String Type;
    private String Title1;
    private String Title2;
    private List<String> Authors=new ArrayList();
    private String Journal;
    private String Volume;
    private String IS;
    private String SP;
    private String EP;
    private String SN;
    private String Year;
    private String PB;
    public PUBLICATION(){
        
    }
    public PUBLICATION(int id,String type,String title1,String title2,String authors,String journal,String volume,String is,String sp,String ep,String sn,String year,String pb){
        Type=type;
        Title1=title1;
        Title2=title2;
        Authors=STRINGS.StringToList(authors,"\n");
        Journal=journal;
        Volume=volume;
        IS=is;
        SP=sp;
        EP=ep;
        SN=sn;
        Year=year; 
        PB=pb;
        this.setID(id);
        
    }        
    @Override
    public void insertMysql() {
        Object[] A={Type,Title1,Title2,STRINGS.ListToString(Authors, '\n'),Journal,Volume,IS,SP,EP,SN,Year,PB};
        //List<Object> A=new ArrayList();
        
        
        try {
            setID(DBC.insertSingle("PUBLICATION",A));
        }catch (SQLException ex) {
            Logger.getLogger(PUBLICATION.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }
    }

    @Override
    public String updateMysql() {
        var A=DBC.getupdates().get("PUBLICATION");        
        try {
            A.setString(1, Type);
            A.setString(2, Title1);
            A.setString(3, Title2);
            A.setString(4, STRINGS.ListToString(Authors, '\n'));
            A.setString(5, Journal);
            A.setString(6, Volume);
            A.setString(7, IS);
            A.setString(8, SP);
            A.setString(9, EP);
            A.setString(10, SN);
            A.setString(11, Year);
            A.setString(12, PB);
            A.setInt(13, getID());
            A.addBatch();
        } catch (SQLException ex) {
            Logger.getLogger(PUBLICATION.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }
        return null;
    }
    ////added
    @JsonProperty("Type")
    public String getType(){ return this.Type;}
    @JsonProperty("Title1")
    public String getTitle1(){ return this.Title1;}
    @JsonProperty("Title2")
    public String getTitle2(){ return this.Title2;}
    @JsonProperty("Journal")
    public String getJournal(){ return this.Journal;}
    @JsonProperty("Volume")
    public String getVolume(){ return this.Volume;}
    @JsonProperty("IS")
    public String getIS(){ return this.IS;}
    @JsonProperty("SP")
    public String getSP(){ return this.SP;}
    @JsonProperty("EP")
    public String getEP(){ return this.EP;}
    @JsonProperty("SN")
    public String getSN(){ return this.SN;}
    @JsonProperty("Year")
    public String getYear(){ return this.Year;}
    @JsonProperty("PB")
    public String getPB(){ return this.PB;}
    @JsonProperty("Authors")
    public List<String> getAuthors(){ return this.Authors;}
    @JsonProperty("Authors")
    public void setAuthors(List<String> Authors){ this.Authors=Authors;}
    @JsonProperty("Journal")
    public void setJournal(String Journal){ this.Journal=Journal;}
    @JsonProperty("Volume")
    public void setVolume(String Volume){ this.Volume=Volume;}
    @JsonProperty("IS")
    public void setIS(String IS){ this.IS=IS;}
    @JsonProperty("SP")
    public void setSP(String SP){ this.SP=SP;}
    @JsonProperty("EP")
    public void setEP(String EP){ this.EP=EP;}
    @JsonProperty("SN")
    public void setSN(String SN){ this.SN=SN;}
    @JsonProperty("Year")
    public void setYear(String Year){ this.Year=Year;}
    @JsonProperty("PB")
    public void setPB(String PB){ this.PB=PB;}
    @JsonProperty("Type")
    public void setType(String Type){ this.Type=Type;}
    @JsonProperty("Title1")
    public void setTitle1(String Title1){ this.Title1=Title1;}
    @JsonProperty("Title2")
    public void setTitle2(String Title2){ this.Title2=Title2;}

	
}
