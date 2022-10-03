/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Definitions;
import com.mycompany.mavenproject3.Commons.DBMYSQL;
import com.mycompany.mavenproject3.Commons.STRINGS;
import com.mycompany.mavenproject3.UIElements.DBViewer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author hasan
 */
public class SESSION {
    private String PROJECTNAME;
    private HashMap<Integer,HEADER> HEADERS;
    private HashMap<Integer,INFO> INFOS;
    private HashMap<Integer,WORKEXPERIENCE> WORKEXPERIENCES;
    private HashMap<Integer,EDUCATION> EDUCATIONS;
    private HashMap<Integer,COMPANY> COMPANIES;
    private HashMap<Integer,LANGUAGE> LANGUAGES;
    private HashMap<Integer,PROFILE> PROFILES;
    private HashMap<Integer,PROJECT> PROJECTS;
    private HashMap<Integer,REFERENCE> REFERENCES;
    private HashMap<Integer,SKILL> SKILLS;
    private HashMap<Integer,String> RESPONSIBILITIES;
    private HashMap<Integer,String> ACHIEVEMENTS;
    private HashMap<Integer,PUBLICATION> PUBLICATIONS;
    private HashMap<Integer,Object[]> CVS;
    private HashMap<String,Integer> CVNames;
    private HashMap<String,DBViewer> DBViewers;
    private CV CurrentCV; 
    private String JsonFilePath;
    private String DefaultDirectory;
    public SESSION(){
        DefaultDirectory=System.getProperty("user.dir")+"\\src\\main\\resources\\com\\mycompany\\HTMLCSSJS\\";
    }
    public void initSESSIONwithDB(DBMYSQL DB)throws SQLException{        
        this.HEADERS=DB.LoadHeaders();
        this.INFOS=DB.LoadInfos();
        this.WORKEXPERIENCES=DB.LoadWorkExperiences();
        this.ACHIEVEMENTS=DB.loadAchi();
        this.COMPANIES=DB.loadCompanies();
        this.CVS=DB.loadCV();
        this.LANGUAGES=DB.loadLanguages();
        this.PROFILES=DB.loadPofiles();
        this.PROJECTS=DB.loadProjects();
        this.REFERENCES=DB.loadReferences();
        this.SKILLS=DB.loadSkills();
        this.RESPONSIBILITIES=DB.loadResp();  
        this.EDUCATIONS=DB.LoadEducations(); 
        this.PUBLICATIONS=DB.loadPublication();       
        DBViewers=new HashMap();
        List<String> A=Arrays.asList("ID","Degree","Institue","StartDate","EndDate","Thesis","CGPA","Link","Comment");
        DBViewers.put("EDUCATION",new DBViewer(EDUCATIONS,A));
        A=Arrays.asList("ID","Company","Link","StartDate","EndDate");
        DBViewers.put("COMPANY",new DBViewer(COMPANIES,A));
        A=Arrays.asList("ID","Name","Discription");
        DBViewers.put("HEADER",new DBViewer(HEADERS,A));          
        A=Arrays.asList("ID","Name","link","Value","imageFilePath");
        DBViewers.put("INFO",new DBViewer(INFOS,A));  
        A=Arrays.asList("ID","Age","MaterialStatus","Profile","ImageFilePath","Comment");
        DBViewers.put("PROFILE",new DBViewer(PROFILES,A));
        A=Arrays.asList("ID","Language","Fluency","Score","Test","Comment");
        DBViewers.put("LANGUAGE",new DBViewer(LANGUAGES,A));
        A=Arrays.asList("ID","Skill","Rate");        
        DBViewers.put("SKILL",new DBViewer(SKILLS,A));
        A=Arrays.asList("ID","Position");
        DBViewers.put("WORKEXPERIENCE",new DBViewer(WORKEXPERIENCES,A));
        A=Arrays.asList("ID","Title","Link","Comment");
        DBViewers.put("PROJECT",new DBViewer(PROJECTS,A));
        A=Arrays.asList("ID","Type","Year","Title1","Authors");
        DBViewers.put("PUBLICATION",new DBViewer(PUBLICATIONS,A));
        DBViewers.put("ACHIEVEMENTS",new DBViewer(ACHIEVEMENTS,"Achievements"));
        DBViewers.put("RESPONSIBILITIES",new DBViewer(RESPONSIBILITIES,"Responsibilities"));     
        A=Arrays.asList("ID","Name","Position","Company","Phone","Email","Comment");
        DBViewers.put("REFERENCE",new DBViewer(REFERENCES,A)); 
        getCVNames();        
    }
    public void setPROJECTNAME(String PN){this.PROJECTNAME=PN;}
    public final HashMap<String,Integer> getCVNames(){
        if (CVNames==null) CVNames=new HashMap();
            CVS.forEach((key,value)->{CVNames.put(value[0].toString(), key);});
        return CVNames;
    }
    public CV CVLoader(String CVname) {
        CurrentCV=new CV();
        CurrentCV.setName(CVname);
        CurrentCV.setID(CVNames.get(CVname));
        Object[] A=CVS.get(CVNames.get(CVname));
        CurrentCV.setHeader(new HEADER((int) A[1],HEADERS.get((int)A[1]).getName(),HEADERS.get((int)A[1]).getDiscription()));
        CurrentCV.getHeader().setInfo(STRINGS.StringtoIntList(A[2].toString()).stream().map((e)->{return (INFO)INFOS.get(e).Clone();}).collect(Collectors.toList()));
        CurrentCV.setProfile((PROFILE)PROFILES.get((int)A[3]).Clone());
        CurrentCV.setEducation(STRINGS.StringtoIntList(A[4].toString()).stream().map((e)->{return (EDUCATION)EDUCATIONS.get(e).Clone();}).collect(Collectors.toList()));
        CurrentCV.setLanguages(STRINGS.StringtoIntList(A[6].toString()).stream().map((e)->{return (LANGUAGE)LANGUAGES.get(e).Clone();}).collect(Collectors.toList()));
        CurrentCV.setSkills(STRINGS.StringtoIntList(A[7].toString()).stream().map((e)->{return (SKILL)SKILLS.get(e).Clone();}).collect(Collectors.toList()));
        CurrentCV.setReferences(STRINGS.StringtoIntList(A[8].toString()).stream().map((e)->{return (REFERENCE)REFERENCES.get(e).Clone();}).collect(Collectors.toList()));
        CurrentCV.setPublications(STRINGS.StringtoIntList(A[9].toString()).stream().map((e)->{return (PUBLICATION)PUBLICATIONS.get(e).Clone();}).collect(Collectors.toList()));      
        String[] WorkExp=A[5].toString().split("/");
        List<WORKEXPERIENCE> WKS=new ArrayList();
        List<PROJECT> PRO=null;
        List<String> ACH=null;
        List<String> RES=null;
        for (int i=0;i<WorkExp.length;i++){
            String[] Pos=WorkExp[i].split("#");
            int wkid=Integer.valueOf(Pos[0]);
            List<COMPANY> Companies=new ArrayList();
            for(int j=1;j<Pos.length;j++){
                String[] Co=Pos[j].split("-");
                int coid=Integer.valueOf(Co[0]);
                List<Integer> ACHIDS=STRINGS.StringtoIntList(Co[1]);
                List<Integer> RESPIDS=STRINGS.StringtoIntList(Co[2]);
                List<Integer> PROIDS=STRINGS.StringtoIntList(Co[3]);
                
                PRO=(PROIDS!=null)? PROIDS.stream().map((e)->(PROJECT)PROJECTS.get(e).Clone()).collect(Collectors.toList()):new ArrayList<>();
                ACH=(ACHIDS!=null)? ACHIDS.stream().map((e)->ACHIEVEMENTS.get(e)).collect(Collectors.toList()):new ArrayList<>();
                RES=(RESPIDS!=null)? RESPIDS.stream().map((e)->RESPONSIBILITIES.get(e)).collect(Collectors.toList()):new ArrayList<>();
                Companies.add(new COMPANY(coid,COMPANIES.get(coid).getCompany(),COMPANIES.get(coid).getLink()
                        ,COMPANIES.get(coid).getStartDate(),COMPANIES.get(coid).getEndDate()
                        ,PRO,ACH,RES,ACHIDS,RESPIDS));                
            }
            WKS.add(new WORKEXPERIENCE(wkid,WORKEXPERIENCES.get(wkid).getPosition(),Companies));
        }
        CurrentCV.setWorkExperience(WKS);
        return CurrentCV;        
    }
    
    public HashMap<Integer,Object[]> getCVS(){return CVS;}
    public HashMap<String,DBViewer> getDBViewers(){return DBViewers;}
    public CV getCurrentCV(){return CurrentCV;}
    public String getJsonFilePath(){return JsonFilePath;}
    public String getDefaultDirectory(){return DefaultDirectory;}
    public void setCurrentCV(CV cv){this.CurrentCV=cv;}
    public void setJsonFilePath(String jsonfilepath){this.JsonFilePath=jsonfilepath;}
    public void setDefaultDirectory(String defaultdirectory){this.DefaultDirectory=defaultdirectory;}
    
    
}
