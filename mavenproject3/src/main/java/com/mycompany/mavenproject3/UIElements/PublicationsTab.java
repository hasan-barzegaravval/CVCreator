/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;

import com.mycompany.mavenproject3.Commons.AlertException;
import com.mycompany.mavenproject3.Commons.FILES;
import com.mycompany.mavenproject3.Definitions.PUBLICATION;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author hasan
 */
public class PublicationsTab extends ScrollableTab<List<PUBLICATION>,PUBLICATION,PublicationPane> {
    private TextField FileNametxt;
    private Button FileLoadbtn;
    private  List<PUBLICATION> PUB;
    private  ExpandableSet<PUBLICATION, PublicationPane> infoset;   
    public PublicationsTab(String name,List<PUBLICATION> data){
        PUB=data;
        init(name);
    }
    @Override
    GridPane ImmutableSection() {
        GridPane GP=new GridPane();
        FileNametxt=new TextField();
        FileLoadbtn=new Button("Load Publications");
        FileLoadbtn.setOnAction((e)->{
            FileChooser fch=new FileChooser();
            fch.setSelectedExtensionFilter(new ExtensionFilter("Publications(*.ris)","*.ris"));
            try {
                String fpath=fch.showOpenDialog(new Stage()).getAbsolutePath();
                PUB=FILES.RISREADER(fpath);
                FileNametxt.setText(fpath);
                setInfo(PUB);
            } catch (IOException ex) {
                Logger.getLogger(PublicationsTab.class.getName()).log(Level.SEVERE, null, ex);
                AlertException.ALERT("Error", ex);
            }
        });
        ColumnConstraints c=new ColumnConstraints();
        c.setPercentWidth(20);
        GP.getColumnConstraints().addAll(c,c,c,c,c);
        GP.add(new Label("Publication File(*.ris):"), 0, 0,1,1);
        GP.add(FileNametxt,1,0,3,1);
        GP.add(FileLoadbtn,4,0,1,1);
        GP.setVgap(10);
        GP.setHgap(10);
        return GP;  
    }

    @Override
    ExpandableSet<PUBLICATION, PublicationPane> MutableSection() {
        infoset=new ExpandableSet(PublicationPane.class,PUBLICATION.class,"Publications");
        infoset.SetInfoList(PUB);
        return infoset;
    }

    @Override
    public void setInfo(List<PUBLICATION> data) {
        infoset.SetInfoList(data);
    }

    @Override
    public List<PUBLICATION> getInfo() {
        return infoset.getInfoList();
    }
    
}
