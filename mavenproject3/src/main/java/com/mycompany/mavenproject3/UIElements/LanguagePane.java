/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;

import com.mycompany.mavenproject3.Commons.FINALS;
import com.mycompany.mavenproject3.Definitions.LANGUAGE;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 *
 * @author hasan
 */
public class LanguagePane extends PANETemplate<LANGUAGE> {
    private TextField Languagetxt;
    private TextField Fluencytxt;
    private TextField Scoretxt;
    private TextField Testtxt;
    private TextArea CommenttxtA;
    private Label   Languagelbl;
    private Label   Fluencylbl;
    private Label   Testlbl;
    private Label   Scorelbl;
    private Label   Commentlbl;
    private LANGUAGE LANG;
    private GridPane GP;
    public LanguagePane(){
                super("LANGUAGE");

    }
    public LanguagePane(LANGUAGE lang){
        super("LANGUAGE");
        Languagetxt=new TextField(lang.getLanguage());
        Fluencytxt=new TextField(lang.getFluency());
        Scoretxt=new TextField(lang.getScore());
        Testtxt=new TextField(lang.getTest());
        CommenttxtA=new TextArea(lang.getComment());CommenttxtA.setWrapText(true);
        Languagelbl=new Label("Language:");
        Fluencylbl=new Label("Fluency:");
        Testlbl=new Label("Test:");
        Scorelbl=new Label("Score:");
        Commentlbl=new Label("Comment:");
        GP=new GridPane();
        ColumnConstraints ct=new ColumnConstraints();
        ct.setPercentWidth(25);
        //ct.setFillWidth(true);
        GP.getColumnConstraints().addAll(ct,ct,ct,ct);
        GP.add(Languagelbl, 0, 0,1,1);
        GP.add(Languagetxt,1,0,3,1);
        GP.add(Fluencylbl, 0, 1,1,1);
        GP.add(Fluencytxt, 1, 1,3,1);
        GP.add(Testlbl, 0, 2,1,1);
        GP.add(Testtxt,1,2,1,1);
        GP.add(Scorelbl,2,2,1,1);
        GP.add(Scoretxt,3,2,1,1);
        GP.add(Commentlbl,0,3,1,1);
        GP.add(CommenttxtA,1,3,3,1);
        GP.setVgap(10.0);
        GP.setHgap(10.0);
        Languagetxt.textProperty().addListener((observable,oldvalue,newvalue)->setText(textchange(newvalue)));
        this.setContent(GP);
        LANG=(LANGUAGE)lang.Clone();
        setText(textchange(LANG.getLanguage()));
    }
    @Override
    public LANGUAGE getInfo() {
        LANG.setComment(CommenttxtA.getText());
        LANG.setFluency(Fluencytxt.getText());
        LANG.setLanguage(Languagetxt.getText());
        LANG.setScore(Scoretxt.getText());
        LANG.setTest(Testtxt.getText());
        return LANG;
    } 

    @Override
    void DBInsert() {
        getInfo();
        LANG.insertMysql();
    }

    @Override
    void setInfo(LANGUAGE D) {
        LANG=(LANGUAGE)D.Clone();
        CommenttxtA.setText(D.getComment());
        Fluencytxt.setText(D.getFluency());
        Languagetxt.setText(D.getLanguage());
        Scoretxt.setText(D.getScore());
        Testtxt.setText(D.getTest());
    }


    
}
