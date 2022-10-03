/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;
import com.mycompany.mavenproject3.Definitions.LANGUAGE;
import java.util.List;
import javafx.scene.layout.GridPane;
/**
 *
 * @author hasan
 */
public class LanguagesTab extends ScrollableTab<List<LANGUAGE>,LANGUAGE,LanguagePane> {
    private  List<LANGUAGE> SKI;
    private  ExpandableSet<LANGUAGE, LanguagePane> infoset; 
    public LanguagesTab(String name,List<LANGUAGE> data){
        SKI=data;
        init(name);
    }
    @Override
    GridPane ImmutableSection() {
        return null;
    }

    @Override
    ExpandableSet<LANGUAGE, LanguagePane> MutableSection() {
        infoset=new ExpandableSet( LanguagePane.class,LANGUAGE.class,"");
        infoset.SetInfoList(SKI);
        return infoset;
    }

    @Override
    void setInfo(List<LANGUAGE> data) {
        SKI=data;
        infoset.SetInfoList(SKI);
    }

    @Override
    public List<LANGUAGE> getInfo() {
        SKI=infoset.getInfoList();
        return SKI;
    }
    


}
