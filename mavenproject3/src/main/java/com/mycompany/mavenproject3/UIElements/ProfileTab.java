/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;
import com.mycompany.mavenproject3.Commons.AlertException;
import com.mycompany.mavenproject3.Definitions.PROFILE;
import java.io.IOException;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author hasan
 */
public class ProfileTab extends ScrollableTab<PROFILE,PROFILE,PANETemplate>{
    private TextField Agetxt;
    private ComboBox MaterialStatuscmb;
    private TextArea  ProfiletxtA;
    private TextArea CommenttxtA;
    private Label Agelbl;
    private Label Profilelbl;
    private Label MaterialStatuslbl;
    private ImageView Profileimg;
    private String ImageFilePath;
    private Button ProfileImagbtn;
    private PROFILE PROF;    
    public ProfileTab(String Name,PROFILE prof){
        PROF=(PROFILE) prof.Clone();
        init(Name);
        setDBView("PROFILE");
    }
    @Override
    GridPane ImmutableSection() {
        GridPane GP=new GridPane();        
        Agetxt=new TextField(PROF.getAge());
        ProfiletxtA=new TextArea(PROF.getProfile());ProfiletxtA.setWrapText(true);
        CommenttxtA=new TextArea(PROF.getComment());CommenttxtA.setWrapText(true);
        Agelbl=new Label("Age:");
        MaterialStatuslbl=new Label("Material Status:");
        String[] items={"Single","Married"};
        MaterialStatuscmb=new ComboBox(observableArrayList(items));
        Profilelbl=new Label("Profile:");
        //Profileimg=new ImageView();
        MaterialStatuscmb.setValue(PROF.getMaterialStatus());
        ProfileImagbtn=new Button("Change Photo");        
         if (PROF.getPhoto()==null){
            if(PROF.getImageFilePath()==null){
                Profileimg=new ImageView();                
            }else{
                try{
                    PROF.setPhoto("data:image/png;base64,"+com.mycompany.mavenproject3.Commons.IMAGES.BitMapToString(PROF.getImageFilePath()));
                    Profileimg=new ImageView(PROF.getPhoto());
                    ImageFilePath=PROF.getImageFilePath();
                }catch(IOException ex){
                    //file not exist; select a new icon file path;
                    AlertException.ALERT("Error", ex);
                }                
            }
        }else{
            Profileimg=new ImageView(PROF.getPhoto());
            ImageFilePath=PROF.getImageFilePath();
        }        
        Profileimg.setFitHeight(100);
        Profileimg.setFitWidth(100);        
        ProfileImagbtn.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {               
                
                try{
                    FileChooser fch=new FileChooser();
                    ImageFilePath=fch.showOpenDialog(new Stage()).getAbsolutePath();
                    PROF.setPhoto("data:image/png;base64,"+com.mycompany.mavenproject3.Commons.IMAGES.BitMapToString(ImageFilePath));
                    Profileimg.setImage(new Image(PROF.getPhoto())); 
                }catch (IOException ex){
                    AlertException.ALERT("Error", ex);
                }
            }
        });   
        BorderPane BP=new BorderPane();
        BP.setBottom(ProfileImagbtn);
        BorderPane.setAlignment(ProfileImagbtn, Pos.CENTER);
        BP.setCenter(Profileimg);
        BP.setMinHeight(200);
        BP.setMinWidth(150);
        TitledPane TP=new TitledPane();
        TP.setText("Comment:");
        TP.setExpanded(false);
        TP.setContent(CommenttxtA);
//        List<ColumnConstraints> cols=new ArrayList();
//        for(int i=0;i<10;i++){
//            ColumnConstraints e=new ColumnConstraints();
//            e.setFillWidth(true);
//            e.setPercentWidth(10);
//            e.setHgrow(Priority.ALWAYS);
//            cols.add(e);
//        }
        
        TP.getStyleClass().add("Comment");
        BP.setStyle("-fx-border-color:black");        
        GP.add(Agelbl, 0, 0,1,1);
        GP.add(Agetxt, 1, 0,1,1);
        GP.add(MaterialStatuslbl,2,0,2,1);
        GP.add(MaterialStatuscmb, 4, 0,2,1);
        GP.add(getloadfromDBbtn(),6,0,1,1);
        GP.add(BP, 7, 0,3,4);
        GP.add(Profilelbl, 0, 1,1,1);
        GP.add(ProfiletxtA, 1, 1,6,3);        
        GP.add(TP,0,5,11,1);
        GP.setVgap(10.0);
        GP.setHgap(10.0);
        return GP;
    }
    @Override
    ExpandableSet<PROFILE, PANETemplate> MutableSection() {
        return null;
    }
    
    @Override
    public void setInfo(PROFILE data) {
        PROF=(PROFILE)data.Clone();
        Agetxt.setText(data.getAge());
        ProfiletxtA.setText(data.getProfile());
        if ((data.getPhoto()==null)||(data.getPhoto().isBlank())){
            try{
                PROF.setPhoto("data:image/png;base64,"+com.mycompany.mavenproject3.Commons.IMAGES.BitMapToString(data.getImageFilePath()));
                Profileimg.setImage(new Image(PROF.getPhoto())); 
            }catch(IOException ex){
                AlertException.ALERT("Error", ex);
            }
        }else{Profileimg.setImage(new Image(data.getPhoto()));}
        CommenttxtA.setText(data.getComment());
        MaterialStatuscmb.setValue(data.getMaterialStatus());
    }
    @Override
    public PROFILE getInfo() {
        PROF.setAge(Agetxt.getText());
        PROF.setComment(CommenttxtA.getText());
        try{
        PROF.setMaterialStatus(MaterialStatuscmb.getValue().toString());
        }catch(NullPointerException e){PROF.setMaterialStatus(null);}
        PROF.setProfile(ProfiletxtA.getText());
        PROF.setImageFilePath(ImageFilePath);
        return PROF;
    }  
}
