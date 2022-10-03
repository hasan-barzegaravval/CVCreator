/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;

import com.mycompany.mavenproject3.Definitions.EDUCATION;
import java.util.List;
import javafx.scene.layout.GridPane;

/**
 *
 * @author hasan
 */
public class EducationTab extends ScrollableTab<List<EDUCATION>,EDUCATION,EducationPane> {    
    
    private  List<EDUCATION> EDU;
    private  ExpandableSet<EDUCATION, EducationPane> infoset;   
    public EducationTab(String name,List<EDUCATION> data){
        EDU=data;
        init(name);
    }
    @Override
    GridPane ImmutableSection() {
        return null;
    }
    @Override
    ExpandableSet<EDUCATION, EducationPane> MutableSection() {
        infoset=new ExpandableSet( EducationPane.class,EDUCATION.class,"");
        infoset.SetInfoList(EDU);
        return infoset;
    }
    @Override
    void setInfo(List<EDUCATION> data) {
        EDU=data;
        infoset.SetInfoList(EDU);
    }
    @Override
    public List<EDUCATION> getInfo() {
        EDU=infoset.getInfoList();
        return EDU;
    }
    
}
