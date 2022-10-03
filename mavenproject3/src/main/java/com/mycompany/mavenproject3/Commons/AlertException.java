/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Commons;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * AlertException is a subclass of java Exception modified to provide visual 
 * alert after an exception occurs. It is possible to create an instance of the
 * class or use the static methods provided. The static methods are to only
 * provide an alert window while the instance works as an exception object. 
 * There are three overloaded forms for this class constructor:
 * <ul>
 * <li>with type of alert and exception</li>
 * <li>with type of alert, message and title of to be shown to the user</li>
 * <li>with type of alert,message,title and exception</li>
 * </ul>
 * Type matches the values of javafx.scene.control.Alert.AlertType. three types are 
 * accepted here as:
 * <ul>
 * <li>Error</li>
 * <li>Warning</li>
 * <li>Information</li>
 * </ul>
 * in the current form of the code, all the types which are not Error and warning
 * are treated as information.
 * 
 * <p> It is possible to append the logger to the following exception. 
 * the logger might be passed through inputs or through reflections or exception
 * structure. in addition, it might be useful to add stack trace information.
 * @author  Hasan.Barzegaravval
 * @version 1.0
 * @since   1.0
 * @see     Exception
 * @see     Alert
 */
public class AlertException extends Exception {
    Alert ALERT;
             /**This constructor accepts 4 parameters:             * 
             * @param Message   This is the Message that is shown on alert. 
             * @param Header    This is the Header of the alert window.
             * @param Type      This is the type of alert window.
             * @param ex        This is the exception occur.
             * In this form it keeps the exception trace info and make alert 
             * window. 
             */
        public AlertException(String Message,String Header,String Type,Exception ex){
            
            super(ex);            
            setAlert(Message,Header,Type);
        }
        /**This constructor accepts 2 parameters:             * 
             * @param Type      This is the type of alert window.
             * @param ex        This is the exception occur.
             * In this form it keeps the exception trace info and make alert 
             * window.Alert window properties are extracted from the exception.
             */
        public AlertException(String Type,Exception ex){
             
            super(ex);            
            ALERT=setAlert(ex.getMessage(),ex.getClass().getSimpleName(),Type);
            ALERT.show();
        }
        /**This constructor accepts 3 parameters:             * 
             * @param Message   This is the Message that is shown on alert. 
             * @param Header    This is the Header of the alert window.
             * @param Type      This is the type of alert window.
             * In this form it creates an exception from the message and shows
             * an alert window. 
             */
        public AlertException(String Message,String Header,String Type){
             
            super(Message);
            ALERT=setAlert(Message,Header,Type);
            ALERT.show();
        }
        /**This static method accepts 3 parameters:             * 
             * @param Message   This is the Message that is shown on alert. 
             * @param Header    This is the Header of the alert window.
             * @param Type      This is the type of alert window.
             * In this form it keeps the exception trace info and make alert 
             * window. 
             */
        private static Alert setAlert(String Message,String Header,String Type){
            
            Alert alert;
            switch(Type){
            case "Error":                    
                alert=new Alert(AlertType.ERROR);break;
            case "Warning":
                alert=new Alert(AlertType.WARNING);break;
            default:
                alert=new Alert(AlertType.INFORMATION);break;
            }
            alert.setTitle(Type);
            alert.setContentText(Message);
            alert.setHeaderText(Header);            
            return alert;
        }
         /**This static method accepts 3 parameters:             * 
             * @param Message   This is the Message that is shown on alert. 
             * @param Header    This is the Header of the alert window.
             * @param Type      This is the type of alert window.
             * This static method only shows an alert window and does not create
             * any instance of exception. 
             */
        public static void ALERT(String Message,String Header,String Type){
           
            setAlert(Message,Header,Type).show();
            
        }
        /**This static method accepts 3 parameters:             * 
             * @param ex        This is the exception that occurs in outer block 
             * @param Type      This is the type of alert window.
             * The information on the alert window are extracted from the 
             * input exception parameter. 
             */
        public static void ALERT(String Type,Exception ex){
            
             ALERT(ex.getMessage(),ex.getClass().getSimpleName(),Type);
             Logger.getLogger("Alert").log(Level.SEVERE,ex.toString(),ex);
        }
        
}
