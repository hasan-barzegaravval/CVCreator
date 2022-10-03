/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;

import com.mycompany.mavenproject3.Definitions.REFERENCE;
import java.util.List;
import javafx.scene.layout.GridPane;

/**
 *
 * @author hasan
 */
public class ReferencesTab extends ScrollableTab<List<REFERENCE>,REFERENCE,ReferencePane> {
    
    private  List<REFERENCE> SKI;
    private  ExpandableSet<REFERENCE, ReferencePane> infoset; 
    public ReferencesTab(String name,List<REFERENCE> data){
        SKI=data;
        init(name);
    }
    @Override
    GridPane ImmutableSection() {
        return null;
    }

    @Override
    ExpandableSet<REFERENCE, ReferencePane> MutableSection() {
        infoset=new ExpandableSet( ReferencePane.class,REFERENCE.class,"");
        infoset.SetInfoList(SKI);
        return infoset;
    }

    @Override
    void setInfo(List<REFERENCE> data) {
        SKI=data;
        infoset.SetInfoList(SKI);
    }

    @Override
    public List<REFERENCE> getInfo() {
        SKI=infoset.getInfoList();
        return SKI;
    }
    
}
