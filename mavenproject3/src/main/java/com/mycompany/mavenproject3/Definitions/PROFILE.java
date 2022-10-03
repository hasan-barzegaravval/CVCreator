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
public class PROFILE extends DATATemplate {
    private String Photo;
    private String Age;
    private String MaterialStatus;
    private String Profile;
    private String Comment;
    private String ImageFilePath;
    public PROFILE(){};
    public PROFILE(int id,String age,String imagefilepath,String matSt,String profile,String comment,String photo){
        setID(id);
        Age=age;
        ImageFilePath=imagefilepath;
        MaterialStatus=matSt;
        Profile=profile;
        Comment=comment;
        if (!photo.isBlank()){
            Photo=photo;
        }else{
            ///load photo
        }
        
    }
    @Override
    public void insertMysql() {
        Object[] A={Age,ImageFilePath,MaterialStatus,Profile,Comment};        
        try {
            //st+="') WHERE Name='"+FINALS.ProjectName+"';";
            setID(DBC.insertSingle("PROFILE",A));
        } catch (SQLException ex) {
            Logger.getLogger(PROFILE.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }        
    }

    @Override
    public String updateMysql() {
        var A=DBC.getupdates().get("PROFILE");
        try {
            A.setString(1,Age);
            A.setString(2,ImageFilePath);
            A.setString(3,MaterialStatus);
            A.setString(4,Profile);
            A.setString(5,Comment);
            A.setInt(6,getID());
            A.addBatch();
        } catch (SQLException ex) {
            Logger.getLogger(PROFILE.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }
     
        return null;
    }
    ////added
    @JsonProperty("Photo")
    public String getPhoto(){ return this.Photo;}
    @JsonProperty("Age")
    public String getAge(){ return this.Age;}
    @JsonProperty("MaterialStatus")
    public String getMaterialStatus(){ return this.MaterialStatus;}
    @JsonProperty("Profile")
    public String getProfile(){ return this.Profile;}
    @JsonProperty("Comment")
    public String getComment(){ return this.Comment;}
    @JsonProperty("ImageFilePath")
    public String getImageFilePath(){ return this.ImageFilePath;}
    @JsonProperty("Age")
    public void setAge(String Age){ this.Age=Age;}
    @JsonProperty("MaterialStatus")
    public void setMaterialStatus(String MaterialStatus){ this.MaterialStatus=MaterialStatus;}
    @JsonProperty("Profile")
    public void setProfile(String Profile){ this.Profile=Profile;}
    @JsonProperty("Comment")
    public void setComment(String Comment){ this.Comment=Comment;}
    @JsonProperty("ImageFilePath")
    public void setImageFilePath(String ImageFilePath){ this.ImageFilePath=ImageFilePath;}
    @JsonProperty("Photo")
    public void setPhoto(String photo){this.Photo=photo;};

}
