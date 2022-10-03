/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Definitions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.mavenproject3.Commons.AlertException;
import com.mycompany.mavenproject3.Commons.FINALS;
import static com.mycompany.mavenproject3.Commons.FINALS.DBC;
import com.mycompany.mavenproject3.UIElements.CompanyPane;
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
public class COMPANY extends DATATemplate{
        private String Company;
        private String Link;
        private List<PROJECT> Projects;
        private String StartDate;
        private String EndDate;
        private List<String> Achievements;
        private List<String> Responsibilities;
        private List<Integer> ACHIDS;
        private List<Integer> RESPIDS;
        public COMPANY(){}
        public COMPANY(COMPANY co){
            setID(co.getID());
            this.Company=co.getCompany();
            this.Link=co.getLink();
            this.Projects=new ArrayList(co.getProjects());
            this.StartDate=co.getStartDate();
            this.EndDate=co.getEndDate();
            this.Achievements=new ArrayList(co.getAchievements());
            this.Responsibilities=new ArrayList(co.getResponsibilities());
            this.ACHIDS=new ArrayList(co.getACHIDS());
            this.RESPIDS=new ArrayList(co.getRESPIDS());
        }
        public COMPANY(int id,String company,String link,String startdate,String enddate,List<PROJECT> projects,List<String> Achi,List<String> Resp,List<Integer> AchiID,List<Integer> RespID){
            setID(id);
            Company=company;
            Link=link;
            StartDate=startdate;
            EndDate=enddate;
            Projects=projects;
            Achievements=Achi;
            Responsibilities=Resp;
            ACHIDS=AchiID;
            RESPIDS=RespID;   
        }
        public COMPANY(int id,String company,String link,String startdate,String enddate){
            setID(id);
            Company=company;
            Link=link;
            StartDate=startdate;
            EndDate=enddate;              
        }
        @Override
        public void insertMysql() {
            Object[] A={Company,Link,StartDate,EndDate};       
            try {
                setID(DBC.insertSingle("COMPANY",A));
            }catch (SQLException ex) {
                Logger.getLogger(PROJECT.class.getName()).log(Level.SEVERE, null, ex);
                AlertException.ALERT("Error", ex);
            }
            getACHIDS();getRESPIDS();
            Projects.forEach((e)->e.getID());

        }
        @JsonProperty("ACHIDS")
        public void setACHIDS(List<Integer> ACHIDS){
            this.ACHIDS=ACHIDS;
        }
        @JsonProperty("RESPIDS")
        public void setRESPIDS(List<Integer> respid){
            RESPIDS=respid;
        }
        @JsonProperty("ACHIDS")
        public List<Integer> getACHIDS(){
            if (Achievements==null) 
                return this.ACHIDS;
            if (DBC.getstatus().equals("Not Connected")) return this.ACHIDS;        
            try {
                ACHIDS=DBC.insertList(Achievements,"achievements","Achievements");
            }catch (SQLException ex) {
                Logger.getLogger(COMPANY.class.getName()).log(Level.SEVERE, null, ex);
                AlertException.ALERT("Error", ex);

            }
            return ACHIDS;
        }
        @JsonProperty("RESPIDS")
        public List<Integer> getRESPIDS(){
            if (DBC.getstatus().equals("Not Connected")) return RESPIDS;
            try {
                RESPIDS=DBC.insertList(Responsibilities,"responsibilities","Responsibilities");
            }catch (SQLException ex) {
                Logger.getLogger(CompanyPane.class.getName()).log(Level.SEVERE, null, ex);
                AlertException.ALERT("Error", ex);

            }
            return RESPIDS;
        }
        @Override
        public String updateMysql() { 
            var A=DBC.getupdates().get("COMPANY");        
            try {
                A.setString(1, Company);
                A.setString(2, Link);
                A.setString(3, StartDate);
                A.setString(4, EndDate);
                A.setInt(5, getID());
                A.addBatch();
                int i=0;
                A=DBC.getupdates().get("RESPONSIBILITES");
                if (Responsibilities!=null){
                    for (String e:Responsibilities){
                        try{
                            A.setString(1, e);
                            A.setInt(2, RESPIDS.get(i));
                            A.addBatch();
                        }catch(IndexOutOfBoundsException exc){
                            RESPIDS.add(FINALS.DBC.insertSingleUnique(e,"responsibilities","Responsibilities"));


                        }
                        i++;
                    }
                }
                A=DBC.getupdates().get("ACHIEVEMENTS");
                i=0;
                if (Achievements!=null){
                    for (String e:Achievements){
                        try{
                            A.setString(1, e);
                            A.setInt(2, ACHIDS.get(i));
                            A.addBatch();
                        }catch(IndexOutOfBoundsException exc){
                            ACHIDS.add(FINALS.DBC.insertSingleUnique(e,"achievements","Achievements"));
                        }
                        i++;
                    } 
                }

            } catch (SQLException ex) {
                Logger.getLogger(COMPANY.class.getName()).log(Level.SEVERE, null, ex);
                AlertException.ALERT("Error", ex);
            }
            Projects.forEach((e)->e.updateMysql());
            return null;
        }        
        @JsonProperty("Company")
        public String getCompany(){ return this.Company;}
        @JsonProperty("Company")
        public void setCompany(String company){this.Company=company;}
        @JsonProperty("Link")
	public String getLink(){ return this.Link;}
        @JsonProperty("Link")
        public void setLink(String Link){ this.Link=Link;}
        @JsonProperty("Projects")
	public List<PROJECT> getProjects(){ return this.Projects;}
        @JsonProperty("StartDate")
	public String getStartDate(){ return this.StartDate;}
	@JsonProperty("EndDate")
        public String getEndDate(){ return this.EndDate;}
	@JsonProperty("Achievements")
        public List<String> getAchievements(){ return this.Achievements;}
	@JsonProperty("Responsibilities")
        public List<String> getResponsibilities(){ return this.Responsibilities;}	
        @JsonProperty("Projects")
	public void setProjects(List<PROJECT> Projects){ this.Projects=Projects;}
	@JsonProperty("StartDate")
        public void setStartDate(String StartDate){ this.StartDate=StartDate;}
	@JsonProperty("EndDate")
        public void setEndDate(String EndDate){ this.EndDate=EndDate;}
	@JsonProperty("Achievements")
        public void setAchievements(List<String> Achievements){ this.Achievements=Achievements;}
	@JsonProperty("Responsibilities")
        public void setResponsibilities(List<String> Responsibilities){ this.Responsibilities=Responsibilities;}
     
}
