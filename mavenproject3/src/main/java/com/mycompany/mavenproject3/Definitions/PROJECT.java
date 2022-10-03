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
 public class PROJECT extends DATATemplate{
        private String Title;
        private String Comment;
        private String Link;
        public PROJECT(){}
        public PROJECT(int id, String title,String link, String comment){
            setID(id);
            Title=title;
            Link=link;
            Comment=comment;
        }

    @Override
    public void insertMysql() {
        Object[] A={Title,Link,Comment};
        try {
            setID(DBC.insertSingle("PROJECT",A));
        }catch (SQLException ex) {
            Logger.getLogger(PROJECT.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }
    }
    @Override
    public String updateMysql() {
        var A=DBC.getupdates().get("PROJECT");
            try {
                A.setString(1, Title);
                A.setString(2, Link);
                A.setString(3,Comment);
                A.setInt(4, getID());
                A.addBatch();
            } catch (SQLException ex) {
                Logger.getLogger(PROJECT.class.getName()).log(Level.SEVERE, null, ex);
                AlertException.ALERT("Error", ex);
            }
        return null;
    }
    ////added
    @JsonProperty("Title")
    public String getTitle(){ return this.Title;}
    @JsonProperty("Comment")
    public String getComment(){ return this.Comment;}
    @JsonProperty("Link")
    public String getLink(){ return this.Link;}
    @JsonProperty("Comment")
    public void setComment(String Comment){ this.Comment=Comment;}
    @JsonProperty("Link")
    public void setLink(String Link){ this.Link=Link;}
    @JsonProperty("Title")
    public void setTitle(String title){ this.Title=title;}

}
