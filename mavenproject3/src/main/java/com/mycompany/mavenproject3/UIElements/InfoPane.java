/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;

import com.mycompany.mavenproject3.Commons.AlertException;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.mycompany.mavenproject3.Definitions.INFO;
import java.io.IOException;

/**
 *
 * @author hasan
 */
public class InfoPane extends PANETemplate<INFO> {
        private GridPane GP;        
        private Label linklbl;
        private Label namelbl;
        private Button iconbtn;
        private Label valuelbl;
        private TextField linktxt;
        private TextField nametxt;
        private TextField valuetxt;
        private String icon;
        private String imagefilepath;
        private INFO INF;
        private ImageView iconimg=new ImageView();
    public InfoPane(INFO info){        
        super("INFO");
        INF=(INFO)info.Clone();
        GP=new GridPane(); 
        linklbl=new Label("Link:");
        namelbl=new Label("Name:");
        iconbtn=new Button("icon");
        valuelbl=new Label("Value(Text):");
        linktxt=new TextField(INF.getLink());
        nametxt=new TextField(INF.getName());
        valuetxt=new TextField(INF.getValue());
        if (INF.geticon()==null){
            if(INF.getImageFilePath()==null){
                iconimg=new ImageView();                
            }else{
                try{
                    icon="data:image/png;base64,"+com.mycompany.mavenproject3.Commons.IMAGES.BitMapToString(info.getImageFilePath());
                    iconimg=new ImageView(icon);
                    imagefilepath=INF.getImageFilePath();
                    INF.seticon(icon);
                }catch(IOException ex){
                    //file not exist; select a new icon file path;
                    AlertException.ALERT("Error", ex);
                }                
            }
        }else{
            icon=INF.geticon();
            iconimg=new ImageView(info.geticon());
            imagefilepath=info.getImageFilePath();
        }
        //iconimg=(info.icon==null)?new ImageView():new ImageView(info.icon);
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(30);
        GP.getColumnConstraints().add(column);
        iconbtn.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                
                try{
                    FileChooser fch=new FileChooser(); 
                    imagefilepath=fch.showOpenDialog(new Stage()).getAbsolutePath();
                    icon="data:image/png;base64,"+com.mycompany.mavenproject3.Commons.IMAGES.BitMapToString(imagefilepath);
                    iconimg.setImage(new Image(icon)); 
                }catch (Exception ex){
                    AlertException.ALERT("Error", ex);
                }
            }
        });
        INF.seticon(icon);
        nametxt.textProperty().addListener((observable,oldvalue,newvalue)->this.setText(textchange(newvalue)));
        iconimg.setFitWidth(100);
        iconimg.setFitHeight(100);
        GP.add(namelbl, 0, 0, 1, 1);
        GP.add(linklbl, 0, 1, 1, 1);
        GP.add(valuelbl, 0, 2, 1, 1);
        GP.add(iconbtn, 3, 0, 1, 1);
        GP.add(nametxt,1,0, 2, 1);
        GP.add(linktxt,1,1, 2, 1);
        GP.add(valuetxt,1,2, 2, 1);
        GP.add(iconimg,5,0, 3, 3);        
        GP.setHgap(10);
        GP.setVgap(10);
        this.setText(textchange(info.getName()));
        this.setContent(GP);
    }
        @Override
    public INFO getInfo(){        
        INF.setLink(linktxt.getText());
        INF.setName(nametxt.getText());
        INF.setValue(valuetxt.getText());
        INF.setImageFilePath(imagefilepath);
        INF.seticon(icon);
        return INF;
    }

    @Override
    void DBInsert() {
        getInfo();
        INF.insertMysql();        
    }

    @Override
    void setInfo(INFO D) {
        INF=(INFO)D.Clone();
        linktxt.setText(D.getLink());
        nametxt.setText(D.getName());
        valuetxt.setText(D.getValue());
        INF.seticon(D.geticon());
        if (INF.geticon()==null){
            try{                  
                    icon="data:image/png;base64,"+com.mycompany.mavenproject3.Commons.IMAGES.BitMapToString(INF.getImageFilePath());
                    iconimg.setImage(new Image(icon));
                    INF.seticon(icon);
                }catch (IOException ex){
                    AlertException.ALERT("Error", ex);
                }
        }     
    }
    
        
}
