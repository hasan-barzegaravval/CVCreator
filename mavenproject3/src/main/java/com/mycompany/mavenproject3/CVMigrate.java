/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;
import com.mycompany.mavenproject3.Commons.AlertException;
import com.mycompany.mavenproject3.Commons.FINALS;
import com.mycompany.mavenproject3.Definitions.CV;
import java.sql.SQLException;

/**
 *
 * @author hasan
 */
public class CVMigrate {
    public static CV Migrateto(CV cv,String DBNAME) throws SQLException{
        FINALS.DBC.CreateRDB(DBNAME); 
        try{
            if (cv==null) throw new AlertException("CV is not defined yet. create or load a cv.","CV is not defined","Error");
            if ((cv.getName()==null)) throw new AlertException("Set a CV name. Its Null.","CV name is not set","Error");
            if ((cv.getName().isEmpty())) throw new AlertException("Set a CV name. Its Empty.","CV name is not set","Error");
            cv.getEducation().forEach((e)->{e.insertMysql();});
            cv.getHeader().insertMysql();
            cv.getLanguages().forEach((e)->{e.insertMysql();});
            cv.getProfile().insertMysql();
            cv.getPublications().forEach((e)->{e.insertMysql();});
            cv.getReferences().forEach((e)->{e.insertMysql();});
            cv.getSkills().forEach((e)->{e.insertMysql();});
            cv.getWorkExperience().forEach((ew)->{
                ew.insertMysql();
                ew.getCompanies().forEach((ec)->{                
                    ec.getProjects().forEach((ep)->{ep.insertMysql();});
                    ec.insertMysql();
                    });
                ew.insertMysql();
            });
            cv.insertMysql();
        }catch(AlertException aex){};
        return cv;
    }
}
