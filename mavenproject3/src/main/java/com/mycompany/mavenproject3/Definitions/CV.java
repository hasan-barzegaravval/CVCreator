/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Definitions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.mavenproject3.Commons.AlertException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
/**
 *
 * @author hasan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CV extends DATATemplate {
    private String Name;
    private HEADER Header;
    private PROFILE Profile;
    private List<EDUCATION> Education;
    private List<WORKEXPERIENCE> WorkExperience;
    private List<SKILL> Skills;
    private List<LANGUAGE> Languages;
    private List<REFERENCE> References;
    private List<PUBLICATION> Publications;
    public CV(){ 
        
    }
    public CV(String name){
        Name=name;
        Header=new HEADER();
        Profile=new PROFILE();
        Education=new ArrayList();
        WorkExperience=new ArrayList();
        Skills=new ArrayList();
        Languages=new ArrayList();
        References=new ArrayList();
        Publications=new ArrayList();
        
    }
    @Override
    public void insertMysql() {        
        try {      
            setID(DBC.insertSingle("CV",getObjectlist()));
        } catch (SQLException ex) {
            Logger.getLogger(CV.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error", ex);
        }
    }
    
    public Object[] getObjectlist(){        
        Object[] A=new Object[10];        
        A[0]=Name;
        A[1]=Header.getID();
        A[2]=Header.getInfo().stream().map((e)->(e.getID())).collect(Collectors.toList()).toString();
        A[3]=Profile.getID();
        A[4]=Education.stream().map((e)->(e.getID())).collect(Collectors.toList()).toString();
        A[6]=Languages.stream().map((e)->(e.getID())).collect(Collectors.toList()).toString();
        A[7]=Skills.stream().map((e)->(e.getID())).collect(Collectors.toList()).toString();
        A[8]=References.stream().map((e)->(e.getID())).collect(Collectors.toList()).toString();
        A[9]=Publications.stream().map((e)->(e.getID())).collect(Collectors.toList()).toString();
        StringBuilder st=new StringBuilder();
        for (WORKEXPERIENCE w:WorkExperience){
            st.append(w.getID()).append("#");
            for(COMPANY c:w.getCompanies()){
                st.append(c.getID())
                        .append("-").append(c.getACHIDS().toString())
                        .append("-").append(c.getRESPIDS().toString())
                        .append("-").append(c.getProjects().stream().map((e)->(e.getID())).collect(Collectors.toList()).toString())
                        .append("#");
            }
            st.append("/");
        }
        A[5]=st.toString();        
        return A;
    }
    @Override
    public String updateMysql() {
        Header.updateMysql();
        Profile.updateMysql();
        Education.forEach(e->e.updateMysql());
        Languages.forEach(e->e.updateMysql());
        Skills.forEach(e->e.updateMysql());
        References.forEach(e->e.updateMysql());
        Publications.forEach(e->e.updateMysql());
        WorkExperience.forEach(e->{e.updateMysql();e.getCompanies().forEach(s->s.updateMysql());});        
        DBC.getupdates().forEach((key,value)->{if(!(key.equals("CV"))){
            try {
                value.executeBatch();
            } catch (SQLException ex) {
                Logger.getLogger(CV.class.getName()).log(Level.SEVERE, null, ex);
            }}});
        Object[] A=getObjectlist();  
        try {
            for(int i=0;i<A.length;i++) DBC.getupdates().get("CV").setObject(i+1, A[i]);
            DBC.getupdates().get("CV").setObject(A.length+1,getID());
            DBC.getupdates().get("CV").executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(CV.class.getName()).log(Level.SEVERE, null, ex);
                AlertException.ALERT("Error", ex);
            }
        return null;
    }
    public void updateor(){
        getObjectlist();
        getID();
    }   
    
    @JsonProperty("Name")
    public void setName(String name){this.Name=name;}
    @JsonProperty("Header")
    public void setHeader(HEADER Header){ this.Header=Header;}
    @JsonProperty("Profile")
    public void setProfile(PROFILE Profile){ this.Profile=Profile;}
    @JsonProperty("Education")
    public void setEducation(List<EDUCATION> Education){ this.Education=Education;}
    @JsonProperty("WorkExperience")
    public void setWorkExperience(List<WORKEXPERIENCE> WorkExperience){ this.WorkExperience=WorkExperience;}
    @JsonProperty("Skills")
    public void setSkills(List<SKILL> Skills){ this.Skills=Skills;}
    @JsonProperty("Languages")
    public void setLanguages(List<LANGUAGE> Languages){ this.Languages=Languages;}
    @JsonProperty("References")
    public void setReferences(List<REFERENCE> References){ this.References=References;}
    @JsonProperty("Publications")
    public void setPublications(List<PUBLICATION> Publications){ this.Publications=Publications;} 
    @JsonProperty("Name")
    public String getName(){ return this.Name;}
    @JsonProperty("Header")
    public HEADER getHeader(){ return this.Header;}
    @JsonProperty("Profile")
    public PROFILE getProfile(){ return this.Profile;}
    @JsonProperty("Education")
    public List<EDUCATION> getEducation(){ return this.Education;}
    @JsonProperty("WorkExperience")
    public List<WORKEXPERIENCE> getWorkExperience(){ return this.WorkExperience;}
    @JsonProperty("Skills")
    public List<SKILL> getSkills(){ return this.Skills;}
    @JsonProperty("Languages")
    public List<LANGUAGE> getLanguages(){ return this.Languages;}
    @JsonProperty("References")
    public List<REFERENCE> getReferences(){ return this.References;}
    @JsonProperty("Publications")
    public List<PUBLICATION> getPublications(){ return this.Publications;}
 
	
}
