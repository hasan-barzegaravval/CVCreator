/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Commons;
import com.mycompany.mavenproject3.Definitions.SESSION;
import java.io.File;
import javafx.scene.image.Image;

/**
 * FINALS is an interface for shared data.
 * @author hasan
 * @version 1.0
 * 
 */

public interface FINALS {
    public static final double WIDTH=840;
    public static final double HEIGHT=540;
    public static final double TABH=HEIGHT;
    public static final double TABW=WIDTH;
    public static final DBMYSQL DBC=new DBMYSQL();
    public static final SESSION CurrentSession=new SESSION();
    public static final String SEP=File.separator;
    public static final String RESOURCEFOLDER=System.getProperty("user.dir")+SEP+"src"+SEP+"main"+SEP+"resources"+SEP+"com"+SEP+"mycompany"+SEP;
    public static final String STYLES="file:/"+(RESOURCEFOLDER+"Styles"+SEP).replace("\\","/");
    public static final Image  APPICON=new Image(RESOURCEFOLDER+SEP+"icons"+SEP+"Logo.png");
    /**
     * This is an Enum type that can be used instead of the constant values for 
     * class names and their corresponding database tables. 
     * It is not implemented to ease understanding the code but it is useful to 
     * be used to reduce the errors due to wrong spelling of class and table names.
     */
    public enum DMap{
        COMPANY("COMPANY","company"),
        CV("CV","cvs"),
        EDUCATION("EDUCATION","education"),
        HEADER("HEADER","header"),
        INFO("INFO","infos"),
        LANGUAGE("LANGUAGE","language"),
        PROFILE("PROFILE","profile"),
        PROJECT("PROJECT","projects"),
        PUBLICATIONS("PUBLICATION","publications"),
        REFERENCE("REFERENCE","reference"),
        RESPONSIBILITIES("#COMPANY","responsibilities"),
        ACHIEVEMENTS("#COMPANY","achievements"),
        SKILL("SKILL","skills"),
        POSITION("#WORKEXPERIENCE","positions");
        private String ClassName;
        private String TableName;
        private DMap(String classname,String tablename){
            ClassName=classname;
            TableName=tablename;
        }
        public String ClassName(){return ClassName;}
        public String TableName(){return TableName;}
    }   
    
}
