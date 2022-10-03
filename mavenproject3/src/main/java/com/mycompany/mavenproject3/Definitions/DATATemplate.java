/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Definitions;
import com.mycompany.mavenproject3.Commons.AlertException;
import com.mycompany.mavenproject3.Commons.FINALS;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**The DATA superclass. All Data classes in the Definitions Package
 * should implement this class. The class is abstract as it contains abstract
 * methods:
 * <ul>
 * <li>insertMysql()
 * <li>updateMysql()
 * </ul>
 * This class implements Seializable, FINALS and Cloneable. The serializable is
 * necessary for jakson json operatin.Cloneable is necessary for clone method,
 * and the FINALS is an interface for sharing data between all classes. 
 * @author  Hasan.Barzegaravval
 * @version 1.0
 * @since   1.0
 * 
 * 
 */

public abstract class DATATemplate implements Serializable,FINALS,Cloneable {
     private int ID=-1;
     /**
      * setID is the method to set the corresponding mysql table id as reference.
      * It is final and public. 
      * @param id this is the id of data stored in the mysql tables.
      */
     public final void setID(int id){
         ID=id;
     }
     /**
      * getID returns the data id corresponding the data entry on the mysql table.
      * by default id is -1. if id is -1 the function tries to insert the data to 
      * mysql table and get the id by calling the object insertMysql implementation.
      * @return  the integer id of the data.
      */
     public int getID(){         
        if(ID<0){
            if (FINALS.DBC.getDBname()!=null){
                try{
                    insertMysql();
                }catch(Exception ex){
                    AlertException.ALERT("Error", ex);
                }
            }
        }
        return ID;
     }
     /**
      * In circumstances that only id value is required, regardless of the -1 value check,
      * this function is called. it is useful for no sql operations including pure
      * JSON file operations. (When CV is created, saved or loaded only by Json Files)
      * @return ID integer id of data.
      */
     @JsonIgnore
     public int getIDnoinsert(){
         return ID;
     }
     /**
      * This method is an abstract method and any subclass must implement the proper version.
      * This function insert the data to the corresponding table on the database.
      */
     abstract void insertMysql();
      /**
      * This method is an abstract method and any subclass must implement the proper version.
      * This function update the data to the corresponding table on the database.
      */
     abstract String updateMysql();
      /**
      * This is an override version of the cloneable interface. It is final and public
      * it make a Clone form the object itself. it is a shallow copy of the object in 
      * new address.
      * @return a cloned object of the instance. Casting is required. 
      */
     public final Object Clone(){
         try{
         return super.clone();
         }catch(CloneNotSupportedException ex){
             AlertException.ALERT("Error", ex);
             return null;
         }
         
     }
     
     
}
