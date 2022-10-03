/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;

import com.mycompany.mavenproject3.Commons.STRINGS;
import com.mycompany.mavenproject3.Definitions.PUBLICATION;
import java.util.ArrayList;
import java.util.List;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 *
 * @author hasan
 */
public class PublicationPane extends PANETemplate<PUBLICATION> {
        private GridPane GP;        
        private Label Title1lbl,Title2lbl,Authorslbl,Typelbl,Journallbl,Volumelbl,ISlbl,SPlbl,EPlbl,SNlbl,Yearlbl,PBlbl;
        private TextField Title1txt,Title2txt,Journaltxt,Volumetxt,IStxt,SPtxt,EPtxt,SNtxt,Yeartxt,PBtxt;
        private ComboBox Typecmb;
        private TextArea AuthorstxtA;
        private PUBLICATION PUB;
        
    
    public PublicationPane(){
        super("PUBLICATION");
    }
    public PublicationPane(PUBLICATION pub){
        super("PUBLICATION");
        PUB=(PUBLICATION)pub.Clone();
        Title1txt=new TextField(PUB.getTitle1());
        Title2txt=new TextField(PUB.getTitle2());
        String[] items={"JOUR","CONF","BOOK","CHAP"};
        Typecmb=new ComboBox(observableArrayList(items));
        Typecmb.setValue(pub.getType());
        Journaltxt=new TextField(PUB.getJournal());
        Volumetxt=new TextField(PUB.getVolume());
        IStxt=new TextField(PUB.getIS());
        SPtxt=new TextField(PUB.getSP());
        EPtxt=new TextField(PUB.getEP());
        SNtxt=new TextField(PUB.getSN());
        Yeartxt=new TextField(PUB.getYear());
        PBtxt=new TextField(PUB.getPB());
        AuthorstxtA=new TextArea(STRINGS.ListToString(pub.getAuthors(), '\n'));
        Title1lbl=new Label(" Publication Title:");
        Title2lbl=new Label("Collection Title:");
        Authorslbl=new Label("Authors:");
        Typelbl=new Label("Type of Publicatoin:");
        Journallbl=new Label("Source Title (Journal):");
        Volumelbl=new Label("Vol:");
        ISlbl=new Label("IS:");
        SPlbl=new Label("From Page:");
        EPlbl=new Label("To Page");
        SNlbl=new Label("SN:");
        Yearlbl=new Label("Year:");
        PBlbl=new Label("Publisher:");             
        GP=new GridPane();
        List<ColumnConstraints> column = new ArrayList();
        column.forEach((e)->e.setPercentWidth(100/6));
        GP.getColumnConstraints().addAll(column);
        GP.add(Title1lbl,0,0,1,1);
        GP.add(Title1txt,1,0,5,1);
        GP.add(Title2lbl,0,1,1,1);
        GP.add(Title2txt,1,1,5,1);
        GP.add(Typelbl, 0, 2,1,1);
        GP.add(Typecmb,1,2,1,1);
        GP.add(Journallbl, 0, 3,1,1);
        GP.add(Journaltxt,1,3,5,1);
        GP.add(Authorslbl,0,4,1,1);
        GP.add(AuthorstxtA,1,4,5,1);
        GP.add(SPlbl, 0, 5,1,1);
        GP.add(SPtxt, 1, 5,1,1);
        GP.add(EPlbl, 2, 5,1,1);
        GP.add(EPtxt, 3, 5,1,1);
        GP.add(Volumelbl,4,5,1,1);
        GP.add(Volumetxt,5,5,1,1);
        GP.add(ISlbl,0,6,1,1);
        GP.add(IStxt,1,6,1,1);        
        GP.add(SNlbl, 2, 6,1,1);
        GP.add(SNtxt, 3, 6,1,1);
        GP.add(Yearlbl, 4, 6,1,1);
        GP.add(Yeartxt, 5, 6,1,1);
        GP.add(PBlbl, 0, 7,1,1);
        GP.add(PBtxt, 1, 7,5,1);
        GP.setVgap(10.0);
        GP.setHgap(10.0);
        this.setText(textchange(PUB.getYear()+" "+PUB.getTitle1()));
        Title1txt.textProperty().addListener((observable,oldvalue,newvalue)->this.setText(textchange(Yeartxt.getText()+" "+newvalue)));
        Yeartxt.textProperty().addListener((observable,oldvalue,newvalue)->this.setText(textchange(newvalue+" "+Title1txt.getText())));
        this.setContent(GP);        
    }     
        
        
    @Override
    PUBLICATION getInfo() {
        PUB.setTitle1(Title1txt.getText());
        PUB.setTitle2(Title2txt.getText());
        PUB.setType(Typecmb.getValue().toString());
        PUB.setJournal(Journaltxt.getText());
        PUB.setVolume(Volumetxt.getText());
        PUB.setIS(IStxt.getText());
        PUB.setSP(SPtxt.getText());
        PUB.setEP(EPtxt.getText());
        PUB.setSN(SNtxt.getText());
        PUB.setYear(Yeartxt.getText());
        PUB.setPB(PBtxt.getText());
        PUB.setAuthors(STRINGS.StringToList(AuthorstxtA.getText(),"\n"));
        return PUB;
    }

    @Override
    public void DBInsert() {
        getInfo();
        PUB.insertMysql();
    }

    @Override
    void setInfo(PUBLICATION D) {
        PUB=(PUBLICATION)D.Clone();
        Title1txt.setText(D.getTitle1());
        Title2txt.setText(D.getTitle2());
        Typecmb.setValue(D.getType());
        Journaltxt.setText(D.getJournal());
        Volumetxt.setText(D.getVolume());
        IStxt.setText(D.getIS());
        SPtxt.setText(D.getSP());
        EPtxt.setText(D.getSP());
        SNtxt.setText(D.getSN());
        Yeartxt.setText(D.getYear());
        PBtxt.setText(D.getPB());
        AuthorstxtA.setText(STRINGS.ListToString(D.getAuthors(),'\n'));        
    }

    
}
