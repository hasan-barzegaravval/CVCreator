/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Commons;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

/**
 *
 * @author hasan
 */
class TextFieldEvent implements EventHandler{
        TitledPane t;
        TextField txt;
        public TextFieldEvent(){
            
        }
        public void setRef(TitledPane t,TextField text){
            this.t=t;
            this.txt=text;
        }
        public TextFieldEvent(TitledPane t,TextField text){
            this.t=t;
            this.txt=text;
        }
        @Override
        public void handle(Event event) {
            t.setText(txt.getText()); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
        
    }
public class HANDLERS {
    public static TextFieldEvent PaneTitleEvents(TitledPane t,TextField text){
        return new TextFieldEvent(t,text);
    }
}
