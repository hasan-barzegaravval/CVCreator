/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Commons;
import com.mycompany.mavenproject3.Definitions.PUBLICATION;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * This class contains methods for file operations.
 * 
 * @author Hasan Barzegaravval
 * @version 1.0
 * @since   1.0
 */
public class FILES {
    /**
     * This method is the RIS file reader. 
     * The RIS file is a citation format used widely.
     * This method reads a RIS file and convert the file to a list of Publication objects
     * @param fname the filename (string absolute address)of the RIS file.
     * @return list of publications. a list containing instances of PUBLICATION.
     * @throws IOException on file processing (opening-reading) failure.
     */
    public static List<PUBLICATION> RISREADER(String fname)throws IOException{
        List<PUBLICATION> pubs=new ArrayList();
        String file=Files.readString(Paths.get(fname));
        String[] Entries=file.split("ER  -");
        for(String A:Entries){
            if (A.isBlank()||A.isEmpty()) continue;
            String[] Terms=A.trim().split("\n");
            PUBLICATION pb=new PUBLICATION();
            pb.setAuthors(new ArrayList());
            for(String B:Terms){
                if (B.isBlank()||B.isEmpty()) continue;
                int i=B.indexOf('-');
                String name=B.substring(0, i).trim();
                String value=B.substring(i+1).trim();
                switch (name){
                    case "TY":
                        pb.setType(value);
                        break;
                    case "T1":
                        pb.setTitle1(value);
                        break;
                    case "A1":
                        pb.getAuthors().add(value);
                        break;
                    case "JO":
                        pb.setJournal(value);
                        break;
                    case "VL":
                        pb.setVolume(value);
                        break;
                    case "SP":
                        pb.setSP(value);
                        break;
                    case "EP":
                        pb.setEP(value);
                        break;
                    case "SN":
                        pb.setSN(value);
                        break;
                    case "Y1":
                        pb.setYear(value);
                        break;
                    case "PB":
                        pb.setPB(value);
                        break;
                    case "T2":
                        pb.setTitle2(value);
                        break;
                    default:
                        break;
                }
            }
            pubs.add(pb);
        }
        return pubs;
    }
}
