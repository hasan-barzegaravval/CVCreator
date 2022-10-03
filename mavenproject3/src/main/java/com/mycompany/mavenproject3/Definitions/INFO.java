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
public class INFO extends DATATemplate{
    private String Name;
    private String Link;
    private String Value;
    private String icon;
    private String ImageFilePath;
    public INFO(){}
    public INFO(int id,String name,String link, String value, String icon, String imagefilepath){
        setID(id);
        Name=name;
        Link=link;
        Value=value;
        this.icon=icon;
        ImageFilePath=imagefilepath;        
    }
    public INFO(int id,String name,String link, String value, String imagefilepath){
        setID(id);
        Name=name;
        Link=link;
        Value=value;
        ImageFilePath=imagefilepath;        
    }
    @Override
    public void insertMysql() {
        Object[] A={Name,Link,Value,ImageFilePath};
        try {
            setID(DBC.insertSingle("INFO",A));
        }catch (SQLException ex) {
            Logger.getLogger(INFO.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }
    }
    @Override
    public String updateMysql() {        
        var A=DBC.getupdates().get("INFO");
        try {
            A.setString(1, Name);
            A.setString(2,Link);
            A.setString(3,Value);
            A.setString(4,ImageFilePath);
            A.setInt(5,getID());            
            A.addBatch();
        } catch (SQLException ex) {
            Logger.getLogger(HEADER.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }
        //return "UPDATE infos SET "+"Name='"+Name+"',"+"Link='"+Link+"',"+"Value='"+Value+"',"+"icon='"+ImageFilePath+"' WHERE id="+String.valueOf(getID())+";";
        return null;
    }
    	@JsonProperty("Name")
        public String getName(){ return this.Name;}
        @JsonProperty("Link")
	public String getLink(){ return this.Link;}
	@JsonProperty("Value")
        public String getValue(){ return this.Value;}
	@JsonProperty("icon")
        public String geticon(){ return this.icon;}
	@JsonProperty("ImageFilePath")
        public String getImageFilePath(){ return this.ImageFilePath;}	
	@JsonProperty("Name")
        public void setName(String Name){ this.Name=Name;}
	@JsonProperty("Link")
        public void setLink(String Link){ this.Link=Link;}
	@JsonProperty("Value")
        public void setValue(String Value){ this.Value=Value;}
	@JsonProperty("icon")
        public void seticon(String icon){ this.icon=icon;}
	@JsonProperty("ImageFilePath")
        public void setImageFilePath(String ImageFilePath){ this.ImageFilePath=ImageFilePath;}
        
}
