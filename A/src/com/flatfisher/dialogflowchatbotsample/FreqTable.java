package com.flatfisher.dialogflowchatbotsample;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.*;

/** A class containing a frequency table */
public class FreqTable implements Serializable{
    
    HashMap map;
    int total;
    
    public FreqTable(){
    map = new HashMap();
    total = 0;
    }
    
    public ProbTable createProbTable(int vocSize){
    return new ProbTable(map,total,vocSize);
    }
    
    /** insert an element into the frequency table */
    public void insert(String s){
        if(map.get(s) == null)
        map.put(s,new Integer(1));
        else{
        Integer i = (Integer)map.get(s);
        int j = i.intValue();
        map.put(s,new Integer(j+1));
        }
        total++;
    }
    
    public Iterator iterator(){
     return map.entrySet().iterator();   
    }
}
