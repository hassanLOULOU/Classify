package com.flatfisher.dialogflowchatbotsample;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
import java.io.*;

/** A class for reading the files/class data */
public class DataFile{
    
    public Pair readDataFile(String file) throws Exception{

    ReadData datainput = new ReadData();
    Iterator it = datainput.readWords(file,false,false,1).iterator();
    String s1, s2;
    HashMap map = new HashMap();
    int total = 0;
    
    while(it.hasNext()){
     s1 = (String)it.next();
     if(it.hasNext()){
      s2 = (String)it.next();
      Set set = (Set)map.get(s2);
      if(set == null){
          HashSet new_set = new HashSet();
          new_set.add(s1);
          map.put(s2,new_set);
      }
      else
       set.add(s1);
     total++;
     }
    }

   return new Pair(map,new Integer(total));
   }
}
