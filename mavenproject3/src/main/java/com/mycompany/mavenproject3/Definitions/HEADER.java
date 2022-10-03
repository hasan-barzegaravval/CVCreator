/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Definitions;

/**
 *
 * @author hasan
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.mavenproject3.Commons.AlertException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
@JsonIgnoreProperties(ignoreUnknown = true)
public class HEADER extends DATATemplate {
    private String Name;
    private String Discription;
    private List<INFO> Info;
    public HEADER(){}
    public HEADER(int id,String name,String discription,List<INFO> info){
        setID(id);
        Name=name;
        Discription=discription;
        Info=info;
    }
    public HEADER(int id,String name,String discription){
        setID(id);
        Name=name;
        Discription=discription;
    }
    @Override
    public void insertMysql() {
        Object[] A={Name,Discription};
        try {
            setID(DBC.insertSingle("HEADER",A));
            Info.forEach(e->e.insertMysql());
        }catch (SQLException ex) {
            Logger.getLogger(HEADER.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }; 
    }
    @Override
    public String updateMysql() {
        var A=DBC.getupdates().get("HEADER");
        try {
            A.setString(1, Name);
            A.setString(2,Discription);
            A.setInt(3, getID());
            A.addBatch();
            Info.forEach((e)->e.updateMysql());
        } catch (SQLException ex) {
            Logger.getLogger(HEADER.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }
        
//        String st="Update header SET "+
//                "Name='"+Name+"',"+
//                "Discription='"+Discription+"', WHERE id="+getID()+";";
//        for(INFO e:Info) st+=e.updateMysql();    
        return null;
    }  
    ////added
    @JsonProperty("Name")
    public String getName(){ return this.Name;}
    @JsonProperty("Discription")
    public String getDiscription(){ return this.Discription;}
    @JsonProperty("Info")
    public List<INFO> getInfo(){ return this.Info;}
    @JsonProperty("Discription")
    public void setDiscription(String Discription){ this.Discription=Discription;}
    @JsonProperty("Info")
    public void setInfo(List<INFO> Info){ this.Info=Info;}
    @JsonProperty("Name")
    public void setName(String name){ this.Name=name;}
	
}
