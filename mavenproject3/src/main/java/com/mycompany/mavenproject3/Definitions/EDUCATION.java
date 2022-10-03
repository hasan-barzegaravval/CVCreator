/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Definitions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.mavenproject3.Commons.AlertException;
import static com.mycompany.mavenproject3.Commons.FINALS.DBC;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author hasan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EDUCATION extends DATATemplate {
    private String Degree;
    private String Institue;
    private String Link;
    private String StartDate;
    private String EndDate;
    private String Thesis;
    private String CGPA;
    private String Comment;
    public EDUCATION(){} 
    
    public EDUCATION(int id,String degree,String institue,String link,String startdate,String enddate,String thesis,String cgpa,String comment){
        setID(id);
        Degree=degree;
        Institue=institue;
        Link=link;
        StartDate=startdate;
        EndDate=enddate;
        Thesis=thesis;
        CGPA=cgpa;
        Comment=comment;
    }
    

    @Override
    public void insertMysql() {
        Object[] A={Degree,Institue,Link,StartDate,EndDate,Thesis,CGPA,Comment};
        //List<Object> A=new ArrayList();
        
        
        try {
            setID(DBC.insertSingle("EDUCATION",A));
        }catch (SQLException ex) {
            Logger.getLogger(EDUCATION.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }
    }

    @Override
    public String updateMysql() {
        var A=DBC.getupdates().get("EDUCATION");        
        try {
            A.setString(1, Degree);
            A.setString(2, Institue);
            A.setString(3, Link);
            A.setString(4, StartDate);
            A.setString(5, EndDate);
            A.setString(6, Thesis);
            A.setString(7, CGPA);
            A.setString(8, Comment);
            A.setInt(9, getID());
            A.addBatch();
        } catch (SQLException ex) {
            Logger.getLogger(EDUCATION.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }
        
//        
//        return "UPDATE educations SET "+
//                "Degree='"+Degree+"', "+
//                "Institue='"+Institue+"', "+
//                "Link='"+Link+"', "+
//                "StartDate='"+StartDate+"', "+
//                "EndDate='"+EndDate+"', "+
//                "Thesis='"+Thesis+"', "+
//                "CGPA='"+CGPA+"', "+
//                "Comment='"+Comment+"' WHERE id="+String.valueOf(getID())+";"; 
        return null;
    }
    @JsonProperty("Degree")
    public String getDegree(){return Degree;}        
    @JsonProperty("Degree")
    public void setDegree(String degree){Degree=degree;}        
    @JsonProperty("Institue")
    public String getInstitue(){ return this.Institue;}
    @JsonProperty("Link")
    public String getLink(){ return this.Link;}
    @JsonProperty("StartDate")
    public String getStartDate(){ return this.StartDate;}
    @JsonProperty("EndDate")
    public String getEndDate(){ return this.EndDate;}
    @JsonProperty("Thesis")
    public String getThesis(){ return this.Thesis;}
    @JsonProperty("CGPA")
    public String getCGPA(){ return this.CGPA;}
    @JsonProperty("Comment")
    public String getComment(){ return this.Comment;}
    @JsonProperty("Institue")
    public void setInstitue(String Institue){ this.Institue=Institue;}
    @JsonProperty("Link")
    public void setLink(String Link){ this.Link=Link;}
    @JsonProperty("StartDate")
    public void setStartDate(String StartDate){ this.StartDate=StartDate;}
    @JsonProperty("EndDate")
    public void setEndDate(String EndDate){ this.EndDate=EndDate;}
    @JsonProperty("Thesis")
    public void setThesis(String Thesis){ this.Thesis=Thesis;}
    @JsonProperty("CGPA")
    public void setCGPA(String CGPA){ this.CGPA=CGPA;}
    @JsonProperty("Comment")
    public void setComment(String Comment){ this.Comment=Comment;}
}
